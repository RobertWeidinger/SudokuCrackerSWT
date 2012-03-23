package de.rw.sudoku.algorithms;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import de.rw.sudoku.model.SudokuCoords;

public class PossiblePlaces implements Comparable<PossiblePlaces> 
{
	
	private List<SudokuCoords>  listSudokuCoords;

	private Integer 			value=null;

	public PossiblePlaces(Integer v) {
		// TODO Auto-generated constructor stub
		value = v;
		listSudokuCoords = new LinkedList<SudokuCoords>();
	}
	
	public Integer getValue() {
		return value;
	}
	
	public Integer getNumberOfPlaces()
	{
		return this.getListSudokuCoords().size();
	}

	public void addSudokuCoords(SudokuCoords sc)
	{
		listSudokuCoords.add(sc);
		Collections.sort(listSudokuCoords);
	}

	public List<SudokuCoords> getListSudokuCoords() {
		return listSudokuCoords;
	}
	
	@Override
	public int compareTo(PossiblePlaces pp) {
		// Listen vergleichen
		int comp = comparePlacesTo(pp);
		if (comp!=0) return comp;

		// Value vergleichen
		comp = this.getValue().compareTo(pp.getValue());
		if (comp!=0) return comp;

		return 0;
	}

	public int comparePlacesTo(PossiblePlaces pp) {
		// Laenge der Listen vergleichen
		if (this.getNumberOfPlaces()<pp.getNumberOfPlaces()) return -1;
		if (this.getNumberOfPlaces()>pp.getNumberOfPlaces()) return 1;
		
		// Inhalte der Listen vergleichen
		Iterator<SudokuCoords> itScThis = this.getListSudokuCoords().iterator();
		Iterator<SudokuCoords> itScPp = pp.getListSudokuCoords().iterator();
		while (itScThis.hasNext() && itScPp.hasNext())
		{
			SudokuCoords scThis = itScThis.next();
			SudokuCoords scPp   = itScPp.next();
			if (scThis.compareTo(scPp)<0) return -1;
			if (scThis.compareTo(scPp)>0) return 1;
		}
		return 0;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String s = new String();
		s+="[" + getValue() + ",{";
		Iterator<SudokuCoords> it = this.getListSudokuCoords().iterator();
		while (it.hasNext())
		{
			SudokuCoords sc = it.next();
			s+=sc.toString();
		}
		s+="}]";
		return s;
	}

	

}
