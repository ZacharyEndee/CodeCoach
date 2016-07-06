package cc.rules.api.ruleargs;

import org.eclipse.jdt.core.dom.WhileStatement;

public class WhileStatementArgument extends RuleArgument<WhileStatement> {

	private String whileLoopSourceCode;

	public WhileStatementArgument(WhileStatement node) {
		setRawArg(node);
		if (node != null && node.getLength() > 0) {
			whileLoopSourceCode = node.toString();
		}
	}

	public String getWhileLoopSourceCode() {
		return whileLoopSourceCode;
	}

	public void setWhileLoopSourceCode(String whileLoopSourceCode) {
		this.whileLoopSourceCode = whileLoopSourceCode;
	}
}
