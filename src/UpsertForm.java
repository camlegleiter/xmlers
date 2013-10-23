

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import dbconnect.DBManager;
import dbconnect.IDBController;
import form.Form;
import form.factory.DefaultFactory;
import form.factory.FormFactory;

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
		String formData = (String) request.getAttribute("model");

		IDBController controller = DBManager.getInstance();
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(formData);
			
			// Add the userID from the session
			String userID = (String) request.getSession().getAttribute("userID");
			jsonObject.put("formOwner", userID);
			
			Form form = new DefaultFactory().BuildForm(jsonObject);

			controller.upsertForm(form);
			
			response.sendRedirect("app/index");
			
		} catch (JSONException e) {
			jsonObject = new JSONObject();
			jsonObject.put("error", e.getMessage());
			
			response.setContentType("application/json");
			response.getWriter().write(jsonObject.toString());
		} catch (Exception e) {
			jsonObject = new JSONObject();
			jsonObject.put("error", e.getMessage());
			
			response.setContentType("application/json");
			response.getWriter().write(jsonObject.toString());
		}
	}

}
