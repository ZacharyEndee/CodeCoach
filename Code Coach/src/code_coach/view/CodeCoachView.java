package code_coach.view;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

public class CodeCoachView extends ViewPart {

	private Label l;

	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		l = new Label(parent, 0);
		l.setText("Code Coach View");
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		l.setFocus();
	}

}
