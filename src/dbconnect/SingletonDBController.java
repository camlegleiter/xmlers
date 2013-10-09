package dbconnect;

import javax.servlet.http.HttpServletRequest;

import form.Form;
import form.User;
import form.questions.Question;
import form.questions.QuestionResponse;

public class SingletonDBController implements IDBController{
	
	public static final String ENVIRONMENT_STRATEGY = "XMLERS_STRATEGY";
	public static final String XML_STRATEGY = "XML";
	public static final String SQL_STRATEGY = "SQL";
	public static final String DEBUG_STRATEGY = "DEBUG";
	
	/**
	 * The actual instance of a DB Controller that will have it's functionality passed through
	 */
	private static IDBController strategy;
	
	static {
		String strat = System.getenv(ENVIRONMENT_STRATEGY);
		
		switch(strat)
		{
		default:
			System.err.println("Warning:\n\tEnvironment variable \"" + ENVIRONMENT_STRATEGY + "\" was not defined.\n\tAssuming default behavior.\n ");
		case DEBUG_STRATEGY:
			SingletonDBController.strategy = new StubController();
			break;
		case XML_STRATEGY:
			SingletonDBController.strategy = new XmlController();
			break;
		case SQL_STRATEGY:
			SingletonDBController.strategy = new SqlController();
			break;
		}
	}
	
	/**
	 * Gets the currently employed strategy for serializing data.
	 * @return
	 */
	public static IDBController getInstance()
	{
		return strategy;
	}


	public boolean checkLogin(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean registerNewUser(User userDAO, String password) {
		// TODO Auto-generated method stub
		return false;
	}

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
	public boolean responseExists(String key) {
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


	public boolean upsertResponse(String questionID, QuestionResponse<?> resp) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean questionExists(String key) {
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
	public User fetchUser(String id) {
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
