import junit.framework.Assert;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class SudokuViewTest {
	
	private Shell shell;
	private Display display;
	private SudokuView sv;

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
		SuText st = sv.getSuText(0, 0);
		String s = st.getString();
		Assert.assertEquals("9", s);
	}

	@Test
	public void testUpdate() {
		SudokuModel sm = sv.getModel();
		sm.setFixedValue(5, 0, 5);
		SuText st = sv.getSuText(5, 0);
		String s = st.getString();
		Assert.assertEquals("5", s );
	}

	@Test
	public void testUpdateModel() {
		
	}

}
