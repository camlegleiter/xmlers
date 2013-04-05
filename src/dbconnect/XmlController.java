package dbconnect;

import javax.servlet.http.HttpServletRequest;

import dbconnect.dao.UserDAO;

public class XmlController extends AbstractDBController {
	@Override
	public boolean checkLogin(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean registerNewUser(UserDAO userDAO, String password) {
		// TODO Auto-generated method stub
		return false;
	}

}
