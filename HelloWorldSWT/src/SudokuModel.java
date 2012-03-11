import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;


public class SudokuModel implements Model {

	private ArrayList<View> alViews=null;
	private int size;
	public int getSize() {
		return size;
	}

	private int blockSize;
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
	
	private void updateViews()
	{
		for (View v: alViews)
			v.update();
	}
	
	private boolean inBounds(int i, int j)
	{
		try {
			arraySudokuEntries.get(i).get(j);
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public SudokuEntry getSudokuEntry(int i, int j)
	{
		if (!inBounds(i,j)) return null;
		return arraySudokuEntries.get(i).get(j);
	}
	
	public static final int FIXED=1;
	public static final int SUGGESTED=2;
	public static final int EMPTY=3;
	
	private SudokuEntry setNewValue(int i, int j, int value, int flagFixedSuggestedEmpty)
	{
		if (!inBounds(i,j)) return null;
		SudokuEntry se = getSudokuEntry(i, j);
		oldSudokuEntries.push(se);
		se = new SudokuEntry(i,j);
		if (flagFixedSuggestedEmpty!=EMPTY)
		{
			se.setValue(value);
			if (flagFixedSuggestedEmpty==FIXED)
				se.makeFixed();
		}
		arraySudokuEntries.get(i).set(j,se);
		updateViews();
		return se;
	}
	
	public SudokuEntry setEmptyValue(int i, int j)
	{
		return setNewValue(i,j,-1,EMPTY);
	}
	
	public SudokuEntry setFixedValue(int i, int j, int value)
	{
		return setNewValue(i,j,value,FIXED);
	}
	
	public SudokuEntry setSuggestedValue(int i, int j, int value)
	{
		return setNewValue(i,j,value,SUGGESTED);
	}

	public boolean isValidModel(int i, int j)
	// Achtung: Ein Modell, in dem der Anwender einen Denkfehler gemacht hat, ist valide. 
	//          Hat er aber eine unerlaubte Zahl eingetragen (z.B. 2-, 0 oder etwas größeres als size) ==> nicht valide.
	{
		return getSudokuEntry(i,j).isValid(1, getSize());
	}
	
	public boolean isValidModel()
	{
		for (int i=0; i<this.size; i++)
			for (int j=0; j<this.size; j++)
				if (!isValidModel(i,j)) return false;
		return true;
	}
	
	public void makeValuesFixed()
	{
		for (int i = 0; i<size; i++)
		{
			for (int j=0; j<size; j++)
			{
				SudokuEntry se = getSudokuEntry(i, j);
				if (!se.isEmpty())
					se.makeFixed();
			}
		}
		updateViews();
	}

	@Override
	public void registerView(View _v) {
		alViews.add(_v);
	}
		
	public Integer getValue(int row, int col)
	{
		return getSudokuEntry(row, col).getValue();
	}
	
	public boolean isFixed(int row, int col)
	{
		return getSudokuEntry(row, col).isFixed();
	}
	
	public boolean isEmpty(int row, int col)
	{
		return getSudokuEntry(row, col).isEmpty();
	}
	
	public void undo()
	{
		if (oldSudokuEntries.size()==0) return;
		SudokuEntry ose = oldSudokuEntries.pop();
		Vector<SudokuEntry> alSe = arraySudokuEntries.get(ose.getRow());
		alSe.set(ose.getCol(), ose);
		updateViews();
	}
	
	public String toString()
	{
		String s = new String();
		
		s+= this.getSize() + " " + this.getBlockSize() + "\n\n";
				
		for (int i=0; i<this.size; i++)
		{
			for (int j=0; j<this.size; j++)
			{
				SudokuEntry se = this.getSudokuEntry(i, j);
				if (se.isEmpty())
					s+= " _";
				else if (se.isValid(1, getSize()))
					s+= " " + se.getValue();
				else 
					s+= " ?";
			}
			s+="\n";
		}
		return s;
	}
	
	public String toStringWithFlags()
	{
		String s = this.toString() + "\n";
		for (int i=0; i<this.size; i++)
		{
			for (int j=0; j<this.size; j++)
			{
				SudokuEntry se = this.getSudokuEntry(i, j);
				if (se.isEmpty()) s+=" _";
				else if (se.isFixed()) s+=" F";
				else s+=" S";
			}
			s+="\n";
		}
		return s;
	}
	
	public String toDumpString()
	{
		String s = this.toString() + "\n";
		for (int i=0; i<this.size; i++)
		{
			for (int j=0; j<this.size; j++)
			{
				SudokuEntry se = this.getSudokuEntry(i, j);
				s+=se.toDumpString();
			}
			s+="\n";
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
			sm.setFixedValue(i, i, 9-i);
			if (i<sm.getSize()-1)
				sm.setSuggestedValue(i+1, i, i+1);
		}
		sm.setFixedValue(2, 8, 9);
		sm.setSuggestedValue(0, 7, 1);
		return sm;
	}

	public static SudokuModel createCorruptModel1()
	{
		SudokuModel sm = createTestModel1();
		SudokuEntry se = sm.getSudokuEntry(7, 2);
		se.makeFixed();
		return sm;
	}
		
}
