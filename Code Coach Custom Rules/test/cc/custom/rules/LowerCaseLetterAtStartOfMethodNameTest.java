package cc.custom.rules;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.junit.Before;
import org.junit.Test;

import cc.helpers.ASTNodeInstantiator;
import cc.rules.api.ruleargs.MethodDeclarationArgument;

public class LowerCaseLetterAtStartOfMethodNameTest {

	private static final String GOOD_METHOD_NAME = "foo";
	private static final String BAD_METHOD_NAME = "Bar";

	private LowerCaseLetterAtStartOfMethodName rule;
	private MethodDeclarationArgument arg;

	@Before
	public void setup() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		rule = new LowerCaseLetterAtStartOfMethodName();
		MethodDeclaration md = (MethodDeclaration) ASTNodeInstantiator.instantiate(MethodDeclaration.class);
		arg = new MethodDeclarationArgument(md);
	}

	@Test
	public void testRunSuccess() throws JavaModelException {
		arg.setMethodName(GOOD_METHOD_NAME);
		boolean success = rule.run(arg).passed();
		assertTrue(success);
	}

	@Test
	public void testRunFailure() throws JavaModelException {
		arg.setMethodName(BAD_METHOD_NAME);
		boolean success = rule.run(arg).passed();
		assertTrue(!success);
	}

}
