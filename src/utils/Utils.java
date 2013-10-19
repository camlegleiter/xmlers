package utils;

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
	 *         the empty string "".
	 * 
	 * @throws IllegalArgumentException
	 *             if value is null
	 */
	public static final String join(final String delimiter, final String... value) {
		if (value == null) {
			throw new IllegalArgumentException();
		}

		String del = (delimiter == null) ? EMPTY_STRING : delimiter;

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < value.length; ++i) {
			builder.append((value[i] == null) ? EMPTY_STRING : value[i]);
			if (i == value.length - 1)
				return builder.toString();
			builder.append(del);
		}

		return builder.toString();
	}

	private static final String EMPTY_STRING = "";
}
