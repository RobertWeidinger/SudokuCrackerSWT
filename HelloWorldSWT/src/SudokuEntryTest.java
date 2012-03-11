import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;


public class SudokuEntryTest {

	@Test
	public void testRemovePossibleValue() {
		SudokuEntry se = new SudokuEntry(3,2);
		se.setValue(5);
		se.addPossibleValue(1);
		se.addPossibleValue(2);
		se.addPossibleValue(3);
		se.removePossibleValue(2);
		ArrayList<Integer> alPossibleValues = se.getPossibleValues();
		Assert.assertEquals(2, alPossibleValues.size());
		Integer i0 = alPossibleValues.get(0);
		Integer i1 = alPossibleValues.get(1);
		Assert.assertEquals(1, i0.intValue());
		Assert.assertEquals(3, i1.intValue());
	}

}
