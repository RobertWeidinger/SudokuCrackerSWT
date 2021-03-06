package de.rw.sudokutest.views;
import java.io.IOException;

import junit.framework.Assert;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.rw.sudoku.io.SudokuFileReaderWriter;
import de.rw.sudoku.model.SudokuCoords;
import de.rw.sudoku.model.SudokuModel;
import de.rw.sudoku.views.SuText;
import de.rw.sudoku.views.SudokuView;


public class SudokuViewTest {
	
	private Shell shell;
	private Display display;
	private SudokuView sv;

	private SudokuModel sm;

//	private void log(String s)
//	{
//		//System.out.println(s);
//	}

	
	@Before
	public void setUp() throws Exception {
    	display = new Display();
		shell = new Shell(display);
		shell.setText("Sudoku Cracker (in spe...)");
		shell.open();
        sv = SudokuView.createTestSudokuView(shell);
	}

	@After
	public void tearDown() throws Exception {
		shell.getDisplay().dispose();
    }

	@Test
	public void testSudokuView() {
		SuText st = sv.getSuText(new SudokuCoords(0, 0));
		String s = st.getString();
		Assert.assertEquals("9", s);
	}

	@Test
	public void testUpdate() {
		SudokuModel sm = sv.getModel();
		SudokuCoords sc = new SudokuCoords(5,0);
		sm.setFixedValue(sc, 5);
		SuText st = sv.getSuText(sc);
		String s = st.getString();
		Assert.assertEquals("5", s );
	}

	@Test
	public void testUpdateModel() {
		
	}
	
	@Test
	public void testSubString() {
		sm = new SudokuModel(9,3);
		try {
			SudokuFileReaderWriter.readSudokuFromFile(sm, "src\\de\\rw\\sudokutest\\zzTestdata\\BlackberryProfi4_VieleBlockingValues_Format2.0.txt");
		} catch (IOException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		SudokuView sv = new SudokuView(shell, sm);
		sv.update();
		String subText = sv.getSubTextStringForTest();
		boolean bEnthaeltGeloest = subText.contains("gel�st");
		Assert.assertEquals(bEnthaeltGeloest,false);
	}

}
