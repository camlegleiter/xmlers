import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import dbconnect.DBManager;
import dbconnect.IDBController;
import form.Form;
import form.User;

/**
 * Servlet implementation class UnparticipateForm
 */
@WebServlet("/UnparticipateForm")
public class UnparticipateForm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UnparticipateForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		JSONObject jsonObject = new JSONObject();
		try {
			User user = (User) request.getSession().getAttribute("user");
			System.out.println("The user attribute is " + user.getEmail() + " with ID " + user.getUserID());
			int formID = Integer.parseInt(request.getParameter("formID"));

			IDBController controller = DBManager.getInstance();
			Form form = controller.fetchForm(formID);
			System.out.println("The formID " + formID +" with title " + form.getTitle());
			form.removeParticipant(user);

			controller.upsertForm(form);

			jsonObject = new JSONObject().put("success",
					"You have successfully unparticipated from the form.");
		} catch (Exception e) {
			jsonObject = new JSONObject()
					.put("error",
							"There was an issue attempting to unparticipate from the form. Please try again later.");
		} finally {
			response.setContentType("application/json");
			response.getWriter().write(jsonObject.toString());
		}
	}

}
