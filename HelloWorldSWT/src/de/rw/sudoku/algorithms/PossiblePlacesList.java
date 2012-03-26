package de.rw.sudoku.algorithms;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PossiblePlacesList {

	private List<PossiblePlaces> listPossiblePlaces;
	public PossiblePlacesList()
	{
		listPossiblePlaces = new LinkedList<PossiblePlaces>();
	}
	
	public void addValueAndPossiblePlaces(PossiblePlaces pp)
	{
		listPossiblePlaces.add(pp);
		Collections.sort(listPossiblePlaces);
	}
	
	// Value-Mengen mit identischen PossiblePlaces rausfischen:
	public List<PossiblePlacesList> getClusters()
	{
		List<PossiblePlacesList> listPPL = new LinkedList<PossiblePlacesList>();
		Iterator<PossiblePlaces> it = listPossiblePlaces.iterator();
		PossiblePlaces pp2 = null;
		PossiblePlaces pp = null;
		while (it.hasNext())
		{
			PossiblePlacesList pplCluster = new PossiblePlacesList();
			listPPL.add(pplCluster);
			if (pp2==null)
				pp = it.next();
			else
				pp = pp2;
			pplCluster.addValueAndPossiblePlaces(pp);
			while (it.hasNext())
			{
				pp2 = it.next();
				if (pp.comparePlacesTo(pp2)==0)
				{
					pplCluster.addValueAndPossiblePlaces(pp2);
					pp2=null;
				}
				else
					break;
			}
		}
		if (pp2!=null)
		{
			PossiblePlacesList pplCluster = new PossiblePlacesList();
			listPPL.add(pplCluster);
			pplCluster.addValueAndPossiblePlaces(pp2);
		}
		
		return listPPL;
	}
	
	public int isNCluster()
	{
		// precond: es muss bereits ein gültiges Cluster sein, d.h. Werte unterschiedlich, 
		// aber die Mengen der zugehörigen PossiblePlaces alle gleich.
		// Geprüft wird, ob die Größe des Clusters mit der Mächtigkeit der Mengen übereinstimmt.
		if (this.size()<=0) return -1;
		PossiblePlaces pp = this.get(0);
		int numOfPlaces = pp.getNumberOfPlaces();
		if (this.size()==numOfPlaces)
			return numOfPlaces;
		return -1;
	}
	
	public static List<PossiblePlacesList> reduceToImportantClusters(List<PossiblePlacesList> lppl)
	{
		Iterator<PossiblePlacesList> it = lppl.iterator();
		while (it.hasNext())
		{
			PossiblePlacesList ppl = it.next();
			if (ppl.isNCluster()<=0)
				it.remove();
		}
		return lppl;
	}

	public PossiblePlaces get(int index)
	{
		return listPossiblePlaces.get(index);
	}
	
	public int size()
	{ 
		return listPossiblePlaces.size();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		Iterator<PossiblePlaces> it = listPossiblePlaces.iterator();
		String s = new String();
		while (it.hasNext())
			s+= it.next().toString() + System.getProperty("line.separator");; 
		return s;
	}
	
	public String getClusterToString()
	{
		List<PossiblePlacesList> lppl = this.getClusters();
		Iterator<PossiblePlacesList> it = lppl.iterator();
		String s = new String();
		while (it.hasNext())
			s+=it.next().toString() + System.getProperty("line.separator");;
		return s;
	}
	
	
	
}
