package cc.custom.rules;

import static org.junit.Assert.assertTrue;

import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.IfStatement;
import org.junit.Before;
import org.junit.Test;

import cc.helpers.ASTNodeInstantiator;
import cc.rules.api.ruleargs.IfStatementArgument;

public class MisplacedSemiColonOnIfTest {

	public static final String BAD_CODE = "if(condition);";
	public static final String GOOD_CODE = "if(condition){}";

	private MisplacedSemiColonOnIf rule;
	private IfStatementArgument arg;

	@Before
	public void setup() {
		rule = new MisplacedSemiColonOnIf();
		IfStatement fs = (IfStatement) ASTNodeInstantiator.instantiate(IfStatement.class);
		arg = new IfStatementArgument(fs);
	}

	@Test
	public void testRunSuccess() throws JavaModelException {
		arg.setIfStmtSourceCode(GOOD_CODE);
		assertTrue(rule.run(arg).passed());
	}

	@Test
	public void testRunFailure() throws JavaModelException {
		arg.setIfStmtSourceCode(BAD_CODE);
		assertTrue(!rule.run(arg).passed());
	}

}
