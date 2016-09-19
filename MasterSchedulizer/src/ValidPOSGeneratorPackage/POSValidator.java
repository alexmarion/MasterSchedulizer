package ValidPOSGeneratorPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import POSPackage.Course;
import POSPackage.POS;
import POSPackage.Quarter;
import POSPackage.Year;

public class POSValidator {
	// check to see if a quarter is valid (valid number of credits + prereqs)
	public static boolean validateQuarter(POS plan, Quarter quarter)
	{
		if(quarter.getCredits() > 20)
		{
			System.out.println("More than 20 credits");
			return false;
		}
		/*
		if(quarter.getCredits() < 12)
		{
			System.out.println("Less than 12 credits");
			return false;
		}
		*/
		
		for(Course course : quarter.getCourseList())
		{
			if(!checkPrereqs(plan, course, quarter))
			{
				//System.out.println(course.getCourseName() + ": " + checkPrereqs(plan, course, quarter));
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean validateYear(POS plan, Year year)
	{
		for(Quarter quarter : year.getQuarterList())
		{
			if(!validateQuarter(plan, quarter))
				return false;
		}
		
		return true;
	}
	
	public static boolean validatePOS(POS plan)
	{
		for(Year year : plan.getYears())
		{
			if(!validateYear(plan, year))
				return false;
		}
		
		return true;
	}
	
	private static boolean prereqsNotTaken(List<Course> courseList) {
		for(Course course : courseList) {
			if(!course.getTermTaken().matches("0"))
				return false;
		}
		return true;
	}
	
	private static boolean checkPrereqs(POS planOfStudy, Course c, Quarter q) {
		boolean isValid = false;
		//get list list of arraylist of prereq
		List<ArrayList<Course>> outerList = c.getPreReqs();
		//if there is no prereq add coursess
		if (outerList.size() == 0) {
			return true;
		}
		//iterate through list of arraylist
		for (List<Course> innerList : outerList) {
			//iterate through arraylist
			int counter = 1;
			for (Course courseInPr : innerList) {
				//get all courses in POS and iterate through it
				for (Course courseInPOS : planOfStudy.getAllCourses()) {
					//only interest in checking course's (being added) prereq(s) is before the term being added to
					//check if courseInPr is before the quarterID of the course being added
					if(innerList.size() == 1 && prereqsNotTaken(innerList))
					{
						return false;
					}
					else
					{
						if (Integer.parseInt(courseInPr.getTermTaken()) < Integer.parseInt(q.getQuarterID()) && Integer.parseInt(courseInPr.getTermTaken()) != 0) {
							//courseInPr exists in POS (innerList), if true need to break loop
							if (courseInPr.compareTo(courseInPOS) == 1) {
								isValid = true;
								break;
							} else {
								isValid = false;
							}
						}
					}
				}
				if(isValid){
					break;
				}
				// if cannot find any prereqs in the inner list
				else if (!isValid && counter == innerList.size()){
					return false;
				}
				counter++;
			}
		}
		return true;
	}
	
}
