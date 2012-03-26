package de.rw.sudokutest.algorithms;

import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.rw.sudoku.algorithms.PossiblePlaces;
import de.rw.sudoku.algorithms.PossiblePlacesList;
import de.rw.sudoku.model.SudokuCoords;

public class PossiblePlacesListTest {

	private PossiblePlacesList ppl=null;
	
	private void log(String s)
	{
	//	System.out.println(s);
	}
	
	@Before
	public void setUp() throws Exception {
		ppl = new PossiblePlacesList();
		PossiblePlaces pp = new PossiblePlaces(1);
		pp.addSudokuCoords(new SudokuCoords(0,0));
		pp.addSudokuCoords(new SudokuCoords(1,1));
		pp.addSudokuCoords(new SudokuCoords(2,0));
		ppl.addValueAndPossiblePlaces(pp);
		pp = new PossiblePlaces(6);
		pp.addSudokuCoords(new SudokuCoords(1,1));
		pp.addSudokuCoords(new SudokuCoords(0,0));
		ppl.addValueAndPossiblePlaces(pp);
		pp = new PossiblePlaces(7);
		pp.addSudokuCoords(new SudokuCoords(1,1));
		pp.addSudokuCoords(new SudokuCoords(0,0));
		ppl.addValueAndPossiblePlaces(pp);
		log("setUp - ppl:");
		log(ppl.toString());
	}

	@Test
	public void testGetClusters() {
		log("testGetClusters:");
		log(ppl.getClusterToString());
		List<PossiblePlacesList> lppl = ppl.getClusters();
		Iterator<PossiblePlacesList> it = lppl.iterator();
		Assert.assertTrue(it.hasNext());
		PossiblePlacesList pplLoc = it.next();
		Assert.assertEquals(2,pplLoc.size());
		Assert.assertEquals(6,pplLoc.get(0).getValue().intValue());
		Assert.assertEquals(2,pplLoc.get(0).getNumberOfPlaces().intValue());
		Assert.assertEquals(7,pplLoc.get(1).getValue().intValue());
		Assert.assertEquals(2,pplLoc.get(1).getNumberOfPlaces().intValue());
		Assert.assertTrue(it.hasNext());
		pplLoc = it.next();
		Assert.assertEquals(1,pplLoc.size());
		Assert.assertEquals(1,pplLoc.get(0).getValue().intValue());
		Assert.assertEquals(3,pplLoc.get(0).getNumberOfPlaces().intValue());
	}

	@Test
	public void testIsNCluster() {
		List<PossiblePlacesList> lppl = ppl.getClusters();
		PossiblePlacesList pplLoc = lppl.get(0);
		Assert.assertEquals(2,pplLoc.isNCluster());
		pplLoc = lppl.get(1);
		Assert.assertEquals(-1,pplLoc.isNCluster());
	}
	
	@Test
	public void testReduceToImportantClusters()
	{
		List<PossiblePlacesList> lppl = ppl.getClusters();
		lppl = PossiblePlacesList.reduceToImportantClusters(lppl);
		Assert.assertEquals(1, lppl.size());
		PossiblePlacesList pplLoc = lppl.get(0);
		Assert.assertEquals(6,pplLoc.get(0).getValue().intValue());
		Assert.assertEquals(2,pplLoc.get(0).getNumberOfPlaces().intValue());
		Assert.assertEquals(7,pplLoc.get(1).getValue().intValue());
		Assert.assertEquals(2,pplLoc.get(1).getNumberOfPlaces().intValue());
	}
	

}
