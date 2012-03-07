import java.util.LinkedList;


public class SudokuHelper {

	private SudokuModel sm;
	
	public SudokuHelper(SudokuModel _sm)
	{sm=_sm;}
	
	public int findValueInRow(int row, int value)
	{
		for (int j=0; j<sm.getSize(); j++)
		{
			if (sm.getValue(row, j)==value)
				return j;
		}
		return -1;
	}
	
	public int[] findValueInBlock(int row1stField, int col1stField, int value)
	// result[0]=Zeile, result[0]=Spalte
	{
		int res[] = new int[2];
		res[0]=-1;
		res[1]=-1;
		for (int i=row1stField; i<row1stField+sm.getSubSize();i++)
			for (int j=col1stField; j<col1stField+sm.getSubSize(); j++)
				if (sm.getValue(i, j)==value)
				{
					res[0]=i;
					res[1]=j;
					return res;
				}
		return res;
	}
	
	public LinkedList<SudokuFieldValues> findSiblingsInRows(int rowBlock, // 0,1,2
								  				int number)
	{
		LinkedList<SudokuFieldValues> res = new LinkedList<SudokuFieldValues>();
		for (int i=rowBlock*sm.getSubSize(); i<(rowBlock+1)*sm.getSubSize();i++)
		{
			for (int j=0; j<sm.getSize(); j++)
				if (sm.getValue(i, j).intValue()==number)
				{
					SudokuFieldValues sfv = new SudokuFieldValues(i, j);
					sfv.addValue(number);
					res.add(sfv);
				}
		}
		if (res.size()<2)
			res.clear();
		return res;
	}
	
	public LinkedList<SudokuFieldValues> findSiblingsInCols(int colBlock, // 0,1,2
				int number)
	{
		LinkedList<SudokuFieldValues> res = new LinkedList<SudokuFieldValues>();
		for (int c=colBlock*sm.getSubSize(); c<(colBlock+1)*sm.getSubSize();c++)
			{
			for (int r=0; r<sm.getSize(); r++)
				if (sm.getValue(r, c).intValue()==number)
				{
				SudokuFieldValues sfv = new SudokuFieldValues(r, c);
				sfv.addValue(number);
				res.add(sfv);
				}
			}
		if (res.size()<2)
		res.clear();
		return res;
	}
	
	public int findUniquePlaceForNumberInRowPart(int row, int startCol, int endCol, int number)
	{
		int numberOfFreePlaces=0;
		int colRes = -1;
		for (int i=startCol; i<=endCol; i++)
		{
			if (sm.getValue(row, i).intValue()==number) return -1;
			if (sm.getValue(row, i).intValue()==-1)
			{
				numberOfFreePlaces++;
				colRes = i;
			}
		}
		if (numberOfFreePlaces==1) return colRes;
		return -1;
	}
	
	public int[] blockCoords(int row, int col)
	{
		int []res = new int[2];
		res[0]= row / sm.getSubSize();
		res[1]= col / sm.getSubSize();
		return res;
	}
	
	public LinkedList<SudokuFieldValues> findConflicts(int row, int col, int number)
	{
		LinkedList<SudokuFieldValues> ll = new LinkedList<SudokuFieldValues>();
		// Suche in Zeile
		for (int j=0; j<sm.getSize(); j++)
		{
			if (j==col) continue;
			if (sm.getValue(row, j)==number)
			{
				LinkedList<Integer> llI = new LinkedList<Integer>();
				llI.add(number);
				ll.add(new SudokuFieldValues(row, j, llI));
			}
		}
		
		// Suche in Spalte
		for (int i=0; i<sm.getSize(); i++)
		{
			if (i==row) continue;
			if (sm.getValue(i, col)==number)
			{
				LinkedList<Integer> llI = new LinkedList<Integer>();
				llI.add(number);
				ll.add(new SudokuFieldValues(i, col, llI));
			}
		}
		
		// Suche in Block
		int iBlockCoords[] = blockCoords(row, col);
		for (int i=iBlockCoords[0]*sm.getSubSize(); i<(iBlockCoords[0]+1)*sm.getSubSize(); i++ )
			for (int j=iBlockCoords[1]*sm.getSubSize(); j<(iBlockCoords[1]+1)*sm.getSubSize(); j++)
			{
				if (i==row && j==col) continue;
				if (sm.getValue(i,j).intValue()==number)
				{
					LinkedList<Integer> llI = new LinkedList<Integer>();
					llI.add(number);
					ll.add(new SudokuFieldValues(i, j, llI));
				}
			}
		return ll;
	}
	
}
