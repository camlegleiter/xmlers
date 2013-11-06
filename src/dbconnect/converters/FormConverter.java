package dbconnect.converters;

import dbconnect.dao.TextQuestion;
import dbconnect.dao.VariadicBooleanQuestion;
import form.Form;

public class FormConverter implements IConverter<Form, dbconnect.dao.Form> {

	private final static IConverter<Form, dbconnect.dao.Form> INSTANCE;
		
	static
	{
		INSTANCE = new FormConverter();
	}
	
	private FormConverter()
	{
		//Intentionally Left Blank.
	}
	
	public static IConverter<Form, dbconnect.dao.Form> getInstance()
	{
		return INSTANCE;
	}
	
	@Override
	public Form convert(dbconnect.dao.Form other) {
		
		Form retval;
		
		
		retval = new Form();

		retval.setFormId(Integer.parseInt(other.getId()));
		retval.setDescription(other.getDescription());
		
		for(dbconnect.dao.Question q : other.getQuestions().getTextQuestionOrVariadicBooleanQuestionOrComplexQuestion())
		{
			form.questions.Question<?> cq;
			
			if(q instanceof dbconnect.dao.VariadicBooleanQuestion)
			{
				cq = VariadicQuestionConverter.getInstance().convert((VariadicBooleanQuestion) q);
			}
			else if(q instanceof dbconnect.dao.TextQuestion)
			{
				cq = TextQuestionConverter.getInstance().convert((TextQuestion) q);
			}
			else
			{
				throw new RuntimeException("Questions of type \"" + q.getClass().getName() + "\" are unsupported at the moment."); 
			}
			
			retval.add(cq);
		}
		
		for(dbconnect.dao.Response resp : other.getResponses().getTextResponseOrVariadicBooleanResponseOrComplexQuestionResponse())
		{
			
		}
		
		return retval;
	}

	@Override
	public dbconnect.dao.Form unconvert(Form other) {
		// TODO Auto-generated method stub
		return null;
	}
}
