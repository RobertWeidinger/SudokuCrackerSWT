package de.rw.sudokutest.model.iterators;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.rw.sudoku.model.SudokuCoords;
import de.rw.sudoku.model.SudokuModel;

public class SudokuIteratorRowTest {

	private SudokuModel sm;
	
	@Before
	public void setUp() throws Exception {
		sm = SudokuModel.createTestModel1();
	}
	@Test
	public void testNext() {
		SudokuIteratorRow sir = new SudokuIteratorRow(sm.getSize(), sm.getBlockSize(), 3);
		int i=1;
		while (sir.hasNext())
		{
			SudokuCoords sc = sir.next();
			if (i==2)
				Assert.assertEquals(1, sc.getCol());
			else if (i==9)
				Assert.assertEquals(8, sc.getCol());
			i++;
		}
	}

}
