package MenuPackage;

public class BackMenu extends Menu {
	private int numSteps;
	
	/**
	 * Returns the user to the previous menu
	 */
	public BackMenu() {
		super();
		numSteps = 1;
		super.outputMessage = "Returning to the previous menu... \n";
	}
	
	/**
	 * Returns the user to the nth previous menu
	 * @param numSteps
	 */
	public BackMenu(int numSteps) {
		super();
		this.numSteps = numSteps;
		super.outputMessage = "Returning to the previous menu... \n";
	}
	
	public int getNumSteps() {
		return numSteps;
	}

	// Runs the BackMenu action which returns itself
	public Menu runMenu() {
		this.outputMessage();
		return this;
	}
}
