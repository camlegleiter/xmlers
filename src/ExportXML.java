import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.json.XML;

import dbconnect.DBManager;
import dbconnect.IDBController;
import form.Form;

/**
 * Servlet implementation class ExportXML
 */
@WebServlet("/ExportXML")
public class ExportXML extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExportXML() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		IDBController controller = DBManager.getInstance();
		JSONObject jsonObject = null;
		try {
			// Get the Form and convert the entire document to XML
			Integer formId = Integer.parseInt(request.getParameter("formId"));
			Form f = controller.fetchForm(formId);
			String xml = XML.toString(f.getJSON(), "form");

			String fileName = generateFileName(f.getTitle());

			// Set the response for downloading a file
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ fileName);

			// Output the file as a byte buffer
			InputStream in = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			byte[] output = new byte[1024];
			while (in.read(output, 0, 1024) != -1) {
				response.getOutputStream().write(output, 0, 1024);
			}
			in.close();
			response.getOutputStream().flush();
			return;

		} catch (Exception e) {
			jsonObject = new JSONObject().put("error", e.getMessage());

			response.setContentType("application/json");
			response.getWriter().write(jsonObject.toString());
			response.getWriter().flush();
			return;
		}
	}

	/**
	 * Creates a file name with the format:
	 * 
	 * FormName_MMDDYYYY.xml
	 * 
	 * @return
	 */
	private static String generateFileName(String formName) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		StringBuilder fileName = new StringBuilder();
		fileName.append(formName.replaceAll("\\s+", "")).append("_")
				.append(calendar.get(Calendar.MONTH) + 1)
				.append(calendar.get(Calendar.DAY_OF_MONTH))
				.append(calendar.get(Calendar.YEAR)).append(".xml");
		return fileName.toString();
	}
}
