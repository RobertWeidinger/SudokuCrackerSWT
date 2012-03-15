package de.rw.sudokutest.model.iterators;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.rw.sudoku.model.SudokuCoords;
import de.rw.sudoku.model.SudokuModel;
import de.rw.sudoku.model.iterators.SudokuIteratorWhole;

public class SudokuIteratorWholeTest {

	private SudokuModel sm;
	
	@Before
	public void setUp() throws Exception {
		sm = SudokuModel.createTestModel1();
	}

	@Test
	public void testNext() {
		SudokuIteratorWhole sia = new SudokuIteratorWhole(sm.getSize(), sm.getBlockSize());
		int i=1;
		boolean b81=false;
		while (sia.hasNext())
		{
			SudokuCoords sc = sia.next();
			if (i==1)
			{
				Assert.assertEquals(0, sc.getRow());
				Assert.assertEquals(0, sc.getCol());
			}
			else if (i==10)
			{
				Assert.assertEquals(1, sc.getRow());
				Assert.assertEquals(0, sc.getCol());
			}
			else if (i==81)
			{
				Assert.assertEquals(8, sc.getRow());
				Assert.assertEquals(8, sc.getCol());
				b81=true;
			}
			i++;
		}
		Assert.assertTrue(b81);
	}

}
