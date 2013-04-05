import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Index
 */
@WebServlet("/Index")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Index() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String logout = request.getParameter("logout");
		if (null != logout && logout.equals("logout")) {
			request.getSession().invalidate();
			deleteRememberMeCookie(request);
			
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
