package dbconnect;

import java.util.List;

import form.Form;
import form.User;

public class XmlController implements IDBController {

	@Override
	public boolean formExists(String formId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean userExists(String userId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean upsertForm(Form form) {
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

	@Override
	public List<Form> getOwnerForms(String userID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Form> getParticipantForms(String userID) {
		// TODO Auto-generated method stub
		return null;
	}
}
