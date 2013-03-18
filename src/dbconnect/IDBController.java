package dbconnect;

import javax.servlet.http.HttpServletRequest;

public interface IDBController {
	/**
	 * Checks the login to determine if the POSTed user data is valid.
	 * 
	 * @param request
	 * 			The given request data that should contain the POST user credentials.
	 * @return
	 * 			Returns <code>true</code> if the credentials are valid, <code>false</code> otherwise.
	 */
	public boolean checkLogin(HttpServletRequest request);
}
