package de.rw.sudoku.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;


class SudokuEntry {

	private int row=-1;
	private int col=-1;
	private Integer value = null;
	private boolean isFixed = false;
	private ArrayList<Integer> blockingValues = null;
	
	protected SudokuEntry(int _row, int _col)
	{
		blockingValues = new ArrayList<Integer>();
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
		clearBlockingValues();
	}
	
	protected boolean isEmpty() {
		return value==null; // && blockingValues.size()==0;
	}
	
	protected boolean isFixed() {
		return isFixed;
	}

	protected void makeFixed() {
		this.isFixed = true;
	}

	protected ArrayList<Integer> getBlockingValues() {
		return blockingValues;
	}

	protected void addBlockingValue(int value)
	{
		if (!blockingValues.contains(new Integer(value)) )
			blockingValues.add(new Integer(value));
		Collections.sort(blockingValues); // Voraussetzung für den Vergleich!!!
	}
	
	protected void removeBlockingValue(int value)
	{
		blockingValues.remove(new Integer(value));
	}
	
	protected void clearBlockingValues()
	{
		blockingValues.clear();
	}
	
	protected boolean isBlocked()
	{
		return blockingValues.size()>0;
	}
	
	protected boolean equalBlockingValues(SudokuEntry se)
	{
		// Voraussetzung: blockingValues sind immer sortiert!
		if (blockingValues.size()!=se.blockingValues.size()) return false;
		Iterator<Integer> it1 = blockingValues.iterator();
		Iterator<Integer> it2 = se.blockingValues.iterator();
		while (it1.hasNext())
		{
			Integer i1 = it1.next();
			Integer i2 = it2.next();
			if (!i1.equals(i2)) return false;
		}
		return true;
	}
	
	protected boolean isValid(int minValue, int maxValue)
	{
		ArrayList<Integer> pV = getBlockingValues();
		for (Integer i: pV)
			if (i.intValue()<minValue || i.intValue()>maxValue) return false;
		if (isEmpty() && isFixed()) return false;
		if (getValue()!=null)
			if (getValue().intValue()<minValue || getValue().intValue()>maxValue) return false;
		return true;
	}
	
	protected String toDisplayString(int minValue, int maxValue)
	{
		if (isBlocked())
		{
			String s=new String();
			ArrayList<Integer> pV = getBlockingValues();
			for (Integer i: pV)
				s+= i + " ";
			return s;
		}
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
		for (int i=0; i<blockingValues.size(); i++)
		{
			s+=blockingValues.get(i).intValue();
			if (i<blockingValues.size()-1) s+=", ";
		}
		s+="}}";
		return s;
	}
	
}
