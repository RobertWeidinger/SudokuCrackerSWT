package de.rw.sudoku.model;

public class SudokuCoords {
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
		return "[" + getRow() + ","+ getCol()+"]";
	}

	
}
