package com.exist.ecc.service;

import com.exist.ecc.model.Cell;

public class CellOperations {
	public static int countOccurencesInKey(Cell cell, String text) {
		int countInKey = StringCounter.countOccurencesOf(text, cell.getKey());
		return countInKey;
	}

	public static int countOccurencesInValue(Cell cell, String text) {
		int countInValue = StringCounter.countOccurencesOf(text, cell.getValue());
		return countInValue;
	}
}
