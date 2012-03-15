package de.rw.sudokutest.model.iterators;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.rw.sudoku.model.SudokuCoords;
import de.rw.sudoku.model.SudokuModel;
import de.rw.sudoku.model.iterators.SudokuIteratorCol;

public class SudokuIteratorColTest {

	private SudokuModel sm;
	
	@Before
	public void setUp() throws Exception {
		sm = SudokuModel.createTestModel1();
	}
	@Test
	public void testNext() {
		SudokuIteratorCol sir = new SudokuIteratorCol(sm.getSize(), sm.getBlockSize(), 3);
		int i=1;
		while (sir.hasNext())
		{
			SudokuCoords sc = sir.next();
			if (i==2)
				Assert.assertEquals(1, sc.getRow());
			else if (i==9)
				Assert.assertEquals(8, sc.getRow());
			i++;
		}
		Assert.assertEquals(10, i);
	}
}
