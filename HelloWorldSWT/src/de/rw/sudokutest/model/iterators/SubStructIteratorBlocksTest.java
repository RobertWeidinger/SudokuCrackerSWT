package de.rw.sudokutest.model.iterators;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.rw.sudoku.model.SudokuCoords;
import de.rw.sudoku.model.iterators.SubStructIteratorBlocks;

public class SubStructIteratorBlocksTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testNext() {
		SubStructIteratorBlocks ssib = new SubStructIteratorBlocks(9, 3);
		Assert.assertTrue(ssib.hasNext());
		SudokuCoords sc = ssib.next();
		Assert.assertTrue(sc.getRow()==0 && sc.getCol()==0);
		ssib.next();
		sc = ssib.next();
		Assert.assertTrue(sc.getRow()==0 && sc.getCol()==6);
		ssib.next(); // 
		ssib.next(); // 
		ssib.next(); // 
		ssib.next(); // 
		ssib.next(); // 
		sc = ssib.next(); // 
		Assert.assertTrue(sc.getRow()==6 && sc.getCol()==6);
		Assert.assertFalse(ssib.hasNext());
	}

}
