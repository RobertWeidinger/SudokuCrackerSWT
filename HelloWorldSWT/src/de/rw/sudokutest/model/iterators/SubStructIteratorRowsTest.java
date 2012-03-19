package de.rw.sudokutest.model.iterators;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.rw.sudoku.model.SudokuCoords;
import de.rw.sudoku.model.iterators.SubStructIteratorRows;

public class SubStructIteratorRowsTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testNext() {
		SubStructIteratorRows ssir = new SubStructIteratorRows(9, 3);
		Assert.assertTrue(ssir.hasNext());
		SudokuCoords sc = ssir.next();
		Assert.assertTrue(sc.getRow()==0 && sc.getCol()==0);
		ssir.next();
		sc = ssir.next();
		Assert.assertTrue(sc.getRow()==2 && sc.getCol()==0);
		ssir.next(); // 3
		ssir.next(); // 4
		ssir.next(); // 5
		ssir.next(); // 6
		ssir.next(); // 7
		sc = ssir.next(); // 8
		Assert.assertTrue(sc.getRow()==8 && sc.getCol()==0);
		Assert.assertFalse(ssir.hasNext());
	}

}
