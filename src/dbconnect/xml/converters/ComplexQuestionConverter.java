package dbconnect.xml.converters;

import dbconnect.IConverter;
import form.questions.ComplexQuestion;

public class ComplexQuestionConverter implements
		IConverter<ComplexQuestion, dbconnect.xml.dao.ComplexQuestion> {

	private final static ComplexQuestionConverter INSTANCE;
	
	static
	{
		INSTANCE = new ComplexQuestionConverter();
	}
	
	private ComplexQuestionConverter()
	{
		
	}
	
	public static ComplexQuestionConverter getInstance()
	{
		return INSTANCE;
	}
	
		//Intent
	@Override
	public ComplexQuestion convert(dbconnect.xml.dao.ComplexQuestion other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public dbconnect.xml.dao.ComplexQuestion unconvert(ComplexQuestion other) {
		// TODO Auto-generated method stub
		return null;
	}

}
