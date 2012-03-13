package de.rw.sudoku.model.iterators;

import de.rw.sudoku.model.SudokuModel;

public abstract class ScannerAction<E> {
	protected SudokuModel sm;
	protected E param;
	
	public ScannerAction(SudokuModel _sm, E _param)
	{
		sm=_sm;
		param = _param;
	}
	
	public E getParam()
	{
		return param;
	}
	
	public abstract boolean act(int row, int col, E param);
	
}
