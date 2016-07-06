package cc.custom.rules;

import org.eclipse.jdt.core.JavaModelException;

import cc.rules.api.RuleResult;
import cc.rules.api.ruleargs.MethodDeclarationArgument;
import cc.rules.api.ruletypes.MethodDeclarationRule;

public class MethodLength extends MethodDeclarationRule {

	private static final int MAX_LENGTH = 50;

	@Override
	public String getHint() {
		return "Methods should not be longer than " + 50 + " lines.";
	}

	@Override
	public String getName() {
		return "Method Length";
	}

	@Override
	public RuleResult run(MethodDeclarationArgument o) throws JavaModelException {
		RuleResult r = new RuleResult(this);
		r.setStartPosition(o.getRawArg().getStartPosition());
		String source = o.getMethodBodySourceCode();
		String[] lines = source.split("\n");
		r.setPassed(!(lines.length > MAX_LENGTH));
		return r;
	}
}
