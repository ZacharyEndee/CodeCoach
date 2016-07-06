package cc.rules.api.ruletypes;

import org.eclipse.jdt.core.JavaModelException;

import cc.rules.api.IRule;
import cc.rules.api.RuleResult;
import cc.rules.api.ruleargs.MethodDeclarationArgument;

public abstract class MethodDeclarationRule implements IRule {

	public abstract RuleResult run(MethodDeclarationArgument o) throws JavaModelException;

	@Override
	public RuleResult apply(Object o) throws JavaModelException {
		return run((MethodDeclarationArgument) o);
	}
}
