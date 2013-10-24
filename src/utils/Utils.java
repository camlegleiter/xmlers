package utils;

import java.util.Iterator;
import java.util.List;

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

	public static final String EMPTY_STRING = "";
	
	/**
	 * <p>
	 * Abbreviates a String using ellipses. This will turn
	 * "Now is the time for all good men" into "Now is the time for..."
	 * </p>
	 * <p>
	 * Specifically:
	 * </p>
	 * <ul>
	 * <li>If <code>str</code> is less than <code>maxWidth</code> characters
	 * long, return it.</li>
	 * <li>Else abbreviate it to <code>(substring(str, 0, max-3) + "...")</code>
	 * .</li>
	 * <li>If <code>maxWidth</code> is less than <code>4</code>, throw an
	 * <code>IllegalArgumentException</code>.</li>
	 * <li>In no case will it return a String of length greater than
	 * <code>maxWidth</code>.</li>
	 * </ul>
	 * 
	 * <pre>
	 *  StringUtils.abbreviate(null, *)      = null
	 *  StringUtils.abbreviate("", 4)        = ""
	 *  StringUtils.abbreviate("abcdefg", 6) = "abc..."
	 *  StringUtils.abbreviate("abcdefg", 7) = "abcdefg"
	 *  StringUtils.abbreviate("abcdefg", 8) = "abcdefg"
	 *  StringUtils.abbreviate("abcdefg", 4) = "a..."
	 *  StringUtils.abbreviate("abcdefg", 3) = IllegalArgumentException
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param maxWidth
	 *            maximum length of result String, must be at least 4
	 * @return abbreviated String, <code>null</code> if null String input
	 * @throws java.lang.IllegalArgumentException
	 *             if the width is too small
	 */
	public static String abbreviate(String str, int maxWidth) {
		return abbreviate(str, 0, maxWidth);
	}
	
	/**
	 * <p>
	 * Abbreviates a String using ellipses. This will turn
	 * "Now is the time for all good men" into "...is the time for..."
	 * </p>
	 * <p>
	 * Works like <code>abbreviate(String, int)</code>, but allows you to specify a
	 * "left edge" offset. Note that this left edge is not necessarily going to
	 * be the leftmost character in the result, or the first character following
	 * the ellipses, but it will appear somewhere in the result.
	 * </p>
	 * <p>
	 * In no case will it return a String of length greater than
	 * <code>maxWidth</code>.
	 * </p>
	 * 
	 * <pre>
	 *  StringUtils.abbreviate(null, *, *)                = null
	 *  StringUtils.abbreviate("", 0, 4)                  = ""
	 *  StringUtils.abbreviate("abcdefghijklmno", -1, 10) = "abcdefg..."
	 *  StringUtils.abbreviate("abcdefghijklmno", 0, 10)  = "abcdefg..."
	 *  StringUtils.abbreviate("abcdefghijklmno", 1, 10)  = "abcdefg..."
	 *  StringUtils.abbreviate("abcdefghijklmno", 4, 10)  = "abcdefg..."
	 *  StringUtils.abbreviate("abcdefghijklmno", 5, 10)  = "...fghi..."
	 *  StringUtils.abbreviate("abcdefghijklmno", 6, 10)  = "...ghij..."
	 *  StringUtils.abbreviate("abcdefghijklmno", 8, 10)  = "...ijklmno"
	 *  StringUtils.abbreviate("abcdefghijklmno", 10, 10) = "...ijklmno"
	 *  StringUtils.abbreviate("abcdefghijklmno", 12, 10) = "...ijklmno"
	 *  StringUtils.abbreviate("abcdefghij", 0, 3)        = IllegalArgumentException
	 *  StringUtils.abbreviate("abcdefghij", 5, 6)        = IllegalArgumentException
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param offset
	 * 			  left edge of source String
	 * @param maxWidth
	 *            maximum length of result String, must be at least 4
	 * @return abbreviated String, <code>null</code> if null String input
	 * @throws java.lang.IllegalArgumentException
	 *             if the width is too small
	 */
	public static String abbreviate(String str, int offset, int maxWidth) {
		if (str == null) {
			return null;
		}
		if (maxWidth < 4) {
			throw new IllegalArgumentException("Minimum abbreviation width is 4");
		}
		if (str.length() <= maxWidth) {
			return str;
		}
		if (offset > str.length()) {
			offset = str.length();
		}
		if ((str.length() - offset) < (maxWidth - 3)) {
			offset = str.length() - (maxWidth - 3);
		}
		if (offset <= 4) {
			return str.substring(0, maxWidth - 3) + "...";
		}
		if (maxWidth < 7) {
			throw new IllegalArgumentException("Minimum abbreviation width with offset is 7");
		}
		if ((offset + (maxWidth - 3)) < str.length()) {
			return "..." + abbreviate(str.substring(offset), maxWidth - 3);
		}
		return "..." + str.substring(str.length() - (maxWidth - 3));
	}
}
