package de.rw.sudoku.model.iterators;

import de.rw.sudoku.model.SudokuCoords;

public class SudokuIteratorWhole extends SudokuIterator {

	private SudokuCoords current;
	
	public SudokuIteratorWhole(int sudokuSize, int sudokuBlockSize) {
		super(sudokuSize, sudokuBlockSize, new SudokuCoords(0,0));
		current = this.getStart();
	}

	@Override
	public boolean hasNext() {
		if (current.getRow()>getSudokuSize()-1)
			return false;
		return true;
	}

	@Override
	public SudokuCoords next() {
		// TODO Auto-generated method stub
		if (!hasNext()) return null;
		SudokuCoords result = current;
		int row=current.getRow();
		int col=current.getCol();
		if (col<getSudokuSize()-1) 
			col++;
		else
		{
			col=0;
			row++;
		}
		current = new SudokuCoords(row,col);
		return result;
	}

	public static SudokuCoords getSubStructBase(SudokuCoords sc) {
		// TODO Auto-generated method stub
		return new SudokuCoords(0,0);
	}

}
