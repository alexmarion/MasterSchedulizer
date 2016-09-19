package ParserPackage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import POSPackage.Course;
import POSPackage.POS;

public class CSVWriter {
	// Delimiters used
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	
	
	/*
	 * File Format:
	 * subjCode,courseNum,courseTitle,credits,preReqs,coReqs,termsOffered,termTaken
	 */
	public static void writeCSVFile(String filepath, POS planOfStudy) {
		ArrayList<Course> courseList = planOfStudy.getAllCourses();
		if(courseList.size() == 0) {
			System.out.println("Nothing to save!");
			return;
		}
		
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(filepath);
			
			// Write to the file for each course in the list
			boolean firstLine = true;
			for(Course course : courseList) {
				// Append a new line on every line except the first
				if(!firstLine) {
					fileWriter.append(NEW_LINE_SEPARATOR);
				}
				fileWriter.append(course.getSubjectCode());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(course.getCourseNum());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(course.getCourseTitle());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(course.getCredits().toString());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(course.getPreReqStr());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(course.getCoReqStr());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(course.getTermsOffered());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(course.getTermTaken());
				firstLine = false;
			}
		} catch (Exception e) {
			System.out.println("Error saving file!");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing filewriter!");
				e.printStackTrace();
			}
			
		}
	}
}
