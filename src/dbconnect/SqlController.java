package dbconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dbconnect.dao.UserDAO;

public class SqlController extends AbstractDBController {
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
			query.append("net_id = ? ");
			query.append("AND password = UNHEX(SHA1(CONCAT(?, salt)))");
			
			// Run the query with the POSTed username and password
			preparedStatement = conn.prepareStatement(query.toString());
			preparedStatement.setString(1, request.getParameter("username"));
			preparedStatement.setString(2, request.getParameter("password"));
			
			results = preparedStatement.executeQuery();
			if (results.next()) {
				// Set all the session information with the user's information
				HttpSession session = request.getSession(true);
				session.setAttribute("userid", results.getString("id"));
				session.setAttribute("net_id", results.getString("net_id"));
				session.setAttribute("firstname", results.getString("firstname"));
				session.setAttribute("lastname", results.getString("lastname"));
				session.setAttribute("email", results.getString("email"));
				
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
	public boolean registerNewUser(UserDAO userDAO, String password) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet results = null;
		
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING, "root", "");
			
			// Build the search query
			StringBuilder query = new StringBuilder();
			query.append("INSERT INTO users ");
			query.append("(firstname, lastname, net_id, email, password, salt) ");
			query.append("VALUES ");
			query.append("(?, ?, ?, ?, ?, UNHEX(SHA1(?)), ?)");
			
			preparedStatement = conn.prepareStatement(query.toString());
			preparedStatement.setString(1, userDAO.getFirstName());
			preparedStatement.setString(2, userDAO.getLastName());
			preparedStatement.setString(3, userDAO.getUserName());
			preparedStatement.setString(4, userDAO.getEmail());
			
			String salt = generateUserSalt(128);
			preparedStatement.setString(5, password + salt);
			preparedStatement.setString(6, salt);
			
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
