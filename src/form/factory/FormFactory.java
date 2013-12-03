package form.factory;

import org.json.JSONException;
import org.json.JSONObject;

import dbconnect.IDBController;
import form.Form;
import form.questions.Question;

/**
 * Performs the inverse function of the visitor libraries. Instead of building
 * serialized versions of forms, this takes serialized versions and instantiates
 * forms.
 * @author mstrobel
 *
 */
public abstract class FormFactory {
	/**
	 * Given a JSONObject as source, instantiates a form.
	 * @param src A JSONObject that details the contents of a form.
	 * @return A fully instantiated Form.
	 * @throws JSONException
	 */
	public abstract Form buildForm(JSONObject src, IDBController controller) throws JSONException;
	
	/**
	 * Determines which type of question the JSON Object represents, if any,
	 * and builds the appropriate question based off of that.
	 * @param src
	 * @param weight
	 * @return
	 * @throws JSONException
	 * @throws IllegalArgumentException
	 * 				if the type of question is not defined correctly
	 */
	public abstract Question<?> buildQuestion(JSONObject src, int weight) throws JSONException;
}
