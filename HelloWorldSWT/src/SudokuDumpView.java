import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;


public class SudokuDumpView implements View {

	private SudokuModel sm;
	private Text t;
	private Composite parent;
	
	
	private SudokuModel getModel() {
		return sm;
	}

	private void setModel(SudokuModel sm) {
		this.sm = sm;
	}

	private Text getText() {
		return t;
	}
	
	public String getString() {
		return getText().getText();
	}

	private void setText(Text t) {
		this.t = t;
	}

	private Composite getParent() {
		return parent;
	}

	private void setParent(Composite parent) {
		this.parent = parent;
	}

	public SudokuDumpView(Composite _parent, SudokuModel _sm)
	{
		setModel(_sm);
		setParent(_parent);
		Text _t = new Text(getParent(), SWT.READ_ONLY | SWT.MULTI);
		setText(_t);
		getText().setSize(200,400);
		getText().setLocation(700,30);
		getText().setBackground(new Color(getParent().getBackground().getDevice(),220,220,220));
		getModel().registerView(this);
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		getText().setText(getModel().toStringWithStepValues());
	}

}
