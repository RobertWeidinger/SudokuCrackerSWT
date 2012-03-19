package de.rw.sudoku.algorithms;
import java.util.LinkedList;

import de.rw.sudoku.model.SudokuCoords;
import de.rw.sudoku.model.SudokuFieldValues;
import de.rw.sudoku.model.SudokuModel;
import de.rw.sudoku.model.iterators.SudokuIterator;
import de.rw.sudoku.model.iterators.SudokuIterator.SubStructures;


public class SudokuHelper {

	private SudokuModel sm;
	
	public SudokuHelper(SudokuModel _sm)
	{sm=_sm;}
	
	public SudokuCoords findValueInSubStruct(SudokuCoords start,
			SubStructures type, Integer value) {
		SudokuIterator si = SudokuIterator.createIterator(sm.getSize(), sm.getBlockSize(), start, type);
		SudokuCoords scResult = null;
		while (si.hasNext())
		{
			SudokuCoords sc = si.next();
			if (value.equals(sm.getValue(sc)))
			{
				scResult = sc;
				break;
			}
		}
		return scResult;
	}
	
	public LinkedList<SudokuFieldValues> findSiblingsInRows(int rowBlock, // 0,1,2
								  				Integer number)
	{
		LinkedList<SudokuFieldValues> res = new LinkedList<SudokuFieldValues>();
		for (int i=rowBlock*sm.getBlockSize(); i<(rowBlock+1)*sm.getBlockSize();i++)
		{
			for (int j=0; j<sm.getSize(); j++)
				if (number.equals(sm.getValue(i, j)))
				{
					SudokuFieldValues sfv = new SudokuFieldValues(i, j);
					sfv.addValue(number);
					res.add(sfv);
				}
		}
		if (res.size()<2)
			res.clear();
		return res;
	}
	
	public LinkedList<SudokuFieldValues> findSiblingsInCols(int colBlock, // 0,1,2
				Integer number)
	{
		LinkedList<SudokuFieldValues> res = new LinkedList<SudokuFieldValues>();
		for (int c=colBlock*sm.getBlockSize(); c<(colBlock+1)*sm.getBlockSize();c++)
			{
			for (int r=0; r<sm.getSize(); r++)
				if (number.equals(sm.getValue(r, c)))
				{
				SudokuFieldValues sfv = new SudokuFieldValues(r, c);
				sfv.addValue(number);
				res.add(sfv);
				}
			}
		if (res.size()<2)
		res.clear();
		return res;
	}
	
	public int findUniquePlaceForValueInRowPart(int row, int startCol, Integer value)
	{
		SudokuCoords sc = findUniquePlaceForValueInSubStruct(new SudokuCoords(row,startCol),value,SubStructures.ROWPART);
		if (sc==null) return -1;
		return sc.getCol();
	}
	
	public int findUniquePlaceForValueInColPart(int col, int startRow, Integer value)
	{
		SudokuCoords sc = findUniquePlaceForValueInSubStruct(new SudokuCoords(startRow,col),value,SubStructures.COLPART);
		if (sc==null) return -1;
		return sc.getRow();
	}

	public int findUniquePlaceForValueInCol(int col, Integer value)
	{
		SudokuCoords sc = findUniquePlaceForValueInSubStruct(new SudokuCoords(0,col),value,SubStructures.COL);
		if (sc==null) return -1;
		return sc.getRow();
	}
	
	public int findUniquePlaceForValueInRow(int row, Integer value)
	{
		SudokuCoords sc = findUniquePlaceForValueInSubStruct(new SudokuCoords(row,0),value,SubStructures.ROW);
		if (sc==null) return -1;
		return sc.getCol();
	}
	
	public SudokuCoords findUniquePlaceForValueInBlock(SudokuCoords scInBlock, Integer value)
	{
		return findUniquePlaceForValueInSubStruct(scInBlock,value,SubStructures.BLOCK);
	}
	
	public SudokuCoords findUniquePlaceForValueInSubStruct(SudokuCoords start, Integer value, SubStructures typeSubStruct) {
		
		SudokuIterator si = SudokuIterator.createIterator(sm.getSize(), sm.getBlockSize(), start, typeSubStruct);
		int numberOfFreePlaces=0;
		SudokuCoords scRes = null;
		while (si.hasNext())
		{
			SudokuCoords sc = si.next();
			if (value.equals(sm.getValue(sc))) return null;
			if (sm.isEmpty(sc))
			{
				LinkedList<SudokuFieldValues> ll = findConflicts(sc.getRow(), sc.getCol(), value);
				if (ll.size()==0) // no conflict
				{
					numberOfFreePlaces++;
					scRes = sc;
				}
			}
		}
		if (numberOfFreePlaces==1) return scRes;
		return null;
	}

	
	
	public int[] blockCoords(int row, int col)
	{
		int []res = new int[2];
		res[0]= row / sm.getBlockSize();
		res[1]= col / sm.getBlockSize();
		return res;
	}
	
	public LinkedList<SudokuFieldValues> findConflicts(int row, int col, Integer number)
	{
		LinkedList<SudokuFieldValues> ll = new LinkedList<SudokuFieldValues>();
		
		if (number.intValue()<0) return ll;
		
		for (final SudokuIterator.SubStructures subStruct : SudokuIterator.SubStructures.REAL)
		{
			SudokuCoords subStructBase = 
					SudokuIterator.getSubStructBase(sm.getBlockSize(), new SudokuCoords(row,col), subStruct);
			SudokuIterator si = SudokuIterator.createIterator(sm.getSize(), sm.getBlockSize(), subStructBase, subStruct);
			while (si.hasNext())
			{
				SudokuCoords sc = si.next();
				if (sc.equals(new SudokuCoords(row,col))) continue;
				if (number.equals(sm.getValue(sc)))
				{
					LinkedList<Integer> llI = new LinkedList<Integer>();
					llI.add(number);
					SudokuFieldValues sfv = new SudokuFieldValues(sc.getRow(), sc.getCol(), llI);
					if (!ll.contains(sfv))
						ll.add(sfv);
				}
			}
		}
		return ll;
	}
	
}
