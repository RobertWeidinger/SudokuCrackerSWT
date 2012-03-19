package de.rw.sudoku.model.iterators;

import de.rw.sudoku.model.SudokuCoords;

public class SubStructIteratorCols extends SudokuIterator {

	private SudokuIterator sir; // = Iterator for row0, liefert immer die Spaltenanfänge zurück.
	
	public SubStructIteratorCols(int sudokuSize, int sudokuBlockSize) {
		super(sudokuSize, sudokuBlockSize, new SudokuCoords(0,0));
		sir = SudokuIterator.createIterator(sudokuSize, sudokuBlockSize, getStart(), SubStructures.ROW);
	}

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return sir.hasNext();
	}

	@Override
	public SudokuCoords next() {
		// TODO Auto-generated method stub
		return sir.next();
	}

}
