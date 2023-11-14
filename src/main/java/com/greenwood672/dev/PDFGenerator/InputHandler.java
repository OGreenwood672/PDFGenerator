package com.greenwood672.dev.PDFGenerator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class InputHandler {
	
	private String path;
	private BufferedReader reader;
	
	InputHandler(String _path) throws FileNotFoundException {
		this.path = _path;
		// Fetches file and creates a bufferedreader object
		// in order for the program to understand the file
		reader = new BufferedReader(new FileReader(this.path));
	}
	
	// Acts as a generator, 
	// sends a new line upon each request
	public String NextLine() {
		try {
			return reader.readLine();
		} catch (IOException e) {
			return null;
		}
	}
	
}
