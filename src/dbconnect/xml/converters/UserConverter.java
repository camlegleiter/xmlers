package dbconnect.xml.converters;

import dbconnect.IConverter;
import form.User;

public class UserConverter implements IConverter<User, dbconnect.xml.dao.User> {

	private final static IConverter<User, dbconnect.xml.dao.User> INSTANCE;
	
	static
	{
		INSTANCE = new UserConverter();
	}
	
	private UserConverter()
	{
		//Intentionally left blank.
	}
	
	public static IConverter<User, dbconnect.xml.dao.User> getInstance()
	{
		return INSTANCE;
	}
	
	
	@Override
	public User convert(dbconnect.xml.dao.User other) {
		User retval;
		
		retval = new User();
		
		
		return retval;
	}

	@Override
	public dbconnect.xml.dao.User unconvert(User other) {
		// TODO Auto-generated method stub
		return null;
	}

}
