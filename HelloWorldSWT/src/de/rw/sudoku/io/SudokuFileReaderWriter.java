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
	}
	
	public static void writeSudokuToFile(SudokuModel sm, String longPath) throws IOException
	{
		BufferedWriter bw = new BufferedWriter( new FileWriter(longPath) );
		bw.write(sm.toDumpString());
		bw.close();
	}
	
	
}
