package dbconnect;

import java.util.ArrayList;

import form.Form;
import form.User;

public interface IDBController  
{	
	public boolean formExists(String key);
	
	public boolean userExists(String key);
	
	public boolean upsertForm(Form f);
	
	public boolean upsertUser(User user);
	
	public Form fetchForm(String id);
	
	public User fetchUser(String id);
	
	public boolean deleteForm(String key);
	
	public boolean deleteUser(String key);

	ArrayList<Form> getOwnerForms(String userID);	
}
