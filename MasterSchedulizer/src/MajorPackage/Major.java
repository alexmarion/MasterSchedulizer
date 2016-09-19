package MajorPackage;

import java.util.ArrayList;
import java.util.HashMap;
import POSPackage.Course;

/**
 * The Major Class contains lists of requirements
 * This class is abstract and contains the basic lists of requirements which all majors contain
 * @author Alex Marion
 *
 */
public abstract class Major {
	protected String majorTitle;
	protected int creditReq;
	protected HashMap<Double, ArrayList<ArrayList<Course>>> majorReqs;
	protected HashMap<Double, ArrayList<ArrayList<Course>>> mathReqs;
	protected HashMap<Double, ArrayList<ArrayList<Course>>> sciReqs;
	protected HashMap<Double, ArrayList<ArrayList<Course>>> arthuReqs;
	protected HashMap<Double, ArrayList<ArrayList<Course>>> engReqs;
	protected HashMap<Double, ArrayList<ArrayList<Course>>> univReqs;
	protected HashMap<Double, ArrayList<ArrayList<Course>>> genedReqs;
	
	public Major(){
		
		majorTitle = "";
		creditReq = 0;
		majorReqs = new HashMap<Double, ArrayList<ArrayList<Course>>>();
		mathReqs = new HashMap<Double, ArrayList<ArrayList<Course>>>();
		sciReqs = new HashMap<Double, ArrayList<ArrayList<Course>>>();
		arthuReqs = new HashMap<Double, ArrayList<ArrayList<Course>>>();
		engReqs = new HashMap<Double, ArrayList<ArrayList<Course>>>();
		univReqs = new HashMap<Double, ArrayList<ArrayList<Course>>>();
		genedReqs = new HashMap<Double, ArrayList<ArrayList<Course>>>();
		
	}
	
	public Major(String majorTitle_ ,int creditReq_, HashMap<Double, ArrayList<ArrayList<Course>>> majorReqs_,
			HashMap<Double, ArrayList<ArrayList<Course>>> mathReqs_, HashMap<Double, ArrayList<ArrayList<Course>>> sciReqs_,
			HashMap<Double, ArrayList<ArrayList<Course>>> arthuReqs_, HashMap<Double, ArrayList<ArrayList<Course>>> engReqs_, 
			HashMap<Double, ArrayList<ArrayList<Course>>> univReqs_, HashMap<Double, ArrayList<ArrayList<Course>>> genedReqs_) {
		
		majorTitle = majorTitle_;
		creditReq = creditReq_;
		majorReqs = majorReqs_;
		mathReqs = mathReqs_;
		sciReqs = sciReqs_;
		arthuReqs = arthuReqs_;
		engReqs = engReqs_;
		univReqs = univReqs_;
		genedReqs = genedReqs_;
		
	}

	/**
	 * @return the majorTitle
	 */
	public String getMajorTitle() {
		return majorTitle;
	}

	/**
	 * @return the creditReq
	 */
	public int getCreditReq() {
		return creditReq;
	}

	/**
	 * @return the majorReqs
	 */
	public HashMap<Double, ArrayList<ArrayList<Course>>> getMajorReqs() {
		return majorReqs;
	}

	/**
	 * @param majorReqs the majorReqs to set
	 */
	public void setMajorReqs(HashMap<Double, ArrayList<ArrayList<Course>>> majorReqs) {
		this.majorReqs = majorReqs;
	}

	/**
	 * @return the mathReqs
	 */
	public HashMap<Double, ArrayList<ArrayList<Course>>> getMathReqs() {
		return mathReqs;
	}

	/**
	 * @param mathReqs the mathReqs to set
	 */
	public void setMathReqs(HashMap<Double, ArrayList<ArrayList<Course>>> mathReqs) {
		this.mathReqs = mathReqs;
	}

	/**
	 * @return the sciReqs
	 */
	public HashMap<Double, ArrayList<ArrayList<Course>>> getSciReqs() {
		return sciReqs;
	}

	/**
	 * @param sciReqs the sciReqs to set
	 */
	public void setSciReqs(HashMap<Double, ArrayList<ArrayList<Course>>> sciReqs) {
		this.sciReqs = sciReqs;
	}

	/**
	 * @return the arthuReqs
	 */
	public HashMap<Double, ArrayList<ArrayList<Course>>> getArthuReqs() {
		return arthuReqs;
	}

	/**
	 * @param arthuReqs the arthuReqs to set
	 */
	public void setArthuReqs(HashMap<Double, ArrayList<ArrayList<Course>>> arthuReqs) {
		this.arthuReqs = arthuReqs;
	}

	/**
	 * @return the engReqs
	 */
	public HashMap<Double, ArrayList<ArrayList<Course>>> getEngReqs() {
		return engReqs;
	}

	/**
	 * @param engReqs the engReqs to set
	 */
	public void setEngReqs(HashMap<Double, ArrayList<ArrayList<Course>>> engReqs) {
		this.engReqs = engReqs;
	}

	/**
	 * @return the univReqs
	 */
	public HashMap<Double, ArrayList<ArrayList<Course>>> getUnivReqs() {
		return univReqs;
	}

	/**
	 * @param univReqs the univReqs to set
	 */
	public void setUnivReqs(HashMap<Double, ArrayList<ArrayList<Course>>> univReqs) {
		this.univReqs = univReqs;
	}

	/**
	 * @return the genedReqs
	 */
	public HashMap<Double, ArrayList<ArrayList<Course>>> getGenedReqs() {
		return genedReqs;
	}

	/**
	 * @param genedReqs the genedReqs to set
	 */
	public void setGenedReqs(HashMap<Double, ArrayList<ArrayList<Course>>> genedReqs) {
		this.genedReqs = genedReqs;
	}
	
	public HashMap<Double, ArrayList<ArrayList<Course>>> getReqs() {
		HashMap<Double, ArrayList<ArrayList<Course>>> reqs = new HashMap<Double, ArrayList<ArrayList<Course>>>();
		reqs.putAll(majorReqs);
		reqs.putAll(mathReqs);
		reqs.putAll(sciReqs);
		reqs.putAll(arthuReqs);
		reqs.putAll(engReqs);		
		reqs.putAll(univReqs);
		reqs.putAll(genedReqs);
		return reqs;
	}
	
	public void display() {
		System.out.println(this.getReqs().values());
		
	}
}
