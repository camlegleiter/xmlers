
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbconnect.DBManager;
import dbconnect.IDBController;
import form.User;

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
		// TODO:
		// - Check that password and password-check match
		// - Check that there isn't a user with the username existing
		// - Add the user to the database
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String passwordCheck = request.getParameter("password-check");
		if (!password.equals(passwordCheck)) {
			returnError(request, response, "Passwords do not match, please try again.");
		}
		
		IDBController library = DBManager.getInstance();
		if (library.fetchUser(username) != null) {
			returnError(request, response, "A user with that username already exists.");
		}
		
		User user = new User();
		user.setFirstName((String) request.getParameter("first-name"));
		user.setFirstName((String) request.getParameter("last-name"));
		user.setFirstName(username);
		user.setFirstName((String) request.getParameter("email"));
		user.setPassword(password);
		
		library.upsertUser(user);
		
		request.getSession().setAttribute("user", user);
		response.sendRedirect("app/index.jsp");
	}
	
	private void returnError(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
		request.setAttribute("registererror", message);
		request.getRequestDispatcher("login.jsp#register").forward(request, response);
	}
}
