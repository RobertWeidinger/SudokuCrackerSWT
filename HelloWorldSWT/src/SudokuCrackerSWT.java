import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;


public class SudokuCrackerSWT {
	
	private Shell shell;
	private Display display;
	private SudokuModel sm;
	private SudokuView sv;
	private SudokuDumpView sdv;
	
    public void initUI() {
		
    	display = new Display();
		shell = new Shell(display);
		shell.setText("Sudoku Cracker (naja,... erst mal nur ein Sudoku-Anzeiger)");
		shell.open();
        sm = new SudokuModel(9,3);
        sv = new SudokuView(shell,sm);
        sdv = new SudokuDumpView(shell, sm);

        sv.update();
        sdv.update();
        
        Button loadFile = new Button(shell, SWT.PUSH);
        loadFile.setText("Datei laden");
        loadFile.setBounds(500, 200, 80, 30);
        loadFile.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                //sm.loadFromFile();
            	FileDialog fd = new FileDialog(Display.getCurrent().getActiveShell(), SWT.OPEN);
            	fd.setFilterExtensions(new String[] {"*.txt"});
            	fd.setText("Sudoku laden");  
            	String strFile = fd.open();
            	if (strFile != null)
            	{
            		try {
						SudokuFileReader.readSudokuFromFile(sm, strFile);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
            	}
            }
        });
        
        Button fix = new Button(shell, SWT.PUSH);
        fix.setText("Fixieren");
        fix.setBounds(500, 300, 80, 30);
        fix.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                sm.makeValuesFixed();
            }
        });

        Button undo = new Button(shell, SWT.PUSH);
        undo.setText("Rückgängig");
        undo.setBounds(500, 400, 80, 30);
        undo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                sm.undo();
            }
        });

        
        Button quit = new Button(shell, SWT.PUSH);
        quit.setText("Quit");
        quit.setBounds(500, 500, 80, 30);
        quit.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                shell.getDisplay().dispose();
                System.exit(0);
            }
        });

    }

    public void run()
    {
    	while (!shell.isDisposed()) {
    		if (!display.readAndDispatch()) display.sleep();
    		}
    		display.dispose();
    }
    

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SudokuCrackerSWT hw = new SudokuCrackerSWT();
		hw.initUI();
		hw.run();
	}

}
