package TestingPackage;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ParserPackage.CSVParser;

public class CSVParserTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	// get the current line from a specified csv
	public void testGetLine() {
		CSVParser parser = new CSVParser("unitTestParserPackage.csv");
		String expected = "CI,101,Computing and Informatics Design I,2,,,1000,201515";
				
		String actual = parser.getLine();
		
		assertEquals(expected, actual);
	}
	
	@Test
	// get field from a specified index of current line from a specified csv
	public void testGetField() {
		CSVParser parser = new CSVParser("unitTestParserPackage.csv");
		String expected = "Computing and Informatics Design I";
				
		parser.getLine();
		String actual = parser.getField(2);
		assertEquals(expected, actual);
	}

}
