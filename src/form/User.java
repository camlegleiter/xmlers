package form;


import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base64;

import utils.Utils;

public class User {
	private int id;
	private String userName;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	
	public User()
	{
		id = -1;
		userName = Utils.EMPTY_STRING;
		firstName = Utils.EMPTY_STRING;
		lastName = Utils.EMPTY_STRING;
		email = Utils.EMPTY_STRING;
		password = Utils.EMPTY_STRING;
	}
	
	public User(User other) {
		this.id = other.id;
		this.userName = other.userName;
		this.firstName = other.firstName;
		this.lastName = other.lastName;
		this.email = other.email;
		this.password = other.password;
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
	
	public void setPassword(String passwd)
	{
		//TODO Update this to hash some stuff
		this.password = passwd;
	}
	
	public boolean checkPassword(String passwd)
	{
		//TODO Update this to hash some stuff
		return passwd.equals(this.password);
	}
	
	/**
	 * Returns the user's full name in the format "firstName lastName"
	 * @return
	 */
	public String getFullName() {
		return Utils.join(" ", firstName, lastName);
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
