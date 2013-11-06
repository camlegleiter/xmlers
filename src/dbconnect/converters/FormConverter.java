package dbconnect.converters;

import form.Form;

public class FormConverter implements IConverter<Form, dbconnect.dao.Form> {

	private final static IConverter<Form, dbconnect.dao.Form> INSTANCE;
	
	
	static
	{
		INSTANCE = new FormConverter();
	}
	
	public static IConverter<Form, dbconnect.dao.Form> getInstance()
	{
		return INSTANCE;
	}
	
	@Override
	public Form convert(dbconnect.dao.Form other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public dbconnect.dao.Form unconvert(Form other) {
		// TODO Auto-generated method stub
		return null;
	}
}
