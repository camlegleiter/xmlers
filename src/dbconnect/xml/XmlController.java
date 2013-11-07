package dbconnect.xml;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import dbconnect.IDBController;
import dbconnect.xml.converters.FormConverter;
import dbconnect.xml.converters.UserConverter;
import form.Form;
import form.ResponseForm;
import form.User;

/**
 * A concrete implementation of an interface that is meant to allow for the conversion
 * between volatile and non-volatile memory.
 * @author mstrobel
 *
 */
public class XmlController implements IDBController {

	/**
	 * The directory path to the folder/directory that contains forms.
	 */
	private final static String FORM_DIRECTORY_STRING;
	
	/**
	 * The directory path to the folder/directory that contains users.
	 */
	private final static String USER_DIRECTORY_STRING;
	
	/**
	 * The directory to the folder/directory that contains forms.
	 */
	private final static File FORM_DIRECTORY;
	
	/**
	 * 
	 */
	private final static File USER_DIRECTORY;
	
	/**
	 * Used to determine what file the program should be accessing
	 */
	private final static String EXTENSION;
	

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
	
	
	static {
		String frmLoc; //A temporary place to store the directory path to forms.
		String usrLoc; //A temporary place to store the directory path to users.
		File dDir; //A temporary place to store the directory to forms.
		File uDir; //A temporary place to store the directory to users.
		File defaultRoot;  //The default drive to save XML files to.
		File[] roots = File.listRoots(); //The possible drives to read from
		
		/*
		 * Prepare default assumptions about the file system, or read settings defined in Environment Variables.
		 */
		defaultRoot = new File(roots[0].getAbsolutePath(), "xmlersData");
		
		frmLoc = System.getenv("XMLERS_FORM_DIR");
		usrLoc = System.getenv("XMLERS_USER_DIR");

		EXTENSION = ".xml";
		
		if(null == frmLoc || frmLoc.equals(""))
		{
			File f = new File(defaultRoot.getAbsoluteFile(), "forms");
			FORM_DIRECTORY_STRING = f.getAbsolutePath();
		}
		else
		{
			FORM_DIRECTORY_STRING = frmLoc;
		}
		
		if(null == usrLoc || usrLoc.equals(""))
		{
			USER_DIRECTORY_STRING = new File(defaultRoot.getAbsoluteFile(), "users").getAbsolutePath();
		}
		else
		{
			USER_DIRECTORY_STRING = usrLoc;
		}
		
		/*
		 * Load references to file system, create folders if necessary.
		 */		
		dDir = new File(FORM_DIRECTORY_STRING);
		uDir = new File(USER_DIRECTORY_STRING);
		
		if(!dDir.exists())
		{
			dDir.mkdirs();
		}
		else if(!dDir.isDirectory())
		{
			throw new IllegalArgumentException("XMLERS_FORM_DIR must be a directory");
		}
		
		if(!uDir.exists()){
			uDir.mkdirs();
		}
		else if(!uDir.isDirectory())
		{
			throw new IllegalArgumentException("XMLERS_USER_DIR must be a directory");
		}
		
		
		FORM_DIRECTORY = dDir;
		USER_DIRECTORY = uDir;

		
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
	
	@Override
	public boolean formExists(int formId) {
		return getFormFile(formId).exists();
	}

	@Override
	public boolean userExists(int userId) {
		return getFormFile(userId).exists();
	}

	@Override
	public boolean upsertForm(Form form) {
		boolean success;
		dbconnect.xml.dao.Form formDAO;
			
		formDAO = FormConverter.getInstance().unconvert(form);
		
		try {
			FORM_MARSHALLER .marshal(formDAO, getFormFile(form.getFormId()));
			success = true;
		} catch (JAXBException e) {
			e.printStackTrace();
			success = false;
		}
		return success;
	}

	@Override
	public boolean upsertUser(User user) {
		boolean success;
		dbconnect.xml.dao.User userDAO;
		
		userDAO = UserConverter.getInstance().unconvert(user);
		
		try {
			USER_MARSHALLER.marshal(userDAO, getUserFile(user.getUserID()));
			success = true;
		} catch (JAXBException e) {
			success = false;
			e.printStackTrace();			
		}
		return success;
	}

	@Override
	public Form fetchForm(int id) {
		Form form;
		dbconnect.xml.dao.Form formDAO;
		if(!formExists(id))
		{
			return null;
		}
		try {
			formDAO = (dbconnect.xml.dao.Form) FORM_UNMARSHALLER.unmarshal(getFormFile(id));
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
		
		form = FormConverter.getInstance().convert(formDAO);
		
		return form;
	}

	@Override
	public User fetchUser(int id) {
		return fetchUserHelper(UserAccessor.ID, id);
	}
	
	@Override
	public User fetchUserByUsername(String username) {
		return fetchUserHelper(UserAccessor.USERNAME, username);
	}
	
	private User fetchUserHelper(UserAccessor method, Object data)
	{
		User user;
		dbconnect.xml.dao.User userDAO;
		File userFile = getUserFile(method, data); 
		
		if(null == userFile)
		{
			return null;
		}
		
		try {
			userDAO = (dbconnect.xml.dao.User) USER_UNMARSHALLER.unmarshal(userFile);
		}
		catch (JAXBException e){
			e.printStackTrace();
			return null;
		}
		
		user = UserConverter.getInstance().convert(userDAO);
		
		return user;
	}
	
	@Override
	public User fetchUserByEmail(String email) {
		return fetchUserHelper(UserAccessor.EMAIL, email);
	}
	
	@Override
	public User fetchUserFromLogin(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteForm(int key) {
		// TODO iterate through all users that own or participate in a form, and delete references to those.
		
		File formFile = getFormFile(key);
		
		return formFile.delete();
	}

	@Override
	public boolean deleteUser(int key) {
		File userFile = getUserFile(key);
		//TODO Iterate through all references to this user and delete them.
		return userFile.delete();
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
	
	private File getUserFile(int userId)
	{
		return new File(USER_DIRECTORY.getAbsolutePath(), userId + EXTENSION);
	}
	
	/**
	 * Fetches the File that contains user data.
	 * @param method Specifies which type of data you are using to access a user's information.
	 * @param data 
	 * @return
	 */
	private File getUserFile(UserAccessor method, Object data)
	{
		File retval;
		switch(method)
		{
//		case EMAIL:
		//TODO hook to email query
//			break;
		case ID:
			retval = getUserFile((Integer) data);
			break;
//		case USERNAME:
			//TODO hook to username query
//			break;
		default:
			throw new RuntimeException("Use of unimplemented Accessor: " + method.name());
		}
		return retval;		
	}
	
	private File getFormFile(int formId)
	{
		return new File(FORM_DIRECTORY.getAbsolutePath(), formId + EXTENSION);
	}

	@Override
	public List<ResponseForm> getResponseForms(int formId) {
		// TODO Auto-generated method stub
		return null;
	}
}
