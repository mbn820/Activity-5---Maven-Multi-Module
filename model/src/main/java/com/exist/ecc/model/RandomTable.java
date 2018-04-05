package com.exist.ecc.model;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class RandomTable {
	protected List<List<Cell>> randomTable;
	protected Map<String, String> cellRecords = new HashMap<String, String>();

	public static final int MAX_SIZE = 800;
	public static final int MIN_SIZE = 1;


	//*****************Properties****************//
	public boolean isEmpty() {
		return (randomTable.isEmpty() || randomTable == null); // change!
	}

	public boolean containsKey(String key) {
		return (cellRecords.containsKey(key));
	}

	public int maxRowIndex() {
		return getNumberOfRows() - 1;
	}

	public int maxColIndex() {
		return getNumberOfColumns() - 1;
	}

	//*****************Getters****************//
	public Cell getCell(int rowIndex, int colIndex) {
		return randomTable.get(rowIndex).get(colIndex);
	}

	public int getNumberOfRows() {
		return randomTable.size();
	}

	public int getNumberOfColumns() {
		return randomTable.get(0).size();
	}

	public int getStringLength() {
		return getCell(0, 0).getKeyLength();
	}

	public String getKey(int rowIndex, int colIndex) {
		return getCell(rowIndex, colIndex).getKey();
	}

	public String getValue(int rowIndex, int colIndex) {
		return getCell(rowIndex, colIndex).getValue();
	}

	public Set<String> getKeySet() {
		return cellRecords.keySet();
	}

	//**************************************************************************************//
	public String toString() {
		if(randomTable.isEmpty()) {
			return "";
	 	}

		StringBuilder table = new StringBuilder();
		for(List<Cell> list : randomTable) {
			for(Cell cell : list) {
				table.append("|" + cell + "|");
			}
			table.append("\n");
		}

		return table.toString();
	}

}//endClass
