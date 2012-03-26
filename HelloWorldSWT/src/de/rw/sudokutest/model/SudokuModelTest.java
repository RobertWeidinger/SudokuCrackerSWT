package de.rw.sudokutest.model;
import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.rw.sudoku.model.SudokuCoords;
import de.rw.sudoku.model.SudokuModel;


public class SudokuModelTest {

	private SudokuModel sm;
	
	@Before
	public void setUp() throws Exception {
		sm = SudokuModel.createTestModel1();
	}

	@Test
	public void testSetFixedValue() {
		SudokuCoords sc = new SudokuCoords(0,5);
		sm.setFixedValue(sc, 9);
		Assert.assertTrue(sm.isFixed(sc));
	}

	@Test
	public void testSetEmptyValue() {
		SudokuCoords sc = new SudokuCoords(0,5);
		sm.setEmptyValue(sc);
		Assert.assertTrue(sm.isEmpty(sc));
	}

	@Test
	public void testSetSuggestedValue() {
		SudokuCoords sc = new SudokuCoords(0,5);
		sm.setSuggestedValue(sc, 9);
		Assert.assertFalse(sm.isEmpty(sc));
		Assert.assertFalse(sm.isFixed(sc));
		
	}

	@Test
	public void testMakeValuesFixed() {
		SudokuCoords sc = new SudokuCoords(1,0);
		sm.makeValuesFixed();
		Assert.assertTrue(sm.isFixed(sc));
	}

	@Test
	public void testGetValue() {
		SudokuCoords sc = new SudokuCoords(2,0);
		Assert.assertTrue(sm.isEmpty(sc));
		Assert.assertEquals(1, sm.getValue(new SudokuCoords(1,0)).intValue());
	}

	@Test
	public void testIsFixed() {
		SudokuCoords sc = new SudokuCoords(0,0);
		Assert.assertEquals(true, sm.isFixed(sc));
	}
	
	@Test
	public void testIsConsistentModel() {
		Assert.assertTrue( sm.isValidModel());
		sm = SudokuModel.createCorruptModel1();
		Assert.assertFalse( sm.isValidModel());
	}

	
	@Test
	public void testUndo() {
		SudokuCoords sc = new SudokuCoords(0,7);

		Assert.assertEquals(1, sm.getValue(sc).intValue());
		Assert.assertFalse(sm.isFixed(sc));
		sm.undo();
		Assert.assertTrue(sm.isEmpty(sc));
		Assert.assertTrue(sm.isValidModel());
	}

	@Test
	public void testRemovePossibleValue() {
		SudokuCoords sc = new SudokuCoords(3,2);

		sm.setFixedValue(sc,5);
		sm.addBlockingValue(sc,1);
		sm.addBlockingValue(sc,2);
		sm.addBlockingValue(sc,3);
		sm.removeBlockingValue(sc,2);
		ArrayList<Integer> alPossibleValues = sm.getBlockingValues(sc);
		Assert.assertEquals(2, alPossibleValues.size());
		Integer i0 = alPossibleValues.get(0);
		Integer i1 = alPossibleValues.get(1);
		Assert.assertEquals(1, i0.intValue());
		Assert.assertEquals(3, i1.intValue());
	}
	
	@Test
	public void testEqualBlockingValues()
	{
		SudokuCoords sc = new SudokuCoords(3,2);
		SudokuCoords sc2 = new SudokuCoords(0,5);
		sm.addBlockingValue(sc, 1);
		sm.addBlockingValue(sc, 2);
		sm.addBlockingValue(sc, 5);
		sm.addBlockingValue(sc2, 5);
		sm.addBlockingValue(sc2, 1);
		sm.addBlockingValue(sc2, 2);
		boolean bEqual = sm.equalBlockingValues(sc, sc2);
		Assert.assertEquals(true,bEqual);
		sm.removeBlockingValue(sc, 2);
		bEqual = sm.equalBlockingValues(sc, sc2);
		Assert.assertEquals(false,bEqual);
	}
	
	@Test
	public void testEqualsIgnoringOldValues()
	{
		SudokuModel sm1 = SudokuModel.createTestModel1();
		SudokuModel sm2 = SudokuModel.createTestModel1();
		SudokuCoords sc = new SudokuCoords(3,2);
		sm1.addBlockingValue(sc, 1);
		sm1.addBlockingValue(sc, 2);
		sm1.addBlockingValue(sc, 5);
		sm2.addBlockingValue(sc, 1);
		sm2.addBlockingValue(sc, 2);
		sm2.addBlockingValue(sc, 5);
		
		boolean bEqual = sm1.equalsIgnoringOldValues(sm2);
		Assert.assertEquals(true,bEqual);
		
		sm1.removeBlockingValue(sc, 2);
		bEqual = sm1.equalsIgnoringOldValues(sm2);
		Assert.assertEquals(false,bEqual);
	}
}
