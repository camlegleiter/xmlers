package dbconnect;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import form.Form;
import form.User;

public class XmlController implements IDBController {

	private final static String FORM_DIRECTORY_STRING;
	private final static String USER_DIRECTORY_STRING;
	private final static File FORM_DIRECTORY;
	
	/**
	 * 
	 */
	private final static File USER_DIRECTORY;
	
	/**
	 * Used to determine what file the program should be accessing
	 */
	private final static String EXTENSION;
	
	private static Marshaller FORM_MARSHALLER;
	private static Marshaller USER_MARSHALLER;
	private static Unmarshaller FORM_UNMARSHALLER;
	private static Unmarshaller USER_UNMARSHALLER;
	
	
	static {
		String frmLoc;
		String usrLoc;
		File dDir;
		File uDir;
		File defaultRoot;
		File[] roots = File.listRoots();
		
		defaultRoot = new File(roots[0].getAbsolutePath(), "xmlersData");
		
		frmLoc = System.getenv("XMLERS_FORM_DIR");
		usrLoc = System.getenv("XMLERS_USER_DIR");
		
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

		EXTENSION = ".xml";
		
		
		try {
			JAXBContext formContext = JAXBContext.newInstance(dbconnect.dao.Form.class);
			JAXBContext userContext = JAXBContext.newInstance(dbconnect.dao.User.class);
			
			FORM_MARSHALLER = formContext.createMarshaller();
			FORM_UNMARSHALLER = formContext.createUnmarshaller();
			
			USER_MARSHALLER = userContext.createMarshaller();
			USER_UNMARSHALLER = userContext.createUnmarshaller();
		} catch (JAXBException e) {

			FORM_MARSHALLER = null;
			
			FORM_UNMARSHALLER = null;
			
			USER_MARSHALLER = null;
			USER_UNMARSHALLER = null;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
	}
	
	@Override
	public boolean formExists(String formId) {
		return getFormFile(formId).exists();
	}

	@Override
	public boolean userExists(String userId) {
		return getFormFile(userId).exists();
	}

	@Override
	public boolean upsertForm(Form form) {
		boolean success;
		dbconnect.dao.Form formDAO = new dbconnect.dao.Form();
		
		formDAO.setId(form.getKey());
		formDAO.setTitle(form.getTitle());
		
		//TODO Finish copying form info into DAO
		
		try {
			FORM_MARSHALLER .marshal(formDAO, getFormFile(form.getKey()));
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
		dbconnect.dao.User userDAO;
		
		//TODO Copy data from regular user Object to DAO.
		
		userDAO = new dbconnect.dao.User();
		
		try {
			USER_MARSHALLER.marshal(userDAO, getUserFile(user.getUserID()));
			success = true;
		} catch (JAXBException e) {
			success = false;
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return success;
	}

	@Override
	public Form fetchForm(String id) {
		Form form;
		dbconnect.dao.Form formDAO;
		if(!formExists(id))
		{
			return null;
		}
		try {
			formDAO = (dbconnect.dao.Form) FORM_UNMARSHALLER.unmarshal(getFormFile(id));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		form = new Form();
		form.setKey(formDAO.getId());
		form.setDescription(formDAO.getDescription());
		
		return form;
	}

	@Override
	public User fetchUser(String id) {
		User user;
		dbconnect.dao.User userDAO;
		
		if(!userExists(id))
		{
			return null;
		}		
		
		try {
			userDAO = (dbconnect.dao.User) USER_UNMARSHALLER.unmarshal(getUserFile(id));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		user = new User();
		user.setUserID(userDAO.getId());
		user.setFirstName(userDAO.getFirstName());
		user.setLastName(userDAO.getLastName());
		user.setPassword(userDAO.getPassword());
		
		return user;
	}

	@Override
	public boolean deleteForm(String key) {
		// TODO iterate through all users that own or participate in a form, and delete references to those.
		
		File formFile = getFormFile(key);
		
		return formFile.delete();
	}

	@Override
	public boolean deleteUser(String key) {
		File userFile = getUserFile(key);
		return userFile.delete();
	}

	@Override
	public List<Form> getOwnerForms(String userID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Form> getParticipantForms(String userID) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private File getUserFile(String userId)
	{
		return new File(USER_DIRECTORY.getAbsolutePath(), userId + EXTENSION);
	}
	
	private File getFormFile(String formId)
	{
		return new File(FORM_DIRECTORY.getAbsolutePath(), formId + EXTENSION);
	}
	
	@Override
	public User fetchUser(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}
}
