package MenuPackage;


import DriverPackage.POSManager;
import POSPackage.Course;
import POSPackage.Year;
import POSPackage.Quarter;

public class AddCourseMenu extends Menu {
	private Course c;
	private Year y;
	private Quarter q;
	
	public AddCourseMenu(MenuManager menuManager, POSManager posManager, Course c, Year y, Quarter q) {
		super(menuManager, posManager);
		this.c = c;
		this.y = y;
		this.q = q;
	}

	public Menu runMenu() {
		if(posManager.addCourse(c, y, q)) {
			System.out.println("Succesfully added " + c.getCourseName() + " to " + q.getTerm() + ", " + y.getAcademicYear());
		} else {
			System.out.println("Encountered error while trying to add course!");
		}
		
		// Go back by 4 menus to the loadPOSMenu 
		BackMenu back = new BackMenu(4);
		back.setPOSManager(posManager);
		return back;
	}
}
