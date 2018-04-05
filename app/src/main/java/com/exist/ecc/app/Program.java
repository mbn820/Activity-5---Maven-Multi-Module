package com.exist.ecc.app;

import java.io.File;
import java.io.FileNotFoundException;

import com.exist.ecc.service.TableOperations;
import com.exist.ecc.service.TableFileHandler;
import com.exist.ecc.util.InvalidTableFormatException;
import com.exist.ecc.util.DuplicateKeyException;


public class Program {
	private static TableOperations tableOperations;
	private static ConsoleInputHandler input = new ConsoleInputHandler();
	private static File file;
	private static TableFileHandler tableFileHandler;

	private static final String DEFAULT_FILE = "/home/mnunez/Desktop/activity-2-multi-module-assembly/util/src/default_file/default-file-randomtable.txt";

	public static void main(String[] args) {
		Program program = new Program();

		try {
			program.loadFile(args[0]);
		} catch (IndexOutOfBoundsException e) {
			program.createNewOrLoadDefault();
		}

		program.menu();
	}

	private void createNewOrLoadDefault() {
		String choice = input.getDesiredString("Create [n]ew empty file or load [d]efault? " + "(" + DEFAULT_FILE + ")", "n", "d");

		switch(choice) {
			case "n" : createNewEmptyFile(); break;
			case "d" : loadFile(DEFAULT_FILE); break;
		}
	}

	private void createNewEmptyFile() {
		String fileName = input.getAnyString("Enter file name: ");
		File emptyFile = null;
		try {
			emptyFile = new File(fileName);
		} catch (Exception e) {

		}

		try {
			emptyFile.createNewFile();
		} catch (Exception e) {
			System.out.println("Invalid File");
			createNewOrLoadDefault();
		}

		loadFile(fileName);
	}

	private void loadFile(String fileName) {
		try {
			tableFileHandler = new TableFileHandler(fileName);
		} catch (InvalidTableFormatException e) {
			System.out.println("Invalid Formatting");
			System.exit(0);
		} catch (DuplicateKeyException e) {
			System.out.println("The file contains duplicate keys");
			System.exit(0);
		} catch (FileNotFoundException e) {
			System.out.println("The file does not exist");
			System.exit(0);
		}

		tableOperations = new TableOperations(tableFileHandler);

		menu();
	}

	//*****************************************************************************************************************************************//

	private void menu() {
		if(tableOperations.isEmpty()) {
			showCreateAndExitOnly();
		} else {
			showAllOptions();
		}
	}

	private void showAllOptions() {
		System.out.println("\n\n");
		System.out.println("---OPTIONS---");
		System.out.println("[C]Create New Table");
		System.out.println("[D]Display Table");
		System.out.println("[E]Edit Table");
		System.out.println("[R]Reset");
		System.out.println("[S]Search");
		System.out.println("[T]Sort Table");
		System.out.println("[Ar]Add New Row");
		System.out.println("[Ac]Add New Column");
		System.out.println("[X]Exit");
		System.out.println();

		String[] desiredInputs = {"c", "d", "e", "r", "s", "x", "t", "ac", "ar"};
		//get user input using getDesiredString method from ConsoleInputHandler class
		String choice = input.getDesiredString("ENTER CHOICE: ", desiredInputs);
		//process the input
		switch(choice.toLowerCase()) {
			case "c" : createTable();
			case "d" : displayTable();
			case "e" : editTable();
			case "r" : resetTable();
			case "s" : searchTable();
			case "t" : sortTable();
			case "ar": addNewRow();
			case "ac": addNewColumn();
			case "x" : exit();
			default  : menu();
		}
	}

	private void showCreateAndExitOnly() {
		System.out.println("\n\n");
		System.out.println("---OPTIONS---");
		System.out.println("[C]Create Table");
		System.out.println("[X]Exit");
		System.out.println();

		String[] desiredInputs = {"c", "x"};
		//get user input using getDesiredString method from ConsoleInputHandler class
		String choice = input.getDesiredString("ENTER CHOICE: ", desiredInputs);
		//process the input
		switch(choice.toLowerCase()) {
			case "c" : createTable();
			case "x" : exit();
			default  : menu();
		}
	}

	private void createTable() {
		//error messages in case the user enters wrong input
		String errorMsg = "Minimum Size: " + tableOperations.MIN_SIZE + " Maximum Size: " + tableOperations.MAX_SIZE;

		System.out.println("-------------CREATE TABLE-------------");
		//get user input
		int row = input.getIntegerBetween("Enter number of rows: ", tableOperations.MIN_SIZE , tableOperations.MAX_SIZE, errorMsg);
		int col = input.getIntegerBetween("Enter number of columns: ", tableOperations.MIN_SIZE , tableOperations.MAX_SIZE, errorMsg);

		//create the table
		tableOperations.create(row, col, 3);

		System.out.println("\nTable Created!\n");

		tableOperations.display();

		menu();
	}

	private void displayTable() {
		System.out.println("-------------DISPLAY TABLE-------------");
		tableOperations.display();
		menu();
	}

	private void editTable() {
		System.out.println("-------------EDIT TABLE-------------");

		String choice = input.getDesiredString("Edit [K]ey, [V]alue, [B]oth? : ", "k", "v", "b");

		String errorMsg = "Invalid Index, Current Table size: " + tableOperations.getNumberOfRows() + " X " + tableOperations.getNumberOfColumns();

		int rowIndex = input.getIntegerBetween("Enter row index: ", 0, tableOperations.maxRowIndex(), errorMsg);
		int colIndex = input.getIntegerBetween("Enter column index: ", 0, tableOperations.maxColIndex(), errorMsg);

		switch(choice) {
			case "k" : editKeyOnly(rowIndex, colIndex);
				       break;
			case "v" : editValueOnly(rowIndex, colIndex);
				       break;
		    case "b" : editBoth(rowIndex, colIndex);
					   break;
		}
		tableOperations.display();
		menu();
	}

	private void editBoth(int rowIndex, int colIndex) {
	    editKeyOnly(rowIndex, colIndex);
		editValueOnly(rowIndex, colIndex);
	}

	private void editKeyOnly(int rowIndex, int colIndex) {
		String oldKey = tableOperations.getCell(rowIndex, colIndex).getKey();

		String keyReplacement = input.getAnyStringExcept("Enter replacement for key : ", tableOperations.getKeySet(), "Key already exists");
		tableOperations.editKeyAt(rowIndex, colIndex, keyReplacement.replace("|(", "_"));
		System.out.printf("\nThe key at (%d, %d) has been changed from \"%s\" to \"%s\"\n\n", rowIndex , colIndex, oldKey, keyReplacement);
	}

	private void editValueOnly(int rowIndex, int colIndex) {
		String oldValue = tableOperations.getCell(rowIndex, colIndex).getValue();

		String valueReplacement = input.getAnyString("Enter replacement for value : ");
		tableOperations.editValueAt(rowIndex, colIndex,valueReplacement);
		System.out.printf("\nThe value at (%d, %d) has been changed from \"%s\" to \"%s\"\n\n", rowIndex, colIndex, oldValue, valueReplacement);
	}


	private void searchTable() {
		String query;
		System.out.println("-------------SEARCH TABLE-------------");

		query = input.getAnyString("Search for: ");

		System.out.println("Results: ");
		tableOperations.search(query);
		menu();
	}

	private void sortTable() {
		System.out.println("-------------SORT TABLE-------------");

		tableOperations.sort();

		System.out.println("After: ");
		tableOperations.display();

		menu();
	}

	private void addNewRow() {
		System.out.println("-------------ADD NEW ROW-------------");

		tableOperations.addRow();

		System.out.println("After: ");
		tableOperations.display();

		menu();
	}

	private void addNewColumn() {
		System.out.println("-------------ADD NEW COLUMN-------------");

		tableOperations.addColumn();

		System.out.println("After: ");
		tableOperations.display();

		menu();
	}

	private void resetTable() {
		System.out.println("-------------RESET TABLE-------------");

		String decision = input.getDesiredString("Are you sure? [y/n]: ", "y", "n");

		switch(decision.toLowerCase()) {
			case "y" : tableOperations.reset();
					   break;
			case "n" : menu();
			default  : menu();
		}

		System.out.println("\nTable has been reset\n");
		menu();
	}

	private void exit() {
		System.out.println("-------------EXIT-------------");
		String choice = input.getDesiredString("Exit the program? [y/n]: ", "y", "n");

		switch(choice.toLowerCase()) {
			case "y" : System.exit(0);
			case "n" : break;
			default  : menu();
		}

		menu();
	}

}//endClass
