package cc.custom.rules;

import static org.junit.Assert.assertTrue;

import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.junit.Before;
import org.junit.Test;

import cc.helpers.ASTNodeInstantiator;
import cc.rules.api.ruleargs.VariableDeclarationArgument;

public class ConstantsAreAllUppercaseTest {

	private static final String GOOD_VARIABLE_NAME = "FOO";
	private static final String BAD_VARIABLE_NAME = "foo";
	private static final String GOOD_CONSTANT = "static final Object " + GOOD_VARIABLE_NAME + " = new Object();";
	private static final String BAD_CONSTANT = "static final Object " + BAD_VARIABLE_NAME + " = new Object();";

	private ConstantsAreAllUppercase rule;
	private VariableDeclarationArgument arg;

	@Before
	public void setup() {
		rule = new ConstantsAreAllUppercase();
		VariableDeclarationFragment frag = (VariableDeclarationFragment) ASTNodeInstantiator
				.instantiate(VariableDeclarationFragment.class);
		arg = new VariableDeclarationArgument(frag);
	}

	@Test
	public void testRunSuccess() throws JavaModelException {
		arg.setVariableSourceCode(GOOD_CONSTANT);
		arg.setVariableName(GOOD_VARIABLE_NAME);
		boolean success = rule.run(arg).passed();
		assertTrue(success);
	}

	@Test
	public void testRunFailure() throws JavaModelException {
		arg.setVariableSourceCode(BAD_CONSTANT);
		arg.setVariableName(BAD_VARIABLE_NAME);
		boolean success = rule.run(arg).passed();
		assertTrue(!success);
	}

}
