package de.rw.sudoku.io;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import de.rw.sudoku.model.SudokuModel;


public class SudokuFileReaderWriter {

	public static boolean readSudokuFromFile(SudokuModel sm, String longPath) throws IOException
	{
	    BufferedReader reader = new BufferedReader( new FileReader (longPath));
	    String line  = null;
	    StringBuilder stringBuilder = new StringBuilder();
	    while( ( line = reader.readLine() ) != null ) {
	        stringBuilder.append( line + " ");
	    }
	    
	    return SudokuModel.scanFromDumpString(sm, stringBuilder.toString());
	    
/*	    int size = -1;
	    int subSize = -1;
	    StringTokenizer st = new StringTokenizer(stringBuilder.toString());
	    if (st.hasMoreTokens()) size = Integer.valueOf(st.nextToken()).intValue();
	    if (st.hasMoreTokens()) subSize = Integer.valueOf(st.nextToken()).intValue();
	    
	    sm.reInit(size, subSize); 
	    int i=0;
	    int j=0;
	    while (st.hasMoreTokens()) {
	    	  String s = st.nextToken();
	    	  if (s.equals("_"))
	    		  sm.setEmptyValue(new SudokuCoords(i, j));
	    	  else
	    	  {
	    		  Integer intg = Integer.valueOf(s);
	    		  sm.setFixedValue(new SudokuCoords(i, j), intg);
	    	  }
	    	  if (j==size-1)
	    	  {
	    		  j=0;
	    		  i++;
	    	  }
	    	  else
	    		  j++;
	    	}		
*/	
	    
	}
	
	public static void writeSudokuToFile(SudokuModel sm, String longPath) throws IOException
	{
		BufferedWriter bw = new BufferedWriter( new FileWriter(longPath) );
		bw.write(sm.toDumpString());
		bw.close();
	}
	
	
}
