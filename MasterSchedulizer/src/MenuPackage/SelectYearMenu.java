package MenuPackage;

import java.util.ArrayList;

import DriverPackage.POSManager;
import POSPackage.Course;
import POSPackage.Year;

public class SelectYearMenu extends Menu {
	private String action;
	private Course course;
	private ArrayList<Year> years;
	
	public SelectYearMenu(MenuManager menuManager, POSManager posManager, String action, Course course) {
		super(menuManager, posManager);
		this.action = action;
		this.course = course;
		
		// Adding years to the choices
        years = posManager.getPOS().getYears();
        for(int i = 0; i < years.size(); i++) {
        	choices.add(new MenuChoice(years.get(i).getAcademicYear(), i));
        }
        
		MenuChoice back = new MenuChoice("Back", years.size());
		MenuChoice exit = new MenuChoice("Exit", years.size() + 1);
		
		choices.add(back);
		choices.add(exit);
	}
	
	public Menu select(int i) {
		// Go to the selectQuarterMenu with the specified year
		if(i > 0 && i <= years.size()) {
			Year year = posManager.getPOS().getYears().get(i - 1);	
			return new SelectQuarterMenu(menuManager, posManager, action, course, year);
		} else {
			// Return the back or quit menu
			if(i == years.size() + 1) {
				// Go to the back menu
				BackMenu back = new BackMenu(2);
				back.setPOSManager(posManager);
				return back;
			} else if(i == years.size() + 2) {
				// Go to the quit menu
				QuitMenu quit = new QuitMenu();
				return quit;
			}
		}
		return null;
	}
}
