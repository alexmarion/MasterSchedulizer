package MinorPackage;

public abstract class MinorDecorator extends Minor{
	protected Minor minorInstance;
	
	public MinorDecorator(Minor m) {
		super();
		this.minorInstance = m;
	}
}
