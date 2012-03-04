import java.util.ArrayList;


public class SudokuModel implements Model {

	private ArrayList<View> alViews=null;
	private int size;
	public int getSize() {
		return size;
	}

	private int subSize;
	public int getSubSize() {
		return subSize;
	}

	private Integer[][] iValues;
	private Integer[][] stepValues;
	private int         step;
	
	
	public SudokuModel(int _size, int _subSize)
	{
		reInit(_size, _subSize);
	}



	public void reInit(int _size, int _subSize) {
		size = _size;
		subSize = _subSize;
		iValues = new Integer[size][size];
		stepValues = new Integer[size][size];
		step = -1;
		if (alViews==null) alViews = new ArrayList<View>();
		
		for (int i = 0; i<size; i++)
		{
			for (int j=0; j<size; j++)
			{
				iValues[i][j] = new Integer(step);
				stepValues[i][j] = new Integer(step);
			}
		}
	}
	
	private void updateViews()
	{
		for (View v: alViews)
			v.update();
	}
	
	public void setEmptyValue(int i, int j)
	{
		iValues[i][j] = new Integer(-1);
		stepValues[i][j] = -1;
		updateViews();
	}
	
	
	public void setFixedValue(int i, int j, int value)
	{
		iValues[i][j] = new Integer(value);
		stepValues[i][j] = 0;
		updateViews();
	}
	
	public void setSuggestedValue(int i, int j, int value)
	{
		iValues[i][j] = new Integer(value);
		if (step<0) step=1;
		stepValues[i][j]=step++;
		updateViews();
	}
	
	public Integer getFixedValue(int i, int j)
	{
		if (stepValues[i][j].intValue()==0)
			return iValues[i][j];
		return new Integer(-1);
	}

	public Integer getSuggestedValue(int i, int j)
	{
		if (stepValues[i][j].intValue()>0)
			return iValues[i][j];
		return new Integer(-1);
	}
	
	public boolean isConsistentModel(int i, int j)
	{
		int v=iValues[i][j].intValue();
		int sv = stepValues[i][j].intValue();
		if (v<-1 || v==0 || v>size || sv<-1) return false;
		if (sv<0 && v>=0) return false;
		if (sv>=0 && v<0) return false;
		return true;
	}
	
	public boolean isConsistentModel()
	{
		for (int i=0; i<this.size; i++)
			for (int j=0; j<this.size; j++)
				if (!isConsistentModel(i,j)) return false;
		return true;
	}
	
	public void makeValuesFixed()
	{
		for (int i = 0; i<size; i++)
		{
			for (int j=0; j<size; j++)
			{
				if (stepValues[i][j]>0)
					stepValues[i][j] = new Integer(0);
			}
		}
		updateViews();
	}

	@Override
	public void registerView(View _v) {
		// TODO Auto-generated method stub
		alViews.add(_v);
	}
		
	public Integer getValue(int row, int col)
	{
		if (stepValues[row][col].intValue()>=0)
			return iValues[row][col];
		return new Integer(-1);
	}
	
	public boolean isFixed(int row, int col)
	{
		return stepValues[row][col]==0;
	}
	
	public void undo()
	{
		if (step==0) return;
		for (int i=0; i<this.getSize(); i++)
			for (int j=0; j<this.getSize(); j++)
				if (stepValues[i][j]>=step-1)
				{
					stepValues[i][j]=-1;
					iValues[i][j]=new Integer(-1);
				}
		step--;
		updateViews();
	}
	
	public String toString()
	{
		String s = new String();
		
		s+= this.getSize() + " " + this.getSubSize() + "\n\n";
				
		for (int i=0; i<this.size; i++)
		{
			for (int j=0; j<this.size; j++)
			{
				int v = this.getValue(i, j).intValue();
				if (v==-1)
					s+= " _";
				else if (v>0 && v<=this.getSize())
					s+= " " + v;
				else 
					s+= " ?";
			}
			s+="\n";
		}
		return s;
	}
	
	public String toStringWithStepValues()
	{
		String s = this.toString() + "\n";
		for (int i=0; i<this.size; i++)
		{
			for (int j=0; j<this.size; j++)
			{
				if (stepValues[i][j]>=0) s+=" ";
				s+=" "+stepValues[i][j];
			}
			s+="\n";
		}
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
		sm.stepValues[0][0]=-1;
		return sm;
	}
	
	public int getStepValue(int i, int j)
	{ 
		return stepValues[i][j].intValue();
	}
	
}
