package code_coach.handlers;

import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import code_coach.engine.RuleRunner;

public class RuleConfigHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ProcessBuilder builder = new ProcessBuilder("notepad.exe", RuleRunner.FILE_CUSTOM_RULES);
		try {
			RuleRunner.getInstance();
			Process p = builder.start();
			int exitVal = p.waitFor();
			if (exitVal == 0) {
				RuleRunner.getInstance().reloadRules();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
