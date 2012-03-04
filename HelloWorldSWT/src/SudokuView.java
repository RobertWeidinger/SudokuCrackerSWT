import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;


public class SudokuView implements View {

	private SudokuModel sm;
	private SuText[][] textArray;
	private Composite parent;
	private boolean updateModelSuppressed; // Semaphor;
	private Color colorEditable;
	private Color colorFixed;
	private Color colorError;
	
	public Composite getParent() {
		return parent;
	}

	public Color getColorEditable() {
		return colorEditable;
	}

	public Color getColorFixed() {
		return colorFixed;
	}
	
	public Color getColorError() {
		return colorError;
	}

	private static Point textSize=new Point(30,30);
	private static int textInterspace=10;
	
	public SudokuView(Composite _parent, SudokuModel _sm)
	{
		//super(_parent,0);
		sm = _sm;
		parent = _parent;
		updateModelSuppressed = false;
		textArray = new SuText[sm.getSize()][sm.getSize()];
		colorEditable =new Color(parent.getBackground().getDevice(),255,255,255);
		colorFixed = new Color(parent.getBackground().getDevice(),128,255,128);
		colorError = new Color(parent.getBackground().getDevice(),128,128,255);
		for (int i=0; i<sm.getSize(); i++)
			for (int j=0; j<sm.getSize(); j++)
			{
				textArray[i][j]=new SuText(this, SWT.SINGLE,i,j);
				SuText sut = textArray[i][j];
				sut.setSize(textSize);
				sut.setLocation(textInterspace+j*(textSize.x+textInterspace),textInterspace+i*(textSize.y+textInterspace) );
			}
		sm.registerView(this);
	}
	
	public SudokuModel getModel()
	{
		return sm;
	}
	
	@Override
	public void update() 
	{
		updateModelSuppressed = true;
		for (int i=0; i<sm.getSize(); i++)
			for (int j=0; j<sm.getSize(); j++)
			{
				int v = sm.getValue(i, j);
				SuText sut = textArray[i][j];
				String s = new String();
				if (v<0)
				{
					s+="";
					sut.setTextIfNew(s);
					sut.setPropertiesEditable();
				}
				else if (v<10)
				{
					s+=String.valueOf(v);
					sut.setTextIfNew(s);
					if (sm.isFixed(i, j))
						sut.setPropertiesFixed();
					else
						sut.setPropertiesEditable();
				}
				else
				{
					s+="?!?";
					sut.setTextIfNew(s);
					sut.setPropertiesError();
				}
			}
		updateModelSuppressed=false;
	}
	
	public void updateModel(int row, int col, int value, boolean fixed)
	{
		if (updateModelSuppressed) return;
		if (fixed)
		{
			if (sm.getFixedValue(row, col)!=value)
				sm.setFixedValue(row, col, value);
		}
		else
		{
			if (sm.getSuggestedValue(row, col)!=value)
				sm.setSuggestedValue(row, col, value);
		}
	}

	public SuText getSuText(int i, int j)
	{
		return textArray[i][j];
	}
	
// Testmethode
	public static SudokuView createTestSudokuView(Composite _parent)
	{
		SudokuView sv = new SudokuView(_parent, SudokuModel.createTestModel1());
		sv.update();
		return sv;
	}

	
}
