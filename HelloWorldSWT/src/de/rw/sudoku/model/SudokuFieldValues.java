package de.rw.sudoku.model;
import java.util.LinkedList;


public class SudokuFieldValues {
	
	private int row;
	private int col;
	private LinkedList<Integer> lValues;
	
	public SudokuFieldValues(int row, int col) {
		super();
		this.row = row;
		this.col = col;
		this.lValues = new LinkedList<Integer>();
	}
		
	public SudokuFieldValues(int row, int col, LinkedList<Integer> lValues) {
		super();
		this.row = row;
		this.col = col;
		this.lValues = lValues;
	}
	
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public LinkedList<Integer> getValues() {
		return lValues;
	}
	public void setValues(LinkedList<Integer> _lValues) {
		this.lValues = _lValues;
	}
	
	public void addValue(int v)
	{
		this.lValues.add(new Integer(v));
	}

	@Override
	public boolean equals(Object o) {
	// TODO Auto-generated method stub
		SudokuFieldValues sfv = (SudokuFieldValues) o;
		if (getRow()!=sfv.getRow()) return false;
		if (getCol()!=sfv.getCol()) return false;
		if (!getValues().containsAll(sfv.getValues())) return false;
		if (!sfv.getValues().containsAll(getValues())) return false;
		return true;
	}

}
