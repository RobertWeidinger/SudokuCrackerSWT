package de.rw.sudoku.zUnused;

import de.rw.sudoku.model.SudokuModel;

public abstract class SudokuScanner<E> {
	private SudokuModel sm;
	private ScannerAction<E> sa;
	public SudokuScanner(SudokuModel _sm, ScannerAction<E> _sa)
	{
		sm = _sm;
		sa = _sa;
	}

	public SudokuModel getSudokuModel() {
		return sm;
	}
	
	
	public ScannerAction<E> getScannerAction() {
		return sa;
	}

	abstract void scan();
}
