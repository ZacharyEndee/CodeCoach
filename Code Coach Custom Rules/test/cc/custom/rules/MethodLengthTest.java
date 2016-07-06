package cc.custom.rules;

import static org.junit.Assert.assertTrue;

import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.junit.Before;
import org.junit.Test;

import cc.helpers.ASTNodeInstantiator;
import cc.rules.api.ruleargs.MethodDeclarationArgument;

public class MethodLengthTest {

	private final static String SOURCE_TOO_LONG = "public void foo(){\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n}";
	private final static String SOURCE_OK = "public void foo(){}";

	private MethodLength rule;
	private MethodDeclarationArgument arg;

	@Before
	public void setup() {
		rule = new MethodLength();
		MethodDeclaration md = (MethodDeclaration) ASTNodeInstantiator.instantiate(MethodDeclaration.class);
		arg = new MethodDeclarationArgument(md);
	}

	@Test
	public void testRunSuccess() throws JavaModelException {
		arg.setMethodBody(SOURCE_OK);
		assertTrue(rule.run(arg).passed());
	}

	@Test
	public void testRunFailure() throws JavaModelException {
		arg.setMethodBody(SOURCE_TOO_LONG);
		assertTrue(!rule.run(arg).passed());
	}

}
