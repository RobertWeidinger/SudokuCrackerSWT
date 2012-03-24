package de.rw.sudoku.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;

import de.rw.sudoku.algorithms.SudokuHelper;
import de.rw.sudoku.model.SudokuModel;


public class SudokuView implements View {

	private SudokuModel sm;
	private SuText[][] textArray;
	private Composite parent;
	private boolean updateModelSuppressed; // Semaphor;
	private Color colorEditable;
	private Color colorFixed;
	private Color colorError;
	private Color colorBlocked;
	
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
	
	public Color getColorBlocked() {
		return colorBlocked;
	}

	private static Point textSize=new Point(30,30);
	private static int textInterspace=10;
	
	public SudokuView(Composite _parent, SudokuModel _sm)
	{
		sm = _sm;
		parent = _parent;
		updateModelSuppressed = false;
		textArray = new SuText[sm.getSize()][sm.getSize()];
		colorEditable =new Color(parent.getBackground().getDevice(),255,255,255);
		colorFixed = new Color(parent.getBackground().getDevice(),128,255,128);
		colorError = new Color(parent.getBackground().getDevice(),128,128,255);
		colorBlocked = new Color(parent.getBackground().getDevice(),255,166,166);
		int additionalRowSpace=0;
		for (int i=0; i<sm.getSize(); i++)
		{
			if (i%sm.getBlockSize()==0)
				additionalRowSpace+=5;
			int additionalColSpace=0;
			for (int j=0; j<sm.getSize(); j++)
			{
				if (j%sm.getBlockSize()==0)
					additionalColSpace+=5;
				textArray[i][j]=new SuText(this, SWT.SINGLE,i,j);
				SuText sut = textArray[i][j];
				sut.setSize(textSize);
				sut.setLocation(textInterspace+j*(textSize.x+textInterspace)+additionalColSpace,
								textInterspace+i*(textSize.y+textInterspace)+additionalRowSpace );
			}
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
		SudokuHelper sh = new SudokuHelper(getModel());
		for (int i=0; i<sm.getSize(); i++)
			for (int j=0; j<sm.getSize(); j++)
			{
				SuText sut = textArray[i][j];
				String s = sm.entryToDisplayString(i, j);
				sut.setTextIfNew(s);
				if (sm.isFixed(i, j))//(se.isFixed())
					sut.setPropertiesFixed();
				else if (!sm.isValidModel(i, j))
					sut.setPropertiesError();
				else if (sm.isBlocked(i, j))
					sut.setPropertiesBlocked();
				else
					sut.setPropertiesEditable();
				
				if (!sm.isEmpty(i, j) && sh.findConflicts(i, j, sm.getValue(i, j)).size()>0)
					sut.setPropertiesError();
			}
		
		updateModelSuppressed=false;
	}
	
	public void updateModel(int row, int col, int value, boolean fixed)
	{
		if (updateModelSuppressed) return;
		if (value<0)
			sm.setEmptyValue(row, col);
		else if (fixed)
			sm.setFixedValue(row, col, value);
		else
			sm.setSuggestedValue(row, col, value);
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
