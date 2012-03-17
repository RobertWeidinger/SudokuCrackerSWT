package de.rw.sudoku.zUnused;

import de.rw.sudoku.model.SudokuModel;

public class SudokuScannerForBlock<E> extends SudokuScanner<E> {
	private int block1stRow;
	private int block1stCol;

	public SudokuScannerForBlock(SudokuModel _sm, int anyRowInBlock, int anyColInBlock, ScannerAction<E> _sa) {
		super(_sm, _sa);
		// TODO Auto-generated constructor stub
		block1stRow = (anyRowInBlock / getSudokuModel().getBlockSize())*getSudokuModel().getBlockSize();
		block1stCol = (anyColInBlock / getSudokuModel().getBlockSize())*getSudokuModel().getBlockSize();
	}

	@Override
	public void scan() {
		SudokuModel sm = getSudokuModel();
		ScannerAction<E> sa = getScannerAction();
		for (int i=block1stRow; i<block1stRow+sm.getBlockSize(); i++)
			for (int j=block1stCol; j<block1stCol+sm.getBlockSize(); j++)
				sa.act(i,j,sa.getParam());
	}

	public int getBlock1stRow() {
		return block1stRow;
	}

	public int getBlock1stCol() {
		return block1stCol;
	}

}
