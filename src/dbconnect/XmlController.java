package dbconnect;

import form.Form;
import form.User;

public class XmlController implements IDBController {

	@Override
	public boolean formExists(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean userExists(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean upsertForm(Form f) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean upsertUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Form fetchForm(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User fetchUser(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteForm(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUser(String key) {
		// TODO Auto-generated method stub
		return false;
	}
}
