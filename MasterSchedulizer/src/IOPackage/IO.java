package IOPackage;

public abstract class IO<T> {
	private static final IO INSTANCE = new TerminalIO();
	
	protected IO() {
	}
	
	public static IO getInstance() {
		return INSTANCE;
	}
	
	public void display(Object o) {
		// TODO Auto-generated method stub
		
	}
	
	public void displayNewLine() {
	}
	
	public static IO getIO(String outputType) {
		if(outputType.equalsIgnoreCase("terminal")) {
			return TerminalIO.getInstance();
		} else {
			// Return TerminaIO by default
			return TerminalIO.getInstance();
		}
	}
}
