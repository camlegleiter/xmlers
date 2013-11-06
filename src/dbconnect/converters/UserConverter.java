package dbconnect.converters;

import form.User;

public class UserConverter implements IConverter<User, dbconnect.dao.User> {

	private final static IConverter<User, dbconnect.dao.User> INSTANCE;
	
	static
	{
		INSTANCE = new UserConverter();
	}
	
	private UserConverter()
	{
		//Intentionally left blank.
	}
	
	public static IConverter<User, dbconnect.dao.User> getInstance()
	{
		return INSTANCE;
	}
	
	
	@Override
	public User convert(dbconnect.dao.User other) {
		User retval;
		
		retval = new User();
		
		
		return retval;
	}

	@Override
	public dbconnect.dao.User unconvert(User other) {
		// TODO Auto-generated method stub
		return null;
	}

}
