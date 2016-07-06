package cc.custom.rules;

import org.eclipse.jdt.core.JavaModelException;

import cc.rules.api.RuleResult;
import cc.rules.api.ruleargs.VariableDeclarationArgument;
import cc.rules.api.ruletypes.VariableDeclarationRule;

public class ConstantsAreAllUppercase extends VariableDeclarationRule {

	@Override
	public String getHint() {
		return "By convention, constants in Java should be typed in all uppercase letters.";
	}

	@Override
	public String getName() {
		return "Constants are All Uppercase";
	}

	@Override
	public RuleResult run(VariableDeclarationArgument field) throws JavaModelException {
		String variableAccessModifiers = field.getVariableSourceCode();
		RuleResult r = new RuleResult(this);
		r.setSource(variableAccessModifiers);
		if (variableAccessModifiers.matches(".*static(\\s)*final.*")) {
			r.setStartPosition(field.getRawArg().getStartPosition());
			String varName = field.getVariableName();
			r.setPassed(varName.matches("([A-Z]+)|([A-Z]+_[A-Z]*)"));
		} else {
			r.setPassed(true);
		}
		return r;
	}

}
