package TestingPackage;

import ParserPackage.CourseLoader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import POSPackage.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by ryan on 2/24/16.
 */
public class POSTest {

    @Before
    public void setUp() throws Exception {
        System.out.println("Set up\n");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Tear down\n");
    }

    @Test
    public void testHash() throws Exception {

    }

    @Test
    public void testFindYear() throws Exception {
        CourseLoader loader = new CourseLoader("test.csv");
        ArrayList<Course> courseList = new ArrayList<Course>();
        Course course;
        while((course = loader.getCourse()) != null) {
            courseList.add(course);
        }
        POS planOfStudy = new POS("NAME", 101010101, courseList);
        Year year = planOfStudy.findYear(courseList.get(0));
        Quarter quarter = year.findQuarter(courseList.get(0));
        Course c = quarter.getCourseList().get(0);
        assertEquals(year, planOfStudy.findYear(c));
        assertNotNull(planOfStudy.findYear(c));
    }

    @Test
    public void testFindQuarter() throws Exception {
        CourseLoader loader = new CourseLoader("test.csv");
        ArrayList<Course> courseList = new ArrayList<Course>();
        Course course;
        while((course = loader.getCourse()) != null) {
            courseList.add(course);
        }
        POS planOfStudy = new POS("NAME", 101010101, courseList);
        Year year = planOfStudy.findYear(courseList.get(0));
        Quarter quarter = year.findQuarter(courseList.get(0));
        Course c = quarter.getCourseList().get(0);
        assertEquals(quarter, planOfStudy.findQuarter(c));
        assertNotNull(planOfStudy.findQuarter(c));
    }

    @Test
    public void testFindCourse() throws Exception {
    }

    @Test
    public void testAddCourse() throws Exception {
        CourseLoader loader = new CourseLoader("test.csv");
        ArrayList<Course> courseList = new ArrayList<Course>();
        Course course;
        while((course = loader.getCourse()) != null) {
            courseList.add(course);
        }
        POS planOfStudy = new POS("NAME", 101010101, courseList);
        Year year = planOfStudy.findYear(courseList.get(0));
        Quarter quarter = year.findQuarter(courseList.get(0));
        Course c = quarter.getCourseList().get(0);
        assertFalse(planOfStudy.addCourse(c));

    }

    @Test
    public void testRemoveCourse() throws Exception {
        CourseLoader loader = new CourseLoader("test.csv");
        ArrayList<Course> courseList = new ArrayList<Course>();
        Course course;
        while((course = loader.getCourse()) != null) {
            courseList.add(course);
        }
        POS planOfStudy = new POS("NAME", 101010101, courseList);
        Year year = planOfStudy.findYear(courseList.get(0));
        Quarter quarter = year.findQuarter(courseList.get(0));
        Course c = quarter.getCourseList().get(0);
        assertFalse(planOfStudy.removeCourse(c, year, quarter));
    }

    @Test
    public void testMoveCourse() throws Exception {
        CourseLoader loader = new CourseLoader("test.csv");
        ArrayList<Course> courseList = new ArrayList<Course>();
        Course course;
        while((course = loader.getCourse()) != null) {
            courseList.add(course);
        }
        POS planOfStudy = new POS("NAME", 101010101, courseList);
        Year year = planOfStudy.findYear(courseList.get(0));
        Quarter quarter = year.findQuarter(courseList.get(0));
        Course c = quarter.getCourseList().get(0);
        assertFalse(planOfStudy.moveCourse(c, year, quarter, year, quarter));
    }

    @Test
    public void testCompareTo() throws Exception {
        CourseLoader loader = new CourseLoader("test.csv");
        ArrayList<Course> courseList = new ArrayList<Course>();
        Course course;
        while((course = loader.getCourse()) != null) {
            courseList.add(course);
        }
        POS planOfStudy = new POS("NAME", 101010101, courseList);
        POS planOfStudy2 = new POS("NAME2", 101010102, courseList);
        Year year = planOfStudy.findYear(courseList.get(0));
        Quarter quarter = year.findQuarter(courseList.get(0));
        Course c = quarter.getCourseList().get(0);
        assertTrue(planOfStudy.compareTo(planOfStudy2) == 0);
        assertFalse(planOfStudy.compareTo(planOfStudy2) == 1);
        assertNotSame(planOfStudy, planOfStudy2);
    }

    @Test
    public void testDisplay() throws Exception {

    }
}