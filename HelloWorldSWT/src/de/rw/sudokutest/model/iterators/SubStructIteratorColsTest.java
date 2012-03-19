package de.rw.sudokutest.model.iterators;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.rw.sudoku.model.SudokuCoords;
import de.rw.sudoku.model.iterators.SubStructIteratorCols;

public class SubStructIteratorColsTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testNext() {
		SubStructIteratorCols ssic = new SubStructIteratorCols(9, 3);
		Assert.assertTrue(ssic.hasNext());
		SudokuCoords sc = ssic.next();
		Assert.assertTrue(sc.getRow()==0 && sc.getCol()==0);
		ssic.next();
		sc = ssic.next();
		Assert.assertTrue(sc.getRow()==0 && sc.getCol()==2);
		ssic.next(); // 3
		ssic.next(); // 4
		ssic.next(); // 5
		ssic.next(); // 6
		ssic.next(); // 7
		sc = ssic.next(); // 8
		Assert.assertTrue(sc.getRow()==0 && sc.getCol()==8);
		Assert.assertFalse(ssic.hasNext());
	}

}
