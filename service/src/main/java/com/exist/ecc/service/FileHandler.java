package com.exist.ecc.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

public class FileHandler {
	protected File file;

	public FileHandler(String fileName) throws FileNotFoundException {
		try {
			file = new File(fileName);
		} catch(Exception e) {
			System.out.println("Error");
		}

		if ( !file.exists() ) { throw new FileNotFoundException(); }
	}

	public void printToConsole() {
		try {
			System.out.println( FileUtils.readFileToString(file, "UTF-8") );
		} catch (Exception e) {
			exitIfError();
		}
	}

	public void saveToFile(String str) {
		try {
			FileUtils.write(file, str, "UTF-8");
		} catch (Exception e) {
			exitIfError();
		}
	}

	public List<String> saveLinesToList() {
		List<String> lines = new ArrayList<String>();
		try {
			lines = FileUtils.readLines(file, "UTF-8");
		} catch (Exception e) {
			exitIfError();
		}

		return lines;
	}

	public void exitIfError() {
		System.out.println("Error Occured");
		System.exit(0);
	}


}
