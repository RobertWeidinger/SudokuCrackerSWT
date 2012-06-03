package de.rw.sudoku.algorithms;

import de.rw.sudoku.model.SudokuModel;

public class AlternatingAlgorithms {
	private SudokuModel sm=null;
	public AlternatingAlgorithms(SudokuModel _sm) {
		sm=_sm;
	}
	
	private void log(String s)
	{
		 // System.out.println(s);
	}

	public boolean run()
	{
		sm.notifyAlgorithmStart();
		boolean b = runInternal();
		sm.notifyAlgorithmStop();
		return b;
	}

	
	public boolean runInternal()
	{
		SudokuBruteForceCracker sbfc = new SudokuBruteForceCracker(sm);
		PossiblePlacesAlgorithm ppa = new PossiblePlacesAlgorithm(sm);
		int numChangedFields1 = 0;
		int numChangedFields2 = 0;
		int countGlobal = 0;
		int countSbfc = 0;
		int countPpa = 0;
		do {
			numChangedFields1 = numChangedFields2 = countPpa = countSbfc = 0; 
			do {
				numChangedFields1 = sbfc.oneIterationInternal();
				if (sm.numberOfValueFields()==sm.getSize()*sm.getSize()) return true;
				if (++countGlobal>50) 
					{
						log("Iterationsgrenze erreicht: " + countGlobal);
						return false;
					}
				countSbfc++;
			} while (numChangedFields1>0);
			
			do {
				numChangedFields2 = ppa.oneIterationInternal(); 
				if (sm.numberOfValueFields()==sm.getSize()*sm.getSize()) return true;
				if (++countGlobal>50) 
				{
					log("Iterationsgrenze erreicht: " + countGlobal);
					return false;
				}
				countPpa++;
			} while (numChangedFields2>0);
		} while (countSbfc>1 || countPpa>1);
		return true;
	}
	
}
