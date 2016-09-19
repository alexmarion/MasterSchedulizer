package MenuPackage;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import DriverPackage.POSManager;

public abstract class Menu {
	protected String outputMessage = "Enter menu choice: ";
	protected ArrayList<MenuChoice> choices;
	protected MenuManager menuManager;
	protected POSManager posManager;
	
	public Menu() {
		this.choices = new ArrayList<MenuChoice>();
		posManager = new POSManager(null, 0);
	}
	
	public Menu(MenuManager manager, POSManager posManager) {
		this.choices = new ArrayList<MenuChoice>();
		this.menuManager = manager;
		this.posManager = posManager;
	}
	
	/**
	 * Overridden in other Menu classes.
	 * Allows users to select an option from the menu. 
	 * @param i
	 * @return
	 */
	public Menu select(int i) {
		return null;
	}
	
	public POSManager getPOSManager() {
		return posManager;
	}
	
	public void setPOSManager(POSManager posManager) {
		this.posManager = posManager;
	}
	
	/**
	 * Get input from the user to make a menu selection
	 * @return
	 */
	public int getInput() {
		Scanner in = new Scanner(System.in);
		int i = 0;
		boolean validInput = false;
		while(!validInput) {
			try {
				i = in.nextInt();
				validInput = validInput(i);
			} catch(InputMismatchException e) {
				System.out.println("Invalid input, input must be an integer" + "\n");
				in.next();
				continue;
			}
			if(!validInput(i)) {
				System.out.println("Invalid input, input must be from 1 to " + choices.size() + "\n");
			}
		}
		return i;
	}
	
	/**
	 * Checks for valid input.
	 * Returns true if input is valid, false otherwise.
	 * @param i
	 * @return
	 */
	public boolean validInput(int i) {
		if( i < 1 || i > choices.size()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Displays output message for Menu
	 */
	public void outputMessage() {
		System.out.print(outputMessage);
	}
	
	/**
	 * Displays menu choices
	 */
	public void display() {
		genMenu();
	}
	
	/**
	 * Runs the actions for the Menu
	 * By default displays the menu and allows for selection. 
	 * @return
	 */
	public Menu runMenu() {
		this.display();
		this.outputMessage();
		int i = this.getInput();
		return this.select(i);
	}
	
	//==========================================================
	//================= Display helper methods =================
	//==========================================================
	private void genMenu() {
    	// Print menu top
    	System.out.println("+-------------------------------------------------------------------+ ");
        System.out.println("| = : = : = : = : = : = : = : = : = : = : = : = : = : = : = : = : = |");
        System.out.println("|{>/-------------------------------------------------------------\\<}|");
        
        // Define menu attributes
        // Note: Do not delete spaces
        int menuInsideSpaceSize = "                                                             ".length();
        String menuBorderLeft1 = "|: |";
        String menuBorderLeft2 = "| :|";
        
        String menuBorderRight1 = "| :|";
        String menuBorderRight2 = "|: |";
        
        // Keep track of a 'bit' which prints out borders in alternating order
        String[] leftMenuBorder = {menuBorderLeft1, menuBorderLeft2};
        String[] rightMenuBorder = {menuBorderRight1, menuBorderRight2};
        int bit = 0;
        
        // Print two rows before start of options
        System.out.println(leftMenuBorder[bit] + justify("", menuInsideSpaceSize, " ") + rightMenuBorder[bit]);
        bit = bitFlip(bit);
        System.out.println(leftMenuBorder[bit] + justify("", menuInsideSpaceSize, " ") + rightMenuBorder[bit]);
        bit = bitFlip(bit);
        System.out.println(leftMenuBorder[bit] + justify("Please make a selection:", menuInsideSpaceSize, " ") + rightMenuBorder[bit]);
        bit = bitFlip(bit);
        
        String menuNum;
        for(int i = 0; i < choices.size(); i++) {
        	menuNum = Integer.toString(choices.get(i).getIndex() + 1) + ") ";
        	
            System.out.println(leftMenuBorder[bit] 
            		+ justify(menuNum + choices.get(i).getChoice(), menuInsideSpaceSize, " ") 
            		+ rightMenuBorder[bit]);
            bit = bitFlip(bit);
        }
        
        // Print two rows after end of options
        System.out.println(leftMenuBorder[bit] + justify("", menuInsideSpaceSize, " ") + rightMenuBorder[bit]);
        bit = bitFlip(bit);
        System.out.println(leftMenuBorder[bit] + justify("", menuInsideSpaceSize, " ") + rightMenuBorder[bit]);
        bit = bitFlip(bit);
        
        
        // Print menu bottom
        System.out.println("|{>\\-------------------------------------------------------------/<}|");
        System.out.println("| = : = : = : = : = : = : = : = : = : = : = : = : = : = : = : = : = |");
        System.out.println("+-------------------------------------------------------------------+");
    }
    
    private String justify(String s, int size, String pad) {
    	int LEFT_SPACING_PROMPT = "                ".length();
    	int LEFT_SPACING_OPTION = "                    ".length();
    	
    	if(pad == null || pad.length() == 0 || s == null || size <= s.length()) {
    		System.out.println("Incorrect arguments to 'center()'");
    		return "";
    	}
    	
    	StringBuilder strBuilder = new StringBuilder();
    	
    	// Append half of the padding to the left of the String
    	//for(int i = 0; i < (size - s.length())/2; i++) {
    	if(s.equals("Please make a selection:")) {
        	for(int i = 0; i < LEFT_SPACING_PROMPT; i++) {
        		strBuilder.append(pad);
        	}
    	} else {
        	for(int i = 0; i < LEFT_SPACING_OPTION; i++) {
        		strBuilder.append(pad);
        	}
    	}

    	// Append the string in the center
    	strBuilder.append(s);
    	// Append the rest of the pad to the right
    	while(strBuilder.length() < size) {
    		strBuilder.append(pad);
    	}
    	return strBuilder.toString();
    }
    
    private int bitFlip(int x) {
    	if(x == 0) {
    		return 1;
    	} else {
    		return 0;
    	}
    }
}
