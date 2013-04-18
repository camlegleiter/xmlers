import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import email.EmailParticipants;

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

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String viewRecords = request.getParameter("viewRecords");
		String editForm = request.getParameter("editForm");
		String reemailParticipants = request.getParameter("reemailParticipants");
		
		String formID = request.getParameter("formid");
		
		
		
		if (null != viewRecords) {
			response.sendRedirect(request.getContextPath() + "/app/view.jsp?formid=" + formID);
		} else if (null != editForm) {
			response.sendRedirect(request.getContextPath() + "/app/edit.jsp?formid=" + formID);
		} else if (null != reemailParticipants) {
			// This should only be allowed to execute once every X number of hours/days/etc.
			EmailParticipants.reemailParticipants(formID);
		}

	}
}
