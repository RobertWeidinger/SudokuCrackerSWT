package de.rw.sudoku.views;

import java.io.File;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;

import de.rw.sudoku.io.SudokuFileReaderWriter;
import de.rw.sudoku.model.SudokuModel;

/**
 * 
 */

/**
 * @author Robert
 *
 */
public class WriteFileButton {

	/**
	 * @param arg0
	 * @param arg1
	 */
	private Button b;
	private SudokuModel sm;
	
	public WriteFileButton(Composite arg0, int arg1, SudokuModel _sm, 
			int setBoundsArg0, 
			int setBoundsArg1, 
			int setBoundsArg2, 
			int setBoundsArg3) {
		b = new Button(arg0,arg1);
		
		sm = _sm;
		b.setText("Datei speichern");
        b.setBounds(setBoundsArg0, setBoundsArg1, setBoundsArg2, setBoundsArg3);
        b.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                FileDialog fd = new FileDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
            	fd.setFilterExtensions(new String[] {"*.txt"});
            	fd.setText("Sudoku speichern");  
            	String strFile = fd.open();
            	if (strFile != null)
            	{
            		try {
            			File f = new File(strFile);
            			if (f.exists())
            			{
            				MessageBox mb = new MessageBox(Display.getCurrent().getActiveShell(), SWT.OK | SWT.CANCEL);
            				mb.setText("Datei speichern");
            				mb.setMessage("Wollen Sie " + strFile + " überschreiben?");
            				if (mb.open()==SWT.CANCEL) return;
             		        
            			}
           				SudokuFileReaderWriter.writeSudokuToFile(sm, strFile);
					} 
            		catch (IOException e1) {
						e1.printStackTrace();
					}
            	}
            }
        });
		
	}

}
