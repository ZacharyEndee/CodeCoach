package cc.custom.rules;

import java.lang.reflect.Modifier;

import org.eclipse.jdt.core.JavaModelException;

import cc.rules.api.RuleResult;
import cc.rules.api.ruleargs.MethodDeclarationArgument;
import cc.rules.api.ruletypes.MethodDeclarationRule;

/**
 * (Insert a comment that briefly describes the purpose of this class
 * definition.)
 *
 * <p/>
 * Bugs: (List any known issues or unimplemented features here)
 * 
 * @author (Insert your first and last name)
 *
 */
public class LowerCaseLetterAtStartOfMethodName extends MethodDeclarationRule {

	@Override
	public String getHint() {
		return "By convention, method names in Java should start with a lower case letter.";
	}

	@Override
	public String getName() {
		return "Lowercase Letter at Start of Method Name";
	}

	@Override
	public RuleResult run(MethodDeclarationArgument o) throws JavaModelException {
		RuleResult r = new RuleResult(this);
		String methodName = o.getMethodName();
		r.setStartPosition(o.getRawArg().getStartPosition());
		r.setEndPosition(calcEndPos(o));
		r.setPassed(!methodName.matches("[A-Z].*"));
		return r;
	}

	private int calcEndPos(MethodDeclarationArgument o) {
		return o.getRawArg().getStartPosition() + Modifier.toString(o.getRawArg().getModifiers()).length()
				+ o.getMethodName().length() * 3;
	}
}
