package MenuPackage;

import DriverPackage.POSManager;

public class PrintSamplePOSMenu extends Menu {

	public PrintSamplePOSMenu() {
		// TODO Auto-generated constructor stub
	}
	
	public Menu runMenu() {
		POSManager posManager = new POSManager("Sample", 00000000);
		posManager.loadSample();
		posManager.display();
		BackMenu back = new BackMenu();
		return back;
	}

}
