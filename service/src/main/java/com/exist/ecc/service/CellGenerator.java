package com.exist.ecc.service;

import com.exist.ecc.model.Cell;
import org.apache.commons.lang3.RandomStringUtils;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class CellGenerator {
	private Map<String, String> cellRecords;

	public CellGenerator(Map<String, String> cellRecords) {
		this.cellRecords = cellRecords;
	}

	public Cell getSingleCell(int keyLength, int valueLength) {
		String randomKey, randomValue;

		do {
			randomKey = RandomStringUtils.randomAscii(keyLength);
		} while(cellRecords.containsKey(randomKey));

		randomValue = RandomStringUtils.randomAscii(valueLength);

		cellRecords.put(randomKey, randomValue);
		return new Cell(randomKey, randomValue);
	}

	public List<Cell> getRow(int numberOfElements, int keyLength, int valueLength) {
		List<Cell> cells = new ArrayList<Cell>();

		for(int size = 0; size < numberOfElements; size++) {
			cells.add(getSingleCell(keyLength, valueLength));
		}

		return cells;
	}

	public List<List<Cell>> getTable(int numberOfRows, int numberOfColumns, int keyLength, int valueLength) {
		List<List<Cell>> cellTable = new ArrayList<List<Cell>>();

		for(int row = 0; row < numberOfRows; row++) {
			cellTable.add(getRow(numberOfColumns, keyLength, valueLength));
		}

		return cellTable;
	}
}
