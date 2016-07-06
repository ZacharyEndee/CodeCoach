package cc.custom.rules;

import org.eclipse.jdt.core.JavaModelException;

import cc.rules.api.RuleResult;
import cc.rules.api.ruleargs.IfStatementArgument;
import cc.rules.api.ruletypes.IfStatementRule;

public class MisplacedSemiColonOnIf extends IfStatementRule {

	@Override
	public String getHint() {
		return "There may be a misplaced semicolon after an if statement.  "
				+ "Though, this is not a compilation error, it often leads to incorrect code.";
	}

	@Override
	public String getName() {
		return "Misplaced semi-colon after if";
	}

	@Override
	public RuleResult run(IfStatementArgument o) throws JavaModelException {
		RuleResult r = new RuleResult(this);
		String source = o.getIfStmtSourceCode();
		String[] lines = source.split("\n");
		r.setSource(source);
		for (String line : lines) {
			boolean match = line.matches("(if|for|while)(\\s)*\\(.+\\)(\\s)*;.*$");
			r.setPassed(!match);
		}
		return r;
	}

}
