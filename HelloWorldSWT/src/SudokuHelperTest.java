import java.io.IOException;
import java.util.LinkedList;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;


public class SudokuHelperTest extends TestCase {
	
	private SudokuModel sm;
	private SudokuHelper sh;

	@Before
	public void setUp() throws Exception {
		sm = SudokuModel.createTestModel1();
		sh = new SudokuHelper(sm);
	}

	@Test
	public void testFindValueInRow() {
		int col = sh.findValueInRow(2, 3);
		Assert.assertEquals(-1, col);
		col = sh.findValueInRow(4, 5);
		Assert.assertEquals(4,col);
	}

	@Test
	public void testFindValueInBlock() {
		int res[]=sh.findValueInBlock(3, 0, 3);
		Assert.assertEquals(3, res[0]);
		Assert.assertEquals(2, res[1]);
		res=sh.findValueInBlock(6, 3, 3);
		Assert.assertEquals(-1, res[0]);
		Assert.assertEquals(-1, res[1]);
		res=sh.findValueInBlock(6, 3, 6);
		Assert.assertEquals(6, res[0]);
		Assert.assertEquals(5, res[1]);
	}
	
	@Test
	public void testFindSiblingsInRows() {
		LinkedList<SudokuFieldValues> ll = sh.findSiblingsInRows(0, 9);
		Assert.assertEquals(2, ll.size());
		SudokuFieldValues sfv = ll.getFirst();
		Assert.assertEquals(0, sfv.getRow());
		Assert.assertEquals(0, sfv.getCol());
		Assert.assertEquals(9, sfv.getValues().getFirst().intValue());
		sfv = ll.getLast();
		Assert.assertEquals(2, sfv.getRow());
		Assert.assertEquals(8, sfv.getCol());
		Assert.assertEquals(9, sfv.getValues().getFirst().intValue());
		
		ll = sh.findSiblingsInRows(0, 8);
		Assert.assertEquals(0,ll.size());
	}

	@Test
	public void testFindSiblingsInCols() {
		LinkedList<SudokuFieldValues> ll = sh.findSiblingsInCols(2, 1);
		Assert.assertEquals(2, ll.size());
		SudokuFieldValues sfv = ll.getFirst();
		Assert.assertEquals(0, sfv.getRow());
		Assert.assertEquals(7, sfv.getCol());
		Assert.assertEquals(1, sfv.getValues().getFirst().intValue());
		sfv = ll.getLast();
		Assert.assertEquals(8, sfv.getRow());
		Assert.assertEquals(8, sfv.getCol());
		Assert.assertEquals(1, sfv.getValues().getFirst().intValue());
		
		ll = sh.findSiblingsInCols(0, 8);
		Assert.assertEquals(0,ll.size());
	}

	@Test
	public void testFindUniquePlaceForNumberInRowPart() {
		System.out.print(sm.toStringWithStepValues());
		int res = sh.findUniquePlaceForNumberInRowPart(5, 3, 5, 8);
		Assert.assertEquals(3, res);
		res = sh.findUniquePlaceForNumberInRowPart(3, 3, 5, 8);
		Assert.assertEquals(-1, res);
		res = sh.findUniquePlaceForNumberInRowPart(5, 3, 5, 4);
		Assert.assertEquals(-1, res);
	}
	
	@Test 
	public void testFindConflicts()
	{
		try {
			SudokuFileReader.readSudokuFromFile(sm, "src\\sz20120224.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		sh = new SudokuHelper(sm);
		LinkedList<SudokuFieldValues> ll = sh.findConflicts(0, 0, 6);
		Assert.assertEquals(0,ll.size());
		ll = sh.findConflicts(1, 3, 2);
		Assert.assertEquals(1,ll.size());
		SudokuFieldValues sfv = ll.getFirst();
		Assert.assertEquals(2, sfv.getRow());
		Assert.assertEquals(4, sfv.getCol());
		Assert.assertEquals(2, sfv.getValues().getFirst().intValue());
		
		ll = sh.findConflicts(8, 2, 3);
		Assert.assertEquals(1,ll.size());
		sfv = ll.getFirst();
		Assert.assertEquals(3, sfv.getRow());
		Assert.assertEquals(2, sfv.getCol());
		Assert.assertEquals(3, sfv.getValues().getFirst().intValue());
		
	}

	
}
