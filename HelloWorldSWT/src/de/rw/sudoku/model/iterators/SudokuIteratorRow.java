package de.rw.sudoku.model.iterators;

import de.rw.sudoku.model.SudokuCoords;

public class SudokuIteratorRow extends SudokuIterator {

	private SudokuCoords current;

	public SudokuIteratorRow(int sudokuSize, int sudokuBlockSize, int row) {
		super(sudokuSize, sudokuBlockSize, new SudokuCoords(row,0));
		current = this.getStart();
	}
	
	@Override
	public boolean hasNext() {
		if (current.getCol()>getSudokuSize()-1)
			return false;
		return true;
	}

	@Override
	public SudokuCoords next() {
		if (!hasNext()) return null;
		SudokuCoords result = current;
		current = new SudokuCoords(current.getRow(),current.getCol()+1);
		return result;
	}

	public static SudokuCoords getSubStructBase(SudokuCoords sc) {
		return new SudokuCoords(sc.getRow(),0);
	}

}

