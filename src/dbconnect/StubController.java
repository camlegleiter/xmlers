package dbconnect;

import java.util.HashMap;

import dbconnect.dao.UserDAO;
import form.Form;
import form.questions.Question;
import form.questions.QuestionResponse;

public class StubController implements IDBController {
	
	public HashMap<String, Form> forms;
	
	public HashMap<String, UserDAO> users;
	
	public HashMap<String, QuestionResponse<?>> responses;
	
	public HashMap<String, Question<?>> questions;
	
	public StubController()
	{
		forms = new HashMap<String, Form>();
		users = new HashMap<String, UserDAO>();
		responses = new HashMap<String, QuestionResponse<?>>();
		questions = new HashMap<String, Question<?>>();
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
	public boolean questionExists(String key) {
		boolean found = false;
		
		outside:
		for(Form f: forms.values())
		{
			for(Question<?> q: f)
			{
				if(q.getId().equals(key))
				{
					found = true;
					break outside;
				}
			}
		}
		
		return found;
	}

	@Override
	public boolean responseExists(String key) {
		return responses.containsKey(key);
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
	public boolean upsertUser(UserDAO user) {
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
