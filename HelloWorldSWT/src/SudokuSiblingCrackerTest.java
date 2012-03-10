import java.io.IOException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;


public class SudokuSiblingCrackerTest {

	private SudokuModel sm;
	private SudokuSiblingCracker ssc;
	
	@Before
	public void setUp() throws Exception {
		sm = new SudokuModel(9, 3);
		try {
			SudokuFileReader.readSudokuFromFile(sm, "src\\sz20120224.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		ssc = new SudokuSiblingCracker(sm);
	}

	@Test
	public void testFindSiblingBasedSolutionsOneIteration() {
		boolean b = false;
		try {
			b = ssc.findSiblingBasedSolutionsOneIteration();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertTrue(b);
		Assert.assertEquals(7, sm.getValue(5, 0).intValue());
		Assert.assertEquals(9, sm.getValue(7, 1).intValue());
		Assert.assertEquals(2, sm.getValue(8, 3).intValue());
		Assert.assertEquals(3, sm.getValue(4, 7).intValue());
	}

}
