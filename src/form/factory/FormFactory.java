package form.factory;

import org.json.JSONObject;

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
	 */
	public abstract Form BuildForm(JSONObject src);
	
	/**
	 * Determines which type of question the JSON Object represents, if any,
	 * and builds the appropriate question based off of that.
	 * @param src
	 * @return
	 */
	public abstract Question<?> BuildQuestion(JSONObject src);
}
