package cc.custom.rules;

import org.eclipse.jdt.core.JavaModelException;

import cc.rules.api.RuleResult;
import cc.rules.api.ruleargs.ForStatmentArgument;
import cc.rules.api.ruletypes.ForStatementRule;

public class MisplacedSemiColonOnFor extends ForStatementRule {

	@Override
	public String getHint() {
		return "There may be a misplaced semicolon after a for loop.  "
				+ "Though, this is not a compilation error, it often leads to incorrect code.";
	}

	@Override
	public String getName() {
		return "Misplaced semi-colon after for loop.";
	}

	@Override
	public RuleResult run(ForStatmentArgument o) throws JavaModelException {
		RuleResult r = new RuleResult(this);
		String source = o.getForLoopSourceCode();
		String[] lines = source.split("\n");
		r.setSource(source);
		for (String line : lines) {
			boolean match = line.matches("(if|for|while)(\\s)*\\(.+\\)(\\s)*;.*$");
			r.setPassed(!match);
		}
		return r;
	}

}
