package de.rw.sudoku.algorithms;
import java.util.LinkedList;

import de.rw.sudoku.model.SudokuFieldValues;
import de.rw.sudoku.model.SudokuModel;


public class SudokuSiblingCracker {

	SudokuModel sm;
	public SudokuSiblingCracker(SudokuModel _sm)
	{
		sm = _sm;
	}
	
	public boolean findSiblingBasedSolutionsOneIteration() throws Exception
	{
		boolean bResult = false;
		SudokuHelper sh = new SudokuHelper(sm);
		if (sm.getSize()!=9 || sm.getBlockSize()!=3)
			throw new Exception("Algorithmus nur f�r 9/3-Sudokus implementiert!");
		
		for (int rowBlock=0; rowBlock<sm.getNumberOfBlocksPerRow(); rowBlock++)
		{
			for (int number=1; number<=sm.getSize(); number++)
			{
				LinkedList<SudokuFieldValues> llSfv = sh.findSiblingsInRows(rowBlock, number);
				if (llSfv.size()!=2) continue;
				SudokuFieldValues sfv1 = llSfv.getFirst();
				SudokuFieldValues sfv2 = llSfv.getLast();
				int colBlockWithoutNumber=-1;
				int rowWithoutNumber=-1;
				int blockSfv1[] = sh.blockCoords(sfv1.getRow(), sfv1.getCol());
				int blockSfv2[] = sh.blockCoords(sfv2.getRow(), sfv2.getCol());
				for (int i=0; i<sm.getNumberOfBlocksPerRow(); i++)
				{
					if (i!=blockSfv1[1] && i!=blockSfv2[1])
						colBlockWithoutNumber = i;
				}
				for (int i=rowBlock*sm.getBlockSize(); i<(rowBlock+1)*sm.getBlockSize(); i++)
				{
					if (i!=sfv1.getRow() && i!=sfv2.getRow())
						rowWithoutNumber = i;
				}
				int colRes = sh.findUniquePlaceForNumberInRowPart(rowWithoutNumber, 
									colBlockWithoutNumber*sm.getBlockSize(),
									(colBlockWithoutNumber+1)*sm.getBlockSize()-1,
									number);
				if (colRes>=0)
				{
					sm.setSuggestedValue(rowWithoutNumber, colRes, number);
					bResult = true;
				}
			}
		}
		for (int colBlock=0; colBlock<sm.getNumberOfBlocksPerCol(); colBlock++)
		{
			for (int number=1; number<=sm.getSize(); number++)
			{
				LinkedList<SudokuFieldValues> llSfv = sh.findSiblingsInCols(colBlock, number);
				if (llSfv.size()!=2) continue;
				SudokuFieldValues sfv1 = llSfv.getFirst();
				SudokuFieldValues sfv2 = llSfv.getLast();
				int rowBlockWithoutNumber=-1;
				int colWithoutNumber=-1;
				int blockSfv1[] = sh.blockCoords(sfv1.getRow(), sfv1.getCol());
				int blockSfv2[] = sh.blockCoords(sfv2.getRow(), sfv2.getCol());
				for (int j=0; j<sm.getNumberOfBlocksPerCol(); j++)
				{
					if (j!=blockSfv1[0] && j!=blockSfv2[0])
						rowBlockWithoutNumber = j;
				}
				for (int j=colBlock*sm.getBlockSize(); j<(colBlock+1)*sm.getBlockSize(); j++)
				{
					if (j!=sfv1.getCol() && j!=sfv2.getCol())
						colWithoutNumber = j;
				}
				int rowRes = sh.findUniquePlaceForNumberInColPart(colWithoutNumber, 
									rowBlockWithoutNumber*sm.getBlockSize(),
									(rowBlockWithoutNumber+1)*sm.getBlockSize()-1,
									number);
				if (rowRes>=0)
				{
					sm.setSuggestedValue(rowRes, colWithoutNumber, number);
					bResult=true;
				}
			}
		}
		
		return bResult;
	}
}