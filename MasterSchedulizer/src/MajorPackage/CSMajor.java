package MajorPackage;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

import POSPackage.Course;
import ParserPackage.TMSMasterList;

/**
 * The CSMajor is a subclass of the MajorDecorator It contains an instance of a
 * major (for just a cs major, this instance is BaseMajor) CSMajor contains a
 * list of all requirements including trackRequirements The Constructor sets the
 * list of requirements by reading a CSV for each list of requirements The CSV
 * files are stored in a folder in the project directory
 * 
 * @author Mahmuda Liza
 *
 */

public class CSMajor extends MajorDecorator {
	private HashMap<Double, ArrayList<Course>> freeElecs;
	private HashMap<Double, ArrayList<ArrayList<Course>>> csTracks = new HashMap<Double, ArrayList<ArrayList<Course>>>();
	
	// { "BIO" : [BIO 101, BIO 102,...]
	// "CHEM" : [CHEM 101, CHEM 102,..]}
	private HashMap<String, ArrayList<Course>> coursesList = TMSMasterList.getTMS();

	public CSMajor(Major m) {
		super(m);
		this.populateCSReqs();
		this.populateCCIReqs();
		this.populateMathReqs();
		this.populateSciReqs();
		this.populateArthuReqs();
		this.populateUnivReqs();
		this.populateFreeElectives();
	}

	// This function excludes MATH 261, ENGR 231 and MATH 410. Sorry :(
	public void populateMathReqs() {
		this.majorReqs = new HashMap<Double, ArrayList<ArrayList<Course>>>();
		ArrayList<ArrayList<Course>> math = new ArrayList<ArrayList<Course>>();

		ArrayList<Course> c1 = new ArrayList<Course>();
		c1.add(TMSMasterList.findCourse("MATH", "121"));
		c1.add(TMSMasterList.findCourse("MATH", "122"));
		c1.add(TMSMasterList.findCourse("MATH", "123"));
		c1.add(TMSMasterList.findCourse("MATH", "201"));
		c1.add(TMSMasterList.findCourse("MATH", "221"));
		c1.add(TMSMasterList.findCourse("MATH", "311"));
		math.add(c1);

		this.majorReqs.put((double) 15, math);
	}

	public void populateCSReqs() {
		this.majorReqs = new HashMap<Double, ArrayList<ArrayList<Course>>>();
		ArrayList<ArrayList<Course>> cs = new ArrayList<ArrayList<Course>>();

		ArrayList<ArrayList<Course>> csMain_ = new ArrayList<ArrayList<Course>>();
		ArrayList<Course> csMain = new ArrayList<Course>();
		csMain.add(TMSMasterList.findCourse("CS", "164"));
		csMain.add(TMSMasterList.findCourse("CS", "171"));
		csMain.add(TMSMasterList.findCourse("CS", "172"));
		csMain.add(TMSMasterList.findCourse("CS", "260"));
		csMain.add(TMSMasterList.findCourse("CS", "265"));
		csMain.add(TMSMasterList.findCourse("CS", "270"));
		csMain.add(TMSMasterList.findCourse("CS", "275"));
		csMain.add(TMSMasterList.findCourse("CS", "281"));
		csMain.add(TMSMasterList.findCourse("CS", "283"));
		csMain.add(TMSMasterList.findCourse("CS", "350"));
		csMain.add(TMSMasterList.findCourse("CS", "451"));
		csMain_.add(csMain);
		this.majorReqs.put((double) 37, csMain_);

		// Computer Science electives: any CS course numbered 300 or higher
		ArrayList<ArrayList<Course>> csElec_ = new ArrayList<ArrayList<Course>>();
		ArrayList<Course> csElec = new ArrayList<Course>();
		for (String subjCode : coursesList.keySet()) {
			if (subjCode.equals("CS")) {
				ArrayList<Course> Temp = coursesList.get(subjCode);
				for (Course temp : Temp) {
					double num = Double.parseDouble(temp.getCourseNum());
					if (num >= 300) {
						csElec.add(temp);
					}
				}
			}
		}
		csElec_.add(csElec);
		this.majorReqs.put((double) 6, csMain_);

		// Moved this into setCSTrack which is called from CreatePOSMenu
		// ArrayList<ArrayList<Course>> csTrack_ = new
		// ArrayList<ArrayList<Course>>();
		// csTrack_.add(setCSTrack());
		// csTrack_.add(setCSTrack());
		// this.majorReqs.put((double) 18, csTrack_);
	}

	public HashMap<Double, ArrayList<ArrayList<Course>>> setCSTracks(int[] trackNums) {
		ArrayList<ArrayList<Course>> csTrack_ = new ArrayList<ArrayList<Course>>();
		csTrack_.add(setCSTrack(trackNums[0]));
		csTrack_.add(setCSTrack(trackNums[1]));
		this.csTracks.put((double) 18, csTrack_);
		this.majorReqs.put((double) 18, csTrack_);
		return csTracks;
	}

	/********** HELPER METHODS FOR SELECTING CS TRACKS ****************/

	/**
	 * Checks for valid input. Returns true if input is valid, false otherwise.
	 * 
	 * @param i
	 * @return
	 */
	public boolean validInput(int i, ArrayList<Course> choices) {
		if (i < 1 || i > choices.size()) {
			return false;
		} else {
			return true;
		}
	}

	private int getInput(ArrayList<Course> choices) {
		Scanner in = new Scanner(System.in);
		int i = 0;
		boolean validInput = false;
		while (!validInput) {
			try {
				System.out.print("Input course selection: ");
				i = in.nextInt();
				validInput = validInput(i, choices);
			} catch (InputMismatchException e) {
				System.out.println("Invalid input, input must be an integer" + "\n");
				in.next();
				continue;
			}
			if (!validInput(i, choices)) {
				System.out.println("Invalid input, input must be from 1 to " + choices.size() + "\n");
			}
		}
		return i;
	}

	public Course selectTrackElective(ArrayList<Course> possibleTrackElecs) {
		Course selection = null;
		for (int i = 0; i < possibleTrackElecs.size(); i++) {
			System.out.println(i + 1 + ") " + possibleTrackElecs.get(i).getCourseName());
		}
		selection = possibleTrackElecs.get(getInput(possibleTrackElecs) - 1);
		return selection;
	}

	private ArrayList<Course> setCSTrack(int trackNum) {
		// This is commented because menu class will handle that?
		// System.out.println("Please choose your track from the following : ");
		// System.out.println("Enter the number associated with the track you
		// choose");
		// System.out.println(
		// "1. Algorithms and Data Structures" + '\n' +
		// "2. Artificial Intelligence" + '\n' +
		// "3. Computer and Network Security" + '\n' +
		// "4. Computer Architecture" + '\n' +
		// "5. Computer Graphics and Vision" + '\n' +
		// "6. Computing Systems" + '\n' +
		// "7. Game Development and Design" + '\n' +
		// "8. Human-Computer Interaction" + '\n' +
		// "9. Numeric and Symbolic Computation" + '\n' +
		// "10. Programming Languages" + '\n' +
		// "11. Software Engineering"
		// );
		switch (trackNum) {
		case 1:	// Algorithms and Data Structures
			ArrayList<Course> dsTrack = new ArrayList<Course>();
			dsTrack.add(TMSMasterList.findCourse("CS", "440"));
			dsTrack.add(TMSMasterList.findCourse("CS", "457"));
			dsTrack.add(TMSMasterList.findCourse("CS", "458"));
			return dsTrack;

		case 2:	// Artificial Intelligence
			ArrayList<Course> aiTrack = new ArrayList<Course>();
			aiTrack.add(TMSMasterList.findCourse("CS", "380"));
			// Select two of the following:
			// CS 383, CS 385, CS 387, CS 481
			ArrayList<Course> aiTrackElectives = new ArrayList<Course>();
			aiTrackElectives.add(TMSMasterList.findCourse("CS", "383"));
			aiTrackElectives.add(TMSMasterList.findCourse("CS", "385"));
			aiTrackElectives.add(TMSMasterList.findCourse("CS", "387"));
			aiTrackElectives.add(TMSMasterList.findCourse("CS", "481"));

			System.out.println("<<Artificial Intelligence Track>>");
			System.out.println("Select two of the following courses");

			Course selection1 = selectTrackElective(aiTrackElectives);
			aiTrackElectives.remove(selection1);
			Course selection2 = selectTrackElective(aiTrackElectives);

			aiTrack.add(selection1);
			aiTrack.add(selection2);

			return aiTrack;

		case 3:	// Computer and Network Security
			ArrayList<Course> netTrack = new ArrayList<Course>();
			netTrack.add(TMSMasterList.findCourse("CS", "472"));
			netTrack.add(TMSMasterList.findCourse("CS", "475"));
			netTrack.add(TMSMasterList.findCourse("CS", "303"));
			
			return netTrack;
			
		case 4:	// Computer Architecture
			ArrayList<Course> archTrack = new ArrayList<Course>();
			archTrack.add(TMSMasterList.findCourse("CS", "352"));
			
			// Select two of the following:
			// CS 476, ECEC 356, ECEC 413
			ArrayList<Course> archTrackElectives = new ArrayList<Course>();
			archTrackElectives.add(TMSMasterList.findCourse("CS", "476"));
			archTrackElectives.add(TMSMasterList.findCourse("ECEC", "356"));
			archTrackElectives.add(TMSMasterList.findCourse("ECEC", "413"));

			System.out.println("<<Computer Architecture Track>>");
			System.out.println("Select two of the following courses");
			selection1 = selectTrackElective(archTrackElectives);
			archTrackElectives.remove(selection1);
			selection2 = selectTrackElective(archTrackElectives);

			archTrack.add(selection1);
			archTrack.add(selection2);

			return archTrack;

		case 5:	// Computer Graphics and Vision
			ArrayList<Course> graphicTrack = new ArrayList<Course>();
			graphicTrack.add(TMSMasterList.findCourse("CS", "430"));
			graphicTrack.add(TMSMasterList.findCourse("CS", "435"));
			
			// Select one of the following:
			// CS 431, CS 432
			ArrayList<Course> graphicTrackElectives = new ArrayList<Course>();
			graphicTrackElectives.add(TMSMasterList.findCourse("CS", "431"));
			graphicTrackElectives.add(TMSMasterList.findCourse("CS", "432"));

			System.out.println("<<Computer Graphics and Vision Track>>");
			System.out.println("Select one of the following courses");
			selection1 = selectTrackElective(graphicTrackElectives);
			graphicTrack.add(selection1);

			return graphicTrack;

		case 6:	// Computing Systems
			ArrayList<Course> sysTrack = new ArrayList<Course>();
			sysTrack.add(TMSMasterList.findCourse("CS", "361"));
			sysTrack.add(TMSMasterList.findCourse("CS", "370"));
			
			// Select one of the following:
			// CS 365, CS 461, CS 472
			ArrayList<Course> sysTrackElectives = new ArrayList<Course>();
			sysTrackElectives.add(TMSMasterList.findCourse("CS", "365"));
			sysTrackElectives.add(TMSMasterList.findCourse("CS", "461"));
			sysTrackElectives.add(TMSMasterList.findCourse("CS", "472"));

			System.out.println("<<Computing Systems Track>>");
			System.out.println("Select one of the following courses");
			selection1 = selectTrackElective(sysTrackElectives);
			sysTrack.add(selection1);

			return sysTrack;

		case 7:	// Game Development and Design
			ArrayList<Course> gameTrack = new ArrayList<Course>();
			
			// Select one of the following:
			// CS 345, GMAP 345
			ArrayList<Course> gameTrackElectives1 = new ArrayList<Course>();
			gameTrackElectives1.add(TMSMasterList.findCourse("CS", "345"));
			gameTrackElectives1.add(TMSMasterList.findCourse("GMAP", "345"));

			System.out.println("<<Game Development and Design Track>>");
			System.out.println("Select one of the following courses");

			selection1 = selectTrackElective(gameTrackElectives1);
			gameTrack.add(selection1);

			// Select two of the following:
			// CS 347, CS 348, CS 387, CS 445, GMAP 377, GMAP 378
			ArrayList<Course> gameTrackElectives2 = new ArrayList<Course>();
			gameTrackElectives2.add(TMSMasterList.findCourse("CS", "347"));
			gameTrackElectives2.add(TMSMasterList.findCourse("CS", "348"));
			gameTrackElectives2.add(TMSMasterList.findCourse("CS", "387"));
			gameTrackElectives2.add(TMSMasterList.findCourse("CS", "445"));
			gameTrackElectives2.add(TMSMasterList.findCourse("GMAP", "377"));
			gameTrackElectives2.add(TMSMasterList.findCourse("GMAP", "378"));
			
			System.out.println("Select two of the following courses");
			selection1 =  selectTrackElective(gameTrackElectives2);
			gameTrackElectives2.remove(selection1);
			selection2 =  selectTrackElective(gameTrackElectives2);
			
			gameTrack.add(selection1);
			gameTrack.add(selection2);

			return gameTrack;

		case 8: // Human-Computer Interaction
			ArrayList<Course> hciTrack = new ArrayList<Course>();
			hciTrack.add(TMSMasterList.findCourse("CS", "338"));
			
			// Select one of the following:
			// INFO 310, CS 337, PSY 337
			ArrayList<Course> hciTrackElectives1 = new ArrayList<Course>();
			hciTrackElectives1.add(TMSMasterList.findCourse("INFO", "310"));
			hciTrackElectives1.add(TMSMasterList.findCourse("CS", "337"));
			hciTrackElectives1.add(TMSMasterList.findCourse("PSY", "337"));

			System.out.println("<<Human-Computer Interaction Track>>");
			System.out.println("Select one of the following courses");

			selection1 = selectTrackElective(hciTrackElectives1);
			hciTrack.add(selection1);

			// Select one of the following:
			// CS 345, GMAP 345, CS 432
			ArrayList<Course> hciTrackElectives2 = new ArrayList<Course>();
			hciTrackElectives2.add(TMSMasterList.findCourse("CS", "345"));
			hciTrackElectives2.add(TMSMasterList.findCourse("GMAP", "345"));
			hciTrackElectives2.add(TMSMasterList.findCourse("CS", "432"));

			System.out.println("Select one of the following courses");

			selection1 = selectTrackElective(hciTrackElectives2);
			hciTrack.add(selection1);

			return hciTrack;
			
		case 9:	// Numeric and Symbolic Computation
			ArrayList<Course> symTrack = new ArrayList<Course>();
			symTrack.add(TMSMasterList.findCourse("CS", "300"));
			symTrack.add(TMSMasterList.findCourse("MATH", "300"));

			// Select one of the following:
			// CS 303, MATH 301, MATH 305
			ArrayList<Course> symTrackElectives = new ArrayList<Course>();
			symTrackElectives.add(TMSMasterList.findCourse("CS", "303"));
			symTrackElectives.add(TMSMasterList.findCourse("MATH", "301"));
			symTrackElectives.add(TMSMasterList.findCourse("MATH", "305"));

			System.out.println("<<Numeric and Symbolic Computation Track>>");
			System.out.println("Select one of the following courses");

			selection1 = selectTrackElective(symTrackElectives);
			symTrack.add(selection1);

			return symTrack;
			
		case 10:	// Programming Languages
			ArrayList<Course> progTrack = new ArrayList<Course>();
			progTrack.add(TMSMasterList.findCourse("CS", "440"));
			progTrack.add(TMSMasterList.findCourse("CS", "441"));
			progTrack.add(TMSMasterList.findCourse("CS", "442"));
			
			return progTrack;
			
		case 11:	// Software Engineering
			ArrayList<Course> seTrack = new ArrayList<Course>();
			seTrack.add(TMSMasterList.findCourse("SE", "311"));
			seTrack.add(TMSMasterList.findCourse("SE", "320"));
			seTrack.add(TMSMasterList.findCourse("SE", "410"));
			return seTrack;
		}
		return null;
	}

	private void populateSciReqs() {
		this.sciReqs = new HashMap<Double, ArrayList<ArrayList<Course>>>();
		ArrayList<ArrayList<Course>> science = new ArrayList<ArrayList<Course>>();

		/*
		 * BIO 122 124 126 CHEM 101 102 103 PHYS 101 102 201
		 */

		ArrayList<Course> firstCol = new ArrayList<Course>();
		firstCol.add(TMSMasterList.findCourse("BIO", "122"));
		firstCol.add(TMSMasterList.findCourse("CHEM", "101"));
		firstCol.add(TMSMasterList.findCourse("PHYS", "101"));
		science.add(firstCol);

		ArrayList<Course> secondCol = new ArrayList<Course>();
		secondCol.add(TMSMasterList.findCourse("BIO", "124"));
		secondCol.add(TMSMasterList.findCourse("CHEM", "102"));
		secondCol.add(TMSMasterList.findCourse("PHYS", "102"));
		science.add(secondCol);

		ArrayList<Course> thirdCol = new ArrayList<Course>();
		thirdCol.add(TMSMasterList.findCourse("BIO", "126"));
		thirdCol.add(TMSMasterList.findCourse("CHEM", "103"));
		thirdCol.add(TMSMasterList.findCourse("PHYS", "201"));
		science.add(thirdCol);

		ArrayList<Course> freeSci = new ArrayList<Course>();

		for (String subjCode : coursesList.keySet()) {
			if (subjCode.equals("BIO")) {
				ArrayList<Course> freeBio = coursesList.get(subjCode);
				for (Course temp : freeBio) {
					// BIO 161 is an exception
					// if course is not BIO 161
					if (temp.compareTo(TMSMasterList.findCourse("BIO", "161")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("BIO", "162")) == 0)
						freeSci.add(temp);
				}
			}
		}

		science.add(freeSci);

		this.sciReqs.put((double) 25, science);
	}

	private void populateCCIReqs() {
		this.majorReqs = new HashMap<Double, ArrayList<ArrayList<Course>>>();
		ArrayList<ArrayList<Course>> cci = new ArrayList<ArrayList<Course>>();

		ArrayList<Course> c1 = new ArrayList<Course>();
		c1.add(TMSMasterList.findCourse("CI", "101"));
		c1.add(TMSMasterList.findCourse("CI", "102"));
		c1.add(TMSMasterList.findCourse("CI", "103"));
		c1.add(TMSMasterList.findCourse("CI", "491"));
		c1.add(TMSMasterList.findCourse("CI", "492"));
		c1.add(TMSMasterList.findCourse("CI", "493"));
		cci.add(c1);

		this.majorReqs.put((double) 15, cci);
	}

	private void populateArthuReqs() {
		this.arthuReqs = new HashMap<Double, ArrayList<ArrayList<Course>>>();
		this.arthuReqs.putAll(engReqs);

		ArrayList<ArrayList<Course>> arthuMain = new ArrayList<ArrayList<Course>>();

		ArrayList<Course> c1 = new ArrayList<Course>();
		c1.add(TMSMasterList.findCourse("COM", "230"));
		ArrayList<Course> c2 = new ArrayList<Course>();
		c2.add(TMSMasterList.findCourse("PHIL", "311"));
		arthuMain.add(c1);
		arthuMain.add(c2);

		/*
		 * Writing & Communications electives: any WRIT, COM, ENGL courses
		 * officially certified as Writing Intensive (WI)
		 */
		ArrayList<ArrayList<Course>> _writComElec = new ArrayList<ArrayList<Course>>();
		ArrayList<Course> writComElec = new ArrayList<Course>();
		for (String subjCode : coursesList.keySet()) {
			ArrayList<Course> Temp = new ArrayList();
			if (subjCode.equals("WRIT")) {
				Temp = coursesList.get(subjCode);
				for (Course temp : Temp) {
					if (temp.compareTo(TMSMasterList.findCourse("WRIT", "210")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("WRIT", "220")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("WRIT", "225")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("WRIT", "301")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("WRIT", "304")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("WRIT", "312")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("WRIT", "400")) == 0) {
						writComElec.add(temp);
					}
				}
			} else if (subjCode.equals("COM")) {
				Temp = coursesList.get(subjCode);
				for (Course temp : Temp) {
					if (temp.compareTo(TMSMasterList.findCourse("COM", "260")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("COM", "270")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("COM", "282")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("COM", "300")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("COM", "310")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("COM", "317")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("COM", "320")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("COM", "350")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("COM", "370")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("COM", "375")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("COM", "390")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("COM", "410")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("COM", "617")) == 0) {
						writComElec.add(temp);
					}
				}
			} else if (subjCode.equals("ENGL")) {
				Temp = coursesList.get(subjCode);
				for (Course temp : Temp) {
					if (temp.compareTo(TMSMasterList.findCourse("ENGL", "200")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("ENGL", "202")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("ENGL", "203")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("ENGL", "205")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("ENGL", "206")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("ENGL", "207")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("ENGL", "211")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("ENGL", "215")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("ENGL", "216")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("ENGL", "300")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("ENGL", "305")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("ENGL", "308")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("ENGL", "310")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("ENGL", "315")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("ENGL", "320")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("ENGL", "340")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("ENGL", "355")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("ENGL", "360")) == 0
							|| temp.compareTo(TMSMasterList.findCourse("ENGL", "395")) == 0) {
						writComElec.add(temp);
					}
				}
			}
		}

		/*
		 * any ACCT, BLAW, BUSN, ECON, FIN, HRMT, INTB, MGMT, MKTG, OPM, OPR,
		 * ORGB, POM, STAT, TAX
		 */
		ArrayList<ArrayList<Course>> _busiElec = new ArrayList<ArrayList<Course>>();
		ArrayList<Course> busiElec = new ArrayList<Course>();

		for (String subjCode : coursesList.keySet())

		{
			if (subjCode.equals("ACCT") || subjCode.equals("BLAW") || subjCode.equals("BUSN") || subjCode.equals("ECON")
					|| subjCode.equals("FIN") || subjCode.equals("HRMT") || subjCode.equals("INTB")
					|| subjCode.equals("MGMT") || subjCode.equals("MKTG") || subjCode.equals("OPM")
					|| subjCode.equals("OPR") || subjCode.equals("ORGB") || subjCode.equals("POM")
					|| subjCode.equals("STAT") || subjCode.equals("TAX")) {
				ArrayList<Course> Temp = coursesList.get(subjCode);
				for (Course temp : Temp)
					busiElec.add(temp);
			}
		}

		/*
		 * any AFAS, ANTH, HIST, IAS, JUDA, PSCI, PSY (except 330, 332, 337,
		 * 364, 365), SOC (except 364, 365), WGST
		 */
		ArrayList<ArrayList<Course>> _socialElec = new ArrayList<ArrayList<Course>>();
		ArrayList<Course> socialElec = new ArrayList<Course>();
		for (String subjCode : coursesList.keySet())

		{
			if (subjCode.equals("AFAS") || subjCode.equals("ANTH") || subjCode.equals("HIST") || subjCode.equals("IAS")
					|| subjCode.equals("JUDA") || subjCode.equals("PSCI") || subjCode.equals("WGST")
					|| subjCode.equals("PSY") || subjCode.equals("SOC")) {
				ArrayList<Course> Temp = coursesList.get(subjCode);

				if (subjCode.equals("PSY")) {
					for (Course temp : Temp) {
						if (temp.compareTo(TMSMasterList.findCourse("PSY", "330")) == 0
								|| temp.compareTo(TMSMasterList.findCourse("PSY", "332")) == 0
								|| temp.compareTo(TMSMasterList.findCourse("PSY", "337")) == 0
								|| temp.compareTo(TMSMasterList.findCourse("PSY", "364")) == 0
								|| temp.compareTo(TMSMasterList.findCourse("PSY", "365")) == 0) {
							socialElec.add(temp);
						}
					}
				}

				if (subjCode.equals("SOC")) {
					for (Course temp : Temp) {
						if (temp.compareTo(TMSMasterList.findCourse("SOC", "364")) == 0
								|| temp.compareTo(TMSMasterList.findCourse("SOC", "365")) == 0) {
							socialElec.add(temp);
						}
					}
				} else
					for (Course temp : Temp)
						socialElec.add(temp);
			}
		}

		/*
		 * any ARTH, COM, DANC, EDEX, EDUC, ENGL (except 101, 102, 103, 105),
		 * ESTM, FASH, FMVD, INTR, LING, MUSC, PHIL, PHTO, THTR, VSCM, VSST,
		 * WRIT, and Foreign Language courses as defined by the College of Arts
		 * and Sciences
		 */
		ArrayList<ArrayList<Course>> _miscElec = new ArrayList<ArrayList<Course>>();
		ArrayList<Course> miscElec = new ArrayList<Course>();
		for (String subjCode : coursesList.keySet()) {
			if (subjCode.equals("ARTH") || subjCode.equals("COM") || subjCode.equals("DANC") || subjCode.equals("EDEX")
					|| subjCode.equals("EDUC") || subjCode.equals("ENGL") || subjCode.equals("ESTM")
					|| subjCode.equals("FASH") || subjCode.equals("FMVD") || subjCode.equals("INTR")
					|| subjCode.equals("LING") || subjCode.equals("MUSC") || subjCode.equals("PHIL")
					|| subjCode.equals("PHTO") || subjCode.equals("THTR") || subjCode.equals("VSCM")
					|| subjCode.equals("VSST") || subjCode.equals("WRIT")) {
				ArrayList<Course> Temp = coursesList.get(subjCode);
				if (subjCode.equals("ENGL")) {
					for (Course temp : Temp) {
						if (temp.compareTo(TMSMasterList.findCourse("ENGL", "101")) == 0
								|| temp.compareTo(TMSMasterList.findCourse("ENGL", "102")) == 0
								|| temp.compareTo(TMSMasterList.findCourse("ENGL", "103")) == 0
								|| temp.compareTo(TMSMasterList.findCourse("ENGL", "105")) == 0) {
							miscElec.add(temp);
						}
					}
				} else
					for (Course temp : Temp)
						miscElec.add(temp);
			}
		}

		this.arthuReqs.put((double) 6, arthuMain);

		_writComElec.add(writComElec);
		this.arthuReqs.put((double) 6, _writComElec);

		_busiElec.add(busiElec);
		this.arthuReqs.put((double) 4, _busiElec);

		_socialElec.add(socialElec);
		this.arthuReqs.put((double) 3, _socialElec);

		_miscElec.add(miscElec);
		this.arthuReqs.put((double) 17, _miscElec);

	}

	private void populateUnivReqs() {
		this.univReqs = new HashMap<Double, ArrayList<ArrayList<Course>>>();
		ArrayList<ArrayList<Course>> univ = new ArrayList<ArrayList<Course>>();

		ArrayList<Course> c1 = new ArrayList<Course>();
		c1.add(TMSMasterList.findCourse("UNIV", "101"));
		c1.add(TMSMasterList.findCourse("CI", "120")); // this is or with univ
														// ci101

		ArrayList<Course> c2 = new ArrayList<Course>();
		c2.add(TMSMasterList.findCourse("CIVC", "101"));

		ArrayList<Course> c3 = new ArrayList<Course>();
		c3.add(TMSMasterList.findCourse("COOP", "101"));

		univ.add(c1);
		univ.add(c2);
		univ.add(c3);

		this.univReqs.put((double) 3, univ);
	}

	private void populateFreeElectives() {
		this.freeElecs = new HashMap<Double, ArrayList<Course>>();
		ArrayList<Course> freeElec = new ArrayList<Course>();
		for (String subjCode : coursesList.keySet()) {
			ArrayList<Course> Temp = coursesList.get(subjCode);
			for (Course temp : Temp) {
				freeElec.add(temp);
			}
		}
		this.freeElecs.put((double) 15.5, freeElec);
	}
}
