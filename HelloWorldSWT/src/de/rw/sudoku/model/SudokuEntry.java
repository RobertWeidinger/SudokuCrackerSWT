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
	{ 
	//	System.out.println(s); 
	}
	
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
	
	protected SudokuCoords getSudokuCoords()
	{
		return this.sc;
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
	
	protected boolean noValue() {
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
		Collections.sort(blockingValues); // Voraussetzung für den Vergleich!!!
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
		if (noValue() && isFixed()) return false;
		if (getValue()!=null)
			if (getValue().intValue()<minValue || getValue().intValue()>maxValue) return false;
		return true;
	}
	
	
	@Override
	public boolean equals(Object _se2) {
		if (! (_se2 instanceof SudokuEntry)) return false;
		SudokuEntry se2 = (SudokuEntry) _se2;
		if (! (this.getSudokuCoords().equals(se2.getSudokuCoords()))) return false;
		
		if (getValue()!=se2.getValue()) return false;
		if (getValue()!=null && getValue().intValue()!=se2.getValue().intValue()) return false;
		
		if (this.isFixed()!=se2.isFixed()) return false;
		
		ArrayList<Integer> blv = getBlockingValues();
		ArrayList<Integer> blv2 = se2.getBlockingValues();
		return blv.containsAll(blv2) && blv2.containsAll(blv);
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
		if (noValue()) return "";
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
		s+=" "+VALKENNZ+" " + ((noValue())? EMPTYVAL: value.intValue());
		s+=" "+TYPEKENNZ+" ";
		if (isFixed()) s+= FIXEDKENNZ;
		else if (noValue()) s+=EMPTYKENNZ;
//		else if (isBlocked()) s+=BLOCKEDKENNZ; // Hier kommt er eh nie hin...
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
	
	protected static SudokuEntry scanFromDumpString(StringTokenizer st)
	{
		SudokuEntry se = null;
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
		StringTokenizer st = new StringTokenizer(sIn1);
		SudokuEntry se = scanFromDumpString(st);
		String sOut1 = se.toDumpString();
		log(sOut1);
		if (!sIn1.equals(sOut1)) return 0;
		
		sIn1 = new String(" { r= 5 c= 4 v= _ type= E blockedValues={ } } ");
		st = new StringTokenizer(sIn1);
		se = scanFromDumpString(st);
		sOut1 = se.toDumpString();
		log(sOut1);
		if (!sIn1.equals(sOut1)) return 1;

		sIn1 = new String(" { r= 5 c= 4 v= _ type= E blockedValues={ 2 4 6 8 } } ");
		st = new StringTokenizer(sIn1);
		se = scanFromDumpString(st);
		sOut1 = se.toDumpString();
		log(sOut1);
		if (!sIn1.equals(sOut1)) return 2;
	
		return 3;
	}
	
	static public int testEquals() // Erg.=Zahl der erfolgreichen Tests
	{
		SudokuEntry se1 = new SudokuEntry(new SudokuCoords(1, 4));
		SudokuEntry se2 = new SudokuEntry(new SudokuCoords(1, 4));
		if (!se1.equals(se2)) return 0;
		se1.addBlockingValue(1);
		se1.addBlockingValue(3);
		se1.addBlockingValue(5);
		se2.addBlockingValue(1);
		se2.addBlockingValue(3);
		se2.addBlockingValue(5);
		if (!se1.equals(se2)) return 1;
		
		SudokuEntry se3 = new SudokuEntry(new SudokuCoords(1, 2));
		se3.addBlockingValue(1);
		se3.addBlockingValue(3);
		se3.addBlockingValue(5);
		if (se1.equals(se3)) return 2;
		
		SudokuEntry se5 = new SudokuEntry(new SudokuCoords(1, 4));
		SudokuEntry se6 = new SudokuEntry(new SudokuCoords(1, 4));
		se5.setValue(5);
		se6.setValue(6);
		if (se5.equals(se6)) return 3;
		
		SudokuEntry se7 = new SudokuEntry(new SudokuCoords(1, 4));
		se7.setValue(5);
		if (!se5.equals(se7)) return 4;
		
		return 5;
	}
}
