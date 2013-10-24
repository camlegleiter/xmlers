package form.utils;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import dbconnect.DBManager;
import form.Form;

/**
 * Utility class for getting Form information
 */
public class Forms {
	// Ensures the class cannot be instantiated
	private Forms() {
	}

	/**
	 * Returns a JSONArray containing Form objects (each converted to a
	 * JSONObject) of all the forms the given userID is the owner of
	 * 
	 * @param userID
	 * @return
	 */
	public static final JSONArray getFormsUserIsOwnerOf(String userID) {
		JSONArray userForms = new JSONArray();
		List<Form> formArray = DBManager.getInstance().getOwnerForms(userID);
		for (Form form : formArray) {
			userForms.put(form.getJSON());
		}
		return userForms;
	}

	/**
	 * Returns a JSONArray containing Form objects (each converted to a
	 * JSONObject) of all the forms the given userID is the participant of
	 * 
	 * @param userID
	 * @return
	 */
	public static final JSONArray getFormsUserIsParticipantOf(String userID) {
		JSONArray userForms = new JSONArray();
		List<Form> forms = DBManager.getInstance().getParticipantForms(userID);
		for (Form form : forms) {
			userForms.put(form.getJSON());
		}
		return userForms;
	}

	/**
	 * Returns a JSONObject containing basic Form information and all Response
	 * data. Data is used to populate a table on the viewResponses.jsp page.
	 * 
	 * @param formId
	 * 				the ID of the form data to retrieve.
	 * @return
	 */
	public static final JSONObject getResponseRecordsForForm(String formId) {
		JSONObject jsonObject = new JSONObject();

		return jsonObject;
	}
}
