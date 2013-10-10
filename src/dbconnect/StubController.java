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
		return new User(users.get(id));
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
