package de.rw.sudoku.algorithms;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
			if (sm.isEmpty(sc) && !sm.isBlocked(sc))
			{
				List<SudokuFieldValues> ll = findConflicts(sc.getRow(), sc.getCol(), value);
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

		
	public List<SudokuCoords> findSiblingBlockingValuesInSubStruct(int row, int col, SudokuIterator.SubStructures subStructType)
	{
		List<SudokuCoords> lSc = new LinkedList<SudokuCoords>();
		if (sm.getBlockingValues(row, col).size()==0) return lSc;
		SudokuCoords sc = new SudokuCoords(row,col);
		SudokuCoords scBase = SudokuIterator.getSubStructBase(sm.getBlockSize(), sc, subStructType);
		SudokuIterator si = SudokuIterator.createIterator(sm.getSize(), sm.getBlockSize(), scBase, subStructType);
		while (si.hasNext())
		{
			SudokuCoords sc2 = si.next();
			if (sc2.equals(sc)) continue;
			if (sm.equalBlockingValues(sc, sc2))
				lSc.add(sc2);
		}
		return lSc;
	}
	
	public List<SudokuFieldValues> findConflicts(int row, int col, Integer number)
	{
		List<SudokuFieldValues> ll = new LinkedList<SudokuFieldValues>();
		
		if (number.intValue()<0) return ll;
		if (sm.isBlocked(row, col) && sm.getBlockingValues(row, col).contains(number)) return ll;
		
		for (final SudokuIterator.SubStructures subStruct : SudokuIterator.SubStructures.REAL)
		{
			SudokuCoords subStructBase = 
					SudokuIterator.getSubStructBase(sm.getBlockSize(), new SudokuCoords(row,col), subStruct);
			SudokuIterator si = SudokuIterator.createIterator(sm.getSize(), sm.getBlockSize(), subStructBase, subStruct);
			while (si.hasNext())
			{
				SudokuCoords sc = si.next();
				if (sc.equals(new SudokuCoords(row,col))) continue;
				boolean bConflict=number.equals(sm.getValue(sc));
				if (!bConflict && sm.isBlocked(sc))
				{
					List<SudokuCoords> lSc = findSiblingBlockingValuesInSubStruct(sc.getRow(), sc.getCol(), subStruct);
					ArrayList<Integer> blockingValues = sm.getBlockingValues(sc.getRow(), sc.getCol());
					if (lSc.size()+1==blockingValues.size())
					{
						for (Integer i: blockingValues)
							if (number.equals(i)) bConflict=true;
					}
						
				}
				
				if (bConflict)
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
