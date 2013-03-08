import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
		
		if (library.checkLogin(request)) {
			// Set all the session information with the user's information
//			HttpSession session = request.getSession(false);
//			if (session.isNew()) {
//				response.getWriter().write("Setting new session information");
//			} else {
//				response.getWriter().write("Session already set.");
//			}
//			
//			session.invalidate();
			response.getWriter().write("");
			
		} else {
			response.getWriter().write("Invalid username or password.");
		}
	}

}
