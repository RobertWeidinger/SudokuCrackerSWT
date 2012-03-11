package de.rw.sudoku.model;
import java.util.ArrayList;


class SudokuEntry {

	private int row=-1;
	private int col=-1;
	private Integer value = null;
	private boolean isFixed = false;
	private ArrayList<Integer> possibleValues = null;
	
	protected SudokuEntry(int _row, int _col)
	{
		possibleValues = new ArrayList<Integer>();
		row=_row;
		col=_col;
	}

	protected int getRow() {
		return row;
	}

	protected int getCol() {
		return col;
	}

	protected Integer getValue() {
		return value;
	}

	protected void setValue(int value) {
		this.value = new Integer(value);
	}
	
	protected void setEmpty()
	{
		value = null;
		isFixed = false;
		clearPossibleValues();
	}
	
	protected boolean isEmpty() {
		return value==null;
	}
	
	protected boolean isFixed() {
		return isFixed;
	}

	protected void makeFixed() {
		this.isFixed = true;
	}

	protected ArrayList<Integer> getPossibleValues() {
		return possibleValues;
	}

	protected void addPossibleValue(int value)
	{
		possibleValues.add(new Integer(value));
	}
	
	protected void removePossibleValue(int value)
	{
		possibleValues.remove(new Integer(value));
	}
	
	protected void clearPossibleValues()
	{
		possibleValues.clear();
	}
	
	protected boolean isValid(int minValue, int maxValue)
	{
		ArrayList<Integer> pV = getPossibleValues();
		for (Integer i: pV)
			if (i.intValue()<minValue || i.intValue()>maxValue) return false;
		if (isEmpty() && isFixed()) return false;
		if (!isEmpty())
			if (getValue().intValue()<minValue || getValue().intValue()>maxValue) return false;
		return true;
	}
	
	protected String toDisplayString(int minValue, int maxValue)
	{
		if (isEmpty()) return "";
		if (!isValid(minValue,maxValue)) return "?!?";
		return getValue().toString();
	}
	
	protected String toDumpString()
	{
		String s = new String(" {");
		s+="r=" + row + " c=" + col;
		s+=" v=" + ((isEmpty())? "null": value.intValue());
		s+=" type=" + (isFixed()? "F": (isEmpty()? "E" : "S"));
		s+=" possValues={";
		for (int i=0; i<possibleValues.size(); i++)
		{
			s+=possibleValues.get(i).intValue();
			if (i<possibleValues.size()-1) s+=", ";
		}
		s+="}}";
		return s;
	}
	
}
