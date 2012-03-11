package de.rw.sudokutest.model;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.rw.sudoku.model.SudokuModel;


public class SudokuModelTest {

	private SudokuModel sm;
	
	@Before
	public void setUp() throws Exception {
		sm = SudokuModel.createTestModel1();
	}

	@Test
	public void testSetFixedValue() {
		sm.setFixedValue(0, 5, 9);
		Assert.assertTrue(sm.getSudokuEntry(0, 5).isFixed());
	}

	@Test
	public void testSetEmptyValue() {
		sm.setEmptyValue(0, 5);
		Assert.assertTrue(sm.isEmpty(0, 5));
	}

	@Test
	public void testSetSuggestedValue() {
		sm.setSuggestedValue(0, 5, 9);
		Assert.assertFalse(sm.getSudokuEntry(0, 5).isEmpty());
		Assert.assertFalse(sm.getSudokuEntry(0, 5).isFixed());
		
	}

	@Test
	public void testMakeValuesFixed() {
		sm.makeValuesFixed();
		Assert.assertTrue(sm.getSudokuEntry(1, 0).isFixed());
	}

	@Test
	public void testGetValue() {
		Assert.assertTrue(sm.isEmpty(2, 0));
		Assert.assertEquals(1, sm.getValue(1, 0).intValue());
	}

	@Test
	public void testIsFixed() {
		Assert.assertEquals(true, sm.isFixed(0, 0));
	}
	
	@Test
	public void testIsConsistentModel() {
		Assert.assertTrue( sm.isValidModel());
		sm = SudokuModel.createCorruptModel1();
		Assert.assertFalse( sm.isValidModel());
	}

	
	@Test
	public void testUndo() {
		Assert.assertEquals(1, sm.getValue(0,7).intValue());
		Assert.assertFalse(sm.getSudokuEntry(0, 7).isFixed());
		sm.undo();
		Assert.assertTrue(sm.getSudokuEntry(0, 7).isEmpty());
		Assert.assertTrue(sm.isValidModel());
	}

}
