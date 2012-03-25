package de.rw.sudokutest.algorithms;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.rw.sudoku.algorithms.SudokuFileReader;
import de.rw.sudoku.model.SudokuCoords;
import de.rw.sudoku.model.SudokuModel;


public class SudokuFileReaderTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testReadSudokuFromFile() {
		SudokuModel sm = new SudokuModel(9,3);
		try {
			SudokuFileReader.readSudokuFromFile(sm, "src\\sz20120224.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals(2, sm.getValue(new SudokuCoords(3, 5)).intValue());
		Assert.assertEquals(true, sm.isValidModel());
	}

}
