import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;


public class SudokuModelTest {

	private SudokuModel sm;
	
	@Before
	public void setUp() throws Exception {
		sm = SudokuModel.createTestModel1();
	}

	@Test
	public void testSetFixedValue() {
		sm.setFixedValue(0, 5, 9);
		Assert.assertEquals(9, sm.getFixedValue(0, 5).intValue());
	}

	@Test
	public void testSetEmptyValue() {
		sm.setEmptyValue(0, 5);
		Assert.assertEquals(-1, sm.getValue(0, 5).intValue());
	}

	@Test
	public void testSetSuggestedValue() {
		sm.setSuggestedValue(0, 5, 9);
		Assert.assertEquals(9, sm.getSuggestedValue(0, 5).intValue());
		
	}

	@Test
	public void testGetFixedValue() {
		Assert.assertEquals(8, sm.getFixedValue(1, 1).intValue());
	}

	@Test
	public void testGetSuggestedValue() {
		Assert.assertEquals(1, sm.getSuggestedValue(1, 0).intValue());
	}

	@Test
	public void testMakeValuesFixed() {
		sm.makeValuesFixed();
		Assert.assertEquals(1, sm.getFixedValue(1, 0).intValue());
	}

	@Test
	public void testGetValue() {
		Assert.assertEquals(-1, sm.getValue(2, 0).intValue());
		Assert.assertEquals(1, sm.getValue(1, 0).intValue());
	}

	@Test
	public void testIsFixed() {
		Assert.assertEquals(true, sm.isFixed(0, 0));
	}
	
	@Test
	public void testIsConsistentModel() {
		Assert.assertTrue( sm.isConsistentModel());
		sm = SudokuModel.createCorruptModel1();
		Assert.assertFalse( sm.isConsistentModel());
	}

	
	@Test
	public void testUndo() {
		System.out.println(sm.toStringWithStepValues());
		Assert.assertEquals(1, sm.getSuggestedValue(0,7).intValue());
		Assert.assertEquals(-1, sm.getFixedValue(0,7).intValue());
		sm.undo();
		Assert.assertEquals(-1, sm.getFixedValue(0,7).intValue());
		Assert.assertEquals(-1, sm.getSuggestedValue(0,7).intValue());
		Assert.assertTrue(sm.isConsistentModel());
	}

}
