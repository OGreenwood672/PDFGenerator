package com.greenwood672.dev.PDFGenerator;

import java.io.FileNotFoundException;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

// Some problems with the code,

// Produces spaces, even if a comma is starting the next section of text
// SOLVED: Created an algorithm to check if a space is required before
// the next set of text

// User cannot begin text on a full stop
// SOLVED: Changed the default case (On the switch-case statement)
// to send the command variable to SaveText, it it isn't a command

// Empty lines in the input file crash the program (line.charAt(0))
// SOLVED: Just made sure current_input.length() > 0


public class Main {
	
	public final static String OUTPUT_PATH = ".\\Output.pdf";
	public final static String INPUT_PATH = ".\\input.txt";
	
	private static Document getBlankDoc() {
		
		// Creates a load of objects, I don't need to understand
		// in order to write on a pdf
		PdfWriter pdfwriter = null;
		try {
			pdfwriter = new PdfWriter(OUTPUT_PATH);
		} catch (FileNotFoundException e) {
			System.out.println("ERROR [File Not Found]: " + OUTPUT_PATH + ", No such location");
			e.printStackTrace();
		} 
		
		PdfDocument pdf = new PdfDocument(pdfwriter);
		pdf.addNewPage();
		Document doc = new Document(pdf);

		return doc;
		
	}
	
	public static void main(String[] args) {
		
		System.out.println("[PDF GENEREATOR]");
		
		// Create a blank PDF document to add text to
		System.out.println("[Generating Blank Doc...]");
		Document doc = getBlankDoc();
		
		// Add the content multiple times to check the pagination
		// Not sure what pagination is, assuming it means,
		// Checks that it creates multiple pages when content
		// spills across
		// In production, this wouldn't exist
		for (int i=0; i<8; i++) {
		
			// Fetch the input file, and put it in a file reader
			System.out.println("[Loading Inputs...]");
			InputHandler inp;
			try {
				inp = new InputHandler(INPUT_PATH);
			} catch (FileNotFoundException e) {
				System.out.println("ERROR [File Not Found]: INPUT FILE NOT FOUND");
				e.printStackTrace();
				return;
			}
			
			// Loop through the inputs, and either
			// execute commands or draw the text to the pdf
			System.out.println("[Loading Styles...]");
			String current_input;
			StyleGenerator styles = new StyleGenerator();
			
			// Loops through instructions until no
			// instructions are left
			while ((current_input = inp.NextLine()) != null) {
				// If it is a instruction
				// Fails to find command if user
				// wants to add text that starts with a '.'
				// SOLUTION: If no command found in switch-case, add as text
				if (current_input.length() == 0) { }
				else if (current_input.charAt(0) == '.') {
					// Save style for later text
					styles.AddStyle(current_input, doc);
				} else {
					// Save the text for future drawing
					styles.SaveText(current_input);
				}
			}
			// Draw any extra text still stored in the
			// attributes of StyleGenerator Class
			styles.AddStyle(".paragraph", doc);
		}
	
		// Doesn't technically save here, but
		// reassures user
		System.out.println("[Saving...]");
		
		// Close for good practice
		doc.close();
		System.out.println("[File Created Successfully]");
		
	}

}
