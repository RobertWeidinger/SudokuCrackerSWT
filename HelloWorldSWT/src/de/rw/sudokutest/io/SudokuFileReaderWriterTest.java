package de.rw.sudokutest.io;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.rw.sudoku.io.SudokuFileReaderWriter;
import de.rw.sudoku.model.SudokuCoords;
import de.rw.sudoku.model.SudokuModel;


public class SudokuFileReaderWriterTest {

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
			SudokuFileReaderWriter.readSudokuFromFile(sm, "D:\\dokus\\Robert\\SoftwareEntwicklung\\Testdaten\\sz20120224_Format2.0.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals(2, sm.getValue(new SudokuCoords(3, 5)).intValue());
		Assert.assertEquals(true, sm.isValidModel());
	}
	
	@Test
	public void testWriteSudokuToFile()
	{
		String longPath = new String("src\\modifiedTestModel1.txt");
		SudokuModel sm = SudokuModel.createTestModel1();
		sm.addBlockingValue(new SudokuCoords(7,1), 2);
		sm.addBlockingValue(new SudokuCoords(7,1), 4);
		sm.addBlockingValue(new SudokuCoords(7,1), 6);
		
		try {
			SudokuFileReaderWriter.writeSudokuToFile(sm, longPath);
		} catch (IOException e) {
			Assert.fail("Exception beim Schreiben ins File");
			e.printStackTrace();
		}
		// FEHLT: Datei mit Zieldatei vergleichen.
	}

}
