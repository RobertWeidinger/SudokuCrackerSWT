package de.rw.sudoku.model;

public class SudokuCoords implements Comparable<SudokuCoords>{
	
	private final static int COMPCONST = 100;

	private int row;
	private int col;
	public SudokuCoords(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	@Override
	public boolean equals(Object arg0) {
		SudokuCoords sc = (SudokuCoords)arg0;
		return getRow()==sc.getRow() && getCol()==sc.getCol();
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "(" + getRow() + ","+ getCol()+")";
	}
	@Override
	public int compareTo(SudokuCoords sc2) {
		// TODO Auto-generated method stub
		int cThis = this.getRow()*COMPCONST+this.getCol();
		int cSc2 = sc2.getRow()*COMPCONST+sc2.getCol();
		if (cThis<cSc2) return -1;
		if (cThis>cSc2) return 1;
		return 0;
	}

	
	
}
