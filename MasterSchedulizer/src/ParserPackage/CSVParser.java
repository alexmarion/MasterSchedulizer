package ParserPackage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class CSVParser {
	private String csvFilepath;
	private String csvSplitBy;
	private String line;
	private BufferedReader reader;
	
	public CSVParser(String fp) {
		this.csvFilepath = fp;
		this.csvSplitBy = ",";
		this.line = "";
		
		// Attempt to open the specified file
		Scanner inputReader = new Scanner(System.in);
		boolean foundFile = false;
		while(!foundFile) {
			// Create new reader for file
			try {
				reader = new BufferedReader(new FileReader(csvFilepath));
				foundFile = true;
			} catch (FileNotFoundException e) {
				// Notify the user and try again
				System.out.print("File could not be found! Input path or 'quit' to exit: ");
				csvFilepath = inputReader.nextLine();
				if(csvFilepath.equals("quit")) {
					System.exit(0);
				}
			}	
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public String getLine() {
		try {
			if((line = reader.readLine()) != null) {
				return line;
			} else {
				return null;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// TODO This shouldn't crash the program, handle the error correctly
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @param fieldNum
	 * @return
	 */
	public String getField(int fieldNum) {
		// TODO check for invalid input
		if(fieldNum < line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1).length) {
			return line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1)[fieldNum];
		} else {
			return null;
		}
	}
	
}
