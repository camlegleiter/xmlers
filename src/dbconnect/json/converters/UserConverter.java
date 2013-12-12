package dbconnect.json.converters;

import dbconnect.IConverter;

public class UserConverter implements IConverter<form.User, dbconnect.json.dao.User> {

	private final static IConverter<form.User, dbconnect.json.dao.User> INSTANCE;
	static {
		INSTANCE = new UserConverter();
	}
	
	private UserConverter() {
	}
	
	public static IConverter<form.User, dbconnect.json.dao.User> getInstance() {
		return INSTANCE;
	}
	
	@Override
	public form.User convert(dbconnect.json.dao.User other) {
		form.User u = new form.User();
		u.setUserID(other.getUserId());
		u.setFirstName(other.getFirstName());
		u.setLastName(other.getLastName());
		u.setEmail(other.getEmail());
		u.setUserName(other.getUserName());
		
		return u;
	}

	@Override
	public dbconnect.json.dao.User unconvert(form.User other) {
		dbconnect.json.dao.User u = new dbconnect.json.dao.User();
		u.setUserId(other.getUserID());
		u.setFirstName(other.getFirstName());
		u.setLastName(other.getLastName());
		u.setEmail(other.getEmail());
		u.setUserName(other.getUserName());
		
		return u;
	}
}
