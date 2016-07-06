package code_coach.startup;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.ui.IStartup;

import code_coach.engine.CodeCoach;

public class CodeCoachStartup implements IStartup {

	private Job job;

	@Override
	public void earlyStartup() {		
		JavaCore.addElementChangedListener(new IElementChangedListener() {
			@Override
			public void elementChanged(ElementChangedEvent event) {
				System.out.println("document changed.");
				if (job != null && job.getState() != Job.RUNNING) {
					System.out.println("scheduling job");
					job.schedule();
				} else {
					System.out.println("Job is currently running, or has not been initialized.");
				}
				if (job == null) {
					job = new Job("Code Coach") {
						@Override
						protected IStatus run(IProgressMonitor monitor) {
							System.out.println("Code Coach job running");
							new CodeCoach().execute();
							return Status.OK_STATUS;
						}
					};
					job.setRule(ResourcesPlugin.getWorkspace().getRoot());
					job.schedule();
				}
			}
		});
	}
}
