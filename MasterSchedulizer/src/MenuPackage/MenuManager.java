package MenuPackage;

import java.util.ArrayList;

import DriverPackage.POSManager;
import POSPackage.POS;

public class MenuManager {
	private ArrayList<Menu> menus;
	private ArrayList<Menu> previousMenus;
	private Menu currentMenu;
	private Menu nextMenu;
	private POSManager posManager;
	
	
	/**
	 * Creates a MenuManager with desired output
	 * The MenuManager is responsible for running the Menus and storing an array of them.
	 * @param outputType
	 */
	public MenuManager() {
		this.menus = new ArrayList<Menu>();
		this.previousMenus = new ArrayList<Menu>();
		
		this.menus.add(new StartMenu(this));
		this.currentMenu = menus.get(0);
		this.nextMenu = null;
	}
	
	public void start() {
		this.printWelcomeScreen();
		this.runMenu(this.select(0));
	}
	
	public Menu select(int i) {
		return menus.get(i);
	}
	
	public void runMenu(Menu menu) {
		boolean exit = false;
		int counter = 0;
		currentMenu = menu;
		menus.add(currentMenu);
		
		// Run Menus until exit flag
		while(!exit) {
			// Keep track of the POS and pass it back and forth between menus
			posManager = currentMenu.getPOSManager();
			nextMenu = currentMenu.runMenu();
			
			// Test for null
			if(nextMenu == null) {
				nextMenu = new QuitMenu();
				nextMenu.runMenu();
				exit = true;
			} else if(nextMenu instanceof QuitMenu) {
					nextMenu.runMenu();
					exit = true;
			} else if(nextMenu instanceof BackMenu) {
				// If the previous number of steps is more than the available menus, cannot go back
				if(previousMenus.size() - ((BackMenu)nextMenu).getNumSteps() < 0) {
					System.out.println("Cannot go back any further!");
				} else {
					nextMenu.runMenu();
					// Return to the previous menu using the number of steps
					counter -= ((BackMenu)nextMenu).getNumSteps();
					currentMenu = previousMenus.get(counter);
					currentMenu.setPOSManager(posManager);
				}
			} else {
				// If not null, quit, or back, move to the next menu
				previousMenus.add(currentMenu);
				menus.add(nextMenu);
				currentMenu = nextMenu;
				counter++;
			}
		}
	}
	
    public void printWelcomeScreen(){
        System.out.println(".-----------------------------------------------------------------.");
        System.out.println("/  .-.                                                        .-.  \\");
        System.out.println("|  /   \\                                                       /   \\  |");
        System.out.println("| |\\_.  |                    WELCOME TO                       |    /| |");
        System.out.println("|\\|  | /|                MASTER SCHEDULIZER                   |\\  | |/|");
        System.out.println("| `---' |                                                     | `---' |");
        System.out.println("|       |                                                     |       |");
        System.out.println("|       |-----------------------------------------------------|       |");
        System.out.println("\\       |                                                     |       /");
        System.out.println(" \\     /                                                       \\     /");
        System.out.println("  `---'                                                         `---'");
    }
}
