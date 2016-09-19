package IOPackage;

public class TerminalIO extends IO{
	private static final TerminalIO instance = new TerminalIO();

	protected TerminalIO() {
	}
	
	public static TerminalIO getInstance() {
		return instance;
	}
	
	public void displayString(String s) {
		System.out.print(s);
	}
		
	public void displayNewLn() {
		System.out.println();
	}
} 
