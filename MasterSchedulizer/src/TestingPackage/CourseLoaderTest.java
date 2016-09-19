/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  POSPackage.Course
 *  ParserPackage.CourseLoader
 *  TestingPackage.TestCourseLoader
 *  org.junit.After
 *  org.junit.Assert
 *  org.junit.Before
 *  org.junit.Test
 */
package TestingPackage;

import POSPackage.Course;
import ParserPackage.CourseLoader;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CourseLoaderTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetCourse() {
        Course expected = new Course("CI", "101", "Computing and Informatics Design I", 2.00);
        expected.setTermsOffered("1000");
        expected.setTermTaken("201515");
        
        CourseLoader loader = new CourseLoader("unitTestParserPackage.csv");
        Course actual = loader.getCourse();
        
        int expectedCompareToOutput = 1;
        int actualCompareToOutput = expected.compareTo(actual);
        Assert.assertEquals(expectedCompareToOutput, actualCompareToOutput);
    }

    @Test
    public void testGetCourses() {
        ArrayList<Course> expected = new ArrayList<Course>();
        Course c1 = new Course("CI", "101", "Computing and Informatics Design I", 2.00);
        c1.setTermsOffered("1000");
        c1.setTermTaken("201515");
        expected.add(c1);
        
        Course c2 = new Course("CS", "164", "Introduction to Computer Science", 3.00);
        c2.setTermsOffered("1100");
        c2.setTermTaken("201515");
        expected.add(c2);
        
        Course c3 = new Course("ENGL", "101", "Composition and Rhetoric I: Inquiry and Exploratory Research", 3.00);
        c3.setTermsOffered("1111");
        c3.setTermTaken("201515");
        expected.add(c3);
        
        CourseLoader loader = new CourseLoader("unitTestParserPackage.csv");
        ArrayList<Course> actual = loader.getCourses();
        Assert.assertEquals(listElements(expected), listElements(actual));
    }

    public String listElements(ArrayList<Course> l) {
        String listString = "";
        for (Course s : l) {
            listString = String.valueOf(listString) + s.getSubjectCode() + " " + s.getCourseNum() + " " + s.getCourseTitle() + " " + s.getCredits() + " " + s.getTermsOffered() + " " + s.getTermTaken() + "\n";
        }
        return listString;
    }
}
