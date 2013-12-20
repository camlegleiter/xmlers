

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
 * Servlet implementation class UpsertForm
 */
@WebServlet("/UpsertForm")
public class UpsertForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpsertForm() {
        super();
    }
    	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String formData = (String) request.getParameter("model");
		boolean isEdit = Boolean.parseBoolean(request.getParameter("isEdit"));

		IDBController controller = DBManager.getInstance();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(formData);
			
			// Add the userID from the session
			User user = (User) request.getSession().getAttribute("user");
			jsonObject.put("formOwner", user.getUserID());
			
			Form form = new DefaultFactory().buildForm(jsonObject, controller);
			controller.upsertForm(form);
			
			if (!isEdit){
				//EmailParticipants.emailParticipants(form, request.getContextPath(), user); 
			}
			
			String message = isEdit ? "?m=u" : "?m=a";
			jsonObject = new JSONObject().put("success", request.getContextPath() + "/app/index.jsp" + message);

		} catch (Exception e) {
			jsonObject = new JSONObject().put("error", e.getMessage());
		} finally {
			response.setContentType("application/json");
			response.getWriter().write(jsonObject.toString());
		}
	}
}
