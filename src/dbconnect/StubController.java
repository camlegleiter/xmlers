package dbconnect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import utils.Utils;
import form.Form;
import form.User;
import form.questions.CheckQuestion;
import form.questions.RadioQuestion;
import form.questions.SelectQuestion;
import form.questions.TextQuestion;

public class StubController implements IDBController {
	
	public HashMap<String, Form> forms;
	
	public HashMap<String, User> users;
		
	public StubController()
	{
		forms = new HashMap<String, Form>();
		users = new HashMap<String, User>();
		
		putTestUser();
	}

	@Override
	public boolean formExists(String key) {
		return forms.containsKey(key);
	}

	@Override
	public boolean userExists(String key) {
		return users.containsKey(key);
	}

	@Override
	public boolean upsertForm(Form f) {
		boolean retVal;
		if(this.formExists(f.getKey()))
		{
			forms.put(f.getKey(), new Form(f));
			retVal = true;
		}
		else
		{
			retVal = false;
		}
		
		return retVal;
	}

	@Override
	public boolean upsertUser(User user) {
		return null != users.put(user.getUserID(), user);
	}

	@Override
	public Form fetchForm(String id) {
		return new Form(forms.get(id));
	}

	@Override
	public User fetchUser(String id) {
		User requestedUser = users.get(id);
		if(requestedUser == null){
			return null;
		}
		return new User(requestedUser);
	}


	private void putTestUser() {
		User user = new User();
		user.setFirstName("Test");
		user.setLastName("User");
		user.setUserName("testuser");
		user.setEmail("testuser@example.com");
		user.setPassword("password");
		users.put(user.getUserName(), user);
		
		User user2 = new User();
		user2.setFirstName("T");
		user2.setLastName("U");
		user2.setUserName("t");
		user2.setEmail("tp@example.com");
		user2.setPassword("p");
		users.put(user2.getUserName(), user2);
		
		//Form(String key, String title, String description, String owner)
		Form form = new Form("1", "Are you sure about your gender?", "Tell us what your name is and your sex, like 3 times.", user.getUserName());
		TextQuestion textq = new TextQuestion("first", 1, "What's your name?", 5);
		ArrayList<String> answers = new ArrayList<String>();
		answers.add("Female");
		answers.add("Male");
		RadioQuestion radioq = new RadioQuestion("second", 2, "Sex: ", answers);
		CheckQuestion checkq = new CheckQuestion("third", 3, "Sex: ", answers);
		SelectQuestion selectq = new SelectQuestion("fourth", 4, "Sex: ", answers);
		form.add(textq);
		form.add(radioq);
		form.add(checkq);
		form.add(selectq);
		
		Form form2 = new Form(form);
		form2.setKey("2");
		TextQuestion textq2 = new TextQuestion("first", 5, "Type in your Sex to confirm:", 5);
		form2.add(textq2);
		
		forms.put(form.getKey(), form);
		forms.put(form2.getKey(), form);
	}
	
	@Override
	public ArrayList<Form> getOwnerForms(String userID){
		ArrayList<Form> ownerForms = new ArrayList<Form>();
		for (Map.Entry<String, Form> entry : forms.entrySet()) { 
			Form form = (Form) entry.getValue();
			if(form.getOwner().equals(userID)){
				ownerForms.add(form);
			}
		}
		return ownerForms;
	}

	@Override
	public boolean deleteForm(String key) {
		return null != forms.remove(key);
	}

	@Override
	public boolean deleteUser(String key) {
		return null != users.remove(key);
	}

}
