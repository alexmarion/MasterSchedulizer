package TestingPackage;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;

import POSPackage.Course;
import POSPackage.Quarter;
import org.junit.Test;

public class CourseTest {

		Course c3 = new Course("ABC","123","ABC 123",3.00);
		Quarter q = new Quarter("1");

		@Test
		public void testCompareTo(){
			Course c1 = new Course("CS","162","Introduction to Computer Science",3.00);
			Course c2 = new Course("MATH","121","Calculus I",4.00);
			assertTrue(c1.compareTo(c2) == 0);
		}
		
		@Test
		public void testGetSubjectCode(){
			Course c1 = new Course("CS","162","Introduction to Computer Science",3.00);
			assertEquals(c1.getSubjectCode(),"CS");
		}
		
		@Test
		public void testSetSubjectCode(){
			Course c1 = new Course("CS","162","Introduction to Computer Science",3.00);
			c1.setSubjectCode("CS");
			assertEquals(c1.getSubjectCode(),"CS");
		}
		
		@Test
		public void testGetCourseNum(){
			Course c1 = new Course("CS","162","Introduction to Computer Science",3.00);
			assertEquals(c1.getCourseNum(),"162");
		}
		
		@Test
		public void testSetCourseNum(){
			Course c1 = new Course("CS","162","Introduction to Computer Science",3.00);
			c1.setCourseNum("164");
			assertEquals(c1.getCourseNum(),"164");
		}
		// error
		@Test
		public void testGetCourseName(){
			Course c1 = new Course("CS","162","Introduction to Computer Science",3.00);
			assertEquals(c1.getCourseName(),"CS162");
		}
		
		@Test
		public void testGetCourseTitle(){
			Course c1 = new Course("CS","162","Introduction to Computer Science",3.00);
			assertEquals(c1.getCourseTitle(),"Introduction to Computer Science");
		}
		
		@Test
		public void testSetCourseTitle(){
			Course c1 = new Course("CS","162","Introduction to Computer Science",3.00);
			c1.setCourseTitle("This used to be CS 164 title");
			assertEquals(c1.getCourseTitle(),"This used to be CS 164 title");
		}
		
		@Test
		public void testGetCredits(){
			Course c1 = new Course("CS","162","Introduction to Computer Science",3.00);
			assertTrue(c1.getCredits() == 3);
		}
		
		@Test
		public void testSetCredits(){
			Course c1 = new Course("CS","162","Introduction to Computer Science",3.00);
			c1.setCredits(100.00);
			assertTrue(c1.getCredits() == 100.00);
		}
		// error
		@Test
		public void testGetPreReqs(){
			Course c1 = new Course("CS","162","Introduction to Computer Science",3.00);
			assertTrue(c1.getPreReqs().isEmpty());
		}

		@Test
		public void testSetPreReqs(){
			Course c1 = new Course("CS","162","Introduction to Computer Science",3.00);
			Course c2 = new Course("MATH","121","Calculus I",4.00);
			Course c3 = new Course("ABC","123","ABC 123",3.00);
			
			ArrayList<ArrayList<Course>> c_list = new ArrayList<ArrayList<Course>>() {{
			    add(new ArrayList<Course>(Arrays.asList(c2)));
			    add(new ArrayList<Course>(Arrays.asList(c3)));
			}};	
			c1.setPreReqs(c_list);
			assertEquals(c1.getPreReqs(),c_list);
		}
		
		
		
		@Test
		public void testGetCoReqs(){
			Course c1 = new Course("CS","162","Introduction to Computer Science",3.00);
			assertTrue(c1.getCoReqs().isEmpty());
		}

		@Test
		public void testSetCoReqs(){
			Course c1 = new Course("CS","162","Introduction to Computer Science",3.00);
			Course c2 = new Course("MATH","121","Calculus I",4.00);
			Course c3 = new Course("ABC","123","ABC 123",3.00);

			ArrayList<Course> c_list = new ArrayList<Course>() {{
			    add(c2);
			    add(c3);
			}};
			
			c1.setCoReqs(c_list);
			assertEquals(c1.getCoReqs(),c_list);
		}
		
		
		// error
		@Test
		public void testGetTermsOffered(){
			Course c1 = new Course("CS","162","Introduction to Computer Science",3.00);
			assertEquals(c1.getTermsOffered(),"0000");
		}

		@Test
		public void testSetTermsOffered(){
			Course c1 = new Course("CS","162","Introduction to Computer Science",3.00);
			c1.setTermsOffered("1234");
			assertEquals(c1.getTermsOffered(),"1234");
		}
		
		@Test
		public void testGetTermsTaken(){
			Course c1 = new Course("CS","162","Introduction to Computer Science",3.00);
			assertEquals(c1.getTermTaken(),"00000");
		}
		
		@Test
		public void testSetTermTaken(){
			Course c1 = new Course("CS","162","Introduction to Computer Science",3.00);
			c1.setTermTaken("789029");
			assertEquals(c1.getTermTaken(),"789029");
		}

}
