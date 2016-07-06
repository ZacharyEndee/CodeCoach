package cc.custom.rules;

import static org.junit.Assert.assertTrue;

import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ForStatement;
import org.junit.Before;
import org.junit.Test;

import cc.helpers.ASTNodeInstantiator;
import cc.rules.api.ruleargs.ForStatmentArgument;

public class MisplacedSemiColonOnForTest {

	public static final String BAD_CODE = "for(, , );";
	public static final String GOOD_CODE = "for(, , ){}";

	private MisplacedSemiColonOnFor rule;
	private ForStatmentArgument arg;

	@Before
	public void setup() {
		rule = new MisplacedSemiColonOnFor();
		ForStatement fs = (ForStatement) ASTNodeInstantiator.instantiate(ForStatement.class);
		arg = new ForStatmentArgument(fs);
	}

	@Test
	public void testRunSuccess() throws JavaModelException {
		arg.setForLoopSourceCode(GOOD_CODE);
		assertTrue(rule.run(arg).passed());
	}

	@Test
	public void testRunFailure() throws JavaModelException {
		arg.setForLoopSourceCode(BAD_CODE);
		assertTrue(!rule.run(arg).passed());
	}

}
