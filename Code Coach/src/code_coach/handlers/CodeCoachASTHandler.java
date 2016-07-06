package code_coach.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import code_coach.engine.CodeCoach;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */

public class CodeCoachASTHandler extends AbstractHandler {

	/**
	 * The constructor.
	 */
	public CodeCoachASTHandler() {
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		new CodeCoach().execute();
		return null;
	}
}
