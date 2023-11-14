package com.greenwood672.dev.PDFGenerator;

import java.util.ArrayList;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;

public class StyleGenerator {
	
	
	// Variables to remember the styles which need
	// to be applied to the next Text object
	private int indent;
	
	private boolean bold;
	private boolean italic;
	
	private boolean large;
	
	private boolean fill;
	
	// The texts that are awaiting to form a paragraph	
	private ArrayList<Text> currentTexts;
	
	// Initialises the instances variables
	StyleGenerator() {
		indent = 0;
		bold = false;
		italic = false;
		fill = false;
		currentTexts = new ArrayList<Text>();
	}
	
	// Changes the variables based off of the
	// command. Also calls the function to form the paragraph
	// based off certain changing attributes or simply
	// the paragraph command
	public void AddStyle(String command, Document doc) {
		switch (command) {
		case (".bold"):
			bold = true;
			break;
		case (".italics"):
			italic = true;
			break;
		case (".large"):
			large = true;
			break;
		case (".regular"):
			bold = false;
			italic = false;
			large = false;
			break;
		case (".normal"):
			bold = false;
			italic = false;
			large = false;
			break;
		case (".fill"):
			fill = true;
			break;
		case (".nofill"):
			ExecuteStyles(doc);
			fill = false;
			break;
		case (".paragraph"):
			ExecuteStyles(doc);
			break;
		default:
			if (command.startsWith(".indent")) {
				ExecuteStyles(doc);
				SaveIndent(command);
				break;
			} else {
				SaveText(command);
			}
		}
		}
		
		// Joins the texts together from currentTexts
		// And adds extra attributes like text justification
		// and indentation
		private void ExecuteStyles(Document doc) {
						
			// Does nothing if there is nothing in currentTexts
			if (currentTexts.size() == 0) { return; }
			
			Paragraph paragraph = new Paragraph();
			
			// Loops through the currentTexts
			// If on starts with punctuation, no space is prepended
			// to the text
			paragraph.add(currentTexts.get(0));
			for (int i=1; i<currentTexts.size(); i++) {
				Text txt = currentTexts.get(i);
				if (".,?!:;".indexOf(txt.getText().charAt(0)) == -1) {
					paragraph.add(new Text(" "));
				}
				paragraph.add(txt);
			}
			//Final variables are used to alter the paragraph
			if (fill) { paragraph.setTextAlignment(TextAlignment.JUSTIFIED); }
			paragraph.setMarginLeft(indent * 20);
			doc.add(paragraph);
			
			currentTexts = new ArrayList<Text>();
			
		}
		
		// Creates a Text object and attaches the current
		// styles enabled to the object
		public void SaveText(String txt) {
			Text text = new Text(txt);
			if (bold) { text.setBold(); }
			if (italic) { text.setItalic(); }
			if (large) { text.setFontSize(50); }
			currentTexts.add(text);
		}
		
		// Parses the indent command and edits the
		// current indent variable
		private void SaveIndent(String command) {
			String number_as_string = command.substring(8);
			indent += Integer.parseInt(number_as_string);
		}
}
