package dbconnect;

import javax.servlet.http.HttpServletRequest;

import dbconnect.dao.UserDAO;

public interface IDBController {
	/**
	 * Checks the login to determine if the POSTed user data is valid.
	 * 
	 * @param request
	 * 			The given request data that should contain the POST user credentials.
	 * @return
	 * 			<code>true</code> if the credentials are valid, <code>false</code> otherwise.
	 */
	public boolean checkLogin(HttpServletRequest request);
	
	/**
	 * Attempts to register a new user with parameters passed in the given request.
	 * 
	 * @param request
	 * 			The given request data that should contain the POST user credentials.
	 * @return
	 * 			<code>true</code> if the user was successfully added to the database.
	 */
	public boolean registerNewUser(UserDAO userDAO, String password);
}
