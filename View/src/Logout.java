

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Logout
 */
@WebServlet("/app/logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logout() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Check if the user clicked the "logout" button
		String logout = request.getParameter("logout");
		if (null != logout && logout.equals("logout")) {
			request.getSession().invalidate();
			deleteRememberMeCookie(request);
			
			// Doubly ensures that the headers prevent users from going back after logging out
			// "Doubly" in the sense that the LoginFilter also sets the headers as well.
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	
	/**
	 * Used to specifically remove the "Remember Me" cookie that may be created on user login.
	 * 
	 * Calling cookie.setMaxAge(0) will inform the client/server to destroy the cookie.
	 * 
	 * @param request
	 */
	private void deleteRememberMeCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies(); // request is an instance of type HttpServletRequest

		if (null != cookies) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie c = cookies[i];
				if (c.getName().equals("userid")) {
					c.setMaxAge(0);
					return;
				}
			}
		}
	}

}
