import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * 
 */

/**
 * @author Robert
 *
 */
public class FixButton {

	/**
	 * @param arg0
	 * @param arg1
	 */
	private Button b;
	private SudokuModel sm;
	
	public FixButton(Composite arg0, int arg1, SudokuModel _sm, 
			int setBoundsArg0, 
			int setBoundsArg1, 
			int setBoundsArg2, 
			int setBoundsArg3) {
		b = new Button(arg0, arg1);
		// TODO Auto-generated constructor stub
		sm = _sm;
		b.setText("Fixieren");
        b.setBounds(setBoundsArg0, setBoundsArg1, setBoundsArg2, setBoundsArg3);
        b.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                sm.makeValuesFixed();
            }
        });
	}

}
