package de.rw.sudokutest.algorithms;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.rw.sudoku.algorithms.SudokuBruteForceCracker;
import de.rw.sudoku.io.SudokuFileReaderWriter;
import de.rw.sudoku.model.SudokuCoords;
import de.rw.sudoku.model.SudokuModel;
import de.rw.sudoku.model.iterators.SudokuIterator.SubStructures;

public class SudokuBruteForceCrackerTest {

	private SudokuModel sm;
	private SudokuModel sm2;
	private SudokuBruteForceCracker sbfc;
	private SudokuBruteForceCracker sbfc2;
	
	@Before
	public void setUp() throws Exception {
		sm = new SudokuModel(9,3);
		sm2 = new SudokuModel(9,3);
		try {
			SudokuFileReaderWriter.readSudokuFromFile(sm, "src\\de\\rw\\sudokutest\\zzTestdata\\sz20120224_Format2.0.txt");
			SudokuFileReaderWriter.readSudokuFromFile(sm2, "src\\de\\rw\\sudokutest\\zzTestdata\\KoCo11_Experte_Nr1_Format2.0_NextBruteForceWithConflicts.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		sbfc = new SudokuBruteForceCracker(sm);
		sbfc2 = new SudokuBruteForceCracker(sm2);
	}

	@Test
	public void testBruteForceOnSubStruct() {
		sbfc.bruteForceOnSubStruct(new SudokuCoords(3, 6), SubStructures.BLOCK);
		Assert.assertEquals(3, sm.getValue(new SudokuCoords(4,7)).intValue());
	}

	
	@Test
	public void testOneIteration() {
		sbfc.oneIteration();
		Assert.assertEquals(7, sm.getValue(new SudokuCoords(5,0)).intValue());
		Assert.assertEquals(9, sm.getValue(new SudokuCoords(7,1)).intValue());
		Assert.assertEquals(2, sm.getValue(new SudokuCoords(8,3)).intValue());
		Assert.assertEquals(3, sm.getValue(new SudokuCoords(4,7)).intValue());
		sbfc.oneIteration();
		Assert.assertEquals(2, sm.getValue(new SudokuCoords(4,2)).intValue());
		
		sbfc2.oneIteration();
		Assert.assertEquals(false, sm2.hasConflicts());
	}

}
