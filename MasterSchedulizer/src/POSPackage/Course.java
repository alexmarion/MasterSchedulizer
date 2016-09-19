package POSPackage;
import java.util.ArrayList;

public class Course implements Comparable<Course> {
	private String subjectCode;
	private String courseNum;
	private String courseTitle;
	private Double credits;
	private ArrayList<ArrayList<Course>> preReqs;
	private String preReqStr;
	private ArrayList<Course> coReqs;
	private String coReqStr;
	private String termsOffered;
	private String termTaken;

	public Course(String sc, String cn, String title, Double credits) {
		this.subjectCode = sc;
		this.courseNum = cn;
		this.courseTitle = title;
		this.credits = credits;

		preReqs = new ArrayList<ArrayList<Course>>();
		coReqs = new ArrayList<Course>();
		termsOffered = "0000";
		termTaken = "00000";
	}

	// Deep copy constructor
	public Course(Course c) {
		this.subjectCode = c.getSubjectCode();
		this.courseNum = c.getCourseNum();
		this.courseTitle = c.getCourseTitle();
		this.credits = c.getCredits();
		this.termsOffered = c.getTermsOffered();
		this.termTaken = c.getTermTaken();
		
		this.preReqs = new ArrayList<ArrayList<Course>>();
		this.coReqs = new ArrayList<Course>();
		
		for(int i = 0; i < c.getPreReqs().size(); i++) {
			this.preReqs.add(new ArrayList<Course>());
			for(int j = 0; j < c.getPreReqs().get(i).size(); j++) {
				this.preReqs.get(i).add(new Course(c.getPreReqs().get(i).get(j)));
			}
		}
		
		for(Course coReq : c.getCoReqs()) {
			this.coReqs.add(new Course(coReq));
		}
	}

	/**
	 * Check to see if two courses are equal
	 *
	 * @return 1 if true, 0 if false
	 */
	@Override
	public int compareTo(Course c) {
		if (subjectCode.equals(c.getSubjectCode()) && courseNum.equals(c.getCourseNum())) {
			return 1;
		}
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Course){
			Course c = (Course) obj;
			if(c.compareTo(this) == 1) {
				return true;
			}
		}
		return false;
	}

	/* Returns the string representation of this Course object.*/
	@Override
	public String toString() {
		return String.format(subjectCode + " " + courseNum);
	}

	/**
	 * @return the subjectCode
	 */
	public String getSubjectCode() {
		return subjectCode;
	}

	/**
	 * @param subjectCode the subjectCode to set
	 */
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	/**
	 * @return the courseNum
	 */
	public String getCourseNum() {
		return courseNum;
	}

	/**
	 * @param courseNum the courseNum to set
	 */
	public void setCourseNum(String courseNum) {
		this.courseNum = courseNum;
	}

	public String getCourseName() {
		return this.subjectCode.concat(courseNum);
	}

	/**
	 * @return the courseTitle
	 */
	public String getCourseTitle() {
		return courseTitle;
	}

	/**
	 * @param courseTitle the courseTitle to set
	 */
	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
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
	 * @return the preReqs
	 */
	public ArrayList<ArrayList<Course>> getPreReqs() {
		return preReqs;
	}

	/**
	 * @param arrayList the preReqs to set
	 */
	public void setPreReqs(ArrayList<ArrayList<Course>> arrayList) {
		this.preReqs = arrayList;
	}
	
	public String getPreReqStr() {
		return preReqStr;
	}
	
	public void setPreReqStr(String str) {
		this.preReqStr = str;
	}

	/**
	 * @return the coReqs
	 */
	public ArrayList<Course> getCoReqs() {
		return coReqs;
	}

	/**
	 * @param coReqs the coReqs to set
	 */
	public void setCoReqs(ArrayList<Course> coReqs) {
		this.coReqs = coReqs;
	}
	
	public String getCoReqStr() {
		return coReqStr;
	}
	
	public void setCoReqStr(String str) {
		this.coReqStr = str;
	}

	/**
	 * @return the termsOffered
	 */
	public String getTermsOffered() {
		return termsOffered;
	}

	/**
	 * @param i the termsOffered to set
	 */
	public void setTermsOffered(String t) {
		this.termsOffered = t;
	}

	/**
	 * @return the termTaken
	 */
	public String getTermTaken() {
		return termTaken;
	}

	/**
	 * @param termTaken the termTaken to set
	 */
	public void setTermTaken(String termTaken) {
		this.termTaken = termTaken;
	}

	/**
	 * Display method for a Course
	 */

	//display i.e CS 171; CourseNum + CourseTitle
//	public void display() {
//		System.out.println(this.subjectCode + " " + this.courseNum + " " + this.courseTitle);
//	}
	public void display() {
		String leftAlignFormat = "| %-15s ";
		String course = this.subjectCode + " " + this.courseNum;
		System.out.format(leftAlignFormat, course);
	}
}
