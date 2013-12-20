

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import dbconnect.DBManager;
import dbconnect.IDBController;
import email.EmailParticipants;
import form.Form;
import form.User;
import form.factory.DefaultFactory;

/**
 * Servlet implementation class UpsertResponse
 */
@WebServlet("/UpsertResponse")
public class UpsertResponse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpsertResponse() {
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
		String responseData = request.getParameter("model");

		IDBController controller = DBManager.getInstance();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(responseData);
			
			// Add the userID from the session
			User user = (User) request.getSession().getAttribute("user");
			jsonObject.put("responseOwner", user.getUserID());
			
			Form form = new DefaultFactory().insertResponse(jsonObject, user, true);
			
			controller.upsertForm(form);
			
			//EmailParticipants.emailOwner(form, request.getContextPath(), user, controller.fetchUser(form.getOwnerId()));
			
			jsonObject = new JSONObject().put("success", request.getContextPath() + "/app/index.jsp?r=a");
		} catch (Exception e) {
			jsonObject = new JSONObject().put("error", e.getMessage());
		} finally {
			response.setContentType("application/json");
			response.getWriter().write(jsonObject.toString());
		}
	}

}
