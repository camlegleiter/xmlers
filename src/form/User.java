package form;

import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base64;

public class User {
	private int id;
	private String userName;
	private String firstName;
	private String lastName;
	private String email;
	
	public User()
	{
		id = -1;
		userName = "";
		firstName = "";
		lastName = "";
		email = "";
	}
	
	public User(String email){
		id = -1;
		firstName = "";
		lastName = "";
		this.email = email;
		this.userName = email.substring(0, email.indexOf('@'));
	}
	
	public User(User other) {
		this.id = other.id;
		this.userName = other.userName;
		this.firstName = other.firstName;
		this.lastName = other.lastName;
		this.email = other.email;
	}
	
	public int getUserID() {
		return id;
	}
	
	/**
	 * Sets a user's ID.
	 * @param userID
	 */
	public void setUserID(int id) {
		this.id = id;
	}
	
	/**
	 * Sets the user ID
	 * @param userID
	 * @throws NumberFormatException
	 * @deprecated
	 */
	public void setUserID(String id)
	{
		this.id = Integer.parseInt(id);
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Returns the user's full name in the format "firstName lastName"
	 * @return
	 */
	public String getFullName() {
		return new StringBuilder().append(firstName).append(" ").append(lastName).toString();
	}
	

	/**
	 * Creates a salt of a given length for use in storing user passwords in the database.
	 * 
	 * @param length
	 * 			The length of the salt (i.e. the number of bits for the salt). By default,
	 * 			this generates 128-bit salts.
	 * @return
	 * 			A char array containing a secure random salt.
	 */
	protected static String generateUserSalt(int length) {
		if (0 >= length) length = 128;
		
		byte[] bytes = new byte[length];
		StringBuilder salt = new StringBuilder();
		
		new SecureRandom().nextBytes(bytes);
		salt.append(new Base64(128).encodeAsString(bytes));
		
		return salt.toString().substring(0, 128);
	}
}
