package MenuPackage;

import java.util.ArrayList;
import java.util.HashMap;

import DriverPackage.POSManager;
import MajorPackage.BaseMajor;
import MajorPackage.CSMajor;
import MajorPackage.Major;
import POSPackage.Course;

public class CreatePOSMenu extends Menu {
	Major csMajor;
	
	public CreatePOSMenu(MenuManager menuManager, POSManager posManager) {
		super(menuManager, posManager);
		
		// Load the sample for the base of the new POS
		posManager.loadSample();
		
		// Create a new CSMajor
		csMajor = new CSMajor(new BaseMajor());
		
		this.outputMessage = "Please select two different tracks\n";
		
		MenuChoice dsTrack = new MenuChoice("Algorithms and Data Structures", 0);
		MenuChoice aiTrack = new MenuChoice("Artificial Intelligence", 1);
		MenuChoice netTrack = new MenuChoice("Computer and Network Security", 2);
		MenuChoice archTrack = new MenuChoice("Computer Architecture", 3);
		MenuChoice graphicTrack = new MenuChoice("Computer Graphics and Vision", 4);
		MenuChoice sysTrack = new MenuChoice("Computing Systems", 5);
		MenuChoice gameTrack = new MenuChoice("Game Development and Design", 6);
		MenuChoice hciTrack = new MenuChoice("Human-Computer Interaction", 7);
		MenuChoice symTrack = new MenuChoice("Numeric and Symbolic Computation", 8);
		MenuChoice progTrack = new MenuChoice("Programming Languages", 9);
		MenuChoice seTrack = new MenuChoice("Software Engineering", 10);
		
		// Adding menu choices to list
		super.choices = new ArrayList<MenuChoice>();
		super.choices.add(dsTrack);
		super.choices.add(aiTrack);
		super.choices.add(netTrack);
		super.choices.add(archTrack);
		super.choices.add(graphicTrack);
		super.choices.add(sysTrack);
		super.choices.add(gameTrack);
		super.choices.add(hciTrack);
		super.choices.add(symTrack);
		super.choices.add(progTrack);
		super.choices.add(seTrack);
	}
	
	public Menu select(int i) {
		/*
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
				return saveMenu;s
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
		*/
		return null;
	}
	
	public Menu runMenu() {
		int[] tracks = new int[2];
		tracks[0] = -1;
		tracks[1] = -1;
		
		this.display();
		this.outputMessage();
		
		System.out.print("Select first track: ");
		tracks[0] = this.getInput();
		
		while(tracks[1] == -1 || tracks[1] == tracks[0]) {
			System.out.print("Select second track: ");
			tracks[1] = this.getInput();
			if(tracks[1] == tracks[0]) {
				System.out.println("Cannot select the same track twice");
			}
		}

		// Must cast to CSMajor
		HashMap<Double, ArrayList<ArrayList<Course>>> csTracks = ((CSMajor) csMajor).setCSTracks(tracks);
		
		// Get the key to the hashmap
		Double csTrackCredits = (Double) csTracks.keySet().toArray()[0];
		// Iterate through tracks and add to POS
		for(ArrayList<Course> track : csTracks.get(csTrackCredits)) {
			for(Course trackCourse : track) {
				posManager.addCourseAndPreReqsToFirstAvailableQuarter(trackCourse);
			}
		}
		
		posManager.display();
		
		// Go to the loadPOSMenu so the user can edit/save POS
		LoadPOSMenu loadPOSMenu = new LoadPOSMenu(menuManager, posManager);
		return loadPOSMenu;
	}
}
