package de.rw.sudokutest.model;

import junit.framework.Assert;

import org.junit.Test;

import de.rw.sudoku.model.SudokuEntry;

public class SudokuEntryTest {

	@Test
	public void testScanFromDumpString() {
		Assert.assertEquals(3,SudokuEntry.testDumpToStringAndScan1());
	}
	
	@Test
	public void testEquals()
	{
		Assert.assertEquals(5,SudokuEntry.testEquals());
	}
	
	@Test
	public void testConstructor2()
	{
		Assert.assertEquals(true,SudokuEntry.testConstructor2());
	}
	

}
