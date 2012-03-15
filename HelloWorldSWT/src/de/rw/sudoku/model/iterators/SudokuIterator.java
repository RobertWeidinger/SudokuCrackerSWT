package de.rw.sudoku.model.iterators;

import java.util.EnumSet;
import java.util.Iterator;

import de.rw.sudoku.model.SudokuCoords;
import de.rw.sudokutest.model.iterators.SudokuIteratorRow;

public abstract class SudokuIterator implements Iterator<SudokuCoords> {

	private int sudokuSize;
	private int sudokuBlockSize;
	private SudokuCoords start;
	
	public enum SubStructures {
 		WHOLE, ROW, COL, BLOCK;
 
 		public static final EnumSet<SubStructures> ALL = EnumSet.range(WHOLE, BLOCK);
 		public static final EnumSet<SubStructures> REAL = EnumSet.range(ROW, BLOCK);
 		public static final EnumSet<SubStructures> LINES = EnumSet.range(ROW, COL);
 		}
 
 		
 	public SudokuIterator(int sudokuSize, int sudokuBlockSize, SudokuCoords start) {
 		this.sudokuBlockSize = sudokuBlockSize;
 		this.sudokuSize = sudokuSize;
 		this.start = start;
	}
 	

	protected int getSudokuSize() {
		return sudokuSize;
	}

	protected int getSudokuBlockSize() {
		return sudokuBlockSize;
	}

	protected SudokuCoords getStart() {
		return start;
	}


	@Override
	public abstract boolean hasNext();


	@Override
	public abstract SudokuCoords next(); 


	@Override
	public void remove()
	{
		//	throw new Exception("remove nicht vorgesehen"); // Warum geht das nicht??
	}


	public static SudokuIterator createIterator(int size, int blockSize,
			SudokuCoords sudokuCoords, SubStructures type) {
		// TODO Auto-generated method stub
		SudokuIterator si=null;
		switch (type)
		{
		case WHOLE:
			si = new SudokuIteratorWhole(size, blockSize);
		case ROW:
			si = new SudokuIteratorRow(size,blockSize,sudokuCoords.getRow());
		}
		return si;
	}
 	
}
