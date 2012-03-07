
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


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
		SuText sut = sv.getSuText(0,0);
		Assert.assertEquals("",sut.getString());
		SudokuDumpView sdv = sc.getSdv();
		String s = sdv.getString();
		Assert.assertTrue(s.startsWith("9"));
		Display d = sc.getDisplay(); //getDisplay();
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
