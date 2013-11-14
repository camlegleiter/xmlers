package form.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import dbconnect.DBManager;
import dbconnect.IDBController;
import form.Form;
import form.User;
import form.factory.DefaultFactory;
import form.questions.CheckQuestion;
import form.questions.CheckQuestionResponse;
import form.questions.RadioQuestion;
import form.questions.RadioQuestionResponse;
import form.questions.SelectQuestion;
import form.questions.SelectQuestionResponse;
import form.questions.TextQuestion;
import form.questions.TextResponse;
import form.questions.AbstractVariadicQuestion.Entry;

/**
 * A form is created where one participant has responded to the form. The test
 * creates a response metadata file that represents the second participant's
 * response.
 * 
 * @author Dalia
 * 
 */
public class DefaultFactoryTestResponseMetadata {
	User mainUser;
	User participant1;
	User participant2;
	Form form;
	IDBController controller = DBManager.getInstance();

	TextQuestion textQ1;
	RadioQuestion radioQ1;
	CheckQuestion checkQ1;
	SelectQuestion selectQ1;

	@Before
	public void init() {
		initUsers();
		intiFormsAndResponses();
	}

	@Test
	public void test() {
		System.out.println("The form before inserting response: \n"
				+ form.getResponseFormJSON());
		JSONObject responseMetadata = createResponseMetadata();
		Form defaultForm = new DefaultFactory().insertResponse(
				responseMetadata, participant2);
		System.out.println("The form after inserting response: \n"
				+ defaultForm.getResponseFormJSON());
		assertTrue(form.getResponseFormJSON().toString()
				.equals(defaultForm.getResponseFormJSON().toString()));
	}

	private void initUsers() {
		mainUser = new User();
		mainUser.setFirstName("Main");
		mainUser.setLastName("User");
		mainUser.setUserName("mainuser");
		mainUser.setEmail("mainuser@example.com");
		mainUser.setPassword("password");
		controller.upsertUser(mainUser);

		participant1 = new User();
		participant1.setFirstName("Test");
		participant1.setLastName("User");
		participant1.setUserName("testuser");
		participant1.setEmail("testuser@example.com");
		participant1.setPassword("password");
		controller.upsertUser(participant1);

		participant2 = new User();
		participant2.setFirstName("Other");
		participant2.setLastName("User");
		participant2.setUserName("otheruser");
		participant2.setEmail("otheruser@example.com");
		participant2.setPassword("password");
		controller.upsertUser(participant2);
	}

	private void intiFormsAndResponses() {
		// FORM 1 CREATION (mainUser is the owner)
		// Create questions for the form
		textQ1 = new TextQuestion(12345, 1, "What's your name?", 100);
		ArrayList<String> answers = new ArrayList<String>();
		answers.add("Female");
		answers.add("Male");
		radioQ1 = new RadioQuestion(23456, 2, "(Radio Question) Sex: ", answers);
		checkQ1 = new CheckQuestion(34567, 3, "(Checkbox Question) Sex: ",
				answers);
		selectQ1 = new SelectQuestion(45678, 4, "(Select Question) Sex: ",
				false, answers);
		// Create the form using questions
		form = new Form(-1, "Are you sure about your gender?",
				"Tell us what your name is and your sex, like 3 times.",
				mainUser.getUserID());
		form.add(textQ1);
		form.add(radioQ1);
		form.add(checkQ1);
		form.add(selectQ1);
		// Adding participants
		form.addParticipant(participant1);
		form.addParticipant(participant2);
		controller.upsertForm(form);

		TextResponse textQ1R1 = new TextResponse(textQ1, participant1);
		textQ1R1.setValue("MY name is participant 1");
		textQ1.insertResponse(participant1.getUserID(), textQ1R1);

		CheckQuestionResponse checkQ1R1 = new CheckQuestionResponse(checkQ1,
				participant1);
		ArrayList<Entry> checkQ1R1answers = new ArrayList<Entry>();
		Entry ans1 = null;
		for (String answer : checkQ1.getOptions()) {
			ans1 = checkQ1.new Entry(answer, true);
			checkQ1R1answers.add(ans1);
		}
		checkQ1R1.setValue(checkQ1R1answers);
		checkQ1.insertResponse(participant1.getUserID(), checkQ1R1);

		RadioQuestionResponse radioQ1R1 = new RadioQuestionResponse(radioQ1,
				participant1);
		ArrayList<Entry> radioQ1R1answer = new ArrayList<Entry>();
		radioQ1R1answer.add(ans1);
		radioQ1R1.setValue(radioQ1R1answer);
		radioQ1.insertResponse(participant1.getUserID(), radioQ1R1);

		// TODO test select questions that are multi
		SelectQuestionResponse selectQ1R1 = new SelectQuestionResponse(
				selectQ1, participant1);
		ArrayList<Entry> selectQ1R1answer = new ArrayList<Entry>();
		selectQ1R1answer.add(ans1);
		selectQ1R1.setValue(selectQ1R1answer);
		selectQ1.insertResponse(participant1.getUserID(), selectQ1R1);
	}

	private JSONObject createResponseMetadata() {
		JSONObject md = new JSONObject();
		md.put("formID", form.getFormId());

		JSONArray responses = new JSONArray();
		JSONObject textr = new JSONObject();
		textr.put("questionID", textQ1.getId());
		textr.put("type", "Textbox");
		textr.put("value", "Participant 2 is my name!");
		responses.put(textr);
		JSONObject radior = new JSONObject();
		radior.put("questionID", radioQ1.getId());
		radior.put("type", "Radio");
		radior.put("value", "Male");
		responses.put(radior);
		JSONObject selectr = new JSONObject();
		selectr.put("questionID", selectQ1.getId());
		selectr.put("type", "Select");
		JSONArray selectAnswers = new JSONArray();
		selectAnswers.put("Female");
		selectr.put("values", selectAnswers);
		responses.put(selectr);
		JSONObject checkr = new JSONObject();
		checkr.put("questionID", checkQ1.getId());
		checkr.put("type", "Checkbox");
		JSONArray checkAnswers = new JSONArray();
		checkAnswers.put("Female");
		checkAnswers.put("Male");
		checkr.put("values", checkAnswers);
		responses.put(checkr);

		md.put("formQuestions", responses);

		return md;
	}
}
