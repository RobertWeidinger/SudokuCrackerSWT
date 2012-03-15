package de.rw.sudokutest.model.iterators;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.rw.sudoku.model.SudokuCoords;
import de.rw.sudoku.model.SudokuModel;
import de.rw.sudoku.model.iterators.SudokuIteratorBlock;

public class SudokuIteratorBlockTest {

private SudokuModel sm;
	
	@Before
	public void setUp() throws Exception {
		sm = SudokuModel.createTestModel1();
	}

	@Test
	public void testSudokuIteratorBlock_and_next() {
		int size = sm.getSize();
		int blockSize = sm.getBlockSize();
		SudokuCoords sco = new SudokuCoords(7, 5);
		SudokuCoords scBlock = SudokuIteratorBlock.getBlockStartStatic(3, sco);
		SudokuIteratorBlock sib = new SudokuIteratorBlock(size, blockSize, scBlock);
		SudokuCoords sc = sib.next();
		Assert.assertEquals(6, sc.getRow());
		Assert.assertEquals(3, sc.getCol());
		sib.next();
		sib.next();
		sc = sib.next();
		Assert.assertEquals(7, sc.getRow());
		Assert.assertEquals(3, sc.getCol());
	}

	@Test
	public void testHasNext_and_next() {
		SudokuIteratorBlock sib = new SudokuIteratorBlock(sm.getSize(), sm.getBlockSize(), new SudokuCoords(7, 5));
		SudokuCoords sc = null;
		while (sib.hasNext())
		{
			sc = sib.next();
		}
		Assert.assertEquals(8, sc.getRow());
		Assert.assertEquals(5, sc.getCol());
	}
	
}
