package dbconnect.dao;

import form.User;

public class UserConverter implements IConverter<User, dbconnect.dao.User> {

	private final static IConverter<User, dbconnect.dao.User> INSTANCE;
	
	static
	{
		INSTANCE = new UserConverter();
	}
	
	public static IConverter<User, dbconnect.dao.User> getInstance()
	{
		return INSTANCE;
	}
	
	
	@Override
	public User convert(dbconnect.dao.User other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public dbconnect.dao.User unconvert(User other) {
		// TODO Auto-generated method stub
		return null;
	}

}
