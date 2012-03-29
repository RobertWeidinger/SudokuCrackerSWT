package de.rw.sudoku.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;

import de.rw.sudoku.algorithms.SudokuHelper;
import de.rw.sudoku.model.SudokuCoords;
import de.rw.sudoku.model.SudokuModel;
import de.rw.sudoku.model.iterators.SudokuIterator;


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
				textArray[i][j]=new SuText(this, SWT.SINGLE, new SudokuCoords(i,j));
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
		SudokuIterator si = SudokuIterator.createIterator(getModel().getSize(), getModel().getBlockSize(), new SudokuCoords(0,0), SudokuIterator.SubStructures.WHOLE);
		while (si.hasNext())
		{
			SudokuCoords sc = si.next();
			SuText sut = textArray[sc.getRow()][sc.getCol()];
			String s = sm.entryToDisplayString(sc);
			sut.setTextIfNew(s);
			if (sm.isFixed(sc))//(se.isFixed())
				sut.setPropertiesFixed();
			else if (!sm.isValidModel(sc))
				sut.setPropertiesError();
			else if (sm.isBlocked(sc))
				sut.setPropertiesBlocked();
			else
				sut.setPropertiesEditable();
			
			if (!sm.noValue(sc) && sh.findConflicts(sc, sm.getValue(sc)).size()>0)
				sut.setPropertiesError();
		}
		
		updateModelSuppressed=false;
	}
	
	public void updateModel(SudokuCoords sc, int value, boolean fixed)
	{
		if (updateModelSuppressed) return;
		if (value<0)
			sm.setEmptyValue(sc);
		else if (fixed)
			sm.setFixedValue(sc, value);
		else
			sm.setSuggestedValue(sc, value);
	}

	public SuText getSuText(SudokuCoords sc)
	{
		return textArray[sc.getRow()][sc.getCol()];
	}
	
// Testmethode
	public static SudokuView createTestSudokuView(Composite _parent)
	{
		SudokuView sv = new SudokuView(_parent, SudokuModel.createTestModel1());
		sv.update();
		return sv;
	}

	
}
