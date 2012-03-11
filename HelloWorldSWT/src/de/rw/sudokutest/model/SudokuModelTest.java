package de.rw.sudokutest.model;
import java.util.ArrayList;

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
		Assert.assertTrue(sm.isFixed(0, 5));
	}

	@Test
	public void testSetEmptyValue() {
		sm.setEmptyValue(0, 5);
		Assert.assertTrue(sm.isEmpty(0, 5));
	}

	@Test
	public void testSetSuggestedValue() {
		sm.setSuggestedValue(0, 5, 9);
		Assert.assertFalse(sm.isEmpty(0, 5));
		Assert.assertFalse(sm.isFixed(0, 5));
		
	}

	@Test
	public void testMakeValuesFixed() {
		sm.makeValuesFixed();
		Assert.assertTrue(sm.isFixed(1, 0));
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
		Assert.assertFalse(sm.isFixed(0, 7));
		sm.undo();
		Assert.assertTrue(sm.isEmpty(0, 7));
		Assert.assertTrue(sm.isValidModel());
	}

	@Test
	public void testRemovePossibleValue() {
		sm.setFixedValue(3,2,5);
		sm.addPossibleValue(3,2,1);
		sm.addPossibleValue(3,2,2);
		sm.addPossibleValue(3,2,3);
		sm.removePossibleValue(3,2,2);
		ArrayList<Integer> alPossibleValues = sm.getPossibleValues(3,2);
		Assert.assertEquals(2, alPossibleValues.size());
		Integer i0 = alPossibleValues.get(0);
		Integer i1 = alPossibleValues.get(1);
		Assert.assertEquals(1, i0.intValue());
		Assert.assertEquals(3, i1.intValue());
	}
}
