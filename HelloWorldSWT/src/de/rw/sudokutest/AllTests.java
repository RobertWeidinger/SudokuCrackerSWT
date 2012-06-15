package de.rw.sudokutest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.rw.sudokutest.algorithms.AlternatingAlgorithmsTest;
import de.rw.sudokutest.algorithms.PossiblePlacesAlgorithmTest;
import de.rw.sudokutest.algorithms.PossiblePlacesListTest;
import de.rw.sudokutest.algorithms.PossiblePlacesTest;
import de.rw.sudokutest.algorithms.SudokuBruteForceCrackerTest;
import de.rw.sudokutest.algorithms.SudokuHelperTest;
import de.rw.sudokutest.crackerMain.SudokuCrackerSWTTest;
import de.rw.sudokutest.io.SudokuFileReaderWriterTest;
import de.rw.sudokutest.model.SudokuCoordsTest;
import de.rw.sudokutest.model.SudokuEntryTest;
import de.rw.sudokutest.model.SudokuFieldValuesTest;
import de.rw.sudokutest.model.SudokuModelTest;
import de.rw.sudokutest.model.iterators.SubStructIteratorBlocksTest;
import de.rw.sudokutest.model.iterators.SubStructIteratorColsTest;
import de.rw.sudokutest.model.iterators.SubStructIteratorRowsTest;
import de.rw.sudokutest.model.iterators.SudokuIteratorBlockTest;
import de.rw.sudokutest.model.iterators.SudokuIteratorColTest;
import de.rw.sudokutest.model.iterators.SudokuIteratorRowTest;
import de.rw.sudokutest.model.iterators.SudokuIteratorTest;
import de.rw.sudokutest.model.iterators.SudokuIteratorWholeTest;
import de.rw.sudokutest.views.SudokuViewTest;


@RunWith(Suite.class)
@SuiteClasses({ 
	SudokuCrackerSWTTest.class, 
	SudokuCoordsTest.class,
	SudokuEntryTest.class,
	SudokuHelperTest.class, 
	SudokuModelTest.class, 
	SudokuViewTest.class, 
	SudokuFileReaderWriterTest.class,
	SudokuFieldValuesTest.class,
	SudokuIteratorTest.class,
	SudokuIteratorWholeTest.class,
	SudokuIteratorRowTest.class,
	SudokuIteratorColTest.class,
	SudokuIteratorBlockTest.class,
	SudokuBruteForceCrackerTest.class,
	SubStructIteratorRowsTest.class,
	SubStructIteratorColsTest.class,
	SubStructIteratorBlocksTest.class,
	PossiblePlacesTest.class,
	PossiblePlacesListTest.class,
	PossiblePlacesAlgorithmTest.class,
	AlternatingAlgorithmsTest.class
	})
public class AllTests {

}
