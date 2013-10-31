package form.test;

import static org.junit.Assert.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import dbconnect.DBManager;
import dbconnect.IDBController;
import form.Form;
import form.ResponseForm;
import form.User;
import form.factory.DefaultFactory;

public class TextFormTest {
	User mainUser = new User();
	User participant1 = new User();
	User participant2 = new User();
	IDBController controller = DBManager.getInstance();
	JSONObject initialForm;
	JSONObject responseForm;

	@Before
	public void init() {
		initalizeUsers();

		initialForm = prepareInitialForm();
		responseForm = prepareResposeForm();
		// Add the userID from the session
		User user = mainUser;
		initialForm.put("formOwner", user.getUserID());

	}

	@Test
	public void upsertInitialFormTest() {
		Form form = new DefaultFactory().buildForm(initialForm);

		controller.upsertForm(form);

		System.out.println(controller.fetchForm(form.getFormId())
				.getJSONString());
	}

	@Test
	public void upsertResponseTest() {
		responseForm.put("responseID", 10);
		responseForm.put("responseOwner", participant1.getUserID());
		responseForm.put("responseOwnerName", participant1.getFullName());
		
		ResponseForm responseform = new DefaultFactory().buildResponseForm(responseForm, participant1);

		controller.upsertResponseForm(responseform);

		System.out.println(controller.fetchForm(responseform.getParentFormId())
				.getJSONString());
	}

	private void initalizeUsers() {
		mainUser.setFirstName("Test");
		mainUser.setLastName("User");
		mainUser.setUserName("testuser");
		mainUser.setEmail("testuser@example.com");
		mainUser.setPassword("password");
		mainUser.setUserID(123);
		participant1.setFirstName("T");
		participant1.setLastName("U");
		participant1.setUserName("t");
		participant1.setEmail("tp@example.com");
		participant1.setPassword("p");
		participant1.setUserID(456);
		participant2.setFirstName("T");
		participant2.setLastName("U");
		participant2.setUserName("tt");
		participant2.setEmail("tp2@example.com");
		participant2.setPassword("p");
		participant2.setUserID(789);

		controller.upsertUser(mainUser);
		controller.upsertUser(participant1);
		controller.upsertUser(participant2);
	}

	/**
	 * Preparing the initial form the main user creates
	 */
	private JSONObject prepareInitialForm() {
		JSONObject formData = new JSONObject();
		formData.put("formID", 123456789);
		formData.put("formName", "Bridge Keeper Questionnaire");
		formData.put(
				"formDescription",
				"Who would cross the Bridge of Death must answer me these questions three, ere the other side he see.");
		formData.put("formOwner", 987654321);
		formData.put("participantsCanSeeAll", false);
		formData.put("participantsCanEditResponse", false);
		formData.put("participantResponseIsRequired", true);
		JSONArray participants = new JSONArray();
		participants.put("tp@example.com");
		participants.put("tp2@example.com");
		formData.put("formParticipants", participants);
		JSONObject textQuestion1 = new JSONObject();
		textQuestion1.put("id", 234567890);
		textQuestion1.put("type", "Textbox");
		textQuestion1.put("prompt", "What is your first name?");
		textQuestion1.put("maxLength", 1000);
		JSONObject textQuestion2 = new JSONObject();
		textQuestion2.put("id", 234567891);
		textQuestion2.put("type", "Textbox");
		textQuestion2.put("prompt", "What is your last name?");
		textQuestion2.put("maxLength", 1000);
		JSONArray questions = new JSONArray();
		questions.put(textQuestion1);
		questions.put(textQuestion2);
		formData.put("formQuestions", questions);

		return formData;
	}

	private JSONObject prepareResposeForm() {
		JSONObject formData = new JSONObject();
		// ResponseMetadata basic Information
		formData.put("formID", 123456789);
		formData.put("formName", "Bridge Keeper Questionnaire");
		formData.put(
				"formDescription",
				"Who would cross the Bridge of Death must answer me these questions three, ere the other side he see.");
		formData.put("formOwner", 987654321);
		// ResponseMetadata Questions
		JSONObject textQuestion = new JSONObject();
		textQuestion.put("questionID", -1);
		textQuestion.put("type", "Textbox");
		textQuestion.put("maxLength", 1000);
		textQuestion
				.put("prompt",
						"What is your last name?");
		textQuestion.put("value", "LastName");
		JSONArray questions = new JSONArray();
		questions.put(textQuestion);
		formData.put("formQuestions", questions);
		
		return formData;
	}

}
