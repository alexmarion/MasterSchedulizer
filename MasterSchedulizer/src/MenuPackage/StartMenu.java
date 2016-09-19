package MenuPackage;

import java.util.ArrayList;
import java.util.Scanner;

import DriverPackage.POSManager;

public class StartMenu extends Menu {
	
	public StartMenu(MenuManager menuManager) {
		// Initialilze the new POSManager
		super(menuManager, new POSManager(null, 0));
		
		// Create menu choices
		MenuChoice loadPOS = new MenuChoice("Load Plan of Study", 0);
		MenuChoice createPOS = new MenuChoice("Create New Plan of Study", 1);
		MenuChoice printSamplePOS = new MenuChoice("Print Sample Plan of Study", 2);
		MenuChoice exit = new MenuChoice("Exit", 3);
		
		// Adding menu choices to list
		super.choices = new ArrayList<MenuChoice>();
		super.choices.add(loadPOS);
		super.choices.add(createPOS);
		super.choices.add(printSamplePOS);
		super.choices.add(exit);
	}
	
	// Allows the user to select a MenuChoice
	public Menu select(int i) {
		switch(i) {
			case 1:
				// Go to the loadPOS menu
				Menu loadPOSMenu = new LoadPOSMenu(menuManager, posManager);
				return loadPOSMenu;
			case 2:
				// Go to the createPOS menu
				String name = getName();
				int studentID = getStudentID();
				POSManager newPosManager = new POSManager(name, studentID);
				Menu createPOSMenu = new CreatePOSMenu(menuManager, newPosManager);
				return createPOSMenu;
			case 3:
				// Go to the printSamplePOS menu
				Menu printSamplePOSMenu = new PrintSamplePOSMenu();
				return printSamplePOSMenu;
			case 4:
				// Go to the quit menu
				QuitMenu quit = new QuitMenu();
				return quit;
		}
		return null;
	}
	
	private String getName() {
		String name = "";
		Scanner in = new Scanner(System.in);
		while(name.equals("")) {
			System.out.print("Enter name: ");
			name = in.nextLine();
			if(name.equals("")) {
				System.out.println("Name cannot be blank");
			}
		}
		return name;
	}
	
	private int getStudentID() {
		int studentID = 0;
		boolean gotID = false;
		Scanner in = new Scanner(System.in);
		while(!gotID) {
			try {
				in.reset();
				System.out.print("Enter student ID: ");
				studentID = Integer.parseInt(in.nextLine());
				if(Integer.toString(studentID).length() == 8) {
					gotID = true;
				} else {
					System.out.println("Student ID must be 8 digits");
				}
			} catch(Exception e) {
				System.out.println("Input must be numeric");
				continue;
			}
		}
		return studentID;
	}
}
