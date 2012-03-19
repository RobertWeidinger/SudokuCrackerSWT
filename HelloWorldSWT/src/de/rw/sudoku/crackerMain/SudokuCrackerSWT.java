package de.rw.sudoku.crackerMain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import de.rw.sudoku.model.SudokuModel;
import de.rw.sudoku.views.BruteForceAlgorithmButton;
import de.rw.sudoku.views.FixButton;
import de.rw.sudoku.views.LoadFileButton;
import de.rw.sudoku.views.QuitButton;
import de.rw.sudoku.views.SiblingAlgorithmButton;
import de.rw.sudoku.views.SudokuDumpView;
import de.rw.sudoku.views.SudokuView;
import de.rw.sudoku.views.UndoButton;


public class SudokuCrackerSWT {
	
	private Shell shell;
	private Display display;
	private SudokuModel sm;
	private SudokuView sv;
	private SudokuDumpView sdv;
	
	public SudokuModel getSm() {
		return sm;
	}

	public SudokuView getSv() {
		return sv;
	}

	public SudokuDumpView getSdv() {
		return sdv;
	}

    public Shell getShell() {
		return shell;
	}

	public Display getDisplay() {
		return display;
	}

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
        
        
        new LoadFileButton				(shell, SWT.PUSH, sm, 500, 100, 80, 30);
                
        new FixButton					(shell, SWT.PUSH, sm, 500, 200, 80, 30);
        
        new UndoButton					(shell, SWT.PUSH, sm, 500, 300, 80, 30);

        new SiblingAlgorithmButton		(shell, SWT.PUSH, sm, 500, 400, 80, 30);
        
        new BruteForceAlgorithmButton	(shell, SWT.PUSH, sm, 500, 500, 80, 30);
        
        new QuitButton					(shell, SWT.PUSH, sm, 500, 600, 80, 30);
        

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
		SudokuCrackerSWT sc = new SudokuCrackerSWT();
		sc.initUI();
		sc.run();
	}

}
