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
	public static final JSONArray getFormsUserIsOwnerOf(int userId) {
		JSONArray userForms = new JSONArray();
		List<Form> formArray = DBManager.getInstance().getOwnerForms(userId);
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
	public static final JSONArray getFormsUserIsParticipantOf(int userId) {
		JSONArray userForms = new JSONArray();
		List<Form> forms = DBManager.getInstance().getParticipantForms(userId);
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
	 *            the ID of the form data to retrieve.
	 * @return
	 */
	public static final JSONObject getResponseRecordsForForm(int formId) {
		Form f = DBManager.getInstance().fetchForm(formId);
		return f.getJSON(Form.TITLE_BIT
				| Form.KEY_BIT
				| Form.DESCRIPTION_BIT
				| Form.OWNER_BIT
				| Form.QUESTIONS_BIT
				| (Form.RESPONSES_BIT | Form.RESPONSE_OWNER_BIT | Form.RESPONSE_OWNER_NAME_BIT | Form.RESPONSE_BIT));
	}

	/**
	 * Constructs a JSONObject for use in the query.jsp page when building the
	 * administrator's created queries for the form.
	 * 
	 * @param formId
	 *            the ID of the form data
	 * @param queries
	 *            the query string of XPath/XQuery queries defined by the owner
	 *            of the given form
	 */
	public static final JSONObject constructQueryJSON(int formId, String queries) {
		return new JSONObject().put("formId", formId).put("queries", queries);
	}
}
