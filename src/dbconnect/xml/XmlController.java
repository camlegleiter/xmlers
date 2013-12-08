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
import dbconnect.xml.dao.Forms;
import form.Form;
import form.User;

/**
 * A concrete implementation of an interface that is meant to allow for the conversion
 * between volatile and non-volatile memory.
 * 
 * This particular strategy utilizes XML, and may be prone to performance problems when
 * the application is scaled.
 * 
 * We are particularly worried about scaling, which this will not be able to combat, AT ALL.
 * Our project advisor, however, felt strongly that we not attempt to combat this.
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
	 * A file that stores all of the user information.
	 */
	private final static File USER_REPOSITORY;
	
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
	
	/**
	 * A JAXB Utility to read all Forms from an XML file.
	 */
	private static Marshaller FORM_COLLECTION_MARSHALLER;
	
	/**
	 * A JAXB Utility to read all Forms from an XML file.
	 */
	private static Unmarshaller FORM_COLLECTION_UNMARSHALLER;
	
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
			JAXBContext formsContext = JAXBContext.newInstance(dbconnect.xml.dao.Forms.class);
			
			FORM_MARSHALLER = formContext.createMarshaller();
			FORM_UNMARSHALLER = formContext.createUnmarshaller();
			
			USER_MARSHALLER = userContext.createMarshaller();
			USER_UNMARSHALLER = userContext.createUnmarshaller();
			
			FORM_COLLECTION_MARSHALLER = formsContext.createMarshaller();
			FORM_COLLECTION_UNMARSHALLER = formsContext.createUnmarshaller();
			
			
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
	public synchronized boolean formExists(int formId) {
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
	public synchronized boolean userExists(int userId) {
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
	public synchronized boolean upsertForm(Form form) {

		
		try {
			Forms f;
			f = unmarshallAllForms();
			
			if(formExists(form.getFormId()))
			{
				int i;
				
				i = 0;
				
				for(dbconnect.xml.dao.Form entry : f.getForm())
				{
					if(entry.getId().intValue() == form.getFormId())
					{
						f.getForm().remove(i);
						break;
					}
					i++;
				}
			}
			
			dbconnect.xml.dao.Form newForm = FormConverter.getInstance().unconvert(form);
			
			f.getForm().add(newForm);
			
			marshallAllForms(f);
			
			return true;
			
		} catch (JAXBException e) {
			e.printStackTrace();
			return false;
		}
		
	}

	@Override
	public synchronized boolean upsertUser(User user) {
		return false;
	}

	@Override
	public synchronized Form fetchForm(int id) {
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
	public synchronized User fetchUser(int id) {
		return fetchUserHelper("//user[@id=" + id + "]");
	}
	
	private synchronized User fetchUserHelper(String query)
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
	public synchronized User fetchUserByEmail(String email) {
		return fetchUserHelper("//user[@email=" + email + "]");
	}

	@Override
	public synchronized boolean deleteForm(int key) {
		Forms formCollection;
		boolean found;
		
		found = false;
		
		try {
			int i;
			formCollection = unmarshallAllForms();
			List<dbconnect.xml.dao.Form> list = formCollection.getForm();
			
			i = 0;
			for(dbconnect.xml.dao.Form entry : list)
			{
				if(entry.getId().intValue() == key)
				{
					found = true;
					list.remove(i);
					break;
				}
				i++;
			}
			
			if(found)
			{
				marshallAllForms(formCollection);
			}
			
		} catch (JAXBException e) {
			e.printStackTrace();
			return false;
		}
		
		return found;
	}
	
	private synchronized Forms unmarshallAllForms() throws JAXBException
	{
		Forms retval;
		
		retval = (Forms) XmlController.FORM_COLLECTION_UNMARSHALLER.unmarshal(FORM_REPOSITORY);
		
		return retval;
	}

	private synchronized void marshallAllForms(Forms f) throws JAXBException
	{
		XmlController.FORM_COLLECTION_MARSHALLER.marshal(f, FORM_REPOSITORY);
	}
	
	@Override
	public synchronized boolean deleteUser(int key) {
		//TODO
		return false;
	}

	@Override
	public synchronized List<Form> getOwnerForms(int userID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public synchronized List<Form> getParticipantForms(int userID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public synchronized int getNewQuestionId() {
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
