package de.rw.sudokutest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.rw.sudokutest.algorithms.SudokuBruteForceCrackerTest;
import de.rw.sudokutest.algorithms.SudokuFileReaderTest;
import de.rw.sudokutest.algorithms.SudokuHelperTest;
import de.rw.sudokutest.crackerMain.SudokuCrackerSWTTest;
import de.rw.sudokutest.model.SudokuFieldValuesTest;
import de.rw.sudokutest.model.SudokuModelTest;
import de.rw.sudokutest.model.iterators.SubStructIteratorColsTest;
import de.rw.sudokutest.model.iterators.SubStructIteratorRowsTest;
import de.rw.sudokutest.model.iterators.SudokuIteratorBlockTest;
import de.rw.sudokutest.model.iterators.SudokuIteratorColTest;
import de.rw.sudokutest.model.iterators.SudokuIteratorRowTest;
import de.rw.sudokutest.model.iterators.SudokuIteratorTest;
import de.rw.sudokutest.model.iterators.SudokuIteratorWholeTest;
import de.rw.sudokutest.views.SudokuViewTest;
import de.rw.sudokutest.zUnused.SudokuScannerForBlockTest;


@RunWith(Suite.class)
@SuiteClasses({ 
	SudokuCrackerSWTTest.class, 
	SudokuHelperTest.class, 
	SudokuModelTest.class, 
	SudokuViewTest.class, 
	SudokuFileReaderTest.class,
	SudokuFieldValuesTest.class,
	SudokuScannerForBlockTest.class,
	SudokuIteratorTest.class,
	SudokuIteratorWholeTest.class,
	SudokuIteratorRowTest.class,
	SudokuIteratorColTest.class,
	SudokuIteratorBlockTest.class,
	SudokuBruteForceCrackerTest.class,
	SubStructIteratorRowsTest.class,
	SubStructIteratorColsTest.class
	})
public class AllTests {

}
