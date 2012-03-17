package de.rw.sudoku.model.iterators;

import de.rw.sudoku.model.SudokuCoords;

public class SudokuIteratorColPart extends SudokuIterator {

	private SudokuCoords colPartStart;
	private SudokuCoords current;

	public SudokuIteratorColPart(int sudokuSize, int sudokuBlockSize,
			SudokuCoords start) {
		super(sudokuSize, sudokuBlockSize, start);
		colPartStart = new SudokuCoords(start.getRow()/sudokuBlockSize*sudokuBlockSize, start.getCol());
		current = start;
	}

	@Override
	public boolean hasNext() {
		if (current.getRow()>=(colPartStart.getRow()/getSudokuBlockSize()+1)*getSudokuBlockSize())
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

}

/*
 
public class SudokuIteratorRowPart extends SudokuIterator {

	private SudokuCoords rowPartStart;
	private SudokuCoords current;
	
	public SudokuIteratorRowPart(int sudokuSize, int sudokuBlockSize,
			SudokuCoords start) {
		super(sudokuSize, sudokuBlockSize, start);
		rowPartStart = new SudokuCoords(start.getRow(), start.getCol()/sudokuBlockSize*sudokuBlockSize);
		current = start;
	}

	@Override
	public boolean hasNext() {
		if (current.getCol()>=rowPartStart.getCol()/getSudokuBlockSize()*(getSudokuBlockSize()+1))
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

}

*/