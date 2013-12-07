package dbconnect.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import dbconnect.IDBController;
import dbconnect.xml.converters.FormConverter;
import dbconnect.xml.converters.UserConverter;
import form.Form;
import form.User;

/**
 * A concrete implementation of an interface that is meant to allow for the conversion
 * between volatile and non-volatile memory.
 * 
 * This particular strategy utilizes XML, and may be prone to performance problems when
 * the application is scaled.
 * 
 * @author mstrobel
 */
public class XmlController implements IDBController {
	
	/**
	 * The environment variable that should be set indicating which file contains the form XML file.
	 */
	public final static String XML_FORM_ENVIRONMENT_VARIABLE = "XMLERS_FORM_LOCATION";
	
	/**
	 * The environment variable that should be set indicating which file contains the user XML file.
	 */
	public final static String XML_USER_ENVIRONMENT_VARIABLE = "XMLERS_USER_LOCATION";
	
	/**
	 * The directory to the folder/directory that contains forms.
	 */
	private final static File FORM_REPOSITORY;
	
	/**
	 * 
	 */
	private final static File USER_REPOSITORY;
	
	/**
	 * Specifies things that can be used to uniquely specify a user.
	 * @author mstrobel
	 * @note These are chosen by requests from front-end developers.
	 */
	public enum UserAccessor {
		ID,
		EMAIL,
		USERNAME
	}
	
	/**
	 * A JAXB utility for writing Forms to XML files.
	 */
	private static Marshaller FORM_MARSHALLER;
	
	/**
	 * A JAXB utility for writing Users to XML files.
	 */
	private static Marshaller USER_MARSHALLER;
	
	/**
	 * A JAXB Utility for reading Forms from XML files.
	 */
	private static Unmarshaller FORM_UNMARSHALLER;
	
	/**
	 * A JAXB Utility for reading Users from XML files.
	 */
	private static Unmarshaller USER_UNMARSHALLER;
	
	private static XPath XPATH_INSTANCE;
	
	static {
		String userFileLocation;
		String formFileLocation;
		
		try {
			XPATH_INSTANCE = XPathFactory.newInstance(XPathFactory.DEFAULT_OBJECT_MODEL_URI).newXPath();
		} catch (XPathFactoryConfigurationException e1) {
			e1.printStackTrace();
		}
		
		userFileLocation = System.getenv(XML_USER_ENVIRONMENT_VARIABLE);
		if(null == userFileLocation || userFileLocation.equals(""))
		{
			//TODO pick some arbitrary default file.
		}
		USER_REPOSITORY = new File(userFileLocation);
		
		formFileLocation = System.getenv(XML_FORM_ENVIRONMENT_VARIABLE);
		if(null == formFileLocation || formFileLocation.equals(""))
		{
			//TODO pick some arbitrary default file.
		}
		FORM_REPOSITORY = new File(formFileLocation);
		
		/*
		 * Initialize the JAXB Utilities for reading and writing XML files
		 */
		try {
			JAXBContext formContext = JAXBContext.newInstance(dbconnect.xml.dao.Form.class);
			JAXBContext userContext = JAXBContext.newInstance(dbconnect.xml.dao.User.class);
			
			FORM_MARSHALLER = formContext.createMarshaller();
			FORM_UNMARSHALLER = formContext.createUnmarshaller();
			
			USER_MARSHALLER = userContext.createMarshaller();
			USER_UNMARSHALLER = userContext.createUnmarshaller();
		} catch (JAXBException e) {

			FORM_MARSHALLER = null;
			
			FORM_UNMARSHALLER = null;
			
			USER_MARSHALLER = null;
			USER_UNMARSHALLER = null;
			e.printStackTrace();
		}
	}
		
	public static File getFormLocation()
	{
		return new File(FORM_REPOSITORY.getAbsolutePath());
	}
	
	public static File getUserLocation()
	{
		return new File(USER_REPOSITORY.getAbsolutePath());
	}
	
	@Override
	public boolean formExists(int formId) {
		boolean retval = false;
		try {
			Object queryResult;
			FileReader fr;
			StringBuilder query = new StringBuilder();
			
			fr = new FileReader(FORM_REPOSITORY);
			InputSource inputSource = new InputSource(fr);
			query.append("//form[@id=\"");
			query.append(formId);
			query.append("\"]");
			queryResult = XPATH_INSTANCE.evaluate(query.toString(), inputSource, XPathConstants.BOOLEAN);
			retval = ((Boolean) queryResult).booleanValue();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return retval;
	}

	@Override
	public boolean userExists(int userId) {
		boolean retval = false;
		
		try {
			retval = (boolean) XPATH_INSTANCE.evaluate("//user[@id=" + userId + "]", new InputSource(new FileReader(USER_REPOSITORY)), XPathConstants.BOOLEAN);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return retval;
	}

	@Override
	public boolean upsertForm(Form form) {
		return false;
	}

	@Override
	public boolean upsertUser(User user) {
		return false;
	}

	@Override
	public Form fetchForm(int id) {
		Form form = null;
		dbconnect.xml.dao.Form formDAO;
		if(!formExists(id))
		{
			return null;
		}
		try {
			Node source;
			Object evaluated;
			StringBuilder queryBuilder;
			FileReader fr;
			
			queryBuilder = new StringBuilder();
			queryBuilder.append("//form[@id=\"");
			queryBuilder.append(id);
			queryBuilder.append("\"]");

			fr = new FileReader(FORM_REPOSITORY);
			InputSource inputSource = new InputSource(fr);
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
			Document doc = documentBuilder.parse(inputSource);
			XPathExpression expression = XPATH_INSTANCE.compile(queryBuilder.toString());
			evaluated = expression.evaluate(doc, XPathConstants.NODE);
			//evaluated = XPATH_INSTANCE.evaluate(queryBuilder.toString(), inputSource, XPathConstants.NODE);
			source = (Node) evaluated;
			if(null != source)
			{
				formDAO = (dbconnect.xml.dao.Form) FORM_UNMARSHALLER.unmarshal(source);
				form = FormConverter.getInstance().convert(formDAO);
			}
			else
			{
				return null;
			}
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return form;
	}

	@Override
	public User fetchUser(int id) {
		return fetchUserHelper("//user[@id=" + id + "]");
	}
	
	@Override
	public User fetchUserByUsername(String username) {
		return fetchUserHelper("//user[@handle=" + username + "]");
	}
	
	private User fetchUserHelper(String query)
	{
		User user;
		dbconnect.xml.dao.User userDAO = null;
		
		try {
			String source;
			source = (String) XPATH_INSTANCE.evaluate(query, USER_REPOSITORY.getAbsolutePath(), XPathConstants.STRING);
			userDAO = (dbconnect.xml.dao.User) USER_UNMARSHALLER.unmarshal(new StringReader(source));
		}
		catch (JAXBException e){
			e.printStackTrace();
			return null;
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		
		user = UserConverter.getInstance().convert(userDAO);
		
		return user;
	}
	
	@Override
	public User fetchUserByEmail(String email) {
		return fetchUserHelper("//user[@email=" + email + "]");
	}
	
	@Override
	public User fetchUserFromLogin(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteForm(int key) {
		//TODO
		return false;
	}

	@Override
	public boolean deleteUser(int key) {
		//TODO
		return false;
	}

	@Override
	public List<Form> getOwnerForms(int userID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Form> getParticipantForms(int userID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNewQuestionId() {
		int retval = -1;
		try {
			Object queryResult;
			FileReader fr;
			String query;
			
			query = "max(//question/@id or //comlexQuestion/@id or //textQuestion/@id or //VariadicBooleanQuestion/@id or //ComplexQuestion/@id)";
			
			fr = new FileReader(FORM_REPOSITORY);
			InputSource inputSource = new InputSource(fr);
			queryResult = XPATH_INSTANCE.evaluate(query, inputSource, XPathConstants.NUMBER);
			retval = 1 + ((Double) queryResult).intValue();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return retval;
	}
	
}
