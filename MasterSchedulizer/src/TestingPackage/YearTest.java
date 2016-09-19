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
public class YearTest {



    @Before
    public void setUp() throws Exception {
        System.out.println("Set up\n");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Tear down\n");
    }

    @Test
    public void testGetQuarterList() throws Exception {
        Quarter[] quarter = new Quarter[4];
        Year year2 = new Year("2017", quarter);
        assertArrayEquals(quarter, year2.getQuarterList());
        assertNotNull(year2.getQuarterList());
        assertSame(quarter, year2.getQuarterList());
    }

    @Test
    public void testSetQuarterList() throws Exception {
        Quarter[] quarter = new Quarter[4];
        Quarter[] newQuarter = new Quarter[4];
        Year year2 = new Year("2017", quarter);
        year2.setQuarterList(newQuarter);
        assertArrayEquals(newQuarter, year2.getQuarterList());
    }

    @Test
    public void testGetAcademicYear() throws Exception {
        Quarter[] quarter = new Quarter[4];
        Year year = new Year("2016");
        assertEquals("2016", year.getAcademicYear());;
        assertSame("2016", year.getAcademicYear());
    }

    @Test
    public void testSetAcademicYear() throws Exception {
        Year year = new Year("2016");
        year.setAcademicYear("2017");
        assertEquals("2017", year.getAcademicYear());;
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
        assertEquals(quarter, year.findQuarter(c));
        assertNotNull(year.findQuarter(c));
    }

    @Test
    public void testAddCourse() throws Exception {
        Year year = new Year("2016");
        Quarter q1 = new Quarter("1010", new ArrayList<Course>(4));
        Course c = new Course("subjectCode", "courseNum", "title", 4.00);
        assertEquals(true, year.addCourse(c, q1));
    }

    @Test
    public void testRemoveCourse() throws Exception {
        Year year = new Year("2016");
        Quarter q1 = new Quarter("1010", new ArrayList<Course>(4));
        Course c = new Course("subjectCode", "courseNum", "title", 4.00);
        assertEquals(false, year.removeCourse(c, q1));
        year.addCourse(c, q1);
        assertEquals(true, year.removeCourse(c, q1));
    }

    @Test
    public void testMoveCourse() throws Exception {
        Year year = new Year("2016");
        Quarter q1 = new Quarter("1010", new ArrayList<Course>(4));
        Course c = new Course("subjectCode", "courseNum", "title", 4.00);
        Quarter q2 = new Quarter("1010", new ArrayList<Course>(4));
        year.addCourse(c, q1);
        assertEquals(true, year.moveCourse(c, q1, q2));
    }

    @Test
    public void testCompareTo() throws Exception {
        Year year1 = new Year("2016");
        Year year2 = new Year("2017");
        assertTrue(year1.compareTo(year2) == 0);
        assertFalse(year1.compareTo(year2) == 1);
        assertNotSame(year1, year2);
    }

    @Test
    public void testDisplay() throws Exception {

    }
}