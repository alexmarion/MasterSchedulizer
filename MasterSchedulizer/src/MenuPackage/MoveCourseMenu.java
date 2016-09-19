package MenuPackage;

import DriverPackage.POSManager;
import POSPackage.Course;
import POSPackage.Quarter;
import POSPackage.Year;

public class MoveCourseMenu extends Menu {
	private Course c;
	private Year destY;
	private Quarter destQ;
	private Year srcY;
	private Quarter srcQ;

	
	public MoveCourseMenu(MenuManager menuManager, POSManager posManager, Course course, Year year, Quarter quarter) {
		super(menuManager, posManager);
		this.c = course;
		this.destY = year;
		this.destQ = quarter;
		
		this.srcY = posManager.getPOS().findYear(course);
		this.srcQ = posManager.getPOS().findQuarter(course);
		
		this.outputMessage = c.getCourseName() + " moved from " + srcQ.getTerm() + ", " + srcY.getAcademicYear() + " to " + destQ.getTerm() + ", " + destY.getAcademicYear() + "\n"; 
	}
	
	public Menu runMenu() {
		System.out.println(posManager.getPOS() + "\n");
		if(posManager.moveCourse(c, srcY, srcQ, destY, destQ)) {
			this.outputMessage();
		} else {
			System.out.println("Error moving " + c.getCourseName());
		}
		BackMenu back = new BackMenu(4);
		back.setPOSManager(posManager);
		return back;
	}

}
