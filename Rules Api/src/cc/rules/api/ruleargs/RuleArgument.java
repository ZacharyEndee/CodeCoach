package cc.rules.api.ruleargs;

import org.eclipse.jdt.core.dom.ASTNode;

public class RuleArgument<T extends ASTNode> {

	private T rawArg;

	public T getRawArg() {
		return rawArg;
	}

	public void setRawArg(T rawArg) {
		this.rawArg = rawArg;
	}
}
