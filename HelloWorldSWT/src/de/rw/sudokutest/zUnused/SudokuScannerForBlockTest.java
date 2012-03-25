package de.rw.sudokutest.zUnused;

import java.io.IOException;
import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.rw.sudoku.algorithms.SudokuFileReader;
import de.rw.sudoku.model.SudokuCoords;
import de.rw.sudoku.model.SudokuModel;
import de.rw.sudoku.zUnused.ScannerAction;
import de.rw.sudoku.zUnused.SudokuScannerForBlock;

public class SudokuScannerForBlockTest {

	private SudokuModel sm;
	
	@Before
	public void setUp() throws Exception {
		sm = new SudokuModel(9,3);
		try {
			SudokuFileReader.readSudokuFromFile(sm, "src\\sz20120224.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}

	@Test
	public void testSudokuScannerForBlock() {
		ArrayList<Integer> valuesInBlock = new ArrayList<Integer>();
		SudokuScannerForBlock<ArrayList<Integer>> ssfb = new SudokuScannerForBlock<ArrayList<Integer>>(sm, 4, 5, 
				new ScannerAction<ArrayList<Integer>>(sm,valuesInBlock){
					@Override
					public boolean act(int row, int col, ArrayList<Integer> _valuesInBlock) {
						_valuesInBlock.add(sm.getValue(new SudokuCoords(row, col)));
						return true;
					}
				});
		Assert.assertEquals(3, ssfb.getBlock1stCol());
		Assert.assertEquals(3, ssfb.getBlock1stRow());
		
		ssfb.scan();
		Assert.assertTrue(valuesInBlock.contains(new Integer(7)));
		Assert.assertTrue(valuesInBlock.contains(new Integer(2)));
		Assert.assertTrue(valuesInBlock.contains(new Integer(8)));
		Assert.assertTrue(valuesInBlock.contains(new Integer(3)));
		Assert.assertFalse(valuesInBlock.contains(new Integer(1)));
	}

}
