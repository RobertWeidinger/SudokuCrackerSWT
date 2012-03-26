package de.rw.sudokutest.algorithms;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import de.rw.sudoku.algorithms.SudokuHelper;
import de.rw.sudoku.io.SudokuFileReaderWriter;
import de.rw.sudoku.model.SudokuCoords;
import de.rw.sudoku.model.SudokuFieldValues;
import de.rw.sudoku.model.SudokuModel;
import de.rw.sudoku.model.iterators.SudokuIterator.SubStructures;


public class SudokuHelperTest extends TestCase {
	
	private SudokuModel sm;
	private SudokuHelper sh;

	@Override
	@Before
	public void setUp() throws Exception {
		sm = SudokuModel.createTestModel1();
		sh = new SudokuHelper(sm);
	}
	
	private void readSZ20120224() {
		try {
			SudokuFileReaderWriter.readSudokuFromFile(sm, "D:\\dokus\\Robert\\SoftwareEntwicklung\\Testdaten\\sz20120224_Format2.0.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.assertTrue(false);
		}
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
		
		sm.setSuggestedValue(new SudokuCoords(7, 0), 5);
		res = sh.findUniquePlaceForValueInColPart(8, 6, 5);
		Assert.assertEquals(6, res);
	}
	
	@Test
	public void testfindUniquePlaceForValueInCol_or_Row_or_Block() {
		readSZ20120224();
		int res = sh.findUniquePlaceForValueInCol(/*col=*/1,/*number=*/new Integer(9));
		Assert.assertEquals(7, res);
		res = sh.findUniquePlaceForValueInRow(4, new Integer(3));
		Assert.assertEquals(7, res);
		SudokuCoords sc = sh.findUniquePlaceForValueInBlock(new SudokuCoords(6,0), new Integer(9));
		Assert.assertEquals(new SudokuCoords(7,1), sc);
	}

	
	@Test
	public void testFindSiblingBlockingValues()
	{
		readSZ20120224();
		setSomeBlockingValuesInSZ20120224();
		List<SudokuCoords> lSc = sh.findSiblingBlockingValuesInSubStruct(new SudokuCoords(3,4),SubStructures.BLOCK);
		Assert.assertEquals(2, lSc.size());
		
		lSc = sh.findSiblingBlockingValuesInSubStruct(new SudokuCoords(7, 8), SubStructures.ROW);
		Assert.assertEquals(1, lSc.size());
		
		lSc = sh.findSiblingBlockingValuesInSubStruct(new SudokuCoords(5, 8), SubStructures.COL);
		Assert.assertEquals(0, lSc.size());
	}

	private void setSomeBlockingValuesInSZ20120224() {
		sm.addBlockingValue(new SudokuCoords(7, 6), new Integer(1));
		sm.addBlockingValue(new SudokuCoords(7, 6), new Integer(2));
		sm.addBlockingValue(new SudokuCoords(7, 8), new Integer(1));
		sm.addBlockingValue(new SudokuCoords(7, 8), new Integer(2));
		
		sm.addBlockingValue(new SudokuCoords(3, 4), new Integer(9));
		sm.addBlockingValue(new SudokuCoords(3, 4), new Integer(6));
		sm.addBlockingValue(new SudokuCoords(3, 4), new Integer(5));
		sm.addBlockingValue(new SudokuCoords(5, 3), new Integer(9));
		sm.addBlockingValue(new SudokuCoords(5, 3), new Integer(6));
		sm.addBlockingValue(new SudokuCoords(5, 3), new Integer(5));
		sm.addBlockingValue(new SudokuCoords(5, 5), new Integer(9));
		sm.addBlockingValue(new SudokuCoords(5, 5), new Integer(6));
		sm.addBlockingValue(new SudokuCoords(5, 5), new Integer(5));
		
		sm.addBlockingValue(new SudokuCoords(1, 4), new Integer(4));
		sm.addBlockingValue(new SudokuCoords(1, 4), new Integer(7));
		sm.addBlockingValue(new SudokuCoords(1, 5), new Integer(4));
		sm.addBlockingValue(new SudokuCoords(1, 5), new Integer(7));
		
	}
	
	@Test 
	public void testFindConflicts()
	{
		readSZ20120224();
		LinkedList<SudokuFieldValues> ll = (LinkedList<SudokuFieldValues>)sh.findConflicts(new SudokuCoords(0, 0), 6);
		Assert.assertEquals(0,ll.size());
		ll = (LinkedList<SudokuFieldValues>)sh.findConflicts(new SudokuCoords(1, 3), 2);
		Assert.assertEquals(1,ll.size());
		SudokuFieldValues sfv = ll.getFirst();
		Assert.assertEquals(2, sfv.getRow());
		Assert.assertEquals(4, sfv.getCol());
		Assert.assertEquals(2, sfv.getValues().getFirst().intValue());
		
		ll = (LinkedList<SudokuFieldValues>)sh.findConflicts(new SudokuCoords(8, 2), 3);
		Assert.assertEquals(1,ll.size());
		sfv = ll.getFirst();
		Assert.assertEquals(3, sfv.getRow());
		Assert.assertEquals(2, sfv.getCol());
		Assert.assertEquals(3, sfv.getValues().getFirst().intValue());
		
		ll = (LinkedList<SudokuFieldValues>)sh.findConflicts(new SudokuCoords(2, 3), 2);
		Assert.assertEquals(1, ll.size());
		
		
		// Conflicts im Zsh. m. blocking Values:
		setSomeBlockingValuesInSZ20120224();
		
		List<SudokuFieldValues> lSfv = sh.findConflicts(new SudokuCoords(7, 5), new Integer(1));
		Assert.assertTrue(lSfv.size()>0);
		
		lSfv = sh.findConflicts(new SudokuCoords(4, 8), new Integer(1));
		Assert.assertTrue(lSfv.size()==0);
		
		lSfv = sh.findConflicts(new SudokuCoords(4, 4), new Integer(6));
		Assert.assertTrue(lSfv.size()>0);
		
		lSfv = sh.findConflicts(new SudokuCoords(5, 8), new Integer(6));
		Assert.assertTrue(lSfv.size()==0);

		lSfv = sh.findConflicts(new SudokuCoords(1, 4), new Integer(4));
		Assert.assertTrue(lSfv.size()==0);
		
		// und noch ein Test:
		sm = new SudokuModel(9,3);
		sh = new SudokuHelper(sm);
		ll = (LinkedList<SudokuFieldValues>)sh.findConflicts(new SudokuCoords(0, 0), -1);
		Assert.assertEquals(0,ll.size());
	}

	
}
