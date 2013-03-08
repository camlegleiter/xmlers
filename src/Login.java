import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbconnect.ILibrary;
import dbconnect.SQLLibrary;

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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ILibrary library = new SQLLibrary();
		
		// If the login is valid
		if (library.checkLogin(request)) {
			// Create a "remember me" cookie for logging in
			if (request.getParameter("remember").equals("remember-me")) {
				Cookie cookie = new Cookie("userid", request.getSession().getAttribute("userid").toString());
				cookie.setMaxAge(1);
				response.addCookie(cookie);
			}
			
			// No errors, send back an empty string
			response.getWriter().write("");
			
		} else {
			// Bad credentials, try again
			response.getWriter().write("Invalid username or password.");
		}
	}

}
