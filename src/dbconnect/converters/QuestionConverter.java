package dbconnect.converters;

import dbconnect.dao.VariadicBooleanQuestion;
import form.questions.AbstractVariadicQuestion;

public class QuestionConverter implements IConverter<form.questions.AbstractVariadicQuestion, VariadicBooleanQuestion> {
	
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
		case "check":
			retval = new form.questions.CheckQuestion(id, priority, prompt, options);
			break;
		case "radio":
			retval = new form.questions.RadioQuestion(id, priority, prompt, options);
			break;
		case "select":
			retval = new form.questions.SelectQuestion(id, priority, prompt, other.isAllowMultiple(), options);
			break;
		default:
			retval = null;
			break;
		}

		return retval;
	}

	@Override
	public VariadicBooleanQuestion unconvert(AbstractVariadicQuestion other) {
		// TODO Auto-generated method stub
		return null;
	}

}
