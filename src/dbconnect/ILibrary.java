package dbconnect;

import javax.servlet.http.HttpServletRequest;

public interface ILibrary {
	public boolean checkLogin(HttpServletRequest request);
}
