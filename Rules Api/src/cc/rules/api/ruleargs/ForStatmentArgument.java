package cc.rules.api.ruleargs;

import org.eclipse.jdt.core.dom.ForStatement;

public class ForStatmentArgument extends RuleArgument<ForStatement> {

	private String forLoopSourceCode;

	public ForStatmentArgument(ForStatement node) {
		setRawArg(node);
		if (node != null && node.getLength() > 0) {
			this.forLoopSourceCode = node.toString();
		}
	}

	public String getForLoopSourceCode() {
		return forLoopSourceCode;
	}

	public void setForLoopSourceCode(String forLoopSourceCode) {
		this.forLoopSourceCode = forLoopSourceCode;
	}
}
