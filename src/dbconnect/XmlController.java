package dbconnect;

import dbconnect.dao.UserDAO;
import form.Form;
import form.questions.Question;
import form.questions.QuestionResponse;

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
	public boolean upsertUser(UserDAO user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean questionExists(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean responseExists(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean upsertResponse(QuestionResponse<?> resp) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> boolean upsertQuestion(Question<T> question) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Form fetchForm(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDAO fetchUser(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QuestionResponse<?> fetchResponse(String id) {
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
	public boolean deleteResponse(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteQuestion(String key) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
