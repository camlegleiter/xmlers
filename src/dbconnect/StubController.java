package dbconnect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import form.Form;
import form.User;
import form.questions.CheckQuestion;
import form.questions.RadioQuestion;
import form.questions.SelectQuestion;
import form.questions.TextQuestion;

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
		User user = new User();
		user.setFirstName("Test");
		user.setLastName("User");
		user.setUserName("testuser");
		user.setEmail("testuser@example.com");
		user.setPassword("password");
		upsertUser(user);
		
		User user2 = new User();
		user2.setFirstName("T");
		user2.setLastName("U");
		user2.setUserName("t");
		user2.setEmail("tp@example.com");
		user2.setPassword("p");
		upsertUser(user2);
		
		Form form = new Form(-1, "Are you sure about your gender?", "Tell us what your name is and your sex, like 3 times.", user.getUserID());
		form.addParticipant(user2);
		
		ArrayList<String> answers = new ArrayList<String>();
		answers.add("Female");
		answers.add("Male");
		
		form.add(new TextQuestion(12345, 1, "What's your name?", 5));
		form.add(new RadioQuestion(23456, 2, "Sex: ", answers));
		form.add(new CheckQuestion(34567, 3, "Sex: ", answers));
		form.add(new SelectQuestion(45678, 4, "Sex: ", true, answers));
		
		Form form2 = new Form(form);
		form2.setFormId(2);
		form2.setTitle("Another gender questionnaire");
		form2.addParticipant(user2);
		
		form2.add(new TextQuestion(56789, 5, "Type in your Sex to confirm:", 5));
		
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
