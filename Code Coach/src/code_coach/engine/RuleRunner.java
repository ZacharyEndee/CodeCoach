package code_coach.engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTNode;

import cc.custom.rules._XmlRuleLoader;
import cc.rules.api.IRule;
import cc.rules.api.RuleResult;
import cc.rules.api.RuleType;
import cc.rules.api.ruleargs.RuleArgument;

public class RuleRunner {

	public static final String FILE_CUSTOM_RULES = "CustomRules.xml";

	private static RuleRunner instance;

	public static RuleRunner getInstance() {
		if (instance == null) {
			instance = new RuleRunner();
		}
		return instance;
	}

	private Map<String, List<IRule>> rules = new HashMap<String, List<IRule>>();
	private List<RuleModel> ruleModels = new ArrayList<RuleModel>();

	private String packageName;

	private RuleRunner() {
		loadRules();
	}

	private void loadRules() {
		try {
			loadRulesFromXml();
			// loadCustomRules();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private InputStream getLocalRulesAsStream() throws FileNotFoundException {
		return new FileInputStream(FILE_CUSTOM_RULES);
	}

	private InputStream getRulesFromResources() {
		return _XmlRuleLoader.class.getResourceAsStream("Rules.xml");
	}

	private void copyResourceRulesLocally() throws IOException {
		Scanner sc = new Scanner(getRulesFromResources());
		StringBuilder sb = new StringBuilder();
		String newLine = System.getProperty("line.separator");
		while (sc.hasNextLine()) {
			sb.append(sc.nextLine()).append(newLine);
		}
		sc.close();
		Files.write(Paths.get(FILE_CUSTOM_RULES), sb.toString().getBytes());
	}

	public void reloadRules() {
		rules = new HashMap<String, List<IRule>>();
		loadRules();
	}

	private void loadRulesFromXml() throws XMLStreamException, FactoryConfigurationError, IOException {
		/*
		 * if (!Files.exists(Paths.get(FILE_CUSTOM_RULES))) {
		 * copyResourceRulesLocally(); }
		 */
		XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(getRulesFromResources());
		while (reader.hasNext()) {
			int event = reader.next();
			switch (event) {
			case XMLStreamConstants.COMMENT:
				break;
			case XMLStreamConstants.START_ELEMENT:
				if ("Rule".equalsIgnoreCase(reader.getLocalName())) {
					RuleModel model = new RuleModel();
					String className = reader.getAttributeValue(0);
					model.setRuleName(className);
					String typeAttr = reader.getAttributeValue(1);
					model.setRuleType(typeAttr);
					model.setRulePackage(packageName);
					ruleModels.add(model);
				} else if ("Package".equalsIgnoreCase(reader.getLocalName())) {
					packageName = reader.getAttributeValue(0);
				}
				break;
			}
		}
		reader.close();
		try {
			loadRuleClasses(ruleModels);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void loadRuleClasses(List<RuleModel> ruleModels)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		for (RuleModel ruleModel : ruleModels) {
			IRule r = (IRule) Class.forName(ruleModel.getRulePackage() + "." + ruleModel.getRuleName()).newInstance();
			List<IRule> typeList = rules.get(ruleModel.getRuleType());
			if (typeList == null) {
				typeList = new ArrayList<IRule>();
				typeList.add(r);
				rules.put(ruleModel.getRuleType(), typeList);
			} else {
				typeList.add(r);
			}
			System.out.println("Loaded rule " + r.getName());
		}
	}

	public List<RuleResult> runRulesByType(RuleType type, RuleArgument<? extends ASTNode> node)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, JavaModelException {
		return runRuleSet(rules.get(type.toString()), node);
	}

	private List<RuleResult> runRuleSet(List<IRule> ruleSet, Object ruleArg)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, JavaModelException {
		List<RuleResult> results = new ArrayList<RuleResult>();
		for (IRule rule : ruleSet) {
			results.add(rule.apply(ruleArg));
		}
		return results;
	}
}