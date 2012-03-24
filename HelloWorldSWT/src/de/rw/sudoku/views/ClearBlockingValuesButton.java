package de.rw.sudoku.views;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import de.rw.sudoku.model.SudokuModel;


public class ClearBlockingValuesButton {
	private Button b;
	private SudokuModel sm;
	public ClearBlockingValuesButton(Composite arg0, int arg1, SudokuModel _sm, 
			int setBoundsArg0, 
			int setBoundsArg1, 
			int setBoundsArg2, 
			int setBoundsArg3)
	{
		b = new Button(arg0, arg1);
		// TODO Auto-generated constructor stub
		sm = _sm;
		
		b.setText("Clear");
		b.setBounds(setBoundsArg0, setBoundsArg1, setBoundsArg2, setBoundsArg3);
		b.addSelectionListener(new SelectionAdapter() 
									{
										@Override
										public void widgetSelected(SelectionEvent e) {
											try {
												sm.clearBlockingValues();
											} catch (Exception e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
										}
									});

	}
}
