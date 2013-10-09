package pages;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbconnect.IDBController;
import dbconnect.DBManager;
import form.Form;
import form.questions.Question;
import form.visitors.IQuestionVisitor;
import form.visitors.XMLVisitor;

/**
 * Servlet implementation class ResponseCollector
 */
@WebServlet("/ResponseCollector")
public class ResponseCollector extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static IQuestionVisitor xmler;
	
	static{
		xmler = new XMLVisitor();
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResponseCollector() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String formID = (String) request.getAttribute("formID");
		IDBController foo = DBManager.getInstance();
		
		Form userResponse = foo.fetchForm(formID);
		
		//Build form
		for(Question q: userResponse)
		{
			String questionResponse = (String) request.getAttribute(q.getId());
			q.setResponse(q.getId(), questionResponse);
						
		}
		//TODO Insert XML Generated in for-loop into the XMLDocument that represents the responses to this form.
		
	}

}
