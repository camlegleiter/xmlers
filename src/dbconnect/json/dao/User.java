package dbconnect.json.dao;

import com.mongodb.BasicDBObject;

public class User extends BasicDBObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1087849333311120116L;
	
	public static final String USER_ID = "userID";
	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String EMAIL = "email";
	public static final String USERNAME = "userName";
	
	public int getUserId() {
		return getInt(USER_ID);
	}
	
	public void setUserId(int userId) {
		put(USER_ID, userId);
	}
	
	public String getFirstName() {
		return getString(FIRST_NAME);
	}
	
	public void setFirstName(String firstName) {
		put(FIRST_NAME, firstName);
	}
	
	public String getLastName() {
		return getString(LAST_NAME);
	}
	
	public void setLastName(String lastName) {
		put(LAST_NAME, lastName);
	}
	
	public String getEmail() {
		return getString(EMAIL);
	}
	
	public void setEmail(String email) {
		put(EMAIL, email);
	}
	
	public String getUserName() {
		return getString(USERNAME);
	}
	
	public void setUserName(String userName) {
		put(USERNAME, userName);
	}

}
