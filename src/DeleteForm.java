
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import dbconnect.DBManager;
import dbconnect.IDBController;

/**
 * Servlet implementation class DeleteForm
 */
@WebServlet("/DeleteForm")
public class DeleteForm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteForm() {
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
			int formID = Integer.parseInt(request.getParameter("formID"));

			IDBController controller = DBManager.getInstance();
			controller.deleteForm(formID);
			
			jsonObject = new JSONObject().put("success",
					"The form was successfully deleted.");
		} catch (Exception e) {
			jsonObject = new JSONObject().put("error", "There was an issue attempting to delete this form. Please try again later.");
		} finally {
			response.setContentType("application/json");
			response.getWriter().write(jsonObject.toString());
		}
	}
}
