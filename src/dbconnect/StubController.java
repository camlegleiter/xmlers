package dbconnect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import form.Form;
import form.User;
import form.questions.AbstractVariadicQuestion.Entry;
import form.questions.CheckQuestion;
import form.questions.CheckQuestionResponse;
import form.questions.RadioQuestion;
import form.questions.RadioQuestionResponse;
import form.questions.SelectQuestion;
import form.questions.SelectQuestionResponse;
import form.questions.TextQuestion;
import form.questions.TextResponse;

public class StubController implements IDBController {
	
	public Map<Integer, Form> forms;
	public Map<Integer, User> users;
	
	private static int FORM_ID;
	private static int USER_ID;
	private static int QUESTION_ID;
	
	private final Object mutex = new Object();
		
	public StubController()
	{
		// Ensure no concurrency issues arise when reading/writing
		forms = Collections.synchronizedMap(new HashMap<Integer, Form>());
		users = Collections.synchronizedMap(new HashMap<Integer, User>());
		
		createStubData();
	}
	
	public int getNewQuestionId() {
		synchronized (mutex) {
			return QUESTION_ID++;
		}
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
		mainUser.setFirstName("Main");
		mainUser.setLastName("User");
		mainUser.setUserName("mainuser");
		mainUser.setEmail("mainuser@example.com");
		mainUser.setPassword("password");
		upsertUser(mainUser);
		
		User participant1 = new User();
		participant1.setFirstName("Test");
		participant1.setLastName("User");
		participant1.setUserName("testuser");
		participant1.setEmail("testuser@example.com");
		participant1.setPassword("password");
		upsertUser(participant1);
		
		User participant2 = new User();
		participant2.setFirstName("Other");
		participant2.setLastName("User");
		participant2.setUserName("otheruser");
		participant2.setEmail("otheruser@example.com");
		participant2.setPassword("password");
		upsertUser(participant2);
		
		//FORM 1 CREATION (mainUser is the owner)
		//Create the form using questions
		Form form = new Form(1, "Basic Questionnaire", "Answer a few questions to tell me about yourself", mainUser.getUserID());
		form.add(new TextQuestion(12345, 1, "What's your name?", 100));
		
		ArrayList<String> answers = new ArrayList<String>();
		answers.add("Female");
		answers.add("Male");
		form.add(new RadioQuestion(23456, 2, "Sex", answers));
		
		form.add(new TextQuestion(34567, 3, "Age", 3));
		
		answers = new ArrayList<String>();
		answers.add("Chocolate");
		answers.add("Vanilla");
		answers.add("Strawberry");
		form.add(new SelectQuestion(45678, 4, "Favorite flavor of Ice Cream", false, answers));
		//Adding participants
		form.addParticipant(participant1);
		form.addParticipant(participant2);

		//FORM 2 CREATION (mainUser is the owner)
		Form form2 = new Form(2, "Textbooks for this semester", "I need information about the textbooks to be used next semester. Please respond with the following information.", mainUser.getUserID());
		form2.add(new TextQuestion(9876, 1, "What's the title of the book?", 100));
		form2.add(new TextQuestion(8765, 2, "What is the ISBN?", 20));
		
		answers = new ArrayList<String>();
		answers.add("Yes");
		answers.add("No");
		form2.add(new RadioQuestion(7654, 3, "Are the students required to purchase this book for your class?", answers));
		
		//Adding the forms created
		upsertForm(form);
		upsertForm(form2);
		
		//RESPONSES
//		TextResponse textQ1R1 = new TextResponse(textQ1, participant1);
//		textQ1R1.setValue("MY name is participant 1");
//		textQ1.insertResponse(participant1.getUserID(), textQ1R1);
//		
//		CheckQuestionResponse checkQ1R1 = new CheckQuestionResponse(checkQ1, participant1);
//		ArrayList<Entry> checkQ1R1answers = new ArrayList<Entry>();
//		Entry ans1 = null;
//		for(String answer: checkQ1.getOptions()){
//			ans1 = checkQ1.new Entry(answer, true);
//			checkQ1R1answers.add(ans1);
//		}
//		checkQ1R1.setValue(checkQ1R1answers);
//		checkQ1.insertResponse(participant1.getUserID(), checkQ1R1);
//		
//		CheckQuestionResponse checkQ1R2 = new CheckQuestionResponse(checkQ1, participant2);
//		ArrayList<Entry> checkQ1R2answers = new ArrayList<Entry>();
//		checkQ1R2answers.add(ans1);
//		checkQ1R2.setValue(checkQ1R2answers);
//		checkQ1.insertResponse(participant2.getUserID(), checkQ1R2);
//		
//		RadioQuestionResponse radioQ1R1 = new RadioQuestionResponse(radioQ1, participant1);
//		ArrayList<Entry> radioQ1R1answer = new ArrayList<Entry>();
//		radioQ1R1answer.add(ans1);
//		radioQ1R1.setValue(radioQ1R1answer);
//		radioQ1.insertResponse(participant1.getUserID(), radioQ1R1);
//		
//		//TODO test select questions that are multi
//		SelectQuestionResponse selectQ1R1 = new SelectQuestionResponse(selectQ1, participant1);
//		ArrayList<Entry> selectQ1R1answer = new ArrayList<Entry>();
//		selectQ1R1answer.add(ans1);
//		selectQ1R1.setValue(selectQ1R1answer);
//		selectQ1.insertResponse(participant1.getUserID(), selectQ1R1);

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
	public boolean deleteForm(int key) {
		return null != forms.remove(key);
	}

	@Override
	public boolean deleteUser(int key) {
		return null != users.remove(key);
	}
}
