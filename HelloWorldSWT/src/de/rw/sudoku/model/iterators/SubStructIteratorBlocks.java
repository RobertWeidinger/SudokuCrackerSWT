package de.rw.sudoku.model.iterators;

import de.rw.sudoku.model.SudokuCoords;

public class SubStructIteratorBlocks extends SudokuIterator {

	private SudokuCoords current;

	public SubStructIteratorBlocks(int sudokuSize, int sudokuBlockSize) {
		super(sudokuSize, sudokuBlockSize, new SudokuCoords(0,0));
		current = getStart();
	}

	@Override
	public boolean hasNext() {
		if (current.getRow()>=getSudokuSize())
			return false;
		return true;
	}

	@Override
	public SudokuCoords next() {
		if (!hasNext()) return null;
		SudokuCoords result = current;
		int newCol = current.getCol()+getSudokuBlockSize();
		if (newCol<getSudokuSize())
			current = new SudokuCoords(current.getRow(),newCol);
		else
			current = new SudokuCoords(current.getRow()+getSudokuBlockSize(),0);
		return result;
		}

}

