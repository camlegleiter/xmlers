import java.io.IOException;
import java.util.ArrayList;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		IDBController library = DBManager.getInstance();
		String email = request.getParameter("username");

		// This is for the test accounts
		boolean testing = false;
		ArrayList<String> testEmails = new ArrayList<String>();
		testEmails.add("mainuser");
		testEmails.add("testuser");
		testEmails.add("otheruser");
		if (testEmails.contains(email)) {
			testing = true;
			// Set session to include user object
			request.getSession().setAttribute("user",
					library.fetchUserByEmail(email + "@example.com"));
			response.sendRedirect("app/index.jsp");
		}

		if (!testing) {
			// Using iastate accounts
			GoogleService myService = new CalendarService("some_apps");

			if (!email.contains("@")) {
				email += "@iastate.edu";
			}
			try {
				myService.setUserCredentials(email,
						request.getParameter("password"));

				User user = library.fetchUserByEmail(email);

				if (user == null) {
					user = new User(email);
					library.upsertUser(user);
				}

				// Set session to include user object
				request.getSession().setAttribute("user", user);

				// Create a "remember me" cookie for logging in
				String rememberMe = request.getParameter("remember");
				if (null != rememberMe && rememberMe.equals("remember-me")) {
					Cookie cookie = new Cookie("userid",
							Integer.toString(((User) request.getSession()
									.getAttribute("user")).getUserID()));
					cookie.setMaxAge(15);
					response.addCookie(cookie);
				}

				/*
				 * If the user is responding to an email, there should be a
				 * URLEncoded URI here that we can redirect to after they log in
				 */
				// ...

				response.sendRedirect("app/index.jsp");
			} catch (AuthenticationException e) {
				// Bad credentials, try again
				request.setAttribute("loginerror",
						"Invalid username or password. Please, use your IASTATE credentials.");
				request.getRequestDispatcher("login.jsp").forward(request,
						response);
			}
		}

	}
}
