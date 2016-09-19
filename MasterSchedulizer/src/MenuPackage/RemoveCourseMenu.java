package MenuPackage;

import DriverPackage.POSManager;
import POSPackage.Course;
import POSPackage.Quarter;
import POSPackage.Year;

public class RemoveCourseMenu extends Menu {
	private Course c;
	private Year y;
	private Quarter q;
	
	public RemoveCourseMenu(MenuManager menuManager, POSManager posManager, Course course) {
		super(menuManager, posManager);
		this.c = course;
		this.y = posManager.getPOS().findYear(course);
		this.q = posManager.getPOS().findQuarter(course);
		
		this.outputMessage = course.getCourseName() + " was removed from " + q.getTerm() + ", " + y.getAcademicYear() + "\n";
	}
	
	public Menu runMenu() {
		if(posManager.removeCourse(c, y, q)) {
			this.outputMessage();
		} else {
			System.out.println("Error removing " + c.getCourseName());
		}
		BackMenu back = new BackMenu(2);
		back.setPOSManager(posManager);
		return back;
	}

}
