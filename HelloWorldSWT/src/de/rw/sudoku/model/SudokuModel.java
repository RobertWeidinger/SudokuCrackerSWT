package de.rw.sudoku.model;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

import de.rw.sudoku.model.iterators.SudokuIterator;
import de.rw.sudoku.model.iterators.SudokuIterator.SubStructures;
import de.rw.sudoku.views.View;


public class SudokuModel implements Model {

	private ArrayList<View> alViews=null;
	private int size;
	private int blockSize;
	Vector<Vector<SudokuEntry>> arraySudokuEntries;
	LinkedList<SudokuEntry> oldSudokuEntries;
	
	public SudokuModel(int _size, int _subSize)
	{
		reInit(_size, _subSize);
	}

	public void reInit(int _size, int _subSize) {
		size = _size;
		blockSize = _subSize;
		arraySudokuEntries = new Vector<Vector<SudokuEntry>>();
		arraySudokuEntries.ensureCapacity(size);
		oldSudokuEntries = new LinkedList<SudokuEntry>();
		
		if (alViews==null) alViews = new ArrayList<View>();
		
		for (int i = 0; i<size; i++)
		{
			Vector<SudokuEntry> rowSudokuEntries = new Vector<SudokuEntry>();
			rowSudokuEntries.ensureCapacity(size);
			arraySudokuEntries.add(i, rowSudokuEntries);
			for (int j=0; j<size; j++)
			{
				SudokuEntry se = new SudokuEntry(i,j);
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
		return arraySudokuEntries.get(sc.getRow()).get(sc.getCol());	}
	
	public static final int FIXED=1;
	public static final int SUGGESTED=2;
	public static final int EMPTY=3;
	
	private SudokuEntry setNewValue(SudokuCoords sc, int value, int flagFixedSuggestedEmpty)
	{
		if (!inBounds(sc)) return null;
		SudokuEntry se = getSudokuEntry(sc);
		oldSudokuEntries.push(se);
		se = new SudokuEntry(sc.getRow(),sc.getCol());
		if (flagFixedSuggestedEmpty!=EMPTY)
		{
			se.setValue(value);
			if (flagFixedSuggestedEmpty==FIXED)
				se.makeFixed();
		}
		arraySudokuEntries.get(sc.getRow()).set(sc.getCol(),se);
		updateViews();
		return se;
	}
	
	public SudokuEntry setEmptyValue(SudokuCoords sc)
	{
		return setNewValue(sc,-1,EMPTY);
	}
	
	public SudokuEntry setFixedValue(SudokuCoords sc, int value)
	{
		return setNewValue(sc,value,FIXED);
	}
	
	public SudokuEntry setSuggestedValue(SudokuCoords sc, int value)
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
	
	public void makeValuesFixed()
	{
		SudokuIterator si = SudokuIterator.createIterator(getSize(), getBlockSize(), new SudokuCoords(0,0), SudokuIterator.SubStructures.WHOLE);
		while (si.hasNext())
		{
			SudokuEntry se = getSudokuEntry(si.next());
			if (!se.isEmpty())
				se.makeFixed();
		}
		updateViews();
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
	
	public boolean isEmpty(SudokuCoords sc)
	{
		return getSudokuEntry(sc).isEmpty();
	}
	
	public boolean isBlocked(SudokuCoords sc)
	{
		return getSudokuEntry(sc).isBlocked();
	}
	
	public void undo()
	{
		if (oldSudokuEntries.size()==0) return;
		SudokuEntry ose = oldSudokuEntries.pop();
		Vector<SudokuEntry> alSe = arraySudokuEntries.get(ose.getRow());
		alSe.set(ose.getCol(), ose);
		updateViews();
	}
	
	public String entryToDisplayString(SudokuCoords sc)
	{
		return getSudokuEntry(sc).toDisplayString(1, getSize());
	}
	
	public void addBlockingValue(SudokuCoords sc, int value)
	{
		getSudokuEntry(sc).addBlockingValue(value);
		updateViews();
	}
	
	public void removeBlockingValue(SudokuCoords sc, int value)
	{
		getSudokuEntry(sc).removeBlockingValue(value);
	}
	
	public ArrayList<Integer> getBlockingValues(SudokuCoords sc)
	{
		return getSudokuEntry(sc).getBlockingValues();
	}
	
	public void clearBlockingValues()
	{
		SudokuIterator it = SudokuIterator.createIterator(getSize(), getBlockSize(), new SudokuCoords(0,0), SubStructures.WHOLE);
		while (it.hasNext())
			this.getSudokuEntry(it.next()).clearBlockingValues();
		updateViews();
	}
	
	public boolean equalBlockingValues(SudokuCoords sc1, SudokuCoords sc2)
	{
		return getSudokuEntry(sc1).equalBlockingValues(getSudokuEntry(sc2));
	}
	
	@Override
	public String toString()
	{
		String s = new String();
		
		s+= this.getSize() + " " + this.getBlockSize() + "\n\n";
				
		SudokuIterator it = SudokuIterator.createIterator(getSize(), getBlockSize(), new SudokuCoords(0,0), SubStructures.WHOLE);
		while (it.hasNext())
		{
			SudokuCoords sc = it.next();
			SudokuEntry se = this.getSudokuEntry(sc);
			if (se.isEmpty())
				s+= " _";
			else if (se.isValid(1, getSize()))
				s+= " " + se.getValue();
			else 
				s+= " ?";
			if (sc.getCol()==getSize()-1) s+="\n";
		}
		return s;
	}
	
	public String toStringWithFlags()
	{
		String s = this.toString() + "\n";
		SudokuIterator it = SudokuIterator.createIterator(getSize(), getBlockSize(), new SudokuCoords(0,0), SubStructures.WHOLE);
		while (it.hasNext())
		{
			SudokuCoords sc = it.next();
			SudokuEntry se = this.getSudokuEntry(sc);
			if (se.isBlocked()) s+=" B";
			else if (se.isFixed()) s+=" F";
			else if (se.isEmpty()) s+=" _";
			else s+=" S";
		
			if (sc.getCol()==getSize()-1) s+="\n";
		}
		return s;
	}
	
	public String toDumpString()
	{
		String s = this.toString() + "\n";
		SudokuIterator it = SudokuIterator.createIterator(getSize(), getBlockSize(), new SudokuCoords(0,0), SubStructures.WHOLE);
		while (it.hasNext())
		{
			SudokuCoords sc = it.next();
			SudokuEntry se = this.getSudokuEntry(sc);
			s+=se.toDumpString();
			if (sc.getCol()==getSize()-1) s+="\n";
		}
		
		s+="Old Values:\n{";
		for (int i=0; i<oldSudokuEntries.size();i++)
			s+=oldSudokuEntries.get(i).toDumpString();
		s+="}\n";
		return s;
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
