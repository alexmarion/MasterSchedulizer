package MenuPackage;

import java.util.Scanner;

import DriverPackage.POSManager;
import POSPackage.Course;
import ParserPackage.TMSMasterList;

public class SelectCourseMenu extends Menu {
	private String action;
	
	public SelectCourseMenu(MenuManager menuManager, POSManager posManager, String action) {
		super(menuManager, posManager);
		this.action = action;
		this.outputMessage = "Enter Course: ";
	}
	
    private String getCourseInput() {
        String course;
        Scanner in = new Scanner(System.in);
        course = in.nextLine();
        return course.toUpperCase();
    }
	
    public Course getCourse() {
    	return null;
    }
    
	public Menu runMenu() {
		
		this.display();
		
		// Keep track if the user has entered a valid course
		boolean gotCourse = false;
		
		// Stored the user input
		String courseStr = null;
		
		// Used to separate the subjCode and courseNum
        String[] courseSplit = null;
        
        // Used to store the course that the user inputs
        Course course = null;
        
        // Loop while no valid course has been found
        while(!gotCourse) {
    		this.outputMessage();
            courseStr = this.getCourseInput();
            courseSplit = courseStr.split(" ");
            if(courseSplit.length == 2) {
                gotCourse = true;
            } else {
                System.out.println("Incorrect course format");
            }
        }

        if(action.equals("add")) {
            // Search the master list for the course
            course = TMSMasterList.findCourse(courseSplit[0], courseSplit[1]);
        } else if(action.equals("remove")) {
        	// Search the POS for the course
        	course = posManager.getPOS().findCourse(courseSplit[0] + courseSplit[1]);
        } else if(action.equals("move")) {
        	// Search the POS for the course
        	course = posManager.getPOS().findCourse(courseSplit[0] + courseSplit[1]);
        }

        
        // If not found, reject and go back
        if(course == null) {
            System.out.println("Couldn't find specified course: " + courseStr);
            BackMenu back = new BackMenu();
            back.setPOSManager(posManager);
            return back;
        }
        
        
        Menu nextMenu = null;
        // Cases for which menu to return next depending on what action the user is performing
        if(action.equals("add")) {
            // If POS already contains course, reject and go back
            if(posManager.getPOS().containsCourse(course)){
                System.out.println("Unable to add course. Course already exist.");
                BackMenu back = new BackMenu();
                back.setPOSManager(posManager);
                return back;
            } else {
            	// Otherwise, return the next menu
            	nextMenu = new SelectYearMenu(menuManager, posManager, action, course);
            }       	
        } else if(action.equals("remove")) {
        	nextMenu = new RemoveCourseMenu(menuManager, posManager, course);
        } else if(action.equals("move")) {
        	nextMenu = new SelectYearMenu(menuManager, posManager, action, course);
        }
        
		return nextMenu;
	}
	
	public void display() {
		this.genMenu();
	}
	
	private void genMenu() {
        System.out.println("+-------------------------------------------------------------------+ ");
        System.out.println("| = : = : = : = : = : = : = : = : = : = : = : = : = : = : = : = : = |");
        System.out.println("|{>/-------------------------------------------------------------\\<}|");
        System.out.println("|: |                                                             | :|");
        System.out.println("| :|                                                             |: |");
        System.out.println("|: |                Type in the name of the Course               | :|");
        if(action.equals("add")){
            System.out.printf("| :|                    you would like to %s                    | :|\n", action);
        }else if(action.equals("remove")){
            System.out.printf("| :|                    you would like to %s                 | :|\n", action);
        }else if(action.equals("move")){
            System.out.printf("| :|                    you would like to %s                   | :|\n", action);
        }
        System.out.println("|: |                      (e.g. MATH 221):                       | :|");
        System.out.println("| :|                                                             |: |");
        System.out.println("|: |                                                             | :|");
        System.out.println("|{>\\-------------------------------------------------------------/<}|");
        System.out.println("| = : = : = : = : = : = : = : = : = : = : = : = : = : = : = : = : = |");
        System.out.println("+-------------------------------------------------------------------+");
    }

}
