package cc.rules.api;

import org.eclipse.jdt.core.JavaModelException;

public class TestRule implements IRule {

	public static final String NAME = "Test Rule";
	public static final String HINT = "Test Hint";

	@Override
	public RuleResult apply(Object o) throws JavaModelException {
		return new RuleResult(this);
	}

	@Override
	public String getHint() {
		return HINT;
	}

	@Override
	public String getName() {
		return NAME;
	}

}
