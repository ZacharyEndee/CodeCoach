package cc.rules.api.ruletypes;

import org.eclipse.jdt.core.JavaModelException;

import cc.rules.api.IRule;
import cc.rules.api.RuleResult;
import cc.rules.api.ruleargs.WhileStatementArgument;

public abstract class WhileStatementRule implements IRule {

	public abstract RuleResult run(WhileStatementArgument o) throws JavaModelException;

	@Override
	public RuleResult apply(Object o) throws JavaModelException {
		return run((WhileStatementArgument) o);
	}

}
