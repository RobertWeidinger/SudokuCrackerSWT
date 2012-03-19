package de.rw.sudoku.model.iterators;

import java.util.EnumSet;
import java.util.Iterator;

import de.rw.sudoku.model.SudokuCoords;

public abstract class SudokuIterator implements Iterator<SudokuCoords> {

	private int sudokuSize;
	private int sudokuBlockSize;
	private SudokuCoords start;
	
	public enum SubStructures {
 		WHOLE, ROW, COL, BLOCK, ROWPART, COLPART;
 
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
			break;
		case ROW:
			si = new SudokuIteratorRow(size,blockSize,sudokuCoords.getRow());
			break;
		case COL:
			si = new SudokuIteratorCol(size, blockSize, sudokuCoords.getCol());
			break;
		case BLOCK:
			si = new SudokuIteratorBlock(size, blockSize, sudokuCoords);
			break;
		case ROWPART:
			si = new SudokuIteratorRowPart(size, blockSize, sudokuCoords);
			break;
		case COLPART:
			si = new SudokuIteratorColPart(size, blockSize, sudokuCoords);
			break;
		}
		return si;
	}
	
	public static SudokuIterator createIteratorSubStruct(int size, int blockSize, SubStructures type)
	{
		SudokuIterator si=null;
		switch (type)
		{
		case ROW:
			si = new SubStructIteratorRows(size,blockSize);
			break;
		case COL:
			si = new SubStructIteratorCols(size, blockSize);
			break;
		case BLOCK:
			si = new SubStructIteratorBlocks(size, blockSize);
			break;
		}
		return si;
	}
 	
	public static SudokuCoords getSubStructBase(int blockSize, SudokuCoords sc, SubStructures type)
	{
		SudokuCoords res=null;
		switch (type)
		{
		case WHOLE:
			res = SudokuIteratorWhole.getSubStructBase(sc);
			break;
		case ROW:
			res = SudokuIteratorRow.getSubStructBase(sc);
			break;
		case COL:
			res = SudokuIteratorCol.getSubStructBase(sc);
			break;
		case BLOCK:
			res = SudokuIteratorBlock.getSubStructBase(blockSize, sc);
			break;
		case ROWPART:
			res = SudokuIteratorRowPart.getSubStructBase(blockSize, sc);
			break;
		case COLPART:
			res = SudokuIteratorColPart.getSubStructBase(blockSize, sc);
			break;
		}
		return res;
	}
	
}
