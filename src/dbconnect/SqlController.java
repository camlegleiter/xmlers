package dbconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SqlController implements IDBController {
	private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/task_manager";
	
	public SqlController() {
		registerDriver();
	}
	
	@Override
	public boolean checkLogin(HttpServletRequest request) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet results = null;
		
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING, "root", "");
			
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
	
	/**
	 * Initializes a new JDBC driver connection
	 */
	private void registerDriver() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean registerNewUser(HttpServletRequest request) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet results = null;
		
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING, "root", "");
			
			// Build the search query
			StringBuilder query = new StringBuilder();
			query.append("INSERT INTO users ");
			query.append("(firstname, lastname, isu_id, net_id, email, password) ");
			query.append("VALUES ");
			query.append("(?, ?, ?, ?, ?, UNHEX(SHA1(?)))");
			
			preparedStatement = conn.prepareStatement(query.toString());
			preparedStatement.setString(1, request.getParameter("first-name"));
			preparedStatement.setString(2, request.getParameter("last-name"));
			preparedStatement.setString(3, request.getParameter("isu_id"));
			preparedStatement.setString(4, request.getParameter("net_id"));
			preparedStatement.setString(5, request.getParameter("email"));
			preparedStatement.setString(6, request.getParameter("password"));
			return preparedStatement.execute();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (null != results) results.close();
				if (null != preparedStatement) preparedStatement.close();
				if (null != conn) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
