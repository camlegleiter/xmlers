package utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class UtilsTest {

	/*
	 * Utils.isNullOrEmpty(String)
	 */
	
	@Test
	public void literalNullReturnsTrue() {
		assertTrue(Utils.isNullOrEmpty(null));
	}
	
	@Test
	public void nullStringReturnsTrue() {
		String str = null;
		assertTrue(Utils.isNullOrEmpty(str));
	}
	
	@Test
	public void emptyStringReturnsTrue() {
		String str = "";
		assertTrue(Utils.isNullOrEmpty(str));
	}
	
	@Test
	public void stringWithCharactersReturnsFalse() {
		String str = "a";
		assertFalse(Utils.isNullOrEmpty(str));
	}
	
	/*
	 * Utils.isNullOrWhitespace(String)
	 */
	
	@Test
	public void stringWithOnlyWhitespaceReturnsTrue() {
		assertTrue(Utils.isNullOrWhitespace(" \t\r\n\b"));
	}
	
	@Test
	public void stringWithAllButOneCharacterWhitespaceReturnsFalse() {
		assertFalse(Utils.isNullOrWhitespace(" \t\r\n\ba"));
	}
	
	
	/*
	 * Utils.join(String, Object...)
	 */
	
	@Test(expected = IllegalArgumentException.class)
	public void nullObjectArrayThrowsException() {
		Object[] obj = null;
		Utils.join("", obj);
	}
	
	@Test
	public void emptyObjectArrayReturnsObjectToString() {
		Object[] obj = new Object[0];
		assertEquals(obj, obj);
	}
	
	@Test
	public void objectArrayWithOneItemAndEmptyStringDelimiterReturnsItemToString() {
		Object[] obj = new Object[] { new String("abcd") };
		assertEquals("abcd", Utils.join("", obj));
	}
	
	@Test
	public void objectArrayWithOneItemAndNonEmptyStringDelimiterReturnsItemToString() {
		Object[] obj = new Object[] { new String("abcd") };
		assertEquals("abcd", Utils.join(", ", obj));
	}
	
	@Test
	public void objectArrayWithTwoItemsAndNullStringDelimiterReturnsConcatenatedString() {
		Object[] obj = new Object[] { new String("abcd"), new String("efgh") };
		assertEquals("abcdefgh", Utils.join(null, obj));
	}
	
	@Test
	public void objectArrayWithTwoItemsAndEmptyStringDelimiterReturnsConcatenatedString() {
		Object[] obj = new Object[] { new String("abcd"), new String("efgh") };
		assertEquals("abcdefgh", Utils.join("", obj));
	}
	
	@Test
	public void objectArrayWithTwoItemsAndNonEmptyStringDelimiterReturnsConcatenatedString() {
		Object[] obj = new Object[] { new String("abcd"), new String("efgh") };
		assertEquals("abcd, efgh", Utils.join(", ", obj));
	}
	
	/*
	 * Utils.join(String, List<?>)
	 */
	
	@Test(expected = IllegalArgumentException.class)
	public void nullListThrowsException() {
		List<?> list = null;
		Utils.join("", list);
	}
	
	@Test
	public void emptyListReturnsEmptyString() {
		List<?> list = new ArrayList<>(0);
		assertEquals("", Utils.join("", list));
	}
	
	@Test
	public void stringListWithOneItemAndEmptyStringDelimiterReturnsItemToString() {
		List<String> list = new ArrayList<>();
		list.add("abcd");
		assertEquals("abcd", Utils.join("", list));
	}
	
	@Test
	public void stringListWithOneItemAndNonEmptyStringDelimiterReturnsItemToString() {
		List<String> list = new ArrayList<>();
		list.add("abcd");
		assertEquals("abcd", Utils.join(", ", list));
	}
	
	@Test
	public void stringListWithTwoItemsAndNullStringDelimiterReturnsConcatenatedString() {
		List<String> list = new ArrayList<>();
		list.add("abcd");
		list.add("efgh");
		assertEquals("abcdefgh", Utils.join(null, list));
	}
	
	@Test
	public void stringListWithTwoItemsAndEmptyStringDelimiterReturnsConcatenatedString() {
		List<String> list = new ArrayList<>();
		list.add("abcd");
		list.add("efgh");
		assertEquals("abcdefgh", Utils.join("", list));
	}
	
	@Test
	public void stringListWithTwoItemsAndNonEmptyStringDelimiterReturnsConcatenatedString() {
		List<String> list = new ArrayList<>();
		list.add("abcd");
		list.add("efgh");
		assertEquals("abcd, efgh", Utils.join(", ", list));
	}
	
	@Test
	public void numberListWithDifferentNumberTypesReturnsCorrectDelimitedString() {
		List<Number> list = new ArrayList<>();
		list.add(3);
		list.add(4.2f);
		list.add(55.3d);
		list.add(0xCAFEBABE);
		assertEquals("3 4.2 55.3 -889275714", Utils.join(" ", list));
	}
}
