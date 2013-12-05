import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gdata.client.GoogleService;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.util.AuthenticationException;

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
		IDBController library = DBManager.getInstance();
		
		//Using gmail/iastate accounts
		GoogleService myService = new CalendarService("some_apps");
		String username = request.getParameter("username");
		if(!username.contains("@")){
			username += "@iastate.edu";
			System.out.println(username);
		}
		try {
			myService.setUserCredentials(username,
					 request.getParameter("password"));
			// if success then succes authentication occures
			System.out.println("success authentication");
		} catch (AuthenticationException e) {
			// if not then invalid credentials has been set
			// invalid username or password
			System.out.println("invalid authentication");
		}
		
		
		
		User user = library.fetchUserFromLogin(request.getParameter("username"), request.getParameter("password"));
		
		// If the login is valid
		if (user != null) {
			// Set session to include user object
			request.getSession().setAttribute("user", user);
			
			// Create a "remember me" cookie for logging in
			String rememberMe = request.getParameter("remember");
			if (null != rememberMe && rememberMe.equals("remember-me")) {
				Cookie cookie = new Cookie("userid", Integer.toString(((User) request.getSession().getAttribute("user")).getUserID()));
				cookie.setMaxAge(15);
				response.addCookie(cookie);
			}
			
			/* If the user is responding to an email, there should be a URLEncoded URI here that we can redirect to after they log in */
			// ...
			
			response.sendRedirect("app/index.jsp");
			
		} else {
			// Bad credentials, try again
			request.setAttribute("loginerror", "Invalid username or password.");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
}
