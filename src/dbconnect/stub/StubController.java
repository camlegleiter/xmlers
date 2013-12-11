package dbconnect.stub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import dbconnect.IDBController;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import form.Form;
import form.User;
import form.questions.RadioQuestion;
import form.questions.SelectQuestion;
import form.questions.TextQuestion;

public class StubController implements IDBController {
	
	private final Map<Integer, Form> forms;
	private final Map<Integer, User> users;
	
	private final AtomicInteger formId = new AtomicInteger();
	private final AtomicInteger userId = new AtomicInteger();
	private final AtomicInteger questionId = new AtomicInteger();
	
	public StubController()
	{
		// Ensure no concurrency issues arise when reading/writing
		forms = Collections.synchronizedMap(new HashMap<Integer, Form>());
		users = Collections.synchronizedMap(new HashMap<Integer, User>());
		
		createStubData();
	}
	
	public int getNewQuestionId() {
		return questionId.getAndIncrement();
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
			form.setFormId(formId.getAndIncrement());
		forms.put(form.getFormId(), form);
		return true;
	}

	@Override
	public boolean upsertUser(User user) {
		if (!users.containsKey(user.getUserID())) 
			user.setUserID(userId.getAndIncrement());
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
	public User fetchUserByEmail(String email) {
		for (User user : users.values())
			if (user.getEmail().equals(email))
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
		upsertUser(mainUser);
		
		User participant1 = new User();
		participant1.setFirstName("Test");
		participant1.setLastName("User");
		participant1.setUserName("testuser");
		participant1.setEmail("testuser@example.com");
		upsertUser(participant1);
		
		User participant2 = new User();
		participant2.setFirstName("Other");
		participant2.setLastName("User");
		participant2.setUserName("otheruser");
		participant2.setEmail("otheruser@example.com");
		upsertUser(participant2);
		
		User participant3 = new User();
		participant3.setFirstName("Other");
		participant3.setLastName("User");
		participant3.setUserName("dalia");
		participant3.setEmail("daliaem66@hotmail.com");
		upsertUser(participant3);
		
		User participant4 = new User();
		participant4.setFirstName("Other");
		participant4.setLastName("User");
		participant4.setUserName("cameron");
		participant4.setEmail("cameronl@iastate.edu");
		upsertUser(participant4);
		
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
		form2.isParticipantResponseRequired(false);
		form2.addParticipant(participant1);
		form2.addParticipant(participant2);
		form2.add(new TextQuestion(9876, 1, "What's the title of the book?", 100));
		form2.add(new TextQuestion(8765, 2, "What is the ISBN?", 20));
		
		answers = new ArrayList<String>();
		answers.add("Yes");
		answers.add("No");
		form2.add(new RadioQuestion(7654, 3, "Are the students required to purchase this book for your class?", answers));
		
		//Adding the forms created
		upsertForm(form);
		upsertForm(form2);
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
