package com.exist.ecc.service;

public class TableIndex {
	private int rowIndex, colIndex;

	public TableIndex(int r, int c) {
		rowIndex = r;
		colIndex = c;
	}

	public int getRow() {
		return rowIndex;
	}

	public int getCol() {
		return colIndex;
	}

	public String toString() {
		return String.format("(%d, %d)", rowIndex, colIndex);
	}
}
