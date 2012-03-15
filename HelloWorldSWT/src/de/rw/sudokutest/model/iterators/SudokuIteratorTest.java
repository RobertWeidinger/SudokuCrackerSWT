package de.rw.sudokutest.model.iterators;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.rw.sudoku.model.SudokuCoords;
import de.rw.sudoku.model.SudokuModel;
import de.rw.sudoku.model.iterators.SudokuIterator;

public class SudokuIteratorTest {
	
	private SudokuModel sm;
	
	@Before
	public void setUp() throws Exception {
		sm = SudokuModel.createTestModel1();
	}
	
	@Test
	public void testSudokuIterator() {
		//fail("Not yet implemented");
	}

	@Test
	public void testEnum() {
		int i=0;
		String s=new String();
		for (final SudokuIterator.SubStructures subStruct : SudokuIterator.SubStructures.REAL)
			s+=" "+subStruct.toString()+"["+(i++)+"]";
		Assert.assertEquals(" ROW[0] COL[1] BLOCK[2]", s);
	}
	
	@Test
	public void testFactory() {
		SudokuIterator si = SudokuIterator.createIterator(sm.getSize(),sm.getBlockSize(),
								new SudokuCoords(0,0),
								SudokuIterator.SubStructures.WHOLE);
		Assert.assertEquals(0, si.next().getCol());
		si = SudokuIterator.createIterator(sm.getSize(),sm.getBlockSize(),
				new SudokuCoords(3,0),
				SudokuIterator.SubStructures.ROW);
		Assert.assertEquals(0, si.next().getCol());
	}
}
