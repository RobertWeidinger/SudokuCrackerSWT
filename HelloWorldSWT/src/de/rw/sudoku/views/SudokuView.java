package de.rw.sudoku.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import de.rw.sudoku.model.SudokuCoords;
import de.rw.sudoku.model.SudokuModel;
import de.rw.sudoku.model.iterators.SudokuIterator;


public class SudokuView extends Composite implements View {

	private final static int   additionalSpaceBetweenColsAndRows=5;
	private final static int[] rgbEditable    = new int[]{255,255,255};
	private final static int[] rgbFixed       = new int[]{128,255,128};
	private final static int[] rgbBlocked     = new int[]{255,166,166};
	private final static int[] rgbError       = new int[]{128,128,255};
	private final static Point textSize       = new Point(30,30);
	private final static int   textInterspace = 10;
	private SudokuModel sm;
	private SuText[][] textArray;
	private Label subText;
	private boolean updateModelSuppressed; // Semaphor;
	private Color colorEditable;
	private Color colorFixed;
	private Color colorError;
	private Color colorBlocked;
	
	public void log(String s)
	{
		System.out.println(s);
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

	
	public SudokuView(Composite _parent, SudokuModel _sm)
	{
		super(_parent,SWT.NO_BACKGROUND);
		sm = _sm;
		updateModelSuppressed = false;
		textArray = new SuText[sm.getSize()][sm.getSize()];
		colorEditable =new Color(getBackground().getDevice(),rgbEditable[0],rgbEditable[1],rgbEditable[2]);
		colorFixed = new Color(getBackground().getDevice(),rgbFixed[0],rgbFixed[1],rgbFixed[2]);
		colorError = new Color(getBackground().getDevice(),rgbError[0],rgbError[1],rgbError[2]);
		colorBlocked = new Color(getBackground().getDevice(),rgbBlocked[0],rgbBlocked[1],rgbBlocked[2]);
		int additionalRowSpace=0;
		int rowLocation = 0;
		int colLocation = 0;
		for (int i=0; i<sm.getSize(); i++)
		{
			if (i%sm.getBlockSize()==0)
				additionalRowSpace+=additionalSpaceBetweenColsAndRows;
			int additionalColSpace=0;
			for (int j=0; j<sm.getSize(); j++)
			{
				if (j%sm.getBlockSize()==0)
					additionalColSpace+=additionalSpaceBetweenColsAndRows;
				textArray[i][j]=new SuText(this, SWT.SINGLE, new SudokuCoords(i,j));
				SuText sut = textArray[i][j];
				sut.setSize(textSize);
				rowLocation = textInterspace+i*(textSize.y+textInterspace)+additionalRowSpace;
				colLocation = textInterspace+j*(textSize.x+textInterspace)+additionalColSpace;
				sut.setLocation(colLocation, rowLocation );
			}
		}

		subText = new Label(this.getParent(), SWT.SINGLE);
		subText.setSize(colLocation, textSize.y);
		subText.setLocation(new Point(textInterspace+5, rowLocation+textSize.y+textInterspace+additionalRowSpace));
		updateSubText();
		
		sm.registerView(this);
	}

	private void updateSubText() {
		String additionalString = new String("");
		if (sm.hasConflicts())
			additionalString+=" - Konflikte!";
		else if (sm.numberOfNonEmptyFields()==sm.getSize()*sm.getSize())
			additionalString+=" - gelöst!";
		subText.setText(sm.numberOfValueFields() + "/" + (sm.getSize()*sm.getSize()) + additionalString);
	}
	
	public SudokuModel getModel()
	{
		return sm;
	}
	
	@Override
	public void update() 
	{
		updateModelSuppressed = true;
		SudokuIterator si = SudokuIterator.createIterator(getModel().getSize(), getModel().getBlockSize(), new SudokuCoords(0,0), SudokuIterator.SubStructures.WHOLE);
		while (si.hasNext())
		{
			SudokuCoords sc = si.next();
			SuText sut = textArray[sc.getRow()][sc.getCol()];
			String s = sm.entryToDisplayString(sc);
			sut.setTextIfNew(s);
			if (sm.isFixed(sc))//(se.isFixed())
				sut.setPropertiesFixed();
			else if (!sm.isValidModel(sc) || sm.hasConflicts(sc))
				sut.setPropertiesError();
			else if (sm.isBlocked(sc))
				sut.setPropertiesBlocked();
			else
				sut.setPropertiesEditable();
		}
		updateSubText();

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

//================= was ausprobieren =======================
//	
//	
//	private GC gc=null;
//	private Rectangle r=null;
//	public void tryPaintFrame() {
//		r = new Rectangle(colLocations[0][0]-spaceBetweenTextAndFrame,
//				  rowLocations[0][0]-spaceBetweenTextAndFrame,
//				  colLocations[0][sm.getSize()-1] - colLocations[0][0]+textSize.x+2*spaceBetweenTextAndFrame,
//				  textSize.y+2*spaceBetweenTextAndFrame);
//		gc = new GC(getBackground().getDevice()); //(backgroundImage);
//		gc.setForeground(new Color(getBackground().getDevice(),255,0,0));
//		gc.setLineWidth (1);
//		gc.drawRectangle (r);
//		//getParent().redraw();
//	}
//	
//	public void tryDisposeFrame() {
//		if (gc!=null) gc.dispose();
//		r = null;
//		gc = null;
//	}
	//================= was ausprobieren-ende =======================

}
