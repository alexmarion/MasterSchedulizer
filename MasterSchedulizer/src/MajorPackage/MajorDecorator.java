package MajorPackage;

/**
 * The MajorDecorator Class is an abstract class which is inherited by all specific Majors
 * Each MajorDecorator contains an instance of a Major which must be passed in the constructor
 * @author Alex Marions
 *
 */
public abstract class MajorDecorator extends Major {
	protected Major majorInstance;
	public MajorDecorator(Major m) {
		super();
		this.majorInstance = m;
	}
}
