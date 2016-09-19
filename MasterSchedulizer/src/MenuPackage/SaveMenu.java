package MenuPackage;

import DriverPackage.POSManager;

public class SaveMenu extends Menu {

	public SaveMenu(MenuManager menuManager, POSManager posManager) {
		super(menuManager, posManager);
	}
	
	public Menu runMenu() {
		posManager.savePOS();
		BackMenu back = new BackMenu();
		back.setPOSManager(posManager);
		return back;
	}
}
