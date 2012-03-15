package de.rw.sudokutest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.rw.sudokutest.algorithms.SudokuFileReaderTest;
import de.rw.sudokutest.algorithms.SudokuHelperTest;
import de.rw.sudokutest.algorithms.SudokuSiblingCrackerTest;
import de.rw.sudokutest.crackerMain.SudokuCrackerSWTTest;
import de.rw.sudokutest.model.SudokuFieldValuesTest;
import de.rw.sudokutest.model.SudokuModelTest;
import de.rw.sudokutest.model.iterators.SudokuIteratorRowTest;
import de.rw.sudokutest.model.iterators.SudokuIteratorTest;
import de.rw.sudokutest.model.iterators.SudokuIteratorWholeTest;
import de.rw.sudokutest.model.iterators.SudokuScannerForBlockTest;
import de.rw.sudokutest.views.SudokuViewTest;


@RunWith(Suite.class)
@SuiteClasses({ 
	SudokuCrackerSWTTest.class, 
	SudokuHelperTest.class, 
	SudokuModelTest.class, 
	SudokuViewTest.class, 
	SudokuFileReaderTest.class,
	SudokuFieldValuesTest.class,
	SudokuSiblingCrackerTest.class,
	SudokuScannerForBlockTest.class,
	SudokuIteratorTest.class,
	SudokuIteratorWholeTest.class,
	SudokuIteratorRowTest.class
	})
public class AllTests {

}
