package de.rw.sudokutest.algorithms;
import java.io.IOException;
import java.util.LinkedList;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import de.rw.sudoku.algorithms.SudokuFileReader;
import de.rw.sudoku.algorithms.SudokuHelper;
import de.rw.sudoku.model.SudokuCoords;
import de.rw.sudoku.model.SudokuFieldValues;
import de.rw.sudoku.model.SudokuModel;


public class SudokuHelperTest extends TestCase {
	
	private SudokuModel sm;
	private SudokuHelper sh;

	@Before
	public void setUp() throws Exception {
		sm = SudokuModel.createTestModel1();
		sh = new SudokuHelper(sm);
	}

	@Test
	public void testFindUniquePlaceForValueInRowPart() {
		int res = sh.findUniquePlaceForValueInRowPart(5, 3, 8);
		Assert.assertEquals(3, res);
		res = sh.findUniquePlaceForValueInRowPart(3, 3, 8);
		Assert.assertEquals(-1, res);
		res = sh.findUniquePlaceForValueInRowPart(5, 3, 4);
		Assert.assertEquals(-1, res);
		res = sh.findUniquePlaceForValueInRowPart(6, 6, 9);
		Assert.assertEquals(7, res);
	}

	@Test
	public void testFindUniquePlaceForValueInColPart() {
		System.out.print(sm.toStringWithFlags());
		int res = sh.findUniquePlaceForValueInColPart(3, 3, 8);
		Assert.assertEquals(5, res);
		res = sh.findUniquePlaceForValueInColPart(5, 3, 8);
		Assert.assertEquals(-1, res);
		res = sh.findUniquePlaceForValueInColPart(3, 3, 4);
		Assert.assertEquals(-1, res);
		
		sm.setSuggestedValue(7, 0, 5);
		res = sh.findUniquePlaceForValueInColPart(8, 6, 5);
		Assert.assertEquals(6, res);
	}
	
	@Test
	public void testfindUniquePlaceForValueInCol_or_Row_or_Block() {
		try {
			SudokuFileReader.readSudokuFromFile(sm, "src\\sz20120224.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		sh = new SudokuHelper(sm);
		int res = sh.findUniquePlaceForValueInCol(/*col=*/1,/*number=*/new Integer(9));
		Assert.assertEquals(7, res);
		res = sh.findUniquePlaceForValueInRow(4, new Integer(3));
		Assert.assertEquals(7, res);
		SudokuCoords sc = sh.findUniquePlaceForValueInBlock(new SudokuCoords(6,0), new Integer(9));
		Assert.assertEquals(new SudokuCoords(7,1), sc);
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
		
		ll = sh.findConflicts(2, 3, 2);
		Assert.assertEquals(1, ll.size());
		
		sm = new SudokuModel(9,3);
		sh = new SudokuHelper(sm);
		ll = sh.findConflicts(0, 0, -1);
		Assert.assertEquals(0,ll.size());
	}

	
}
