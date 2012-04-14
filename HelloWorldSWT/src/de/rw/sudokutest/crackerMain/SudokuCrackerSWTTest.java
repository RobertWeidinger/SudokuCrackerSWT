package de.rw.sudokutest.crackerMain;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.rw.sudoku.crackerMain.SudokuCrackerSWT;
import de.rw.sudoku.model.SudokuCoords;
import de.rw.sudoku.views.SuText;
import de.rw.sudoku.views.SudokuView;


public class SudokuCrackerSWTTest {

	private SudokuCrackerSWT sc;
	
	@Before
	public void setUp() throws Exception {
		sc = new SudokuCrackerSWT();
		sc.initUI();
	}

	@After
	public void tearDown() throws Exception {
		sc.getShell().getDisplay().dispose();
	}

	@Test
	public void testInitUI() {
		SudokuView sv = sc.getSv();
		SuText sut = sv.getSuText(new SudokuCoords(0,0));
		Assert.assertEquals("",sut.getString());
		Display d = sc.getDisplay(); 
		Control cArray[] = d.getShells()[0].getChildren();
		boolean bFound=false;
		for (int i=0; i<cArray.length; i++)
		{
			Control c=cArray[i];
			if (c instanceof Button)
			{
				Button b = (Button)c;
				String sb = b.getText();
				if (sb.equals("Datei laden")) bFound=true;
			}
		}
		Assert.assertTrue(bFound);
	}

}
