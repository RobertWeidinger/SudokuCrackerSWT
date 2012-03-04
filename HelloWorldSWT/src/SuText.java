import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Text;


public class SuText {

	private Text t;
	private int row;
	private int col;
	private ModifyListener ml;
	private SudokuView sv;
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	
	public SuText(SudokuView _sv, int style, int _row, int _col) {
		sv = _sv;
		row = _row;
		col = _col;
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
				sv.updateModel(row, col, i, false);
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

	public void setSize(Point size)
	{
		this.getText().setSize(size);
	}
	
	public void setLocation(int x, int y)
	{
		this.getText().setLocation(x, y);
	}
	
}
