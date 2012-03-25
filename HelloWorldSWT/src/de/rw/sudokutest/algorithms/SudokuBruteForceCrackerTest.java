package de.rw.sudokutest.algorithms;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.rw.sudoku.algorithms.SudokuBruteForceCracker;
import de.rw.sudoku.algorithms.SudokuFileReader;
import de.rw.sudoku.model.SudokuCoords;
import de.rw.sudoku.model.SudokuModel;
import de.rw.sudoku.model.iterators.SudokuIterator.SubStructures;

public class SudokuBruteForceCrackerTest {

	private SudokuModel sm;
	private SudokuBruteForceCracker sbfc;
	
	@Before
	public void setUp() throws Exception {
		sm = new SudokuModel(9,3);
		try {
			SudokuFileReader.readSudokuFromFile(sm, "src\\sz20120224.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		sbfc = new SudokuBruteForceCracker(sm);
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
	}

}
