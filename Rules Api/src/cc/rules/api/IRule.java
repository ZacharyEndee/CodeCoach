package cc.rules.api;

import org.eclipse.jdt.core.JavaModelException;

public interface IRule {

	public static final String TYPE_VARIABLE_DECLARTION_RULE = "VariableDeclaration";

	/**
	 * @return True if the rule passes. Otherwise, return false.
	 * @throws JavaModelException
	 */
	RuleResult apply(Object o) throws JavaModelException;

	/**
	 * @return A message describing the rule.
	 */
	String getHint();

	String getName();

	// String getRuleType();
}
