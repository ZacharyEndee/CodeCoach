package cc.rules.api.ruleargs;

import org.eclipse.jdt.core.dom.IfStatement;

public class IfStatementArgument extends RuleArgument<IfStatement> {

	private String ifStmtSourceCode;

	public IfStatementArgument(IfStatement node) {
		setRawArg(node);
		if (node != null && node.getLength() > 0) {
			setIfStmtSourceCode(node.toString());
		}
	}

	public String getIfStmtSourceCode() {
		return ifStmtSourceCode;
	}

	public void setIfStmtSourceCode(String ifStmtSourceCode) {
		this.ifStmtSourceCode = ifStmtSourceCode;
	}
}
