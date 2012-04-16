package de.rw.sudoku.model;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import de.rw.sudoku.algorithms.SudokuHelper;
import de.rw.sudoku.model.iterators.SudokuIterator;
import de.rw.sudoku.model.iterators.SudokuIterator.SubStructures;
import de.rw.sudoku.views.View;


public class SudokuModel implements Model {

	private ArrayList<View> alViews=null;
	private int size;
	private int blockSize;
	private Vector<Vector<SudokuEntry>> arraySudokuEntries;
	private LinkedList<OldSudokuEntry> oldSudokuEntries;
	private Integer algorithmID = null;
	private boolean bAlgorithmRunning = false;

	private static void log(String s)
	{ 
	//	System.out.println(s); 
	}
	
	public SudokuModel(int _size, int _subSize)
	{
		reInit(_size, _subSize);
	}

	public void reInit(int _size, int _subSize) {
		size = _size;
		blockSize = _subSize;
		arraySudokuEntries = new Vector<Vector<SudokuEntry>>();
		arraySudokuEntries.ensureCapacity(size);
		oldSudokuEntries = new LinkedList<OldSudokuEntry>();
		setAlgorithmID(new Integer(0));
		
		if (alViews==null) alViews = new ArrayList<View>();
		
		for (int i = 0; i<size; i++)
		{
			Vector<SudokuEntry> rowSudokuEntries = new Vector<SudokuEntry>();
			rowSudokuEntries.ensureCapacity(size);
			arraySudokuEntries.add(i, rowSudokuEntries);
			for (int j=0; j<size; j++)
			{
				SudokuEntry se = new SudokuEntry(new SudokuCoords(i,j));
				rowSudokuEntries.add(j,se);
			}
		}
	}

	public int getSize() {
		return size;
	}

	public int getBlockSize() {
		return blockSize;
	}

	public int getNumberOfBlocksPerRow()
	{
		return getSize()/getBlockSize();
	}

	public int getNumberOfBlocksPerCol()
	{
		return getSize()/getBlockSize();
	}

	private void updateViews()
	{
		for (View v: alViews)
			v.update();
	}
	
	private boolean inBounds(SudokuCoords sc)
	{
		try {
			arraySudokuEntries.get(sc.getRow()).get(sc.getCol());
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	private SudokuEntry getSudokuEntry(SudokuCoords sc)
	{
		if (!inBounds(sc)) return null;
		return arraySudokuEntries.get(sc.getRow()).get(sc.getCol());
	}
	
	private void setSudokuEntry(SudokuEntry se)
	{
		if (inBounds(se.getSudokuCoords()))
			arraySudokuEntries.get(se.getRow()).set(se.getCol(), se);
		updateViews();
	}
	
	public static final int FIXED=1;
	public static final int SUGGESTED=2;
	public static final int EMPTY=3;
	
	private SudokuEntry setNewValue(SudokuCoords sc, Integer value, int flagFixedSuggestedEmpty)
	{
		boolean bAlgorithmRunning = isAlgorithmRunning();
		if (!bAlgorithmRunning) notifyAlgorithmStart();
		SudokuEntry se = null;
		if (inBounds(sc))
		{
			OldSudokuEntry ose = new OldSudokuEntry(getSudokuEntry(sc), getAlgorithmID());
			oldSudokuEntries.push(ose);
			se = new SudokuEntry(sc);
			if (flagFixedSuggestedEmpty!=EMPTY)
			{
				se.setValue(value);
				if (flagFixedSuggestedEmpty==FIXED)
					se.makeFixed();
			}
			arraySudokuEntries.get(sc.getRow()).set(sc.getCol(),se);
			updateViews();
		}
		if (!bAlgorithmRunning) notifyAlgorithmStop();
		return se;
	}
	
	public SudokuEntry setEmptyValue(SudokuCoords sc)
	{
		return setNewValue(sc,-1,EMPTY);
	}
	
	public SudokuEntry setFixedValue(SudokuCoords sc, Integer value)
	{
		return setNewValue(sc,value,FIXED);
	}
	
	public SudokuEntry setSuggestedValue(SudokuCoords sc, Integer value)
	{
		return setNewValue(sc,value,SUGGESTED);
	}
	
	public boolean isValidModel(SudokuCoords sc)
	// Achtung: Ein Modell, in dem der Anwender einen Denkfehler gemacht hat, ist valide. 
	//          Hat er aber eine unerlaubte Zahl eingetragen (z.B. 2-, 0 oder etwas größeres als size) ==> nicht valide.
	{
		return getSudokuEntry(sc).isValid(1, getSize());
	}
	
	public boolean isValidModel()
	{
		SudokuIterator si = SudokuIterator.createIterator(getSize(), getBlockSize(), new SudokuCoords(0,0), SudokuIterator.SubStructures.WHOLE);
		while (si.hasNext())
			if (!isValidModel(si.next())) return false;
		return true;
	}
	
	public boolean hasConflicts(SudokuCoords sc)
	{
		SudokuHelper sh = new SudokuHelper(this);
		if (getValue(sc)==null) return false;
		List<SudokuFieldValues> lsfv = sh.findConflicts(sc, getValue(sc));
		return lsfv.size()>0;
	}
	
	public boolean hasConflicts()
	{
		SudokuIterator si = SudokuIterator.createIterator(getSize(), getBlockSize(), new SudokuCoords(0,0), SudokuIterator.SubStructures.WHOLE);
		while (si.hasNext())
		{
			SudokuEntry se = getSudokuEntry(si.next());
			if (hasConflicts(se.getSudokuCoords()))
				return true;
		}
		return false;
	}
	
	public void makeValuesFixed()
	{
		SudokuIterator si = SudokuIterator.createIterator(getSize(), getBlockSize(), new SudokuCoords(0,0), SudokuIterator.SubStructures.WHOLE);
		while (si.hasNext())
		{
			SudokuEntry se = getSudokuEntry(si.next());
			if (se.hasValue())
				se.makeFixed();
		}
		updateViews();
	}
	
	public int numberOfNonEmptyFields()
	{
		int iCount = 0;
		SudokuIterator si = SudokuIterator.createIterator(getSize(), getBlockSize(), new SudokuCoords(0,0), SudokuIterator.SubStructures.WHOLE);
		while (si.hasNext())
		{
			SudokuEntry se = getSudokuEntry(si.next());
			if (se.hasValue() || se.isBlocked())
				iCount++;
		}
		return iCount;
	}
	
	public int numberOfValueFields()
	{
		int iCount = 0;
		SudokuIterator si = SudokuIterator.createIterator(getSize(), getBlockSize(), new SudokuCoords(0,0), SudokuIterator.SubStructures.WHOLE);
		while (si.hasNext())
		{
			SudokuEntry se = getSudokuEntry(si.next());
			if (se.hasValue())
				iCount++;
		}
		return iCount;
	}

	
	
	@Override
	public void registerView(View _v) {
		alViews.add(_v);
	}
		
	public Integer getValue(SudokuCoords sc)
	{
		return getSudokuEntry(sc).getValue();
	}
	

	public boolean isFixed(SudokuCoords sc)
	{
		return getSudokuEntry(sc).isFixed();
	}
	
	public boolean hasValue(SudokuCoords sc)
	{
		return getSudokuEntry(sc).hasValue();
	}
	
	public boolean isBlocked(SudokuCoords sc)
	{
		return getSudokuEntry(sc).isBlocked();
	}
	
	public void undo()
	{
		if (oldSudokuEntries.size()==0) return;
		if (oldSudokuEntries.size()==0) return;
		OldSudokuEntry ose = oldSudokuEntries.peekFirst();
		Integer algID = ose.getAlgorithmID();
		do {
			OldSudokuEntry ose2 = oldSudokuEntries.pop();
			Vector<SudokuEntry> alSe = arraySudokuEntries.get(ose2.getSudokuEntry().getRow());
			alSe.set(ose2.getSudokuEntry().getCol(), ose2.getSudokuEntry());
		} while (oldSudokuEntries.size()!=0 && algID.equals(oldSudokuEntries.peekFirst().getAlgorithmID()));
		updateViews();
	}
	
	public String entryToDisplayString(SudokuCoords sc)
	{
		return getSudokuEntry(sc).toDisplayString(1, getSize());
	}
	
	public void addBlockingValue(SudokuCoords sc, Integer value)
	{
		OldSudokuEntry ose = new OldSudokuEntry(getSudokuEntry(sc), getAlgorithmID());
		SudokuEntry se = new SudokuEntry(getSudokuEntry(sc));
		se.addBlockingValue(value);
		setSudokuEntry(se);
		oldSudokuEntries.push(ose);
		updateViews();
	}
	
	public void removeBlockingValue(SudokuCoords sc, Integer value)
	{
		OldSudokuEntry ose = new OldSudokuEntry(getSudokuEntry(sc), getAlgorithmID());
		SudokuEntry se = new SudokuEntry(getSudokuEntry(sc));
		if (value!=null && value.intValue()>0)
			se.removeBlockingValue(value);
		else
			se.clearBlockingValues();
		setSudokuEntry(se);
		oldSudokuEntries.push(ose);
		updateViews();
	}
	
	private void removeBlockingValues(SudokuCoords sc)
	{
		removeBlockingValue(sc,null);
	}
	
	public ArrayList<Integer> getBlockingValues(SudokuCoords sc)
	{
		return getSudokuEntry(sc).getBlockingValues();
	}
	
	public void clearBlockingValues()
	{
		notifyAlgorithmStart();
		SudokuIterator it = SudokuIterator.createIterator(getSize(), getBlockSize(), new SudokuCoords(0,0), SubStructures.WHOLE);
		while (it.hasNext())
			this.removeBlockingValues(it.next());
		updateViews();
		notifyAlgorithmStop();
	}
	
	public boolean equalBlockingValues(SudokuCoords sc1, SudokuCoords sc2)
	{
		return getSudokuEntry(sc1).equalBlockingValues(getSudokuEntry(sc2));
	}
	
	public boolean equalsIgnoringOldValues(SudokuModel sm2)
	{
		if (getSize()!=sm2.getSize() || getBlockSize()!=sm2.getBlockSize()) return false;
		SudokuIterator it = SudokuIterator.createIterator(getSize(), getBlockSize(), new SudokuCoords(0,0), SubStructures.WHOLE);
		while (it.hasNext())
		{
			SudokuCoords sc = it.next();
			SudokuEntry se1 = getSudokuEntry(sc);
			SudokuEntry se2 = sm2.getSudokuEntry(sc);
			if (!se1.equals(se2)) 
				return false;
		}
		return true;
	}
	

	public boolean isAlgorithmRunning() {
		return bAlgorithmRunning;
	}

	private void setAlgorithmRunning(boolean bAlgorithmRunning) {
		this.bAlgorithmRunning = bAlgorithmRunning;
	}

	private Integer getAlgorithmID() {
		return algorithmID;
	}

	private void setAlgorithmID(Integer algorithmID) {
		this.algorithmID = algorithmID;
	}

	@Override
	public void notifyAlgorithmStart() {
		setAlgorithmID(getAlgorithmID().intValue()+1);
		setAlgorithmRunning(true);
	}

	@Override
	public void notifyAlgorithmStop() {
		setAlgorithmRunning(false);
	}

	
	@Override
	public String toString()
	{
		String s = new String();
		
		s+= this.getSize() + " " + this.getBlockSize() + System.getProperty("line.separator")+ System.getProperty("line.separator");
				
		SudokuIterator it = SudokuIterator.createIterator(getSize(), getBlockSize(), new SudokuCoords(0,0), SubStructures.WHOLE);
		while (it.hasNext())
		{
			SudokuCoords sc = it.next();
			SudokuEntry se = this.getSudokuEntry(sc);
			if (!se.hasValue())
				s+= " _";
			else if (se.isValid(1, getSize()))
				s+= " " + se.getValue();
			else 
				s+= " ?";
			if (sc.getCol()==getSize()-1) s+=System.getProperty("line.separator");
		}
		return s;
	}
	
	public String toStringWithFlags()
	{
		String s = this.toString() + System.getProperty("line.separator");
		SudokuIterator it = SudokuIterator.createIterator(getSize(), getBlockSize(), new SudokuCoords(0,0), SubStructures.WHOLE);
		while (it.hasNext())
		{
			SudokuCoords sc = it.next();
			SudokuEntry se = this.getSudokuEntry(sc);
			if (se.isBlocked()) s+=" _";
			else if (se.isFixed()) s+=" F";
			else if (!se.hasValue()) s+=" _";
			else s+=" S";
		
			if (sc.getCol()==getSize()-1) s+=System.getProperty("line.separator");
		}
		return s;
	}
	
	final static String FILEFORMATVERSION=new String("FileFormatVersion=2.0");
	
	public String toDumpString()
	{
		String s = FILEFORMATVERSION + System.getProperty("line.separator") + 
				this.getSize()+" "+ this.getBlockSize() + System.getProperty("line.separator");
		SudokuIterator it = SudokuIterator.createIterator(getSize(), getBlockSize(), new SudokuCoords(0,0), SubStructures.WHOLE);
		while (it.hasNext())
		{
			SudokuCoords sc = it.next();
			SudokuEntry se = this.getSudokuEntry(sc);
			s+=se.toDumpString()+" ";
			if (sc.getCol()==getSize()-1) s+=System.getProperty("line.separator");
		}
		return s;
	}
	
	static public boolean scanFromDumpString(SudokuModel sm, String s)
	{
		int size = -1;
		int blockSize = -1;
	    StringTokenizer st = new StringTokenizer(s);
	    if (!st.nextToken().equals(FILEFORMATVERSION)) return false;
	    if (st.hasMoreTokens()) size = Integer.valueOf(st.nextToken()).intValue();
	    if (st.hasMoreTokens()) blockSize = Integer.valueOf(st.nextToken()).intValue();
	    
	    sm.reInit(size, blockSize);
	    for (int i=0; i<size; i++)
	    	for (int j=0; j<size; j++)
		    {
		    	SudokuEntry se = SudokuEntry.scanFromDumpString(st);
		    	if (se.getRow()!=i || se.getCol()!=j)
		    	{
		    		log("Fehler beim Einlesen von Objekt (Position stimmt nicht): "+se);
		    		sm.reInit(size, blockSize);
		    		return false;
		    	}
		    	sm.setSudokuEntry(se);
		    }
	    
	    return true;
	}
	
// ========================= Testhilfsmethoden =================================================================	
	
	public static SudokuModel createTestModel1()
	{
		SudokuModel sm = new SudokuModel(9,3);
		for (int i=0; i<sm.getSize(); i++)
		{
			sm.setFixedValue(new SudokuCoords(i,i), 9-i);
			if (i<sm.getSize()-1)
				sm.setSuggestedValue(new SudokuCoords(i+1,i), i+1);
		}
		sm.setFixedValue(new SudokuCoords(2,8), 9);
		sm.setSuggestedValue(new SudokuCoords(0,7), 1);
		log("TestModel1:");
		log(sm.toString());
		return sm;
	}

	public static SudokuModel createCorruptModel1()
	{
		SudokuModel sm = createTestModel1();
		SudokuEntry se = sm.getSudokuEntry(new SudokuCoords(7,2));
		se.makeFixed();
		return sm;
	}
		
}
