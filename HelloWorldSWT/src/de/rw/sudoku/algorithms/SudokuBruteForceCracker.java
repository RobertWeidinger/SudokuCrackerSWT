package de.rw.sudoku.algorithms;

import java.util.List;

import de.rw.sudoku.model.SudokuCoords;
import de.rw.sudoku.model.SudokuFieldValues;
import de.rw.sudoku.model.SudokuModel;
import de.rw.sudoku.model.iterators.SudokuIterator;
import de.rw.sudoku.model.iterators.SudokuIterator.SubStructures;

public class SudokuBruteForceCracker {

	private SudokuModel sm;
	private SudokuHelper sh;
	
	public SudokuBruteForceCracker(SudokuModel sm) {
		super();
		this.sm = sm;
		this.sh = new SudokuHelper(this.sm);
	}

	private void log(String s)
	{
		// System.out.println(s);
	}

	public int bruteForceOnSubStruct(SudokuCoords start, SubStructures type)
	{
		log("SudokuBruteForceCracker.bruteForceOnSubStruct("+ start + "," + type + ")");
		int iNumOfValuesFound = 0;
		for (int value=1; value<=sm.getSize(); value++)
		{
			if (sh.findValueInSubStruct(start, type, new Integer(value))!=null)
				continue;
			int iCountFieldsWithoutConflict=0;
			SudokuCoords scWithoutConflicts=null;
			SudokuIterator si = SudokuIterator.createIterator(sm.getSize(), sm.getBlockSize(), start, type);
			while (si.hasNext())
			{
				SudokuCoords sc = si.next();
				boolean isEmpty = sm.isEmpty(sc);
				boolean isBlocked = sm.isBlocked(sc);
				boolean blockingValuesContainValue = sm.getBlockingValues(sc).contains(value);
				if (!isEmpty || (isBlocked && !blockingValuesContainValue))
					continue;
				List<SudokuFieldValues> llSfv = sh.findConflicts(sc, value);
				if (llSfv.isEmpty())
				{
					iCountFieldsWithoutConflict++;
					scWithoutConflicts = sc;
				}
				if (iCountFieldsWithoutConflict>1)
					break;
			}
			if (iCountFieldsWithoutConflict==1)
			{
				log("sm.setSuggestedValue(" +scWithoutConflicts + " ,"+ value +");");
				sm.setSuggestedValue(scWithoutConflicts, value);
				iNumOfValuesFound++;
			}
		}
		return iNumOfValuesFound;
	}

	public int oneIteration()
	{
		log("SudokuBruteForceCracker.oneIteration()");
		int res = 0;

		for (final SudokuIterator.SubStructures subStruct : SudokuIterator.SubStructures.REAL)
		{
			SudokuIterator subStructIt = 
					SudokuIterator.createIteratorSubStruct(sm.getSize(), sm.getBlockSize(), subStruct);
			while (subStructIt.hasNext())
			{
				SudokuCoords scStart = subStructIt.next();
				res += bruteForceOnSubStruct(scStart, subStruct);
			}
		}
		return res;
	}
	
}
