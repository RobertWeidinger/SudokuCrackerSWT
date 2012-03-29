package de.rw.sudokutest.algorithms;

import java.io.IOException;

import junit.framework.Assert;
import de.rw.sudoku.io.SudokuFileReaderWriter;
import de.rw.sudoku.model.SudokuModel;

public class AlgorithmsTestHelper {
	
	static public boolean modelIsEqualToFile(SudokuModel sm, String fileName)
	{
		SudokuModel sm2 = new SudokuModel(9,3);
		try {
			SudokuFileReaderWriter.readSudokuFromFile(sm2, "src\\de\\rw\\sudokutest\\zzRefdata\\" + fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		
		return sm.equalsIgnoringOldValues(sm2);
	}


}
