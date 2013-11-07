package dbconnect.converters;

import form.questions.TextQuestion;

public class TextQuestionConverter implements
		IConverter<TextQuestion, dbconnect.dao.TextQuestion> {
	
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
	public TextQuestion convert(dbconnect.dao.TextQuestion other) {
		
		TextQuestion cq;
		
		cq = new TextQuestion();
		
		cq.setId(Integer.parseInt(other.getId()));
		cq.setMaxLength(other.getMaxLength().intValue());
		cq.setPrompt(other.getPrompt());
		cq.setPosition(other.getPriority().intValue());
		cq.setDefaultAnswer(other.getDefault());
		
		return cq;
	}

	@Override
	public dbconnect.dao.TextQuestion unconvert(TextQuestion other) {
		
		dbconnect.dao.TextQuestion cq;
		
		cq = new dbconnect.dao.TextQuestion();
		
		cq.setPrompt(other.getPrompt());
		cq.setId(Integer.toString(other.getId()));
		cq.setMaxLength(new Long(other.getMaxLength()));
		cq.setPriority(new Long(other.getPosition()));
		cq.setDefault(other.getDefaultAnswer());
		
		return cq;
	}

}
