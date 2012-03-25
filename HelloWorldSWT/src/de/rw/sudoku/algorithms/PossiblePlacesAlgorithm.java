package de.rw.sudoku.algorithms;

import java.util.ArrayList;
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
	
	private void log(String s)
	{
		// System.out.println(s);
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
				if (!sm.isEmpty(sc)) continue;
				if (sm.isBlocked(sc) && !sm.getBlockingValues(sc).contains(value)) continue;
				if (sh.findConflicts(sc, value).size()>0) continue;
				
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
		log("====== PossiblePlacesAlgorithm.oneIteration() ======");
		int res = 0;

		for (final SudokuIterator.SubStructures subStruct : SudokuIterator.SubStructures.REAL) // Schleife über Typen ROW, COL, BLOCK
		{
			log("== Schleife über "+ subStruct +"s ==");
			SudokuIterator subStructIt = 
					SudokuIterator.createIteratorSubStruct(sm.getSize(), sm.getBlockSize(), subStruct);
			while (subStructIt.hasNext()) // Schleife über die Rows oder die Cols oder die Blocks
			{
				SudokuCoords scStart = subStructIt.next();
				log("= "+subStruct+"["+scStart+"] = Schleife über Cluster");
				List<PossiblePlacesList> lppl = findPossiblePlacesClustersOnSubStruct(scStart, subStruct); // Liste von Clustern
				Iterator<PossiblePlacesList> itCluster = lppl.iterator();
				while (itCluster.hasNext()) // Schleife über die Cluster
				{
					PossiblePlacesList ppl = itCluster.next();
					log("Cluster= "+ppl);
					boolean doIt = true;
					for (int i=0; i<ppl.size(); i++) // Schleife über die Elemente eines Clusters, prüfen ob Konflikte mit bereits blockierten Feldern
					{
						PossiblePlaces pp = ppl.get(i);
						Iterator<SudokuCoords> scIt = pp.getListSudokuCoords().iterator();
						while (scIt.hasNext())
						{
							SudokuCoords sc = scIt.next();
							if (sm.isBlocked(sc))
							{
								if (blockingValuesEqualClusterValues(ppl, sc)) continue;
								
								log("Konflikt mit BlockedValues bei "+sc+". Kein Eintrag des Clusters.");
								doIt = false;
							}
						}
					}
					if (!doIt) continue;
					
					for (int i=0; i<ppl.size(); i++) // Schleife über die Elemente eines Clusters, blockingValues setzen.
					{
						PossiblePlaces pp = ppl.get(i);
						Iterator<SudokuCoords> scIt = pp.getListSudokuCoords().iterator();
						while (scIt.hasNext())
						{
							SudokuCoords sc = scIt.next();
							log("sm.addBlockingValue("+sc.getRow()+", "+sc.getCol()+", "+pp.getValue()+");");
							sm.addBlockingValue(sc, pp.getValue());
							res++;
						}
					}
				}
			}
		}
		return res;

	}

	private boolean blockingValuesEqualClusterValues(PossiblePlacesList ppl, SudokuCoords sc) {
		boolean blockingValuesEqualClusterValues = false;
		ArrayList<Integer> alBlockingValuesAtSc = sm.getBlockingValues(sc);
		ArrayList<Integer> alValuesOfPPL = new ArrayList<Integer>();
		for (int j=0; j<ppl.size(); j++)
			alValuesOfPPL.add(ppl.get(j).getValue());
		if (alBlockingValuesAtSc.containsAll(alValuesOfPPL) && alValuesOfPPL.containsAll(alBlockingValuesAtSc))
			blockingValuesEqualClusterValues = true;
		return blockingValuesEqualClusterValues;
	}
	
}
