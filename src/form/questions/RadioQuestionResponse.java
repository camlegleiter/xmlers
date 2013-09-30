package form.questions;

import java.util.List;

import form.questions.CheckQuestion.Entry;

public class RadioQuestionResponse extends CheckQuestionResponse {

	public RadioQuestionResponse(String key, Question<List<Entry>> parent) {
		super(key, parent);
	}
	
	/**
	 * Validates that there is no more than one option selected for the radio button.
	 */
	@Override
	public void setValue(List<CheckQuestion.Entry> answers)
	{
		boolean encountered;
		
		encountered = false;
		
		for(CheckQuestion.Entry entry : answers)
		{
			if(encountered)
			{
				if(entry.getChecked())
				{
					throw new IllegalArgumentException("Radio buttons are not allowed to have more than one response.");
				}
				encountered = true;
			}
		}
		
		super.setValue(answers);
	}
}
