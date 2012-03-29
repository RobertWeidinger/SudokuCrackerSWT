package de.rw.sudoku.views;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import de.rw.sudoku.algorithms.AlternatingAlgorithms;
import de.rw.sudoku.model.SudokuModel;


public class AlternatingAlgorithmButton {
	private Button b;
	private SudokuModel sm;
	private AlternatingAlgorithms aa;

	public AlternatingAlgorithmButton(Composite arg0, int arg1, SudokuModel _sm, 
			int setBoundsArg0, 
			int setBoundsArg1, 
			int setBoundsArg2, 
			int setBoundsArg3)
	{
		b = new Button(arg0, arg1);
		sm = _sm;
		aa = new AlternatingAlgorithms(sm); 
		b.setText("So weit wie möglich.");
		b.setBounds(setBoundsArg0, setBoundsArg1, setBoundsArg2, setBoundsArg3);
		b.addSelectionListener(new SelectionAdapter() 
									{
										@Override
										public void widgetSelected(SelectionEvent e) {
											try {
												aa.run();
											} catch (Exception e1) {
												e1.printStackTrace();
											}
										}
									});

	}
}
