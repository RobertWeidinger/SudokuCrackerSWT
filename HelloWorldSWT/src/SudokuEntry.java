import java.util.ArrayList;


public class SudokuEntry {

	private int row=-1;
	private int col=-1;
	private Integer value = null;
	private boolean isFixed = false;
	private ArrayList<Integer> possibleValues = null;
	
	SudokuEntry(int _row, int _col)
	{
		possibleValues = new ArrayList<Integer>();
		row=_row;
		col=_col;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = new Integer(value);
	}
	
	public void setEmpty()
	{
		value = null;
		isFixed = false;
		clearPossibleValues();
	}
	
	public boolean isEmpty() {
		return value==null;
	}
	
	public boolean isFixed() {
		return isFixed;
	}

	public void makeFixed() {
		this.isFixed = true;
	}

	public ArrayList<Integer> getPossibleValues() {
		return possibleValues;
	}

	public void addPossibleValue(int value)
	{
		possibleValues.add(new Integer(value));
	}
	
	public void removePossibleValue(int value)
	{
		possibleValues.remove(new Integer(value));
	}
	
	public void clearPossibleValues()
	{
		possibleValues.clear();
	}
	
	public boolean isValid(int minValue, int maxValue)
	{
		ArrayList<Integer> pV = getPossibleValues();
		for (Integer i: pV)
			if (i.intValue()<minValue || i.intValue()>maxValue) return false;
		if (isEmpty() && isFixed()) return false;
		if (!isEmpty())
			if (getValue().intValue()<minValue || getValue().intValue()>maxValue) return false;
		return true;
	}
	
	public String toDisplayString(int minValue, int maxValue)
	{
		if (isEmpty()) return "";
		if (!isValid(minValue,maxValue)) return "?!?";
		return getValue().toString();
	}
	
	public String toDumpString()
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
