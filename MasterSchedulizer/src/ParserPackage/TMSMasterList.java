package ParserPackage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import POSPackage.Course;
public class TMSMasterList {
	public static HashMap<String, ArrayList<Course>> TMSMASTER;
	private static Path tmsFilepath;
	private static ArrayList<String> subjectFilepaths;
	private static ArrayList<String> subjectNames;
	private static ArrayList<CourseLoader> courseLoaders;
	static {
		tmsFilepath = Paths.get("TMS/MASTERLIST");
		TMSMASTER = new HashMap<String, ArrayList<Course>>();
		File tmsFolder = new File(tmsFilepath.toString());
		subjectFilepaths = new ArrayList<String>();
		subjectNames = new ArrayList<String>();
		courseLoaders = new ArrayList<CourseLoader>();
		// Get all subjects from the masterlist
		
		// Get all of the subject names from the parent directory
		for(File file : tmsFolder.listFiles()) {
			if(file.isFile()) {
				subjectFilepaths.add(file.getAbsolutePath());
				subjectNames.add(file.getName().replace(".csv", ""));
			}
		}
		
		// Create a CourseLoader for each subject file
		for(String subject : subjectFilepaths) {
			courseLoaders.add(new CourseLoader(subject));
		}	
		
		// Load the courses from each file and put them in the hashmap
		for(int i = 0; i < courseLoaders.size(); i++) {
			TMSMASTER.put(subjectNames.get(i), courseLoaders.get(i).getCourses());
		}
		
		// Assign the preReqs to each class in the masterlist
		CourseLoader preReqLoader = new CourseLoader();
		for(String key : TMSMASTER.keySet()) {
			for(int i = 0; i < TMSMASTER.get(key).size(); i++) {
				TMSMASTER.put(key, preReqLoader.assignPreReqs(TMSMASTER.get(key)));
			}
		}
		
		// Running the loop a second time ensures that the pre-requisites contained
		// In a course also have THEIR pre-requisites filled out as well
		for(String key : TMSMASTER.keySet()) {
			for(int i = 0; i < TMSMASTER.get(key).size(); i++) {
				TMSMASTER.put(key, preReqLoader.assignPreReqs(TMSMASTER.get(key)));
			}
		}
		
		// Assign coReqs
		for(String key : TMSMASTER.keySet()) {
			for(int i = 0; i < TMSMASTER.get(key).size(); i++) {
				TMSMASTER.put(key, preReqLoader.assignCoReqs(TMSMASTER.get(key)));
			}
		}
		
		System.out.println("DONE COMPILING MASTER LIST");
	}
	
	
	public TMSMasterList() {
	}
	
	
	public static HashMap<String, ArrayList<Course>> getTMS() {
		return TMSMASTER;
	}
	
	public static Course findCourse(String subjCode, String courseNum) {
		subjCode = subjCode.toUpperCase();
		ArrayList<Course> courseList = TMSMASTER.get(subjCode);
		if(!TMSMASTER.containsKey(subjCode)) {
			System.out.println("MASTERLIST doesn't contain " + subjCode + courseNum);
			return null;
		}
		for(Course course : courseList) {
			if(course.getSubjectCode().equals(subjCode) && course.getCourseNum().equals(courseNum)) {
				return course;
			}
		}
		return null;
	}

}
