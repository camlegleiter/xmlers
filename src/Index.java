import java.io.IOException;
import java.io.PrintWriter;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import dbconnect.DBManager;
import email.EmailParticipants;
import form.Form;

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
		JSONObject jsonObject = new JSONObject();
		try {
			int formID = Integer.parseInt(request.getParameter("formId"));
			Form form = DBManager.getInstance().fetchForm(formID);

			if (null != form) {
				// TODO: Get form information regarding participants who haven't
				// submitted responses,
				// as well as the last time any emails were sent out to
				// participants.
				// This should only be allowed to execute once every X number of
				// hours/days/etc.

				// if (difference in time now - time last sent > 24 hours
				// (etc...)) {
				EmailParticipants.reemailParticipants(form, request.getContextPath(),  DBManager.getInstance().fetchUser(form.getOwnerId()));
				// else write an error to the user that they cannot reemail so soon
			}

			jsonObject = new JSONObject().put("success", "Participants successfully emailed. Please wait at least 24 hours before attempting to re-email.");
		} catch (Exception e) {
			jsonObject = new JSONObject().put("error", e.getMessage());
		} finally {
			response.setContentType("application/json");
			response.getWriter().write(jsonObject.toString());
		}
	}
}
