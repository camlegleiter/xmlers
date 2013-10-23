import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbconnect.DBManager;
import dbconnect.IDBController;
import form.User;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
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
		String password;
		String userID;
		IDBController library = DBManager.getInstance();
		User user;
		
		userID = request.getParameter("username"); //request.getHeader("userID");
		password =  request.getParameter("password");// request.getHeader("password");
		user = library.fetchUser(userID);
		
		// If the login is valid
		if (user != null && user.checkPassword(password)) {
			// Create a "remember me" cookie for logging in
			String rememberMe = request.getParameter("remember");
			if (null != rememberMe && rememberMe.equals("remember-me")) {
				Cookie cookie = new Cookie("userid", request.getSession().getAttribute("userid").toString());
				cookie.setMaxAge(15);
				response.addCookie(cookie);
			}
			
			/* If the user is responding to an email, there should be a URLEncoded URI here that we can redirect to after they log in */
			// ...
			
			// No errors, send back an empty string
			response.getWriter().write("");
			//request.getRequestDispatcher("app/index.jsp").forward(request, response);
			
		} else {
			// Bad credentials, try again
			response.getWriter().write("Invalid username or password.");
		}
	}
}
