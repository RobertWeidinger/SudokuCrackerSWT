package de.rw.sudokutest.model;

import junit.framework.Assert;

import org.junit.Test;

import de.rw.sudoku.model.SudokuCoords;

public class SudokuCoordsTest {

	@Test
	public void testCompareTo() {
		SudokuCoords sc1 = new SudokuCoords(2,4);
		SudokuCoords sc2 = new SudokuCoords(3,2);
		Assert.assertTrue(sc1.compareTo(sc2)<0);
		SudokuCoords sc3 = new SudokuCoords(0,9);
		Assert.assertTrue(sc1.compareTo(sc3)>0);
		SudokuCoords sc4 = new SudokuCoords(0,9);
		Assert.assertTrue(sc3.compareTo(sc4)==0);
	}

}
