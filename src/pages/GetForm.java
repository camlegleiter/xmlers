package pages;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import form.Form;

/**
 * Servlet implementation class GetForm
 */
@WebServlet(description = "Fetches the HTML that represents a form.", urlPatterns = { "/GetForm" })
public class GetForm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetForm() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int settings = Form.ALL_BITS;
		String formID = request.getHeader("ID");
		String formFormat = request.getHeader("Format");

		if(null == formID || formID.equals(""))
		{
			throw new ServletException("No form specified in request.");
		}
		if(null == formFormat || formFormat.equals(""))
		{
			throw new ServletException("No format specified in request.");
		}
		
		PrintWriter out = response.getWriter();		
		
		Form requestedForm = Form.fetchForm(formID);
		if(null == requestedForm)
		{
			throw new ServletException("Unable to find form: \"" + formID + "\"");
		}
		
		switch(formFormat)
		{
		case "HTML":
			out.write(requestedForm.getHTML(settings));
			break;
		case "JSON":
			out.write(requestedForm.getJSON(settings));
			break;
		case "XML":
			throw new ServletException("Unimplemented Option Requested: \"" + formFormat +"\"");
		default:
			throw new ServletException("Invalid Option: \"" + formFormat + "\"");
		}
		out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
