import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String viewRecords = request.getParameter("viewRecords");
		String editForm = request.getParameter("editForm");
		String reemailParticipants = request.getParameter("reemailParticipants");
		
		String formID = request.getParameter("formid");

		if (null != formID) {
			if (null != viewRecords) {
				// Redirect the user to the view results page
				response.sendRedirect(request.getContextPath() + "/app/view.jsp?formid=" + formID);
			} else if (null != editForm) {
				// Redirect the user to the edit form page
				response.sendRedirect(request.getContextPath() + "/app/edit.jsp?formid=" + formID);
			} else if (null != reemailParticipants) {
				// TODO: Get form information regarding participants who haven't submitted responses,
				// as well as the last time any emails were sent out to participants.
				// This should only be allowed to execute once every X number of hours/days/etc.
				
				// if (difference in time now - time last sent > 24 hours (etc...)) {
					String[] participants = new String[] {};
					PrintWriter out = response.getWriter();
					
					try {
						//EmailParticipants.reemailParticipants(formID, null, null, participants);
					} catch (IllegalArgumentException e) {
						if (0 == participants.length) {
							out.write("Either all of the participants have successfully responded, or there are no participants for this form.");
						} else {
							// Should not happen if correctly logged in
							out.write("Invalid user name. Please make sure you are logged in.");
						}
						return;
					} //catch (MessagingException e) {
					//	out.write("There was an issue sending out emails to the participants. Please try again later!");
					//	return;
					//}
				// } else {
				// out.write("Error: Participants for a form can only be notified once every XX timeperiod. Please try again later.");
				// }
			}
		}
	}
}
