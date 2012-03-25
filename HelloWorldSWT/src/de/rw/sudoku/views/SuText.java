package de.rw.sudoku.views;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Text;

import de.rw.sudoku.model.SudokuCoords;


public class SuText {

	private Text t;
	private SudokuCoords sc;
	private ModifyListener ml;
	private SudokuView sv;
	
	public int getRow() {
		return sc.getRow();
	}

	public int getCol() {
		return sc.getCol();
	}
	
	public SuText(SudokuView _sv, int style, SudokuCoords _sc) {
		sv = _sv;
		sc = _sc;
		t = new Text(sv.getParent(), style);
		// TODO Auto-generated constructor stub
		ml = new ModifyListener(){
			@Override
			public void modifyText(ModifyEvent arg0) {
				// TODO Auto-generated method stub
				int i;
				try {
					i = Integer.parseInt(t.getText());
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					i = -1;
				}
				sv.updateModel(sc, i, false);
			}};
		
		t.addModifyListener(ml);
		}

	private Text getText() {
		return t;
	}
	
	public String getString()
	{
		return this.getText().getText();
	}
	
	public void setTextIfNew(String s)
	{
		if (!this.getText().getText().equals(s)) this.getText().setText(s);
	}
	
	public void setPropertiesFixed()
	{
		this.getText().setBackground(sv.getColorFixed());
		this.getText().setEditable(false);
	}
	
	public void setPropertiesEditable()
	{
		this.getText().setBackground(sv.getColorEditable());
		this.getText().setEditable(true);
	}
	
	public void setPropertiesError()
	{
		this.getText().setBackground(sv.getColorError());
		this.getText().setEditable(true);
	}
	
	public void setPropertiesBlocked()
	{
		this.getText().setBackground(sv.getColorBlocked());
		this.getText().setEditable(false);
	}

	public void setSize(Point size)
	{
		this.getText().setSize(size);
	}
	
	public void setLocation(int x, int y)
	{
		this.getText().setLocation(x, y);
	}
	
}
