package MenuPackage;

import DriverPackage.POSManager;

public class ViewPOSMenu extends Menu {

	public ViewPOSMenu(MenuManager menuManager, POSManager posManager) {
		super(menuManager, posManager);
	}
	
	/**
	 * The ViewPOSMenu displays the plan of study and returns to the previous menu
	 */
	public Menu runMenu() {
		posManager.display();
		BackMenu back = new BackMenu();
		back.setPOSManager(posManager);
		return back;
	}
}
