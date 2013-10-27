package form;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import form.questions.*;
import form.questions.tests.*;
import form.visitors.*;

public class VisitMechanismTest {
	
	private IQuestionVisitor visitor;
	private ArrayList<IVisitable> questionList;
	
	@Before
	public void setup()
	{
		visitor = new StubVisitor();
		questionList = new ArrayList<IVisitable>();
	}

	@Test
	public void multipleTextQuestions()
	{
		
		for(int i = 0; i < 10; i++)
		{
			questionList.add(new TextQuestion(i, i, "Prompt " + i, (int)(Math.pow(2, i))));
		}
		
		String result = VisitMechanism.visit(visitor, questionList, "\n");
		Scanner resultScanner = new Scanner(result);
		
		for(IVisitable entry : questionList)
		{
			assertTrue("Missing Token: Text", resultScanner.hasNext());
			assertEquals("Text", resultScanner.next());
			
			assertTrue("Missing Token: Question:", resultScanner.hasNext());
			assertEquals("Question:", resultScanner.next());
			
			assertTrue("Missing Token: " + entry.toString(), resultScanner.hasNext());
			assertEquals(entry.toString(), resultScanner.next());
		}
		
		resultScanner.close();
	}
	
	@Test
	public void oneQuestion()
	{
		questionList.add(new TextQuestion(1, 3, "Prompt 1", 2));
		
		String result = VisitMechanism.visit(visitor, questionList, "\n");
		assertEquals("Text Question: " + questionList.get(0).toString(), result);
	}
	
	@Test
	public void noQuestions()
	{
		String result = VisitMechanism.visit(visitor, questionList, "\n");
		assertEquals("", result);
	}
}
