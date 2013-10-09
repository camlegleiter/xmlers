package dbconnect;
import java.sql.*;

import form.User;

public class CreateDatabase {
	public static void main(String[] args) {
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Connection conn = null;
		Statement statement = null;
		try	{
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "");
			statement = conn.createStatement();
			
			StringBuilder query = new StringBuilder();
			
			// Drop database (if it exists already)
			query.append("DROP DATABASE IF EXISTS task_manager");
			statement.execute(query.toString());
			System.out.println("Database dropped successfully!");
			
			// Re-add the database
			query = new StringBuilder();
			query.append("CREATE DATABASE task_manager");
			statement.execute(query.toString());
			System.out.println("Database created successfully!");
			
			// Select the database for use
			query = new StringBuilder();
			query.append("USE task_manager");
			statement.execute(query.toString());
			System.out.println("Using task_manager");
			
			// Drop existing tables
			query = new StringBuilder();
			query.append("DROP TABLE IF EXISTS users");
			statement.execute(query.toString());
			System.out.println("Tables dropped successfully!");
			
			// Re-add tables
			statement.execute(createUserTable());
			System.out.println("Tables created successfully!");
			
			// Add test login
			createTestUser(conn);
			System.out.println("Test User inserted successfully!");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != statement) statement.close();
				if (null != conn) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String createUserTable() {
		StringBuilder query = new StringBuilder();
		query.append("CREATE TABLE users (");
		query.append("id INT NOT NULL AUTO_INCREMENT, ");
		query.append("firstname VARCHAR(30) NOT NULL, ");
		query.append("lastname VARCHAR(30) NOT NULL, ");
		query.append("net_id VARCHAR(30) NOT NULL, ");
		query.append("email VARCHAR(30) NOT NULL, ");
		query.append("password BINARY(20) NOT NULL, ");
		query.append("salt VARCHAR(128) NOT NULL, ");
		query.append("PRIMARY KEY (id))");
		
		return query.toString();
	}
	
	public static void createTestUser(Connection conn) throws SQLException {
		User user = new User();
		user.setFirstName("Test");
		user.setLastName("User");
		user.setUserName("testuser");
		user.setEmail("testuser@example.com");
		user.setPassword("password");
//		
//		IDBController controller = new SqlController();
//		controller.registerNewUser(user, "password");
		
		IDBController controller = SingletonDBController.getInstance();
		controller.upsertUser(user);
	}
}
