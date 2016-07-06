package cc.rules.api.ruletypes;

import org.eclipse.jdt.core.JavaModelException;

import cc.rules.api.IRule;
import cc.rules.api.RuleResult;
import cc.rules.api.ruleargs.VariableDeclarationArgument;

public abstract class VariableDeclarationRule implements IRule {

	public abstract RuleResult run(VariableDeclarationArgument o) throws JavaModelException;

	@Override
	public RuleResult apply(Object o) throws JavaModelException {
		return run((VariableDeclarationArgument) o);
	}

}
