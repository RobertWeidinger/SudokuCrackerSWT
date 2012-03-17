package de.rw.sudoku.model.iterators;

import de.rw.sudoku.model.SudokuCoords;

public class SudokuIteratorBlock extends SudokuIterator {

	private SudokuCoords current;
	private SudokuCoords blockStart=null;
	
	public static SudokuCoords getBlockStartStatic(int _sudokuBlockSize, SudokuCoords _start)
	{
		return new SudokuCoords(_start.getRow()/_sudokuBlockSize * _sudokuBlockSize, 
								_start.getCol()/_sudokuBlockSize * _sudokuBlockSize);
	}
	
	public SudokuIteratorBlock(int sudokuSize, int _sudokuBlockSize, SudokuCoords _start) {
		super(sudokuSize, _sudokuBlockSize, _start);
		current = _start;
		blockStart = getBlockStartStatic(_sudokuBlockSize, _start);
	}

	public SudokuCoords getBlockStart() {
		return blockStart;
	}

	@Override
	public boolean hasNext() {
		if (current.getRow()>getBlockStart().getRow()+getSudokuBlockSize()-1)
			return false;
		return true;
	}

	@Override
	public SudokuCoords next() {
		if (!hasNext()) return null;
		SudokuCoords result = current;
		int col = current.getCol();
		if (col==getBlockStart().getCol()+getSudokuBlockSize()-1)
			current = new SudokuCoords(current.getRow()+1, getBlockStart().getCol());
		else 
			current = new SudokuCoords(current.getRow(), current.getCol()+1);
		return result;
	}

	public static SudokuCoords getSubStructBase(int sudokuBlockSize, SudokuCoords sc) {
		// TODO Auto-generated method stub
		return getBlockStartStatic(sudokuBlockSize, sc);
	}
	
}


/*

public class SudokuIteratorRow extends SudokuIterator {

	private SudokuCoords current;

	public SudokuIteratorRow(int sudokuSize, int sudokuBlockSize, int row) {
		super(sudokuSize, sudokuBlockSize, new SudokuCoords(row,0));
		current = this.getStart();
	}
	
	@Override
	public boolean hasNext() {
		if (current.getCol()==getSudokuSize()-1)
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