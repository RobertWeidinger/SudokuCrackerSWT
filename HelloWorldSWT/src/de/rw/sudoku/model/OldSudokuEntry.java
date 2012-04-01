package de.rw.sudoku.model;

public class OldSudokuEntry  {

	private SudokuEntry se;
	private Integer algorithmID;
	
	public OldSudokuEntry(SudokuEntry _se, Integer _algID) {
		setSudokuEntry(_se);
		setAlgorithmID(_algID);
	}

	public Integer getAlgorithmID() {
		return algorithmID;
	}

	public SudokuEntry getSudokuEntry() {
		return se;
	}

	private void setAlgorithmID(Integer algorithmID) {
		this.algorithmID = algorithmID;
	}

	private void setSudokuEntry(SudokuEntry se) {
		this.se = se;
	}

}
