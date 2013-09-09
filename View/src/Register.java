

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbconnect.IDBController;
import dbconnect.SqlController;
import dbconnect.dao.UserDAO;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IDBController library = new SqlController();
		
		UserDAO newUser = new UserDAO();
		newUser.setFirstName((String) request.getAttribute("first-name"));
		newUser.setFirstName((String) request.getAttribute("last-name"));
		newUser.setFirstName((String) request.getAttribute("username"));
		newUser.setFirstName((String) request.getAttribute("email"));
		
		if (library.registerNewUser(newUser, (String) request.getAttribute("password"))) {
			
		}
	}

	/**
	 * Validates the parameters passed in the given request.
	 * @param request
	 * @return
	 */
	private boolean validateCredentials(HttpServletRequest request) {
		return false;
	}
}
