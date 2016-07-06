package cc.rules.api.ruleargs;

import org.eclipse.jdt.core.dom.MethodDeclaration;

public class MethodDeclarationArgument extends RuleArgument<MethodDeclaration> {

	private String methodName;
	private String methodBody;

	public MethodDeclarationArgument(MethodDeclaration node) {
		setRawArg(node);
		MethodDeclaration arg = getRawArg();
		if (arg != null && arg.getLength() > 0) {
			this.methodBody = arg.toString();
			this.methodName = arg.getName().getIdentifier();
		}
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getMethodBodySourceCode() {
		return methodBody;
	}

	public void setMethodBody(String methodBody) {
		this.methodBody = methodBody;
	}

}
