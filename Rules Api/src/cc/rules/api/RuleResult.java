package cc.rules.api;

import java.io.PrintStream;
import java.util.List;

public class RuleResult {

	private IRule rule;
	private boolean passed;
	private String source;
	private int startPosition;
	private int endPosition;

	public RuleResult() {

	}

	public RuleResult(IRule rule) {
		this.rule = rule;
	}

	public static void printResults(List<RuleResult> results, PrintStream stream) {
		for (RuleResult r : results) {
			r.print(stream);
		}
	}

	public void print(PrintStream stream) {
		stream.println("Results:");
		stream.println("----------------------------------------------------------------------------");
		stream.println("Rule: " + rule.getName());
		if (passed) {
			stream.println("Passed");
		} else {
			stream.println("Failed");
			stream.println(rule.getHint());
			stream.println(source);
		}
		stream.println();
	}

	public IRule getRule() {
		return rule;
	}

	public void setRule(IRule rule) {
		this.rule = rule;
	}

	public boolean passed() {
		return passed;
	}

	public void setPassed(boolean passed) {
		this.passed = passed;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(int startPosition) {
		this.startPosition = startPosition;
	}

	public int getEndPosition() {
		return endPosition;
	}

	public void setEndPosition(int endPosition) {
		this.endPosition = endPosition;
	}
}
