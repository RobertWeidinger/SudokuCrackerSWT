import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;

/**
 * 
 */

/**
 * @author Robert
 *
 */
public class QuitButton {

	/**
	 * @param arg0
	 * @param arg1
	 */
	private Button b;
	
	public QuitButton(final Shell arg0, int arg1, SudokuModel _sm, 
			int setBoundsArg0, 
			int setBoundsArg1, 
			int setBoundsArg2, 
			int setBoundsArg3) {
		b = new Button(arg0, arg1);
		b.setText("Quit");
        b.setBounds(setBoundsArg0, setBoundsArg1, setBoundsArg2, setBoundsArg3);
        b.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                arg0.getDisplay().dispose();
                System.exit(0);
            }
        });
	}

}
