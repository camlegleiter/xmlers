package form.questions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import form.User;
import form.questions.AbstractVariadicQuestion.Entry;

public abstract class VariadicQuestionResponse extends QuestionResponse<List<Entry>> {

	public VariadicQuestionResponse(String key, AbstractVariadicQuestion parent,
			User author) {
		super(key, parent, author);
	}

	/**
	 * Sets the list of responses to this field of questions.
	 * @param value A list of options. Any option contained in the parent, that is not provided
	 * in this list is assumed to be false.
	 */
	@Override
	public void setValue(List<Entry> value)
	{
		if(((AbstractVariadicQuestion) this.getParent()).getVariadic() 
		   && countPositives(value) > 1)
		{
			throw new IllegalArgumentException("Non-variadic questions, such as RadioQuestions and certain SelectQuestions are not able to have variadic responses");
		}
		
		AbstractVariadicQuestion realParent = (AbstractVariadicQuestion) this.getParent();
		
		HashSet<String> allOptions = new HashSet<String>(realParent.getOptions());
		allOptions.removeAll(value);
		
		for(String remaining : allOptions)
		{
			value.add(realParent.new Entry(remaining, false));
		}
		
		this.value = new ArrayList<Entry>(value);
	}
	
	private int countPositives(List<Entry> b)
	{
		int count = 0;
		for(Entry e: b)
		{
			if(true == e.getChecked())
			{
				count++;
			}
		}
		return count;
	}
	
}
