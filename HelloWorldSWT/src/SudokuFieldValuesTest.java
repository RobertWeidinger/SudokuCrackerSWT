import java.util.LinkedList;

import junit.framework.Assert;

import org.junit.Test;


public class SudokuFieldValuesTest {

	@Test
	public void testEqualsSudokuFieldValues() {

		LinkedList<Integer> llI = new LinkedList<Integer>();
		llI.add(new Integer(3));
		llI.add(new Integer(4));
		SudokuFieldValues sfv = new SudokuFieldValues(1,2,llI);

		LinkedList<Integer> llI2 = new LinkedList<Integer>();
		llI2.add(new Integer(3));
		llI2.add(new Integer(4));
		SudokuFieldValues sfv2 = new SudokuFieldValues(1,2,llI2);
		
		Assert.assertTrue(sfv.equals(sfv2));
		
		LinkedList<Integer> llI3 = new LinkedList<Integer>();
		llI3.add(new Integer(4));
		llI3.add(new Integer(3));
		SudokuFieldValues sfv3 = new SudokuFieldValues(1,2,llI3);
		
		Assert.assertTrue(sfv.equals(sfv3));
		
		LinkedList<Integer> llI4 = new LinkedList<Integer>();
		llI4.add(new Integer(4));
		llI4.add(new Integer(2));
		SudokuFieldValues sfv4 = new SudokuFieldValues(1,2,llI4);
		
		Assert.assertFalse(sfv.equals(sfv4));
		
	}

}
