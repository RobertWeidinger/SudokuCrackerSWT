package de.rw.sudoku.algorithms;

import java.util.Iterator;
import java.util.List;

import de.rw.sudoku.model.SudokuCoords;
import de.rw.sudoku.model.SudokuModel;
import de.rw.sudoku.model.iterators.SudokuIterator;
import de.rw.sudoku.model.iterators.SudokuIterator.SubStructures;

public class PossiblePlacesAlgorithm {

	private SudokuModel sm=null;
	public PossiblePlacesAlgorithm(SudokuModel _sm) {
		sm=_sm;
	}
	
	public List<PossiblePlacesList> findPossiblePlacesClustersOnSubStruct(SudokuCoords subStructStart, SubStructures type)
	{
		PossiblePlacesList ppl = new PossiblePlacesList();

		SudokuHelper sh = new SudokuHelper(sm);
		
		for (int value=1; value<=sm.getSize(); value++)
		{
			PossiblePlaces pp = new PossiblePlaces(value);
			SudokuIterator si =
					SudokuIterator.createIterator(sm.getSize(), sm.getBlockSize(), subStructStart, type);
			while (si.hasNext())
			{
				SudokuCoords sc = si.next();
				if (sm.isEmpty(sc) && !sm.isBlocked(sc) && sh.findConflicts(sc.getRow(), sc.getCol(), value).size()==0)
					pp.addSudokuCoords(sc);
			}
			ppl.addValueAndPossiblePlaces(pp);
		}
		
		List<PossiblePlacesList> lppl = ppl.getClusters();
		lppl = PossiblePlacesList.reduceToImportantClusters(lppl);
		return lppl;
	}

	public int oneIteration()
	{
		int res = 0;

		for (final SudokuIterator.SubStructures subStruct : SudokuIterator.SubStructures.REAL) // Schleife über Typen ROW, COL, BLOCK
		{
			// Temporär start
			if (!subStruct.equals(SudokuIterator.SubStructures.BLOCK)) continue;
			// Temporär ende
			SudokuIterator subStructIt = 
					SudokuIterator.createIteratorSubStruct(sm.getSize(), sm.getBlockSize(), subStruct);
			while (subStructIt.hasNext()) // Schleife über die Rows oder die Cols oder die Blocks
			{
				SudokuCoords scStart = subStructIt.next();
				List<PossiblePlacesList> lppl = findPossiblePlacesClustersOnSubStruct(scStart, subStruct); // Liste von Clustern
				Iterator<PossiblePlacesList> itCluster = lppl.iterator();
				while (itCluster.hasNext()) // Schleife über die Cluster
				{
					PossiblePlacesList ppl = itCluster.next();
					for (int i=0; i<ppl.size(); i++) // Schleife über die Elemente eines Clusters
					{
						PossiblePlaces pp = ppl.get(i);
						Iterator<SudokuCoords> scIt = pp.getListSudokuCoords().iterator();
						while (scIt.hasNext())
						{
							SudokuCoords sc = scIt.next();
							sm.addBlockingValue(sc.getRow(), sc.getCol(), pp.getValue());
							res++;
						}
					}
				}
			}
		}
		return res;

	}
	
}
