package de.rw.sudokutest.algorithms;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.rw.sudoku.algorithms.AlternatingAlgorithms;
import de.rw.sudoku.io.SudokuFileReaderWriter;
import de.rw.sudoku.model.SudokuModel;

public class AlternatingAlgorithmsTest {

	private SudokuModel sm;

	private void log(String s)
	{
		//System.out.println(s);
	}

	@Before
	public void setUp() throws Exception {
	}
	
	
	@Test
	public void testRun() {
		sm = new SudokuModel(9,3);
		try {
			SudokuFileReaderWriter.readSudokuFromFile(sm, "src\\de\\rw\\sudokutest\\zzTestdata\\sz20120224_Format2.0.txt");
		} catch (IOException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		AlternatingAlgorithms aa = new AlternatingAlgorithms(sm);
		boolean success = aa.run();
		Assert.assertEquals(true, success);
		log(sm.toStringWithFlags());
		Assert.assertEquals(true, AlgorithmsTestHelper.modelIsEqualToFile(sm,"sz20120224_Format2.0_geloest.txt"));
	}

	@Test
	public void testRun2() {
		sm = new SudokuModel(9,3);
		try {
			SudokuFileReaderWriter.readSudokuFromFile(sm, "src\\de\\rw\\sudokutest\\zzTestdata\\BlackberryProfi4_Format2.0.txt");
		} catch (IOException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		AlternatingAlgorithms aa = new AlternatingAlgorithms(sm);
		boolean success = aa.run();
		Assert.assertEquals(true, success);
		log(sm.toStringWithFlags());
		Assert.assertEquals(true, AlgorithmsTestHelper.modelIsEqualToFile(sm,"BlackberryProfi4_Loesung_Format2.0.txt"));
	}

	
	@Test
	public void testRun3() {
		sm = new SudokuModel(9,3);
		try {
			SudokuFileReaderWriter.readSudokuFromFile(sm, "src\\de\\rw\\sudokutest\\zzTestdata\\KoCo11_Experte_Nr1_Format2.0.txt");
		} catch (IOException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		AlternatingAlgorithms aa = new AlternatingAlgorithms(sm);
		boolean success = aa.run();
		Assert.assertEquals(true, success); // Nur Test, dass Iterationsgrenze nicht erreicht wird.
	}

}
