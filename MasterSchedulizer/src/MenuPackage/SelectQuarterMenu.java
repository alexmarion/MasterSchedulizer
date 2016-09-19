package MenuPackage;


import java.util.ArrayList;

import DriverPackage.POSManager;
import POSPackage.Year;
import POSPackage.Course;
import POSPackage.Quarter;

public class SelectQuarterMenu extends Menu {
	private String action;
	private Course course;
	private Year year;
	private Quarter[] quarters;
	
	public SelectQuarterMenu(MenuManager menuManager, POSManager posManager, String action, Course course, Year year) {
		super(menuManager, posManager);
		this.action = action;
		this.course = course;
		this.year = year;
		
        quarters = year.getQuarterList();
        // Add each quarter as a menu choice
        for(int i = 0; i < quarters.length; i++) {
        	choices.add(new MenuChoice(quarters[i].getTerm(), i));
        }
        
		MenuChoice back = new MenuChoice("Back", quarters.length);
		MenuChoice exit = new MenuChoice("Exit", quarters.length + 1);
		
		choices.add(back);
		choices.add(exit);
	}
	
	public Menu select(int i) {
		// If a quarter is selected, return the AddCourseMenu
		if(i > 0 && i <= quarters.length) {
			Quarter quarter = quarters[i - 1];
			if(this.action.equals("add")) {
				return new AddCourseMenu(menuManager, posManager, course, year, quarter);
			} else if(this.action.equals("move")) {
				return new MoveCourseMenu(menuManager, posManager, course, year, quarter);
			}
		} else {
			// Return the back or quit menu
			if(i == quarters.length + 1) {
				// Go to the back menu
				BackMenu back = new BackMenu(4);
				back.setPOSManager(posManager);
				return back;
			} else if(i == quarters.length + 2) {
				// Go to the quit menu
				QuitMenu quit = new QuitMenu();
				return quit;
			}
		}
		return null;
	}
	

}
