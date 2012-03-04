import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;


public class SudokuFileReader {

	public static void readSudokuFromFile(SudokuModel sm, String longPath) throws IOException
	{
	    BufferedReader reader = new BufferedReader( new FileReader (longPath));
	    String line  = null;
	    StringBuilder stringBuilder = new StringBuilder();
	    while( ( line = reader.readLine() ) != null ) {
	        stringBuilder.append( line + " ");
	    }
	    int size = -1;
	    int subSize = -1;
	    StringTokenizer st = new StringTokenizer(stringBuilder.toString());
	    if (st.hasMoreTokens()) size = Integer.valueOf(st.nextToken()).intValue();
	    if (st.hasMoreTokens()) subSize = Integer.valueOf(st.nextToken()).intValue();
	    
	    sm.reInit(size, subSize); 
	    int i=0;
	    int j=0;
	    while (st.hasMoreTokens()) {
	    	  String s = st.nextToken();
	    	  System.out.println(s);
	    	  if (s.equals("_"))
	    		  sm.setEmptyValue(i, j);
	    	  else
	    	  {
	    		  Integer intg = Integer.valueOf(s);
	    		  int i2 = intg.intValue();
	    		  sm.setFixedValue(i, j, i2);
	    	  }
	    	  if (j==size-1)
	    	  {
	    		  j=0;
	    		  i++;
	    	  }
	    	  else
	    		  j++;
	    	}		
	}
}
