import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;


public class SiblingAlgorithmButton {
	private Button b;
	private SudokuModel sm;
	private SudokuSiblingCracker ssc;

	public SiblingAlgorithmButton(Composite arg0, int arg1, SudokuModel _sm, 
			int setBoundsArg0, 
			int setBoundsArg1, 
			int setBoundsArg2, 
			int setBoundsArg3)
	{
		b = new Button(arg0, arg1);
		// TODO Auto-generated constructor stub
		sm = _sm;
		ssc = new SudokuSiblingCracker(sm); 
		b.setText("Sibling-Alg 1x");
		b.setBounds(setBoundsArg0, setBoundsArg1, setBoundsArg2, setBoundsArg3);
		b.addSelectionListener(new SelectionAdapter() 
									{
										@Override
										public void widgetSelected(SelectionEvent e) {
											try {
												ssc.findSiblingBasedSolutionsOneIteration();
											} catch (Exception e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
										}
									});

	}
}
