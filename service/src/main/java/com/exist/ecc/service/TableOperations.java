package com.exist.ecc.service;

import com.exist.ecc.model.Cell;
import com.exist.ecc.model.RandomTable;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


public class TableOperations extends RandomTable{
	private TableFileHandler handler;
	private CellGenerator cellGenerator;

	public TableOperations(TableFileHandler handler) {
		this.handler = handler;
		super.randomTable = handler.getTable();
		super.cellRecords = handler.getCellRecords();
		cellGenerator = new CellGenerator(super.cellRecords);
		updateFile();
	}

	public void create(int numberOfRows, int numberOfColumns, int stringLength) {
		reset();
		super.randomTable = cellGenerator.getTable(numberOfRows, numberOfColumns, stringLength, stringLength);
		updateFile();
	}

	public void display() {
		handler.printToConsole();
	}

	public void reset() {
		super.randomTable = new ArrayList<List<Cell>>();
		super.cellRecords = new HashMap<String, String>();
		updateFile();
	}

	private void editCell(int rowIndex, int colIndex, String newKey, String newValue) {
		Cell targetCell = super.getCell(rowIndex, colIndex);
		String oldKey = targetCell.getKey();

		targetCell.setKey(newKey);
		targetCell.setValue(newValue);
		super.cellRecords.remove(oldKey);
		super.cellRecords.put(newKey, newValue);

		updateFile();
	}

	public void editKeyAt(int rowIndex, int colIndex, String newKey) {
		String origValue = super.getCell(rowIndex, colIndex).getValue();
		editCell(rowIndex, colIndex, newKey, origValue);
	}

	public void editValueAt(int rowIndex, int colIndex, String newValue) {
		String origKey = super.getCell(rowIndex, colIndex).getKey();
		editCell(rowIndex, colIndex, origKey, newValue);
	}

	public void addRow() {
		int numberOfCellsPerRow = getNumberOfColumns();
		super.randomTable.add(cellGenerator.getRow(numberOfCellsPerRow, 3, 3));

		updateFile();
	}

	public void addColumn() {
		for(List<Cell> row : super.randomTable) {
			row.add(cellGenerator.getSingleCell(3, 3));
		}

		updateFile();
	}

	public void sort() {
		//convert 2d to 1d array
		List<Cell> oneDimensional = new ArrayList<Cell>();
		for(List<Cell> row : super.randomTable) {
			for(Cell c : row) {
				oneDimensional.add(c);
			}
		}
		//sort
		Collections.sort(oneDimensional);
		//convert back to 2d
		int index = 0;
		for(int row = 0; row < getNumberOfRows(); row++) {
			for(int col = 0; col < getNumberOfColumns(); col++) {
				super.randomTable.get(row).set(col, oneDimensional.get(index));
				index++;
			}
		}

		updateFile();
	}

	public void search(String text) {
		List<TableIndex> results = findIndicesContaining(text);

		if(results.isEmpty()) {
			System.out.println("No occurences found");
			return;
		}

		Cell currentCell;
		int countInKey, countInValue;
		int totalCountInKey = 0;
		int totalCountInValue = 0;

		System.out.printf("%-15s%-20s%-15s%-15s%s\n", "INDICES", "CELL", "KEY", "VALUE", "TOTAL");
		for(TableIndex index : results) {
			currentCell = super.getCell(index.getRow(), index.getCol());
			countInKey = CellOperations.countOccurencesInKey(currentCell, text);
			countInValue = CellOperations.countOccurencesInValue(currentCell, text);
			System.out.printf("%-15s%-20s%-15d%-15d%-15d\n", index, currentCell, countInKey, countInValue, countInKey + countInValue);
			totalCountInKey += countInKey;
			totalCountInValue += countInValue;
		}

		System.out.printf("%-35s%-15d%-15d[%d]\n", "[TOTAL]", totalCountInKey, totalCountInValue, totalCountInKey + totalCountInValue);
	}

	private List<TableIndex> findIndicesContaining(String text) {
		List<TableIndex> indices = new ArrayList<TableIndex>();
		Cell currentCell;

		for(int row = 0; row < getNumberOfRows(); row++) {
			for(int col = 0; col < getNumberOfColumns(); col++) {
				currentCell = super.getCell(row, col);
				if(currentCell.contains(text)) {
					indices.add(new TableIndex(row, col));
				}
			}
		}

		return indices;
	}

	public void updateFile() {
		handler.saveToFile(super.toString());
	}
}
