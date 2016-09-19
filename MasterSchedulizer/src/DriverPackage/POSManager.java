package DriverPackage;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import POSPackage.*;
import ParserPackage.CSVWriter;
import ParserPackage.CourseLoader;

public class POSManager {
	private String name;
	private int studentID;
	private POS planOfStudy;
	private String filepath;

	// private POSReconfigurer reconfigurer

	public POSManager(String name, int studentID) {
		this.name = name;
		this.studentID = studentID;
		planOfStudy = null;
		filepath = this.name + Integer.toString(this.studentID) + ".csv";
	}

	public POSManager(POS pos) {
		this.planOfStudy = pos;
		this.name = planOfStudy.getStudentName();
		this.studentID = planOfStudy.getStudentID();
		filepath = this.name + Integer.toString(this.studentID) + ".csv";
	}

	public String getStudentName() {
		return this.name;
	}

	public int getStudentID() {
		return this.studentID;
	}

	public POS getPOS() {
		return this.planOfStudy;
	}

	public void setPOS(POS planOfStudy) {
		this.planOfStudy = planOfStudy;
	}

	public void savePOS() {
		if (planOfStudy == null) {
			System.out.println("There is no plan of study to save!");
		} else {
			CSVWriter.writeCSVFile(filepath, planOfStudy);
			System.out.println("Plan of study sucessfully saved to " + filepath);
		}
	}

	public POS loadPOS(String filepath) {
		// File Name is studentName + studentID
		// Split between the alphabetic and numeric characters
		Path fp = Paths.get(filepath);
		String filename = fp.getFileName().toString();

		CourseLoader loader = new CourseLoader(filepath);


		// Dont get the name for sample files
		if (!filename.contains("sample")) {
			String[] filenameFields = filename.split("(?<=\\d)(?=\\D)|(?=\\d)(?<=\\D)");
			if (filenameFields.length != 2) {
				System.out.println("Bad file name format!");
			} else {
				filename = fp.getFileName().toString();
				this.name = filenameFields[0];
				this.studentID = Integer.parseInt(filenameFields[1].replace(".csv", ""));
			}
		}

		ArrayList<Course> courseList = loader.getCourses();
		courseList = loader.assignPreReqs(courseList);
		courseList = loader.assignCoReqs(courseList);

		for (Course course : courseList) {
			for (ArrayList<Course> preReqList : course.getPreReqs()) {
				for (Course preReq : preReqList) {
					for (Course isPreReq : courseList) {
						if (isPreReq.compareTo(preReq) == 1) {
							preReq.setTermTaken(isPreReq.getTermTaken());
							// System.out.println(preReq.getCourseName() + ": "
							// + preReq.getTermTaken());
						}
					}
				}
			}
		}
		System.out.println("=================================");
		this.planOfStudy = new POS(name, studentID, courseList);
		return this.planOfStudy;
	}

	public POS loadSample() {
		return this.loadPOS("sample.csv");
	}

	public boolean addCourse(Course c, Year y, Quarter q) {
		if(q.getCredits() + c.getCredits() > 20) {
			System.out.println("Cannot add course: too many credits");
			return false;
		}

		String termOffered = c.getTermsOffered(); // 1101
		String termTaken = q.getQuarterID().substring(4, 5);// 201515 --> 1 or
															// 201525 --> 2
		while(termOffered.length() < 4) {
			termOffered = "0" + termOffered;
		}

		String fall, winter, spring, summer;
		fall = termOffered.substring(0, 1);
		winter = termOffered.substring(1, 2);
		spring = termOffered.substring(2, 3);
		summer = termOffered.substring(3, 4);
		// --------------------------------checks if coursed is offered in that
		// term------------------------------------
		switch (termTaken) {
		case "1":
			if (fall.compareTo("1") != 0) {
				System.out.println(c.getSubjectCode() + " " + c.getCourseNum() + " is not offered in this term.");
				return false;
			}
			break;
		case "2":
			if (winter.compareTo("1") != 0) {
				System.out.println(c.getSubjectCode() + " " + c.getCourseNum() + " is not offered in this term.");
				return false;
			}
			break;
		case "3":
			if (spring.compareTo("1") != 0) {
				System.out.println(c.getSubjectCode() + " " + c.getCourseNum() + " is not offered in this term.");
				return false;
			}
			break;
		case "4":
			if (summer.compareTo("1") != 0) {
				System.out.println(c.getSubjectCode() + " " + c.getCourseNum() + " is not offered in this term.");
				return false;
			}
			break;
		default:
			System.out.println("Invalid term taken");
		}
		// --------------------------------checks if prereq is
		// satisfied-----------------------------------------------
		boolean prereqsSatisfied = checkPrereqs(c, y, q);
		if (prereqsSatisfied) {
			// System.out.println("Found " + courseInPr + " in the POS.");
			System.out.println("Successfully found prereq in POS");
			return planOfStudy.addCourse(c, y, q);
		} else {
			System.out.println("Did not find prereq in POS, unable to add");
			// System.out.println(courseInPr + " does not exist in the POS.");
		}
		return false;
	}

	private boolean checkPrereqs(Course c, Year y, Quarter q) {
		// get list list of arraylist of prereq
		List<ArrayList<Course>> entirePreReqList = c.getPreReqs();
		int qid = Integer.parseInt(q.getQuarterID());
		for (List<Course> pr : entirePreReqList) {
			boolean satisfied = false;
			for (Course cc : pr) {
				cc = planOfStudy.findCourse(cc.getCourseName());
				if (cc != null) {
					int qtaken = Integer.parseInt(cc.getTermTaken());
					if (qtaken != 0 && qtaken < qid) {
						satisfied = true;
						break;
					}
				}
			}
			if (!satisfied) {
				return false;
			}
		}
		return true;
	}

	public boolean addCourseAndPreReqsToFirstAvailableQuarter(Course c) {
		if (c == null) {
			return false;
		}
		if (this.addCourseToFirstAvailableQuarter(c)) {
			return true;
		} else {
			// Add the first item from every preReq List
			for (ArrayList<Course> preReqList : c.getPreReqs()) {
				this.addCourseAndPreReqsToFirstAvailableQuarter(preReqList.get(0));
			}
			return this.addCourseToFirstAvailableQuarter(c);
		}
	}

	// Run through the quarters in the POS and add the course to the first one
	// available
	public boolean addCourseToFirstAvailableQuarter(Course c) {
		if (c == null) {
			return false;
		}

		// Copy the POS and assign it to a manager
		POS posCopy = new POS(planOfStudy);
		POSManager posCopyManager = new POSManager(posCopy.getStudentName(), posCopy.getStudentID());
		posCopyManager.setPOS(posCopy);
		for (Year year : posCopyManager.getPOS().getYears()) {
			for (Quarter quarter : year.getQuarterList()) {
				if (posCopyManager.addCourse(c, year, quarter)) {
					c.setTermTaken(quarter.getQuarterID());
					Year yToAdd = planOfStudy.getYearByID(year.getAcademicYear());
					Quarter qToAdd = planOfStudy.getQuarterByID(quarter.getQuarterID());
					if (qToAdd == null || yToAdd == null) {
						return false;
					} else {
						return this.addCourse(c, yToAdd, qToAdd);
					}
				}
			}
		}
		return false;
	}

	public boolean removeCourse(Course c, Year y, Quarter q) {
		// get all courses in POS and iterate through it
		for (Course outerList : planOfStudy.getAllCourses()) {
			// only check course after the removed course getTermTaken
			if (Integer.parseInt(outerList.getTermTaken()) > Integer.parseInt(c.getTermTaken())) {
				for (List<Course> innerList : outerList.getPreReqs()) {
					if (!innerList.isEmpty()) {
						for (Course preReq : innerList) {
							if (c.compareTo(preReq) == 1) {
								System.out.println("Unable to remove " + c.getSubjectCode() + " " + c.getCourseNum()
										+ " " + "because is a prereq for " + outerList + ".");
								return false;
							}
						}
					}
				}
			}
		}
		System.out.println(c.getSubjectCode() + " " + c.getCourseNum() + " is sucessfully removed.");
		return planOfStudy.removeCourse(c, y, q);

	}

	public boolean moveCourse(Course c, Year srcY, Quarter srcQ, Year destY, Quarter destQ) {
		String termOffered = c.getTermsOffered(); // 1101
		String termTaken = destQ.getQuarterID().substring(4, 5);
		String fall, winter, spring, summer;
		fall = termOffered.substring(0, 1);
		winter = termOffered.substring(1, 2);
		spring = termOffered.substring(2, 3);
		summer = termOffered.substring(3, 4);
		// --------------------------------checks if coursed is offered in that
		// term------------------------------------
		switch (termTaken) {
		case "1":
			if (fall.compareTo("1") != 0) {
				System.out.println(c.getSubjectCode() + " " + c.getCourseNum() + " is not offered in this term.");
				return false;
			}
			break;
		case "2":
			if (winter.compareTo("1") != 0) {
				System.out.println(c.getSubjectCode() + " " + c.getCourseNum() + " is not offered in this term.");
				return false;
			}
			break;
		case "3":
			if (spring.compareTo("1") != 0) {
				System.out.println(c.getSubjectCode() + " " + c.getCourseNum() + " is not offered in this term.");
				return false;
			}
			break;
		case "4":
			if (summer.compareTo("1") != 0) {
				System.out.println(c.getSubjectCode() + " " + c.getCourseNum() + " is not offered in this term.");
				return false;
			}
			break;
		default:
			System.out.println("Invalid term taken");
		}
		// --------------------------------checks if prereq is
		// satisfied-----------------------------------------------
		boolean prereqsSatisfied = checkMovePreReqs(c, srcY, srcQ, destY, destQ);
		if (prereqsSatisfied) {
			System.out.println("Successfully move course");
			return planOfStudy.moveCourse(c, srcY, srcQ, destY, destQ);
		} else {
			System.out.println("Unable to move course");
			return false;
		}
	}

	private boolean checkMovePreReqs(Course c, Year srcY, Quarter srcQ, Year destY, Quarter destQ) {
		boolean isValid = true;
		// if course move forward, check if the course being moved is a prereq
		// for any courses before the destination
		if (Integer.parseInt(srcQ.getQuarterID()) <= Integer.parseInt(destQ.getQuarterID())) {
			// iterate through all the courses in POS
			for (Course outerList : planOfStudy.getAllCourses()) {
				// only check between srcQ(included) and destQ
				if (Integer.parseInt(outerList.getTermTaken()) >= Integer.parseInt(srcQ.getQuarterID())
						&& Integer.parseInt(outerList.getTermTaken()) < Integer.parseInt(destQ.getQuarterID())) {
					// get list of prereq from course in POS
					for (List<Course> innerList : outerList.getPreReqs()) {
						// if course in list doesn't have any prereq
						if (!innerList.isEmpty()) {
							// go through inner list
							if (innerList.contains((Course) c)) {
								for (Course preReq : innerList) {
									for (Course courseInPOS : planOfStudy.getAllCourses()) {
										if (preReq.compareTo(courseInPOS) == 1 && c.compareTo(courseInPOS) == 1) {
											isValid = false;
										} else if (innerList.contains((Course) preReq)
												&& preReq.compareTo(courseInPOS) == 1) {
											isValid = true;
										}
									}
								}
							}
						}
					}
				}
			}
			// if course move backward/take a course ealier check to see if you
			// have the prerequise satisfy to take it early
		} else {
			for (List<Course> outerList : c.getPreReqs()) {
				for (Course innerList : outerList) {
					for (Course coursesInPOS : planOfStudy.getAllCourses()) {
						if (Integer.parseInt(coursesInPOS.getTermTaken()) <= Integer.parseInt(c.getTermTaken())) {
							// check to see if prerequise is found in the POS
							if (innerList.compareTo(coursesInPOS) == 1) {
								isValid = true;
								break;
								// System.out.println("Unable to move " +
								// c.getSubjectCode() + " " + c.getCourseNum() +
								// " " +
								// "because you did not satisfy the prerequisite
								// of " + innerList + ".");
								// return false;
							}
						}
					}
				}
			}
		}
		return isValid;
	}

	public String getTotalCredits() {
		double totalCredits = 0.0;
		for (Course c : planOfStudy.getAllCourses()) {
			if (!c.getSubjectCode().equals("COOP")) {
				totalCredits += c.getCredits();
			}
		}
		return String.valueOf((totalCredits));
	}

	public void display() {
		getTotalCredits();
		GeneratePOS generatePOS = new GeneratePOS(this.planOfStudy, getTotalCredits());
	}

}
