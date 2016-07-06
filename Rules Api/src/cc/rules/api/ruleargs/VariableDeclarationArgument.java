package cc.rules.api.ruleargs;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class VariableDeclarationArgument extends RuleArgument<VariableDeclarationFragment> {

	private String variableSourceCode;
	private String variableName;

	public VariableDeclarationArgument(VariableDeclarationFragment node) {
		setRawArg(node);
		if (node != null && node.getLength() > 0) {
			ASTNode parent = node.getParent();
			if (parent != null) {
				variableSourceCode = parent.toString().trim();
			}
			SimpleName name = node.getName();
			if (name != null) {
				variableName = name.getIdentifier();
			}
		}
	}

	public String getVariableSourceCode() {
		return variableSourceCode;
	}

	public void setVariableSourceCode(String variableSourceCode) {
		this.variableSourceCode = variableSourceCode;
	}

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
}
