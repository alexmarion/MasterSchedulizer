package TestingPackage;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import POSPackage.Course;
import POSPackage.Quarter;
import POSPackage.POS;
import POSPackage.Year;

public class QuarterTest {

	@Test
	public final void testCompareTo() {
		Quarter test = new Quarter("201515");
		Course cs283 = new Course("cs", "283", "Title", 3.00);
		Course cs275 = new Course("cs", "275", "Title", 3.00);
		Course cs260 = new Course("cs", "260", "Title", 3.00);
	    
		ArrayList<Course> courseList = new ArrayList<Course>();
	    courseList.add(cs275);
	    courseList.add(cs283);
	    courseList.add(cs260);
	    
	    
	    ArrayList<Course> courseList2 = new ArrayList<Course>();
	    courseList2.add(cs283);
	    courseList2.add(cs260);
	    courseList2.add(cs275);
	    System.out.println("Courses added to courselist" + courseList);
	    System.out.println("Courses added to courselist2" + courseList2);
	    assertTrue(courseList.size() == courseList2.size());
		test.addCourse(cs283);
		test.addCourse(cs275);
	    test.setCourseList(courseList);
		
	}

	@Test
	public final void testContainsCourse() {
		Quarter test = new Quarter("201515");
		Course cs283 = new Course("cs", "283", "Title", 3.00);
		Course cs275 = new Course("cs", "275", "Title", 3.00);
		Course cs260 = new Course("cs", "260", "Title", 3.00);
	    ArrayList<Course> courseList = new ArrayList<Course>();
	    courseList.add(cs275);
	    courseList.add(cs283);
	    courseList.add(cs260);
	    
	    
	    assertTrue(courseList.contains(cs260));
	    assertTrue(courseList.contains(cs275));
	    assertTrue(courseList.contains(cs283));
	    
	}
	public void testSetQuarter() {
		Quarter test = new Quarter("201515");
		test.setQuarterID("201515");
		assertTrue(test.getQuarterID() == "201515");		
	}

	@Test
	public final void testAddCourse() {
		
		Quarter test = new Quarter("201515");
		Course cs283 = new Course("cs", "283", "Title", 3.00);
		Course cs275 = new Course("cs", "275", "Title", 3.00);
		Course cs260 = new Course("cs", "260", "Title", 3.00);
	    
	    //test.addCourse(cs283);
	    //test.addCourse(cs275);
	    //test.addCourse(cs260);
	    ArrayList<Course> courseList = new ArrayList<Course>();
	    courseList.add(cs275);
	    courseList.add(cs283);
	    courseList.add(cs260);
	    

	    Collections.sort(courseList);
	    
	    assertTrue(test.addCourse(cs260));
	    assertTrue(test.addCourse(cs275));
	    assertTrue(test.addCourse(cs283));
	    
	    //assertTrue(test.getCourseList().equals(courseList));
	}

	@Test
	public final void testRemoveCourse() {
		Quarter test = new Quarter("201515");
		Course cs283 = new Course("cs", "283", "Title", 3.00);
		Course cs275 = new Course("cs", "275", "Title", 3.00);
		
		test.addCourse(cs283);
		test.addCourse(cs275);
	    
	    ArrayList<Course> courseList = new ArrayList<Course>();
	    courseList.add(cs275);
	    
	    assertTrue(test.removeCourse(cs283));
	    assertTrue(test.getCourseList().equals(courseList));
	}




}
