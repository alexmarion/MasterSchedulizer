package MinorPackage;

import java.util.ArrayList;
import java.util.HashMap;

import POSPackage.Course;

public abstract class Minor {
	protected String minorTitle;
	protected int creditRequirements;
	protected HashMap<Integer, ArrayList<Course>> minorReqs;
	
	public Minor() {
	}

	/**
	 * @return the minorReqs
	 */
	public HashMap<Integer, ArrayList<Course>> getMinorReqs() {
		return minorReqs;
	}

	/**
	 * @param minorReqs the minorReqs to set
	 */
	public void setMinorReqs(HashMap<Integer, ArrayList<Course>> minorReqs) {
		this.minorReqs = minorReqs;
	}

	/**
	 * @return the minorTitle
	 */
	public String getMinorTitle() {
		return minorTitle;
	}

	/**
	 * @return the creditRequirements
	 */
	public int getCreditRequirements() {
		return creditRequirements;
	}
	
	public void display() {
	}
	
}
