package de.rw.sudoku.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.StringTokenizer;


public class SudokuEntry {

	private SudokuCoords sc=null;
	private Integer value = null;
	private boolean isFixed = false;
	private ArrayList<Integer> blockingValues = null;
	
	static private void log(String s)
	{ System.out.println(s); }
	
	protected SudokuEntry(SudokuCoords _sc)
	{
		blockingValues = new ArrayList<Integer>();
		sc = _sc;
	}

	protected int getRow() {
		return sc.getRow();
	}

	protected int getCol() {
		return sc.getCol();
	}

	protected Integer getValue() {
		return value;
	}

	protected void setValue(Integer value) {
		this.value = value;
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

	protected void addBlockingValue(Integer value)
	{
		if (!blockingValues.contains(value) )
			blockingValues.add(value);
		Collections.sort(blockingValues); // Voraussetzung f�r den Vergleich!!!
	}
	
	protected void removeBlockingValue(Integer value)
	{
		blockingValues.remove(value);
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
	
	final static String KLAMMERAUF = new String("{");
	final static String KLAMMERZU = new String("}");
	final static String ROWKENNZ = new String("r=");
	final static String COLKENNZ = new String("c=");
	final static String VALKENNZ = new String("v=");
	final static String EMPTYVAL = new String("_");
	final static String TYPEKENNZ = new String("type=");
	final static String FIXEDKENNZ = new String("F");
	final static String EMPTYKENNZ = new String("E");
	final static String BLOCKEDKENNZ = new String("B");
	final static String SUGGESTEDKENNZ = new String("S");
	final static String BLOCKVALKENNZ = new String("blockedValues={");
	
	protected String toDumpString()
	{
		String s = new String(" "+KLAMMERAUF);
		s+=" "+ROWKENNZ+" " + getRow() + " "+COLKENNZ+" " + getCol();
		s+=" "+VALKENNZ+" " + ((isEmpty())? EMPTYVAL: value.intValue());
		s+=" "+TYPEKENNZ+" ";
		if (isFixed()) s+= FIXEDKENNZ;
		else if (isEmpty()) s+=EMPTYKENNZ;
		else if (isBlocked()) s+=BLOCKEDKENNZ;
		else s+=SUGGESTEDKENNZ; 
		s+=" "+BLOCKVALKENNZ+" ";
		for (int i=0; i<blockingValues.size(); i++)
		{
			s+=blockingValues.get(i).intValue()+" ";
		}
		s+=KLAMMERZU+" "+KLAMMERZU+" ";
		return s;
	}
	
	private static boolean nextToken(StringTokenizer st, String expected)
	{
		String s = st.nextToken();
		if (!s.equals(expected))
		{
			log("Fehler bei "+ expected);
			return false;
		}
		return true;
	}
	
	protected static SudokuEntry scanFromDumpString(String s)
	{
		SudokuEntry se = null;
		StringTokenizer st = new StringTokenizer(s);
		nextToken(st,KLAMMERAUF);
		nextToken(st,ROWKENNZ);
		Integer row = Integer.valueOf(st.nextToken());
		nextToken(st,COLKENNZ);
		Integer col = Integer.valueOf(st.nextToken());
		nextToken(st,VALKENNZ);
		String val = st.nextToken();
		Integer value = null;
		if (!val.equals(EMPTYVAL)) value = Integer.valueOf(val);
		nextToken(st,TYPEKENNZ);
		String type = st.nextToken();

		se = new SudokuEntry(new SudokuCoords(row,col));
		if (value!=null) se.setValue(value);
		if (type.equals(FIXEDKENNZ)) se.makeFixed();
		
		nextToken(st,BLOCKVALKENNZ);
		String token=null;
		while (!(token=st.nextToken()).equals(KLAMMERZU))
			se.addBlockingValue(Integer.valueOf(token));
			
		nextToken(st,KLAMMERZU);
		
		return se;
	}
	
// ===== Testmethode =====

	static public int testDumpToStringAndScan1() // Erg.=Zahl der erfolgreichen Tests
	{
		String sIn1 = new String(" { r= 5 c= 4 v= 9 type= F blockedValues={ } } ");
		SudokuEntry se = scanFromDumpString(sIn1);
		String sOut1 = se.toDumpString();
		log(sOut1);
		if (!sIn1.equals(sOut1)) return 0;
		
		sIn1 = new String(" { r= 5 c= 4 v= _ type= E blockedValues={ } } ");
		se = scanFromDumpString(sIn1);
		sOut1 = se.toDumpString();
		log(sOut1);
		if (!sIn1.equals(sOut1)) return 1;

		sIn1 = new String(" { r= 5 c= 4 v= _ type= E blockedValues={ 2 4 6 8 } } ");
		se = scanFromDumpString(sIn1);
		sOut1 = se.toDumpString();
		log(sOut1);
		if (!sIn1.equals(sOut1)) return 2;
	
		return 3;
	}
}
