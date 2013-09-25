package form.questions.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import form.questions.TextQuestion;

public class TextQuestionTests {


	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void happyConstructor1()
	{
		TextQuestion q1 = new TextQuestion("1", 1, "Howdy", 255);
		assertEquals("1", q1.getId());
		assertEquals("Howdy", q1.getPrompt());
		assertEquals(1, q1.getPosition());
		assertEquals(255, q1.getMaxLength());
	}
	
	@Test
	public void happyConstructor2()
	{
		String prompt = "Doodle";
		TextQuestion q2 = new TextQuestion("2", 1, prompt, 253);
		assertEquals("2", q2.getId());
		assertEquals(prompt, q2.getPrompt());
		assertEquals(253, q2.getMaxLength());
		assertEquals(1, q2.getPosition());
	}
}
