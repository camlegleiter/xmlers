package dbconnect;

import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base64;

public abstract class AbstractDBController implements IDBController {
	
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
