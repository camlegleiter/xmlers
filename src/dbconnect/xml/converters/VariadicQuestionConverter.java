package dbconnect.xml.converters;

import java.math.BigInteger;

import dbconnect.IConverter;
import dbconnect.xml.dao.VariadicBooleanQuestion;
import form.questions.AbstractVariadicQuestion;
import form.questions.CheckQuestion;
import form.questions.RadioQuestion;
import form.questions.SelectQuestion;

public class VariadicQuestionConverter implements IConverter<form.questions.AbstractVariadicQuestion, VariadicBooleanQuestion> {
		
	private static final VariadicQuestionConverter INSTANCE;
	
	static
	{
		INSTANCE = new VariadicQuestionConverter();
	}
	
	private VariadicQuestionConverter()
	{
		//Intentionally Left Blank.
	}
	
	public static VariadicQuestionConverter getInstance()
	{
		return INSTANCE;
	}
	
	@Override
	public AbstractVariadicQuestion convert(VariadicBooleanQuestion other) {
		int id;
		int priority;
		String prompt;
		Iterable<String> options;
		AbstractVariadicQuestion retval;
		
		id = other.getId().intValue();
		priority = other.getPriority().intValue();
		prompt = other.getPrompt();
		options = other.getOption();
		
		switch(other.getType())
		{
		case CheckQuestion.TYPE_STRING:
			retval = new form.questions.CheckQuestion(id, priority, prompt, options);
			break;
		case RadioQuestion.TYPE_STRING:
			retval = new form.questions.RadioQuestion(id, priority, prompt, options);
			break;
		case SelectQuestion.TYPE_STRING:
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
		
		retval.setType(other.getType());
		retval.setAllowMultiple(other.getVariadic());
		retval.setType(other.getType());
		retval.getOption().addAll(other.getOptions());
		retval.setId(BigInteger.valueOf(other.getId()));		
		
		return retval;
	}

}
