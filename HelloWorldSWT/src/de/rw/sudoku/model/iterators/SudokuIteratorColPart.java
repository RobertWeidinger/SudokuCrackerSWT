package de.rw.sudoku.model.iterators;

import de.rw.sudoku.model.SudokuCoords;

public class SudokuIteratorColPart extends SudokuIterator {

	private SudokuCoords colPartStart;
	private SudokuCoords current;

	public SudokuIteratorColPart(int sudokuSize, int sudokuBlockSize,
			SudokuCoords start) {
		super(sudokuSize, sudokuBlockSize, start);
		colPartStart = getSubStructBase(sudokuBlockSize, start);
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

	public static SudokuCoords getSubStructBase(int blockSize, SudokuCoords sc) {
		// TODO Auto-generated method stub
		return new SudokuCoords(sc.getRow()/blockSize*blockSize, sc.getCol());
	}

}

