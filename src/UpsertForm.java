

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbconnect.IDBController;
import dbconnect.DBManager;
import form.Form;
import net.sf.json.*;

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
        // TODO Auto-generated constructor stub
    }
    	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IDBController controller;
		String formSource = request.getHeader("Form");		
		
		if(null == formSource || formSource.equals(""))
		{
			throw new ServletException("No form provided for upsert function.");
		}
		
		JSONObject foo = JSONObject.fromObject(formSource);
		
		Form product = new Form(foo.getString("formKey"), foo.getString("formTitle"), foo.getString("formDescription"));
		

		controller = DBManager.getInstance();
		
		controller.upsertForm(product);
		
		//TODO Discuss this handshake
		response.getWriter().print("Success\n");
	}

}
