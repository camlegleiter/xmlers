package utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import dbconnect.DBManager;
import form.Form;

public final class Utils {
	// Ensures the class cannot be instantiated
	private Utils() {
	}

	/**
	 * Returns <code>true</code> if the given string is null or has length == 0.
	 * 
	 * @param str
	 * @return
	 */
	public static final boolean isNullOrEmpty(final String str) {
		return (str == null || str.isEmpty());
	}

	/**
	 * Returns <code>true</code> if the given string is null or the contents of
	 * the given string contain only whitespace characters.
	 * 
	 * @param str
	 * @return
	 */
	public static final boolean isNullOrWhitespace(final String str) {
		return (str == null || str.trim().isEmpty());
	}

	/**
	 * <p>
	 * Concatenates all of the strings in value using the delimiter value.
	 * </p>
	 * 
	 * <p>
	 * For example, if separator is ", " and the elements of value are "apple",
	 * "orange", "grape", and "pear", join(separator, value) returns
	 * "apple, orange, grape, pear".
	 * </p>
	 * 
	 * <p>
	 * If separator is null, an empty string is used instead. If any element in
	 * value is null, an empty string is used instead.
	 * </p>
	 * 
	 * @param delimiter
	 *            The string to use as a delimiter. delimiter is included in the
	 *            returned string only if value has more than one element.
	 * @param value
	 *            An array that contains the elements to concatenate.
	 * @return A string that consists of the elements in value delimited by the
	 *         delimiter string. If value is an empty array, the method returns
	 *         an empty string.
	 * 
	 * @throws IllegalArgumentException
	 *             if value is null
	 */
	public static final String join(final String delimiter, final Object... value) {
		if (value == null) {
			throw new IllegalArgumentException();
		}
		
		if (value.length == 0) {
			return EMPTY_STRING;
		}

		final String del = (delimiter == null) ? EMPTY_STRING : delimiter;

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < value.length; ++i) {
			builder.append((value[i] == null) ? EMPTY_STRING : value[i].toString());
			if (i == value.length - 1)
				return builder.toString();
			builder.append(del);
		}

		return builder.toString();
	}
	
	/**
	 * <p>
	 * Concatenates all of the elements in value using the delimiter value.
	 * </p>
	 * 
	 * <p>
	 * For example, if separator is ", " and the elements of value are "apple",
	 * "orange", "grape", and "pear", join(separator, value) returns
	 * "apple, orange, grape, pear".
	 * </p>
	 * 
	 * <p>
	 * If separator is null, an empty string is used instead. If any element in
	 * value is null, an empty string is used instead.
	 * </p>
	 * 
	 * @param delimiter
	 *            The string to use as a delimiter. delimiter is included in the
	 *            returned string only if value has more than one element.
	 * @param value
	 *            A list that contains the elements to concatenate.
	 * @return A string that consists of the elements in value delimited by the
	 *         delimiter string. If value is an empty list, the method returns
	 *         an empty string.
	 * 
	 * @throws IllegalArgumentException
	 *             if value is null
	 */
	public static final String join(final String delimiter, final List<?> value) {
		if (value == null) {
			throw new IllegalArgumentException();
		}
		
		Iterator<?> it = value.iterator();
		if (!it.hasNext()) {
			return EMPTY_STRING;
		}

		final String del = (delimiter == null) ? EMPTY_STRING : delimiter;

		StringBuilder builder = new StringBuilder();
		for (;;) {
			Object val = it.next();
			builder.append(val == null ? EMPTY_STRING : val.toString());
			if (!it.hasNext())
				return builder.toString();
			builder.append(del);
		}
	}

	private static final String EMPTY_STRING = "";
	/**
	 * Returns a JSONArray containing Form objects (each converted to a JSONObject) of all the forms the given userID is the owner of
	 * @param userID
	 * @return
	 */
	public static final JSONArray getFormsUserIsOwnerOf(String userID){
		JSONArray userForms = new JSONArray();
		
		ArrayList<Form> formArray = DBManager.getInstance().getOwnerForms(userID);
		for(Form form: formArray){
			userForms.put(new JSONObject(form.getJSON()));
		}
		return userForms;
		
	}
	/**
	 * Returns a JSONArray containing Form objects (each converted to a JSONObject) of all the forms the given userID is the participant of
	 * @param userID
	 * @return
	 */

	public static final JSONArray getFormsUserIsParticipantOf(String userID){
		JSONArray userForms = new JSONArray();
		return userForms;
	}
}
