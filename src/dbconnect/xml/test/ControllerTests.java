package dbconnect.xml.test;

import static org.junit.Assert.*;

import org.hamcrest.CoreMatchers;
import org.hamcrest.CoreMatchers.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import org.junit.Before;
import org.junit.Test;

import dbconnect.DBManager;
import dbconnect.IDBController;
import dbconnect.xml.XmlController;
import dbconnect.xml.dao.Forms;
import form.Form;

public class ControllerTests {

	public IDBController controller;
	
	/**
	 * Just in case, setup new instances for each test.
	 */
	@Before
	public void setup()
	{
		controller = DBManager.getInstance();
		Forms emptyForms = new Forms();
		
	}
	
	/**
	 * Verifies that the environment for running these tests is appropriate.
	 * For instance, ensures that the XML controller is being used, and that
	 * the form and user files are specified.
	 */
	@Test
	public void environment()
	{
		String formVariable = System.getenv(XmlController.XML_FORM_ENVIRONMENT_VARIABLE);
		String userVariable = System.getenv(XmlController.XML_USER_ENVIRONMENT_VARIABLE);
		String controllerStrat = System.getenv(DBManager.ENVIRONMENT_STRATEGY);
		
		assertNotNull(formVariable);
		assertNotNull(userVariable);
		assertThat(formVariable, is(not(formVariable)));
		assertThat(userVariable, is(not(userVariable)));
		
		
		assertEquals(DBManager.XML_STRATEGY, controllerStrat);
	}
	
	@Test
	public void Initilization() {
		//TODO update this so that it is not dependent on my File System.
		assertTrue(controller instanceof XmlController);
		assertEquals("C:\\Users\\mstrobel\\Documents\\xmlers\\testMaterial\\Forms.xml", XmlController.getFormLocation().getAbsolutePath());
		assertEquals("C:\\Users\\mstrobel\\Documents\\xmlers\\testMaterial\\Users.xml", XmlController.getUserLocation().getAbsolutePath());
	}
	
	/**
	 * Verifies that forms that are in the file are regarded as existing.
	 */
	@Test
	public void formExistsHappy()
	{
		assertTrue(controller instanceof XmlController);
		assertTrue(controller.formExists(1));
		assertTrue(controller.formExists(2));
		assertTrue(controller.formExists(3));		
	}
	
	/**
	 * Verifies that formExists returns false when asked for a form that
	 * it does not actually contain.
	 */
	@Test
	public void formExistNonexisting()
	{
		assertFalse(controller.formExists(1000000000));
	}
	
	/**
	 * Verifies that Forms are initialized correctly.
	 */
	@Test
	public void formFetchHappy()
	{
		assertTrue(controller instanceof XmlController);
		
		Form f = controller.fetchForm(1);
		
		assertNotNull(f);
		
		assertEquals(1, f.getFormId());
		//TODO verify that all other field match anticipated values.
		
	}

}
