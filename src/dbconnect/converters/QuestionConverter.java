package dbconnect.converters;

import dbconnect.dao.VariadicBooleanQuestion;
import form.questions.AbstractVariadicQuestion;
import form.questions.CheckQuestion;
import form.questions.RadioQuestion;
import form.questions.SelectQuestion;

public class QuestionConverter implements IConverter<form.questions.AbstractVariadicQuestion, VariadicBooleanQuestion> {
	
	public static final String SELECT_STRING = "select";
	public static final String RADIO_STRING = "radio";
	public static final String CHECK_STRING  = "check";
	
	@Override
	public AbstractVariadicQuestion convert(VariadicBooleanQuestion other) {
		int id;
		int priority;
		String prompt;
		Iterable<String> options;
		AbstractVariadicQuestion retval;
		
		id = Integer.parseInt(other.getId());
		priority = other.getPriority().intValue();
		prompt = other.getPrompt();
		options = other.getOption();
		
		switch(other.getType())
		{
		case CHECK_STRING:
			retval = new form.questions.CheckQuestion(id, priority, prompt, options);
			break;
		case RADIO_STRING:
			retval = new form.questions.RadioQuestion(id, priority, prompt, options);
			break;
		case SELECT_STRING:
			retval = new form.questions.SelectQuestion(id, priority, prompt, other.isAllowMultiple(), options);
			break;
		default:
			throw new IllegalArgumentException("An unsupported type of VariadicBooleanQuestion was provided.");
		}

		return retval;
	}

	@Override
	public VariadicBooleanQuestion unconvert(AbstractVariadicQuestion other) {
		VariadicBooleanQuestion retval;
		
		retval = new VariadicBooleanQuestion();
		
		if(other instanceof SelectQuestion)
		{
			retval.setType(SELECT_STRING);
		}
		else if(other instanceof RadioQuestion)
		{
			retval.setType(RADIO_STRING);
		}
		else if(other instanceof CheckQuestion)
		{
			retval.setType(CHECK_STRING);
		}
		else
		{
			throw new IllegalArgumentException("An unsupported type of question was passed to unconvert.");
		}
		
		return retval;
	}

}
