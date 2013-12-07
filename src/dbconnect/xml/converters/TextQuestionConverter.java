package dbconnect.xml.converters;

import java.math.BigInteger;

import form.questions.TextQuestion;

public class TextQuestionConverter implements
		IConverter<TextQuestion, dbconnect.xml.dao.TextQuestion> {
	
	private final static TextQuestionConverter INSTANCE;
	
	static
	{
		INSTANCE = new TextQuestionConverter();
	}
	
	private TextQuestionConverter()
	{
		//Intentionally Left Blank.
	}

	public static TextQuestionConverter getInstance()
	{
		return INSTANCE;
	}
	
	@Override
	public TextQuestion convert(dbconnect.xml.dao.TextQuestion other) {
		
		TextQuestion cq;
		
		cq = new TextQuestion();
		
		cq.setId(other.getId().intValue());
		cq.setMaxLength(other.getMaxLength().intValue());
		cq.setPrompt(other.getPrompt());
		cq.setPosition(other.getPriority().intValue());
		cq.setDefaultAnswer(other.getDefault());
		
		return cq;
	}

	@Override
	public dbconnect.xml.dao.TextQuestion unconvert(TextQuestion other) {
		
		dbconnect.xml.dao.TextQuestion cq;
		
		cq = new dbconnect.xml.dao.TextQuestion();
		
		cq.setPrompt(other.getPrompt());
		cq.setId(BigInteger.valueOf(other.getId()));
		cq.setMaxLength(new Long(other.getMaxLength()));
		cq.setPriority(new Long(other.getPosition()));
		cq.setDefault(other.getDefaultAnswer());
		
		return cq;
	}

}
