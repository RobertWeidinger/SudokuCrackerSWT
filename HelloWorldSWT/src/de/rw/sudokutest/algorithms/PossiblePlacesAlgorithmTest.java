package de.rw.sudokutest.algorithms;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.rw.sudoku.algorithms.PossiblePlaces;
import de.rw.sudoku.algorithms.PossiblePlacesAlgorithm;
import de.rw.sudoku.algorithms.PossiblePlacesList;
import de.rw.sudoku.algorithms.SudokuBruteForceCracker;
import de.rw.sudoku.io.SudokuFileReaderWriter;
import de.rw.sudoku.model.SudokuCoords;
import de.rw.sudoku.model.SudokuModel;
import de.rw.sudoku.model.iterators.SudokuIterator.SubStructures;

public class PossiblePlacesAlgorithmTest {

	private SudokuModel sm;

	private void log(String s)
	{
	//	System.out.println(s);
	}
	
	@Before
	public void setUp() throws Exception {
		sm = new SudokuModel(9,3);
		try {
			SudokuFileReaderWriter.readSudokuFromFile(sm, "src\\de\\rw\\sudokutest\\zzTestdata\\sz20120224_Format2.0.txt");
		} catch (IOException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

	@Test
	public void testFindPossiblePlacesClustersOnSubStruct() {
		SudokuBruteForceCracker sbfc = new SudokuBruteForceCracker(sm);
		sbfc.oneIteration();
		sbfc.oneIteration();
		PossiblePlacesAlgorithm ppa = new PossiblePlacesAlgorithm(sm);
		List<PossiblePlacesList> lppl =
				ppa.findPossiblePlacesClustersOnSubStruct(new SudokuCoords(6,6), SubStructures.BLOCK);
		// Konsolenausgabe - Start
		Iterator<PossiblePlacesList> itPrint = lppl.iterator();
		while (itPrint.hasNext())
		{
			PossiblePlacesList ppl = itPrint.next();
			log(ppl.toString());
		}
		// Konsolenausgabe - Ende
		PossiblePlaces ppExpected1 = new PossiblePlaces(1);
		ppExpected1.addSudokuCoords(new SudokuCoords(7, 6));
		ppExpected1.addSudokuCoords(new SudokuCoords(7, 8));
		PossiblePlaces ppExpected2 = new PossiblePlaces(2);
		ppExpected2.addSudokuCoords(new SudokuCoords(7, 6));
		ppExpected2.addSudokuCoords(new SudokuCoords(7, 8));

		Iterator<PossiblePlacesList> it = lppl.iterator();
		while (it.hasNext())
		{
			PossiblePlacesList ppl = it.next();
			int iFound=0;
			for (int i=0; i<ppl.size(); i++)
			{
				PossiblePlaces pp = ppl.get(i);
				if (pp.compareTo(ppExpected1)==0) iFound++;
				if (pp.compareTo(ppExpected2)==0) iFound++;
				if (iFound==2 && ppl.size()==2) return;
			}
		}
		Assert.assertTrue(false);
	}
	
	
	@Test 
	public void testOneIterationBruteForceAndOneIterationPossiblePlaces()
	{
		SudokuBruteForceCracker sbfc = new SudokuBruteForceCracker(sm);
		sbfc.oneIteration();
		Assert.assertEquals(true, AlgorithmsTestHelper.modelIsEqualToFile(sm,"sz20120224_Format2.0_nach1BruteForce.txt"));
		sbfc.oneIteration();
		Assert.assertEquals(true, AlgorithmsTestHelper.modelIsEqualToFile(sm,"sz20120224_Format2.0_nach2BruteForce.txt"));
		sbfc.oneIteration();
		Assert.assertEquals(true, AlgorithmsTestHelper.modelIsEqualToFile(sm,"sz20120224_Format2.0_nach3BruteForce.txt"));
		
		PossiblePlacesAlgorithm ppa = new PossiblePlacesAlgorithm(sm);
		ppa.oneIteration();
		Assert.assertEquals(true, AlgorithmsTestHelper.modelIsEqualToFile(sm,"sz20120224_Format2.0_nach3BruteForce_1BlockingValues.txt"));
		ppa.oneIteration();
		Assert.assertEquals(true, AlgorithmsTestHelper.modelIsEqualToFile(sm,"sz20120224_Format2.0_nach3BruteForce_2BlockingValues.txt"));

		sbfc.oneIteration();
		Assert.assertEquals(true, AlgorithmsTestHelper.modelIsEqualToFile(sm,"sz20120224_Format2.0_nach3BruteForce_2BlockingValues_1BruteForce.txt"));
		sbfc.oneIteration();
		Assert.assertEquals(true, AlgorithmsTestHelper.modelIsEqualToFile(sm,"sz20120224_Format2.0_nach3BruteForce_2BlockingValues_2BruteForce.txt"));
		sbfc.oneIteration();
		Assert.assertEquals(true, AlgorithmsTestHelper.modelIsEqualToFile(sm,"sz20120224_Format2.0_geloest.txt"));
	}

}
