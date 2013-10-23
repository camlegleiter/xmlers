package dbconnect;

import java.util.List;

import form.Form;
import form.User;

public interface IDBController  
{	
	/**
	 * Returns <code>true</code> if the form with the given ID exists.
	 * 
	 * @param formId
	 * 				The ID of the form to check for existence.
	 * @return
	 */
	public boolean formExists(String formId);
	
	/**
	 * Returns <code>true</code> if the user with the given ID exists.
	 * 
	 * @param formId
	 * 				The ID of the form to check for existence.
	 * @return
	 */
	public boolean userExists(String userId);
	
	/**
	 * Checks if the given form exists in the database. If it does, the form
	 * information is updated (UPsert). Otherwise, the form is inserted (upSERT)
	 * as a new form.
	 * 
	 * @param f
	 * 				the Form object to upsert into the database.
	 * @return
	 * 				<code>true</code> if the form was correctly upserted
	 */
	public boolean upsertForm(Form form);
	
	/**
	 * Checks if the given user exists in the database. If it does, the user
	 * information is updated (UPsert). Otherwise, the user is inserted (upSERT)
	 * as a new form.
	 * 
	 * @param f
	 * 				the User object to upsert into the database.
	 * @return
	 * 				<code>true</code> if the form was correctly upserted
	 */
	public boolean upsertUser(User user);
	
	public Form fetchForm(String formId);
	
	public User fetchUser(String userId);
	
	public boolean deleteForm(String formId);
	
	public boolean deleteUser(String userId);

	public List<Form> getOwnerForms(String userID);

	public List<Form> getParticipantForms(String userID);	
}
