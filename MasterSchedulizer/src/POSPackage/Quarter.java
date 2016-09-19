package POSPackage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
//getters and setter are done
//add course method, remove course and display method-printout quarter and index, have it print new-lines too

public class Quarter implements Comparable<Quarter> {
	private ArrayList<Course> courseList;
	private String quarterID;
	private Double credits;
	private String term;
	
	public Quarter(String qID) {
		this.quarterID = qID;
		
		courseList = new ArrayList<Course>();
		credits = (double) 0;
		
		// Get the name of the term from the Quarter ID
		term = assignTerm();
	}
	
	public Quarter(String qID, ArrayList<Course> cl) {
		this.quarterID = qID;
		this.courseList = cl;
		credits = (double) 0;
		for(Course course : courseList) {
			credits += course.getCredits();
		}
		
		// Initialize credits to number of credits in course list
		for(int i = 0; i < courseList.size(); i++) {
			credits = courseList.get(i).getCredits();
		}
		term = this.assignTerm();
	}
	
	// Deep copy constructor
	public Quarter(Quarter q) {
		this.quarterID = q.quarterID;
		this.courseList = new ArrayList<Course>();
		for(Course course : q.getCourseList()) {
			this.courseList.add(new Course(course));
		}
		credits = (double) 0;
		for(Course course : courseList) {
			credits += course.getCredits();
		}
		term = assignTerm();
	}

	@Override
	public String toString() {
		return String.format(quarterID);
	}
	
	String assignTerm() {
		// Get the name of the term from the Quarter ID
		String term = "Couldn't assign term name from Quarter ID!";
		switch(quarterID.charAt(4)) {
			case '1':
				term = "Fall";
				break;
			case '2':
				term = "Winter";
				break;
			case '3':
				term = "Spring";
				break;
			case '4':
				term = "Summer";
				break;
		}
		return term;
	}
	
	
	/**
	 * @return the courseList
	 */
	public List<Course> getCourseList() {
		return courseList;
	}

	/**
	 * @param courseList the courseList to set
	 */
	public void setCourseList(ArrayList<Course> courseList) {
		this.courseList = courseList;
		//courselist.add("CI 101 Computing and Informatics Design I");
		//courselist.add("CS 164 Introduction to Computer Science");
	}

	/**
	 * @return the quarterID
	 */
	public String getQuarterID() {
		return quarterID;
	}

	/**
	 * @param quarterID the quarterID to set
	 */
	public void setQuarterID(String quarterID) {
		this.quarterID = quarterID;
		term = assignTerm();
	}
	
	public String getTerm() {
		return term;
	}

	/**
	 * @return the credits
	 */
	public Double getCredits() {
		return credits;
	}

	/**
	 * @param credits the credits to set
	 */
	public void setCredits(Double credits) {
		this.credits = credits;
	}


	/**
	 * Check to see if two Quarters are equal
	 * @return 1 if true, 0 if false
	 */
	public int compareTo(Quarter q) {
		// If course lists are of different size, not equal
		if(this.courseList.size() != q.courseList.size()) {
			return 0;
		}
		
		for(int i = 0; i < this.courseList.size(); i++) {
			// If two courses are not equal, return 0 (false)
			// Courses are stored in alphanumeric order, if two quarters are equal they will have the same order
			if(this.courseList.get(i).compareTo(q.courseList.get(i)) == 0) {
				return 0;
			}
		}
		return 1;
	}
	
	/**
	 * Check if course list contains course
	 * @param c
	 * @return true or false
	 */
	public boolean containsCourse(Course c) {
		if(courseList.contains(c)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Add a Course to the Quarter
	 * @param c
	 * @return true if successful
	 */
	public boolean addCourse(Course c) {
		if(courseList.contains(c)) {
			return false;
		} else {
			c.setTermTaken(this.quarterID);
			courseList.add(c);
			Collections.sort(courseList);
			return true;
		}
	}
	/**
	 * Remove a Course from the Quarter
	 * @param c
	 * @return true if successful 
	 */
	public boolean removeCourse(Course c) {
		if(courseList.contains(c)) {
			courseList.remove(c);
			return true;
		} else {
			for(Course classes : courseList){
				System.out.println(c.getCourseName() + " is not in this quarter!");
				System.out.println(classes);
			}
			return false;
		}
	}
	
	/**
	 * Display method for Quarter
	 */
//	public void display() {
//		System.out.println("Quarter: " + this.quarterID);
//		for (Course classes : courseList){
//			classes.display();
//		}
//		System.out.println();
//	}

	//------------------------------------------display row of courses---------------------------------------------
	//display row of courses, only the 1st course of all the quarters
	public void display() {
		String leftAlignFormat = "| %-15s ";
		if(courseList.size() == 0){
			System.out.format(leftAlignFormat, " ");
		}else {
			courseList.get(0).display();
		}
	}
	//display row of courses, only the 2nd course of all the quarters
	public void display1() {
		//year2, year3, year4 spring & summaer quarter only have 1 course: co-op < 2 will print column space
		String leftAlignFormat = "| %-15s ";
		//if a quarter only have less than or equal to 1 courses print column space
		if (courseList.size() <= 1) {
			System.out.format(leftAlignFormat, " ");
			//else quarter have more than 5 courses check if
		} else {
			//first year fall quarter only have 1 course : SUMMER BREAK, size < 2 so it will print column space
			if(courseList.size() < 2){
				System.out.format(leftAlignFormat, "");
			}else {
				//print the 3th course of that quarter
				courseList.get(1).display();
			}
		}
	}
	//display row of courses, only the 3rd course of all the quarters
	public void display2(){
		String leftAlignFormat = "| %-15s ";
		//if a quarter only have less than or equal to 2 courses print column space
		if (courseList.size() <= 2) {
			System.out.format(leftAlignFormat, " ");
			//else quarter have more than 5 courses check if
		} else {
			//first year fall quarter only have 1 course : SUMMER BREAK, size < 2 so it will print column space
			if(courseList.size() < 2){
				System.out.format(leftAlignFormat, "");
			}else {
				//print the 3th course of that quarter
				courseList.get(2).display();
			}
		}
	}
	//display row of courses, only the 4th course of all the quarters
	public void display3() {
		String leftAlignFormat = "| %-15s ";
		//if a quarter only have less than or equal to 3 courses print column space
		if (courseList.size() <= 3) {
			System.out.format(leftAlignFormat, " ");
			//else quarter have more than 5 courses check if
		} else {
			//first year fall quarter only have 1 course : SUMMER BREAK, size < 2 so it will print column space
			if(courseList.size() < 2){
				System.out.format(leftAlignFormat, "");
			}else {
				//print the 3th course of that quarter
				courseList.get(3).display();
			}
		}
	}
	//display row of courses, only the 5th course of all the quarters
	public void display4() {
		String leftAlignFormat = "| %-15s ";
		//if a quarter only have less than or equal to 4 courses print column space
		if (courseList.size() <= 4) {
			System.out.format(leftAlignFormat, " ");
			//else quarter have more than 5 courses check if
		} else {
			//first year fall quarter only have 1 course : SUMMER BREAK, size < 2 so it will print column space
			if(courseList.size() < 2){
				System.out.format(leftAlignFormat, "");
			}else {
				//print the 3th course of that quarter
				courseList.get(4).display();
			}
		}
	}
	//display row of courses, only the 6th course of all the quarters
	public void display5() {
		String leftAlignFormat = "| %-15s ";
		//if a quarter only have less than or equal to 5 courses print column space
		if (courseList.size() <= 5) {
			System.out.format(leftAlignFormat, " ");
		//else quarter have more than 5 courses check if
		} else {
			//first year fall quarter only have 1 course : SUMMER BREAK, size < 2 so it will print column space
			if(courseList.size() < 2){
				System.out.format(leftAlignFormat, "");
			}else {
				//print the 6th course of that quarter
				courseList.get(5).display();
			}
		}
	}
	//display row of courses, only the 7th course of all the quarters
	public void display6() {
		String leftAlignFormat = "| %-15s ";
		//if a quarter only have less than or equal to 6 courses print column space
		if (courseList.size() <= 6) {
			System.out.format(leftAlignFormat, " ");
			//else quarter have more than 5 courses check if
		} else {
			//first year fall quarter only have 1 course : SUMMER BREAK, size < 2 so it will print column space
			if(courseList.size() < 2){
				System.out.format(leftAlignFormat, "");
			}else {
				//print the 6th course of that quarter
				courseList.get(6).display();
			}
		}
	}
	//display row of courses, only the 8th course of all the quarters
	public void display7() {
		String leftAlignFormat = "| %-15s ";
		//if a quarter only have less than or equal to 7 courses print column space
		if (courseList.size() <= 7) {
			System.out.format(leftAlignFormat, " ");
			//else quarter have more than 5 courses check if
		} else {
			//first year fall quarter only have 1 course : SUMMER BREAK, size < 2 so it will print column space
			if(courseList.size() < 2){
				System.out.format(leftAlignFormat, "");
			}else {
				//print the 7th course of that quarter
				courseList.get(7).display();
			}
		}
	}
}
