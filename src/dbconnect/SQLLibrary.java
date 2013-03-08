package dbconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SQLLibrary implements ILibrary {
	public SQLLibrary() {
		registerDriver();
	}
	
	@Override
	public boolean checkLogin(HttpServletRequest request) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet results = null;
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/task_manager", "root", "");
			
			// Build the search query to see if the login is good
			StringBuilder query = new StringBuilder();
			query.append("SELECT * FROM users WHERE ");
			query.append("username = ? ");
			query.append("AND password = UNHEX(SHA1(?))");
			
			// Run the query with the POSTed username and password
			preparedStatement = conn.prepareStatement(query.toString());
			preparedStatement.setString(1, request.getParameter("username"));
			preparedStatement.setString(2, request.getParameter("password"));
			
			results = preparedStatement.executeQuery();
			if (results.next()) {
				// Set all the session information with the user's information
				HttpSession session = request.getSession(true);
				if (session.isNew()) {
					session.setAttribute("userid", results.getString("id"));
					session.setAttribute("username", results.getString("username"));
					session.setAttribute("firstname", results.getString("firstname"));
					session.setAttribute("lastname", results.getString("lastname"));
					session.setAttribute("email", results.getString("email"));
				}
				
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != results) results.close();
				if (null != preparedStatement) preparedStatement.close();
				if (null != conn) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	private void registerDriver() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	private UserDAO populateUserDAO(ResultSet results) throws SQLException {
//		UserDAO userDAO = new UserDAO();
//		userDAO.setId(results.getInt("id"));
//		userDAO.setFirstname(results.getString("firstname"));
//		userDAO.setLastname(results.getString("lastname"));
//		userDAO.setId(results.getInt("isu_id"));
//		userDAO.setUsername(results.getString("username"));
//		userDAO.setFirstname(results.getString("email"));
//		
//		return userDAO;
//	}
}
