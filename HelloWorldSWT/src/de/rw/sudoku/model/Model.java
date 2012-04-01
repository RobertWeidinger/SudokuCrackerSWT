package de.rw.sudoku.model;
import de.rw.sudoku.views.View;


public interface Model {
	public void registerView(View v);
	public void notifyAlgorithmStart();
	public void notifyAlgorithmStop();
}
