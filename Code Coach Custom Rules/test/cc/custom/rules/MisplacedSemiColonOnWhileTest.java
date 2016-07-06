package cc.custom.rules;

import static org.junit.Assert.assertTrue;

import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.junit.Before;
import org.junit.Test;

import cc.helpers.ASTNodeInstantiator;
import cc.rules.api.ruleargs.WhileStatementArgument;

public class MisplacedSemiColonOnWhileTest {

	public static final String BAD_CODE = "while(condition);";
	public static final String GOOD_CODE = "while(condition){}";

	private MisplacedSemiColonOnWhile rule;
	private WhileStatementArgument arg;

	@Before
	public void setup() {
		rule = new MisplacedSemiColonOnWhile();
		WhileStatement fs = (WhileStatement) ASTNodeInstantiator.instantiate(WhileStatement.class);
		arg = new WhileStatementArgument(fs);
	}

	@Test
	public void testRunSuccess() throws JavaModelException {
		arg.setWhileLoopSourceCode(GOOD_CODE);
		assertTrue(rule.run(arg).passed());
	}

	@Test
	public void testRunFailure() throws JavaModelException {
		arg.setWhileLoopSourceCode(BAD_CODE);
		assertTrue(!rule.run(arg).passed());
	}

}
