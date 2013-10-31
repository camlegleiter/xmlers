package dbconnect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import form.Form;
import form.ResponseForm;
import form.User;
import form.questions.CheckQuestion;
import form.questions.RadioQuestion;
import form.questions.RadioQuestionResponse;
import form.questions.SelectQuestion;
import form.questions.TextQuestion;
import form.questions.TextResponse;

public class StubController implements IDBController {
	
	public Map<Integer, Form> forms;
	public Map<Integer, User> users;
	
	private static int FORM_ID;
	private static int USER_ID;
		
	public StubController()
	{
		// Ensure no concurrency issues arise when reading/writing
		forms = Collections.synchronizedMap(new HashMap<Integer, Form>());
		users = Collections.synchronizedMap(new HashMap<Integer, User>());
		
		createStubData();
	}

	@Override
	public boolean formExists(int formId) {
		return forms.containsKey(formId);
	}

	@Override
	public boolean userExists(int userId) {
		return users.containsKey(userId);
	}

	@Override
	public boolean upsertForm(Form form) {
		if (!forms.containsKey(form.getFormId()))
			form.setFormId(FORM_ID++);
		forms.put(form.getFormId(), form);
		return true;
	}

	@Override
	public void upsertResponseForm(ResponseForm responseform) {
		Form parentForm = forms.get(responseform.getParentFormId());
		parentForm.add(responseform);		
	}

	@Override
	public boolean upsertUser(User user) {
		if (!users.containsKey(user.getUserID()))
			user.setUserID(USER_ID++);
		users.put(user.getUserID(), user);
		return true;
	}

	@Override
	public Form fetchForm(int formId) {
		return forms.get(formId);
	}

	@Override
	public User fetchUser(int userId) {
		return users.get(userId);
	}
	
	@Override
	public User fetchUserByUsername(String username) {
		for (User user : users.values())
			if (user.getUserName().equals(username))
				return user;
		return null;
	}
	
	@Override
	public User fetchUserByEmail(String email) {
		for (User user : users.values())
			if (user.getEmail().equals(email))
				return user;
		return null;
	}
	
	@Override
	public User fetchUserFromLogin(String username, String password) {
		for (User user : users.values())
			if (user.getUserName().equals(username) &&
					user.checkPassword(password))
				return user;
		return null;
	}

	private void createStubData() {
		//Create the Users
		User mainUser = new User();
		mainUser.setFirstName("Test");
		mainUser.setLastName("User");
		mainUser.setUserName("testuser");
		mainUser.setEmail("testuser@example.com");
		mainUser.setPassword("password");
		upsertUser(mainUser);
		User participant1 = new User();
		participant1.setFirstName("T");
		participant1.setLastName("U");
		participant1.setUserName("t");
		participant1.setEmail("tp@example.com");
		participant1.setPassword("p");
		upsertUser(participant1);
		User participant2 = new User();
		participant2.setFirstName("T");
		participant2.setLastName("U");
		participant2.setUserName("tt");
		participant2.setEmail("tp@example.com");
		participant2.setPassword("p");
		upsertUser(participant2);
		
		//FORM 1 CREATION (mainUser is the owner)
		//Create questions for the form
		TextQuestion textQ1 = new TextQuestion(12345, 1, "What's your name?", 5);
		ArrayList<String> answers = new ArrayList<String>();
		answers.add("Female");
		answers.add("Male");
		RadioQuestion radioQ1 = new RadioQuestion(23456, 2, "Sex: ", answers);
		CheckQuestion checkQ1 = new CheckQuestion(34567, 3, "Sex: ", answers);
		SelectQuestion selectQ1 = new SelectQuestion(45678, 4, "Sex: ", false, answers);
		//Create the form using questions
		Form form = new Form(-1, "Are you sure about your gender?", "Tell us what your name is and your sex, like 3 times.", mainUser.getUserID());
		form.add(textQ1);
		form.add(radioQ1);
		form.add(checkQ1);
		form.add(selectQ1);
		//Adding participants
		form.addParticipant(participant1);
		form.addParticipant(participant2);

		//FORM 2 CREATION (mainUser is the owner)
		Form form2 = new Form(form);
		form2.setFormId(2);
		form2.setTitle("Another gender questionnaire");
		form2.add(new TextQuestion(56789, 5, "Type in your Sex to confirm:", 5));
		//Adding participants
		form2.addParticipant(participant1);
		
		//Adding the forms created
		upsertForm(form);
		upsertForm(form2);
		
		//RESPONSES
		//Participant 1 Response
		ResponseForm p1Response = new ResponseForm(1, participant1.getUserID(), participant1.getFullName(), form.getFormId());
		TextResponse p1TextQ1Response = new TextResponse(1, textQ1, participant1);
		p1TextQ1Response.setValue("I am participant 1!");
		p1Response.add(p1TextQ1Response);
		RadioQuestionResponse radioQ1Response = new RadioQuestionResponse(2, radioQ1, participant1);
		CheckQuestion.Entry radioAnswer = radioQ1.new Entry(answers.get(0), true);
		CheckQuestion.Entry radioAnswer2 = radioQ1.new Entry(answers.get(1), false);
		ArrayList<CheckQuestion.Entry> radioQ1responseList = new ArrayList<CheckQuestion.Entry>();
		radioQ1responseList.add(radioAnswer);
		radioQ1responseList.add(radioAnswer2);
		radioQ1Response.setValue(radioQ1responseList);
		p1Response.add(radioQ1Response);
		
		//Participant 2 Response
		ResponseForm p2Response = new ResponseForm(2, participant2.getUserID(), participant2.getFullName(), form.getFormId());
		TextResponse p2TextQ1Response = new TextResponse(1, textQ1, participant1);
		p2TextQ1Response.setValue("I am participant 2!");
		p2Response.add(p2TextQ1Response);
		
		//Adding the responses to the form
		form.add(p1Response);
		form.add(p2Response);
	}
	
	@Override
	public List<Form> getOwnerForms(int userId){
		List<Form> ownerForms = new ArrayList<Form>();
		for (Map.Entry<Integer, Form> entry : forms.entrySet()) { 
			Form form = (Form) entry.getValue();
			if(form.getOwnerId() == userId){
				ownerForms.add(form);
			}
		}
		return ownerForms;
	}
	
	@Override
	public List<Form> getParticipantForms(int userId) {
		List<Form> participantForms = new ArrayList<Form>();
		for (Map.Entry<Integer, Form> entry : forms.entrySet()) {
			Form form = (Form) entry.getValue();
			if (form.containsParticipant(userId))
				participantForms.add(form);
		}
		return participantForms;
	}
	@Override
	public List<ResponseForm> getResponseForms(int formId) {
		Form form = forms.get(formId);
		return form.getResponses();
	}

	@Override
	public boolean deleteForm(int key) {
		return null != forms.remove(key);
	}

	@Override
	public boolean deleteUser(int key) {
		return null != users.remove(key);
	}
}
