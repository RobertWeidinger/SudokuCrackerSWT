package de.rw.sudoku.model.iterators;

import de.rw.sudoku.model.SudokuCoords;

public class SudokuIteratorCol extends SudokuIterator {

	private SudokuCoords current;
	
	public SudokuIteratorCol(int sudokuSize, int sudokuBlockSize,
			int col) {
		super(sudokuSize, sudokuBlockSize, new SudokuCoords(0,col));
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
		if (!hasNext()) return null;
		SudokuCoords result = current;
		current = new SudokuCoords(current.getRow()+1,current.getCol());
		return result;
		}

	public static SudokuCoords getSubStructBase(SudokuCoords sc) {
		return new SudokuCoords(0, sc.getCol());
	}

}

