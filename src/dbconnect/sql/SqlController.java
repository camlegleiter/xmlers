package dbconnect.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dbconnect.IDBController;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import form.Form;
import form.User;

public class SqlController implements IDBController {
	private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/task_manager";
	
	public SqlController() {
		registerDriver();
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
	public boolean formExists(int key) {
		//TODO
		throw new NotImplementedException();
	}

	@Override
	public boolean userExists(int key) {
		// TODO Auto-generated method stub
		throw new NotImplementedException();
	}

	@Override
	public boolean upsertForm(Form f) {
		// TODO Auto-generated method stub
		throw new NotImplementedException();
	}

	@Override
	public boolean upsertUser(User user) {
		// TODO Auto-generated method stub
		throw new NotImplementedException();
	}


	@Override
	public Form fetchForm(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User fetchUser(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public User fetchUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public User fetchUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public User fetchUserFromLogin(String username, String password) {
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
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			
			results = preparedStatement.executeQuery();
			if (results.next()) {
				// Set all the session information with the user's information
				User user = new User();
				user.setUserID(results.getString("id"));
				user.setUserName(results.getString("net_id"));
				user.setFirstName(results.getString("firstname"));
				user.setLastName(results.getString("lastname"));
				user.setEmail(results.getString("email"));
				
				return user;
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
		
		return null;
	}

	@Override
	public boolean deleteForm(int key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUser(int key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Form> getOwnerForms(int userID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Form> getParticipantForms(int userID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNewQuestionId() {
		// TODO Auto-generated method stub
		return 0;
	}
}
