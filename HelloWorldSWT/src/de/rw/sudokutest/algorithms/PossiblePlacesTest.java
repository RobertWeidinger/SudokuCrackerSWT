package de.rw.sudokutest.algorithms;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.rw.sudoku.algorithms.PossiblePlaces;
import de.rw.sudoku.model.SudokuCoords;

public class PossiblePlacesTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCompareTo() {
		PossiblePlaces pp = new PossiblePlaces(new Integer(5));
		pp.addSudokuCoords(new SudokuCoords(1,5));
		pp.addSudokuCoords(new SudokuCoords(3,0));
		
		PossiblePlaces pp2 = new PossiblePlaces(new Integer(5));
		pp2.addSudokuCoords(new SudokuCoords(3,0));
		pp2.addSudokuCoords(new SudokuCoords(1,5));
		
		Assert.assertEquals(0, pp.compareTo(pp2));
		
		PossiblePlaces pp3 = new PossiblePlaces(new Integer(5));
		pp3.addSudokuCoords(new SudokuCoords(8,8));

		Assert.assertEquals(1, pp.compareTo(pp3));
		
		PossiblePlaces pp4 = new PossiblePlaces(new Integer(5));
		pp4.addSudokuCoords(new SudokuCoords(2,2));
		pp4.addSudokuCoords(new SudokuCoords(1,5));
		
		Assert.assertEquals(1, pp.compareTo(pp4));
	
	}

}
