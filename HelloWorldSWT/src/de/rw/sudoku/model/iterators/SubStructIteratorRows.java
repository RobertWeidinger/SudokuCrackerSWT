package de.rw.sudoku.model.iterators;

import de.rw.sudoku.model.SudokuCoords;

public class SubStructIteratorRows extends SudokuIterator {

	private SudokuIterator sic; // = Iterator for col0, liefert immer die Zeilenanfänge zurück.
	
	public SubStructIteratorRows(int sudokuSize, int sudokuBlockSize) {
		super(sudokuSize, sudokuBlockSize, new SudokuCoords(0,0));
		sic = SudokuIterator.createIterator(sudokuSize, sudokuBlockSize, getStart(), SubStructures.COL);
	}

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return sic.hasNext();
	}

	@Override
	public SudokuCoords next() {
		// TODO Auto-generated method stub
		return sic.next();
	}

}
