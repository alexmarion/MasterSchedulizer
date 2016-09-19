package POSPackage;
import java.util.ArrayList;

import IOPackage.IO;
import MajorPackage.Major;
import MinorPackage.Minor;

public class POS implements Comparable<POS> {
	private String studentName;
	private int studentID;
	private Major major;
	private Minor minor;
	private ArrayList<Year> years;
	private int hashVal;
	
	public POS(String sn, int sID, ArrayList<Course> courseList) {
		this.studentName = sn;
		this.studentID = sID;
		
		this.major = null;
		this.minor = null;
		this.years = new ArrayList<Year>();

		String[] yearList = findYearRange(courseList);
		int numYears = Integer.parseInt(yearList[1])+1 - (Integer.parseInt(yearList[0]));
		String yr = yearList[0];

		for(int i = 0; i < numYears; i++) {
			this.years.add(new Year(yr));
			yr = Integer.toString(Integer.parseInt(yr) + 1);
		}

		for(int i = 0; i < courseList.size(); i++) {
			this.addCourse(courseList.get(i));
		}
		this.hashVal = this.hash();
	}
	
	public POS(String sn, int sID, Major mj) {
		this.studentName = sn;
		this.studentID = sID;
		this.major = mj;
		
		this.minor = null;
		this.years = null;

		this.hashVal = this.hash();
	}
	
	public POS(String sn, int sID, Major mj, Minor mn) {
		this.studentName = sn;
		this.studentID = sID;
		this.major = mj;	
		this.minor = mn;
		
		this.years = null;
		
		this.hashVal = this.hash();
	}
	
	public POS(POS pos) {
		this.studentName = pos.getStudentName();
		this.studentID = pos.getStudentID();
		this.major = pos.getMajor();
		this.minor = pos.getMinor();
		this.hashVal = pos.hash();
		this.years = new ArrayList<Year>();
		for(Year year : pos.getYears()) {
			this.years.add(new Year(year));
		}
	}
	
	public int hash() {
		return 0;
	}
	
	public String getStudentName() {
		return studentName;
	}
	
	public int getStudentID() {
		return studentID;
	}
	
	public Major getMajor() {
		return this.major;
	}
	
	public Minor getMinor() {
		return this.minor;
	}
	
	private String[] findYearRange(ArrayList<Course> courseList) {
		// TODO: Replace this with a function that finds the first as the lowest and the last as the highest
		int highest = Integer.parseInt(courseList.get(0).getTermTaken().substring(0, 4));;
		int lowest = Integer.parseInt(courseList.get(0).getTermTaken().substring(0, 4));
		int curr;
		for(Course course : courseList) {
			curr = Integer.parseInt(course.getTermTaken().substring(0, 4));
			if(curr < lowest) {
				lowest = curr;
			}
			if(curr > highest) {
				highest = curr;
			}
		}
		String[] yearList = new String[2];
		yearList[0] = Integer.toString(lowest);
		yearList[1] = Integer.toString(highest);
		return yearList;
	}

	public ArrayList<Year> getYears() {
		return this.years;
	}
	
	public Year getYearByID(String academicYear) {
		for(Year year : this.years) {
			if(year.getAcademicYear().equals(academicYear)) {
				return year;
			}
		}
		return null;
	}
	
	public Quarter getQuarterByID(String quarterID) {
		for(Year year : this.years) {
			for(Quarter quarter : year.getQuarterList()) {
				if(quarter.getQuarterID().equals(quarterID)) {
					return quarter;
				}
			}
		}
		return null;
	}

	/**
	 * Returns the Year containing the specified Course
	 * @param c
	 * @return Containing Year or null if not found
	 */
	public Year findYear(Course c) {
		// Iterate through the years
		// If a quarter in the year contains the course, return the year
		// otherwise return null
		for(int i = 0; i < this.years.size(); i++) {
			if(this.years.get(i).findQuarter(c) != null) {
				return this.years.get(i);
			}
		}
		return null;
	}

	/**
	 * Returns the Quarter containing the specified Course
	 * @param c
	 * @return Containing Quarter or null if not found
	 */
	public Quarter findQuarter(Course c) {
		Year y;
		// If no year is found, return null
		if((y = this.findYear(c)) == null) {
			return null;
		} else {
			return y.findQuarter(c);
		}
	}
	
	/**
	 * Finds a course given the string name
	 * @param courseName
	 * @return Course or null if not found
	 */
	public Course findCourse(String courseName) {
		for(Year year : this.years) {
			for(Quarter quarter : year.getQuarterList()) {
				for(Course course : quarter.getCourseList()) {
					if(course.getCourseName().equalsIgnoreCase(courseName)) {
						return course;
					}
				}
			}
		}
		return null;
	}

	public boolean containsCourse(Course courseName) {
		for(Year year : this.years) {
			for(Quarter quarter : year.getQuarterList()) {
				for(Course course : quarter.getCourseList()) {
					if(course.compareTo(courseName) == 1){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean addCourse(Course c) {
		//TODO this function calls the private function with the year and quarter taken from the Course object
		String termTaken = c.getTermTaken();
		String academicYear = termTaken.substring(0, 4);
		int quarterIndex = Character.getNumericValue(termTaken.charAt(4)) - 1;
		
		Year year = null;
		for(int i = 0; i < this.years.size(); i++) {
			if(this.years.get(i).getAcademicYear().equals(academicYear)) {
				year = this.years.get(i);
				break;
			}
		}
		
		if(year == null) {
			IO.getInstance().display("Invalid Year\n");
		}
		
		Quarter[] quarters = year.getQuarterList();
		return this.addCourse(c, year, quarters[quarterIndex]);
	}
	
	public boolean addCourse(Course c, Year y, Quarter q) {
		if(this.getAllCourses().contains(c)){
			System.out.println("Cannot add course. Course already exist!");
			return false;
		}else{
			return y.addCourse(c, q);
		}
	}
	
	public boolean removeCourse(Course c, Year y, Quarter q) {
		return y.removeCourse(c, q);
	}
	
	public boolean moveCourse(Course c, Year srcY, Quarter srcQ, Year destY, Quarter destQ) {
		// If the course can be added to the destination, remove it from the source
		if(destY.addCourse(c, destQ)) {
			return srcY.removeCourse(c, srcQ);
		} 
		return false;
	}
	
	public ArrayList<Course> getAllCourses() {
		ArrayList<Course> courseList = new ArrayList<Course>();
		for(Year year : this.years) {
			for(Quarter quarter : year.getQuarterList()) {
				for(Course course : quarter.getCourseList()) {
					courseList.add(course);
				}
			}
		}
		return courseList;
	}
	
	@Override
	public int compareTo(POS o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
//	public void display() {
//		System.out.println("Plan of Study for: " + this.studentName + "    " + "Student ID: " + this.studentID);
//		for(Year yr : years){
//			yr.display();
//		}
//		System.out.println();
//	}


	//----------------------------------------------year 1-------------------------------------------------------
	public void displayAcademicYear0(){
		years.get(0).displayAcademicYear();
	}

	//print year 1, row 1(1st courses of all quarters)
	public void display() {
		years.get(0).display();
		System.out.println("|");
	}

	public void display1() {
		years.get(0).display1();
		System.out.println("|");
	}

	public void display2() {
		years.get(0).display2();
		System.out.println("|");
	}

	public void display3() {
		years.get(0).display3();
		System.out.println("|");
	}
	public void display4() {
		years.get(0).display4();
		System.out.println("|");
	}

	public void display5() {
		years.get(0).display5();
		System.out.println("|");
	}

	public void display6() {
		years.get(0).display6();
		System.out.println("|");
	}

	public void display7() {
		years.get(0).display7();
		System.out.println("|");
	}
	//----------------------------------------------year 2-------------------------------------------------------
	public void displayAcademicYear1(){
		years.get(1).displayAcademicYear();
	}
	//print year 2 row 1(1st courses of all quarters)
	public void display20() {
		years.get(1).display();
		System.out.println("|");
	}

	public void display21() {
		years.get(1).display1();
		System.out.println("|");
	}

	public void display22() {
		years.get(1).display2();
		System.out.println("|");
	}

	public void display23() {
		years.get(1).display3();
		System.out.println("|");
	}

	public void display24() {
		years.get(1).display4();
		System.out.println("|");
	}

	public void display25() {
		years.get(1).display5();
		System.out.println("|");
	}

	public void display26() {
		years.get(1).display6();
		System.out.println("|");
	}

	public void display27() {
		years.get(1).display7();
		System.out.println("|");
	}
	//----------------------------------------------year 3-------------------------------------------------------
	public void displayAcademicYear2(){
		years.get(2).displayAcademicYear();
	}

	public void display30() {
		years.get(2).display();
		System.out.println("|");
	}

	public void display31() {
		years.get(2).display1();
		System.out.println("|");
	}

	public void display32() {
		years.get(2).display2();
		System.out.println("|");
	}

	public void display33() {
		years.get(2).display3();
		System.out.println("|");
	}

	public void display34() {
		years.get(2).display4();
		System.out.println("|");
	}

	public void display35() {
		years.get(2).display5();
		System.out.println("|");
	}

	public void display36() {
		years.get(2).display6();
		System.out.println("|");
	}

	public void display37() {
		years.get(2).display7();
		System.out.println("|");
	}
	//----------------------------------------------year 4-------------------------------------------------------
	public void displayAcademicYear3(){
		years.get(3).displayAcademicYear();
	}

	public void display40() {
		years.get(3).display();
		System.out.println("|");
	}

	public void display41() {
		years.get(3).display1();
		System.out.println("|");
	}

	public void display42() {
		years.get(3).display2();
		System.out.println("|");
	}

	public void display43() {
		years.get(3).display3();
		System.out.println("|");
	}

	public void display44() {
		years.get(3).display4();
		System.out.println("|");
	}

	public void display45() {
		years.get(3).display5();
		System.out.println("|");
	}

	public void display46() {
		years.get(3).display6();
		System.out.println("|");
	}

	public void display47() {
		years.get(3).display7
				();
		System.out.println("|");
	}
	//----------------------------------------------year 5-------------------------------------------------------
	public void displayAcademicYear4(){
		years.get(4).displayAcademicYear();
	}

	public void display50() {
		years.get(4).display();
		System.out.println("|");
	}

	public void display51() {
		years.get(4).display1();
		System.out.println("|");
	}

	public void display52() {
		years.get(4).display2();
		System.out.println("|");
	}

	public void display53() {
		years.get(4).display3();
		System.out.println("|");
	}

	public void display54() {
		years.get(4).display4();
		System.out.println("|");
	}

	public void display55() {
		years.get(4).display5();
		System.out.println("|");
	}

	public void display56() {
		years.get(4).display6();
		System.out.println("|");
	}

	public void display57() {
		years.get(4).display7();
		System.out.println("|");
	}
}
