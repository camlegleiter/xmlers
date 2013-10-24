package dbconnect;

import java.util.List;

import form.Form;
import form.User;

public interface IDBController {
	/**
	 * Returns <code>true</code> if the form with the given ID exists.
	 * 
	 * @param formId
	 *            The ID of the form to check for existence.
	 * @return
	 */
	public boolean formExists(String formId);

	/**
	 * Returns <code>true</code> if the user with the given ID exists.
	 * 
	 * @param formId
	 *            The ID of the form to check for existence.
	 * @return
	 */
	public boolean userExists(String userId);

	/**
	 * Checks if the given form exists in the database. If it does, the form
	 * information is updated (UPsert). Otherwise, the form is inserted (upSERT)
	 * as a new form.
	 * 
	 * @param f
	 *            the Form object to upsert into the database.
	 * @return <code>true</code> if the form was correctly upserted
	 */
	public boolean upsertForm(Form form);

	/**
	 * Checks if the given user exists in the database. If it does, the user
	 * information is updated (UPsert). Otherwise, the user is inserted (upSERT)
	 * as a new form.
	 * 
	 * @param f
	 *            the User object to upsert into the database.
	 * @return <code>true</code> if the form was correctly upserted
	 */
	public boolean upsertUser(User user);

	/**
	 * Retrieves a Form with the given ID.
	 * 
	 * @param formId
	 * @return The corresponding Form, or <code>null</code> if the given formId
	 *         is null or has length == 0, or if there is no Form that exists
	 *         that matches the formId
	 */
	public Form fetchForm(String formId);

	/**
	 * Retrieves a User with the given ID.
	 * 
	 * @param formId
	 * @return The corresponding User, or <code>null</code> if the given userId
	 *         is null or has length == 0, or if there is no User that exists
	 *         that matches the userId
	 */
	public User fetchUser(String userId);

	/**
	 * Retrieves a User with the given username and password. Verifies that the
	 * given username and password is valid.
	 * 
	 * @param username
	 * @param password
	 * @return A User object containing basic information about a user, or
	 *         <code>null</code> if the given information is invalid.
	 */
	public User fetchUser(String username, String password);

	/**
	 * Deletes a Form with the given ID.
	 * 
	 * @param formId
	 * @return <code>true</code> if the Form matching the given ID was deleted.
	 */
	public boolean deleteForm(String formId);

	/**
	 * Deletes a User with the given ID.
	 * 
	 * @param userId
	 * @return <code>true</code> if the User matching the given ID was deleted.
	 */
	public boolean deleteUser(String userId);

	/**
	 * Retrieves all of the Forms where the owner matches the given userId.
	 * 
	 * @param userId
	 * @return a List of all Forms that the given userId is the owner of.
	 */
	public List<Form> getOwnerForms(String userId);

	/**
	 * Retrieves all of the Forms the given userId is a participant of.
	 * 
	 * @param userId
	 * @return a List of all Forms that the given userId is a participant of.
	 */
	public List<Form> getParticipantForms(String userId);
}
