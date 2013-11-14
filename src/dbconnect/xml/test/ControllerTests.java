package dbconnect.xml.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dbconnect.DBManager;
import dbconnect.IDBController;
import dbconnect.xml.XmlController;
import form.Form;

public class ControllerTests {

	public IDBController controller;
	
	static {
		
	}
	
	@Before
	public void setup()
	{
		controller = DBManager.getInstance();
	}
	
	@Test
	public void environment()
	{
		String formVariable = System.getenv(XmlController.XML_FORM_ENVIRONMENT_VARIABLE);
		String userVariable = System.getenv(XmlController.XML_USER_ENVIRONMENT_VARIABLE);
		String controllerStrat = System.getenv(DBManager.ENVIRONMENT_STRATEGY);
		
		assertNotNull(formVariable);
		assertNotNull(userVariable);
		assertNotEquals("", formVariable);
		assertNotEquals("", userVariable);
		
		
		assertEquals(DBManager.XML_STRATEGY, controllerStrat);
	}
	
	@Test
	public void Initilization() {
		assertEquals("C:\\Users\\mstrobel\\Documents\\xmlers\\testMaterial\\Forms.xml", XmlController.getFormLocation().getAbsolutePath());
		assertEquals("C:\\Users\\mstrobel\\Documents\\xmlers\\testMaterial\\Users.xml", XmlController.getUserLocation().getAbsolutePath());
	}
	
	@Test
	public void formFetchHappy()
	{
		assertTrue(controller instanceof XmlController);
		
		Form f = controller.fetchForm(1);
		
		assertEquals(1, f.getFormId());
		//TODO verify that all other field match anticipated values.
		
	}

}
