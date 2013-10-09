package dbconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import form.Form;
import form.User;
import form.questions.Question;
import form.questions.QuestionResponse;

public class SqlController implements IDBController {
	private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/task_manager";
	
	public SqlController() {
		registerDriver();
	}
	
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

	public boolean registerNewUser(User userDAO, String password) {
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
			query.append("(?, ?, ?, ?, UNHEX(SHA1(?)), ?)");
			
			preparedStatement = conn.prepareStatement(query.toString());
			preparedStatement.setString(1, userDAO.getFirstName());
			preparedStatement.setString(2, userDAO.getLastName());
			preparedStatement.setString(3, userDAO.getUserName());
			preparedStatement.setString(4, userDAO.getEmail());
			
			//TODO Find missing Salt function!
			//String salt = SingletonDBController.generateUserSalt(128);
			String salt = "tr5r";
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

	@Override
	public boolean formExists(String key) {
		//TODO
		throw new RuntimeException("Function is not yet implemented.");
	}

	@Override
	public boolean userExists(String key) {
		// TODO Auto-generated method stub
		throw new RuntimeException("Function is not yet implemented.");
	}

	@Override
	public boolean upsertForm(Form f) {
		// TODO Auto-generated method stub
		throw new RuntimeException("Function is not yet implemented.");
	}

	@Override
	public boolean upsertUser(User user) {
		// TODO Auto-generated method stub
		throw new RuntimeException("Function is not yet implemented.");
	}


	@Override
	public Form fetchForm(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User fetchUser(String id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean deleteForm(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUser(String key) {
		// TODO Auto-generated method stub
		return false;
	}
}
