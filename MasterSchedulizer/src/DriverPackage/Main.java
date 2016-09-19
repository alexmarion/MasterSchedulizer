package DriverPackage;

import java.awt.Menu;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import MenuPackage.MenuManager;
import POSPackage.Course;
import POSPackage.GeneratePOS;
import POSPackage.POS;
import POSPackage.Quarter;
import ParserPackage.*;

public class Main {
	
	public static void main(String[] args) {
		HashMap<String, ArrayList<Course>> MASTERLIST = TMSMasterList.getTMS();
		
		POSManager posManager = new POSManager("alex", 12345678);
		posManager.loadPOS("sample.csv");
		
//		posManager.display();
//	System.out.println(posManager.addCourseToFirstAvailableQuarter(TMSMasterList.findCourse("CS", "361")));
//		posManager.display();
		
		MenuManager menuManager = new MenuManager();
		menuManager.start();
	}
}
