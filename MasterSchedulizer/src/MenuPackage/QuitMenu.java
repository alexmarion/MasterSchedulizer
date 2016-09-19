package MenuPackage;

public class QuitMenu extends Menu {
	/**
	 * Creates a new QuitMenu.
	 * When QuitMenu is returned the program terminates. 
	 */
	public QuitMenu() {
		super();
		super.outputMessage = "Goodbye!\nExiting Program";
	}
	
	// Automatically return null because there are no menu choices
	public Menu runMenu() {
		this.outputMessage();
		return null;
	}
}
