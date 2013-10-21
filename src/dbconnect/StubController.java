package dbconnect;

import java.util.HashMap;

import form.Form;
import form.User;

public class StubController implements IDBController {
	
	public HashMap<String, Form> forms;
	
	public HashMap<String, User> users;
		
	public StubController()
	{
		forms = new HashMap<String, Form>();
		users = new HashMap<String, User>();
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
		//TODO remove this call when testing is done.
		putTestUser();
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
