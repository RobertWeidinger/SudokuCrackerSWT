import java.util.LinkedList;


public class SudokuHelper {

	private SudokuModel sm;
	
	public SudokuHelper(SudokuModel _sm)
	{sm=_sm;}
	
	public int findValueInRow(int row, Integer value)
	{
		for (int j=0; j<sm.getSize(); j++)
		{
			if (value.equals(sm.getValue(row, j)))
				return j;
		}
		return -1;
	}
	
	public int[] findValueInBlock(int row1stField, int col1stField, Integer value)
	// result[0]=Zeile, result[1]=Spalte
	{
		int res[] = new int[2];
		res[0]=-1;
		res[1]=-1;
		for (int i=row1stField; i<row1stField+sm.getBlockSize();i++)
			for (int j=col1stField; j<col1stField+sm.getBlockSize(); j++)
				if (value.equals(sm.getValue(i, j)))
				{
					res[0]=i;
					res[1]=j;
					return res;
				}
		return res;
	}
	
	public LinkedList<SudokuFieldValues> findSiblingsInRows(int rowBlock, // 0,1,2
								  				Integer number)
	{
		LinkedList<SudokuFieldValues> res = new LinkedList<SudokuFieldValues>();
		for (int i=rowBlock*sm.getBlockSize(); i<(rowBlock+1)*sm.getBlockSize();i++)
		{
			for (int j=0; j<sm.getSize(); j++)
				if (number.equals(sm.getValue(i, j)))
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
				Integer number)
	{
		LinkedList<SudokuFieldValues> res = new LinkedList<SudokuFieldValues>();
		for (int c=colBlock*sm.getBlockSize(); c<(colBlock+1)*sm.getBlockSize();c++)
			{
			for (int r=0; r<sm.getSize(); r++)
				if (number.equals(sm.getValue(r, c)))
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
	
	public int findUniquePlaceForNumberInRowPart(int row, int startCol, int endCol, Integer number)
	{
		int numberOfFreePlaces=0;
		int colRes = -1;
		for (int i=startCol; i<=endCol; i++)
		{
			if (number.equals(sm.getValue(row, i))) return -1;
			if (sm.isEmpty(row, i))
			{
				LinkedList<SudokuFieldValues> ll = findConflicts(row, i, number);
				if (ll.size()==0) // no conflict
				{
					numberOfFreePlaces++;
					colRes = i;
				}
			}
		}
		if (numberOfFreePlaces==1) return colRes;
		return -1;
	}
	
	public int findUniquePlaceForNumberInColPart(int col, int startRow, int endRow, Integer number)
	{
		int numberOfFreePlaces=0;
		int rowRes = -1;
		for (int i=startRow; i<=endRow; i++)
		{
			if (number.equals(sm.getValue(i,col))) return -1;
			if (sm.isEmpty(i,col))
			{
				LinkedList<SudokuFieldValues> ll = findConflicts(i, col, number);
				if (ll.size()==0) // no conflict
				{
					numberOfFreePlaces++;
					rowRes = i;
				}
			}
		}
		if (numberOfFreePlaces==1) return rowRes;
		return -1;
	}
	
	public int[] blockCoords(int row, int col)
	{
		int []res = new int[2];
		res[0]= row / sm.getBlockSize();
		res[1]= col / sm.getBlockSize();
		return res;
	}
	
	public LinkedList<SudokuFieldValues> findConflicts(int row, int col, Integer number)
	{
		LinkedList<SudokuFieldValues> ll = new LinkedList<SudokuFieldValues>();
		
		if (number.intValue()<0) return ll;
		
		// Suche in Zeile
		for (int j=0; j<sm.getSize(); j++)
		{
			if (j==col) continue;
			if (number.equals(sm.getValue(row, j)))
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
			if (number.equals(sm.getValue(i, col)))
			{
				LinkedList<Integer> llI = new LinkedList<Integer>();
				llI.add(number);
				ll.add(new SudokuFieldValues(i, col, llI));
			}
		}
		
		// Suche in Block
		int iBlockCoords[] = blockCoords(row, col);
		for (int i=iBlockCoords[0]*sm.getBlockSize(); i<(iBlockCoords[0]+1)*sm.getBlockSize(); i++ )
			for (int j=iBlockCoords[1]*sm.getBlockSize(); j<(iBlockCoords[1]+1)*sm.getBlockSize(); j++)
			{
				if (i==row && j==col) continue;
				if (number.equals(sm.getValue(i,j)))
				{
					LinkedList<Integer> llI = new LinkedList<Integer>();
					llI.add(number);
					SudokuFieldValues sfv = new SudokuFieldValues(i, j, llI);
					if (!ll.contains(sfv))
						ll.add(new SudokuFieldValues(i, j, llI));
				}
			}
		return ll;
	}
	
}
