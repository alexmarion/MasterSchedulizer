package POSPackage;
public class Year implements Comparable<Year> {
	private Quarter[] quarterList;
	private String academicYear;
	
	public Year(String ay) {
		this.academicYear = ay;
		this.quarterList = new Quarter[4];
		
		for(int i = 0; i < quarterList.length; i++) {
			quarterList[i] = new Quarter(ay + (i + 1) + "5");
		}
	}
	
	public Year(String ay, Quarter[] ql) {
		this.academicYear = ay;
		
		//TODO: Check if ql is of correct size
		if(ql.length <= 4 && ql.length >= 0) {
			this.quarterList = ql;
		}else{
			System.out.println("Quarter list is not of correct size");
		}
	}
	
	// Deep copy constructor
	public Year(Year y) {
		this.academicYear = y.getAcademicYear();
		this.quarterList = new Quarter[4];
		for(int i = 0; i < y.getQuarterList().length; i++) {
			this.quarterList[i] = new Quarter(y.getQuarterList()[i]);
		}
	}

	@Override
	public String toString() {
		return String.format(academicYear);
	}

	public Quarter getQuarter(int i){
		Quarter q[] = new Quarter[4];
		if(i <= 4 && i >= 0) {
			q = this.getQuarterList();
		}else{
			System.out.println("Invalid input. Input must be between 1 and 4");
		}
		return q[i-1];
	}


	/**
	 * @return the quarterList
	 */
	public Quarter[] getQuarterList() {
		return quarterList;
	}

	/**
	 * @param quarterList the quarterList to set
	 */
	public void setQuarterList(Quarter[] quarterList) {
		//TODO: check list size
		if(quarterList.length <= 4 && quarterList.length >= 0) {
			this.quarterList = quarterList;
		}else{
			System.out.println("Quarter list is not of correct size");
		}
	}

	/**
	 * @return the academicYear
	 */
	public String getAcademicYear() {
		return academicYear;
	}

	/**
	 * @param academicYear the academicYear to set
	 */
	public void setAcademicYear(String academicYear) {
		// TODO: check format
		if(Integer.parseInt(academicYear) > 1900 && Integer.parseInt(academicYear) < 2100) {
			this.academicYear = academicYear;
		}else{
			System.out.println("Invalid academic year");
		}
	}
	
	/**
	 * Search the list of quarters and return the quarter that contains the specified Course
	 * @param c
	 * @return Quarter which contains Course or null if not found
	 */
	public Quarter findQuarter(Course c) {
		for(int i = 0; i < this.quarterList.length; i++) {
			if(this.quarterList[i].containsCourse(c)) {
				return this.quarterList[i];
			}
		}
		return null;
	}
	
	/**
	 * Add a Course to the specified Quarter
	 * @param c
	 * @param q
	 * @return True if successful, False otherwise
	 */
	public boolean addCourse(Course c, Quarter q) {
		return q.addCourse(c);
	}

	/**
	 * Remove a Course from the specified Quarter
	 * @param c
	 * @param q
	 * @return True if successful, False otherwise
	 */
	public boolean removeCourse(Course c, Quarter q) {
		return q.removeCourse(c);
	}
	
	/**
	 * Move the specified course from qSrc to qDest
	 * @param c
	 * @param qSrc
	 * @param qDest
	 * @return True if sucessful, False otherwise
	 */
	public boolean moveCourse(Course c, Quarter qSrc, Quarter qDest) {
		// If the Course can be added to the destination, remove it from the source
		if(qDest.addCourse(c)) {
			return qSrc.removeCourse(c);
		}
		return false;
	}
	
	@Override
	public int compareTo(Year y) {
		if(this.hashCode() != y.hashCode()) {
			return 0;
		}
		
		Quarter[] yQuarterList = y.getQuarterList();
		for(int i = 0; i < this.quarterList.length; i++) {
			if(this.quarterList[i] != yQuarterList[i]) {
				return 0;
			}
		}
		return 1;
	}
	
//	public void display() {
//		System.out.println("Year: " + this.academicYear + " - " + (Integer.parseInt(this.academicYear) + 1));
//		Quarter[] q = getQuarterList();
//		for(int i=0; i < q.length; i++){
//			q[i].display();
//		}
//		System.out.println();
//	}

	//------------------------------------------display academic year---------------------------------------------------

	public void displayAcademicYear(){
		String leftAlignFormat = "| %-50s |%n";
		String academicYear = "\t\t\t\t\t\t\tYear: " + this.academicYear + " - " + (Integer.parseInt(this.academicYear) + 1);
		System.out.format(leftAlignFormat, academicYear);
//		System.out.println("\t\t\t\t\t\t\tYear: " + this.academicYear + " - " + (Integer.parseInt(this.academicYear) + 1));
	}

	//------------------------------------------display quarters-------------------------------------------------------
	//iterate through quarters 0-3, fall-summer, calling the quarter display method to print the 1st course
	//of the ith quarter
	public void display() {
		Quarter[] q = getQuarterList();
		for(int i=0; i < q.length; i++){
			q[i].display();
		}
	}
	//iterate through quarters 0-3, fall-summer, calling the quarter display method to print the 2nd course
	//of the ith quarter
	public void display1() {
		Quarter[] q = getQuarterList();
		for(int i=0; i < q.length; i++){
			q[i].display1();
		}
	}
	//iterate through quarters 0-3, fall-summer, calling the quarter display method to print the 3rd course
	//of the ith quarter
	public void display2() {
		Quarter[] q = getQuarterList();
		for(int i=0; i < q.length; i++){
			q[i].display2();
		}
	}
	//iterate through quarters 0-3, fall-summer, calling the quarter display method to print the 4th course
	//of the ith quarter
	public void display3() {
		Quarter[] q = getQuarterList();
		for(int i=0; i < q.length; i++){
			q[i].display3();
		}
	}
	//iterate through quarters 0-3, fall-summer, calling the quarter display method to print the 5th course
	//of the ith quarter
	public void display4() {
		Quarter[] q = getQuarterList();
		for(int i=0; i < q.length; i++){
			q[i].display4();
		}
	}
	//iterate through quarters 0-3, fall-summer, calling the quarter display method to print the 6th course
	//of the ith quarter
	public void display5() {
		Quarter[] q = getQuarterList();
		for(int i=0; i < q.length; i++){
			q[i].display5();
		}
	}
	//iterate through quarters 0-3, fall-summer, calling the quarter display method to print the 7th course
	//of the ith quarter
	public void display6() {
		Quarter[] q = getQuarterList();
		for(int i=0; i < q.length; i++){
			q[i].display6();
		}
	}

	public void display7() {
		Quarter[] q = getQuarterList();
		for(int i=0; i < q.length; i++){
			q[i].display7();
		}
	}
}
