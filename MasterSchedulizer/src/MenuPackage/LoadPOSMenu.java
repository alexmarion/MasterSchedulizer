package MenuPackage;

import java.util.ArrayList;
import java.util.Scanner;

import DriverPackage.POSManager;

public class LoadPOSMenu extends Menu {
	Scanner in;
	public LoadPOSMenu(MenuManager menuManager, POSManager posManager) {
		super(menuManager, posManager);
		
		this.in = new Scanner(System.in);

		// Create menu choices
		MenuChoice viewPOS = new MenuChoice("View Plan of Study", 0);
		MenuChoice addCourse = new MenuChoice("Add Course", 1);
		MenuChoice removeCourse= new MenuChoice("Remove Course", 2);
		MenuChoice moveCourse= new MenuChoice("Move Course", 3);
		MenuChoice save= new MenuChoice("Save", 4);
		MenuChoice back = new MenuChoice("Back", 5);
		MenuChoice exit = new MenuChoice("Exit", 6);
		
		// Adding menu choices to list
		super.choices = new ArrayList<MenuChoice>();
		super.choices.add(viewPOS);
		super.choices.add(addCourse);
		super.choices.add(removeCourse);
		super.choices.add(moveCourse);
		super.choices.add(save);
		super.choices.add(back);
		super.choices.add(exit);
	}
	
	public Menu select(int i) {
		switch(i) {
			case 1:
				// Go to the viewPOS menu
				Menu viewPOSMenu = new ViewPOSMenu(menuManager, posManager);
				return viewPOSMenu;
			case 2:
				// Go to the selectCourse menu with the "add" command
				Menu selectCourseMenuAdd = new SelectCourseMenu(menuManager, posManager, "add");
				return selectCourseMenuAdd;
			case 3:
				// Go to the removeCourse menu
				Menu selectCourseMenuRemove = new SelectCourseMenu(menuManager, posManager, "remove");
				return selectCourseMenuRemove;
			case 4:
				// Go to the moveCourse menu
				Menu selectCourseMenuMove = new SelectCourseMenu(menuManager, posManager, "move");
				return selectCourseMenuMove;
			case 5:
				// Go to the save menu
				Menu saveMenu = new SaveMenu(menuManager, posManager);
				return saveMenu;
			case 6:
				// Go to the back menu
				BackMenu back = new BackMenu();
				back.setPOSManager(posManager);
				return back;
			case 7:
				// Go to the quit menu
				QuitMenu quit = new QuitMenu();
				return quit;
		}
		return null;
	}
	
	/**
	 * Runs the actions for the Menu
	 * Asks for filepath to POS and loads POS using POSManager
	 * @return
	 */
	public Menu runMenu() {
		// TODO: this keeps the program from re-loading the POS when the user goes back from any of the load POS options
		// Need to think of a way to allow the user to load a *different* POS in a single session 
		if(posManager.getPOS() == null) {
	        System.out.print("Enter file path: ");
	        posManager.loadPOS(in.nextLine());
			posManager.display();
		}
		
		this.display();
		this.outputMessage();
		int i = this.getInput();
		return this.select(i);
	}
}
