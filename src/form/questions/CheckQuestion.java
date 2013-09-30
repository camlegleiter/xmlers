package form.questions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import form.visitors.IQuestionVisitor;

public class CheckQuestion extends Question<List<CheckQuestion.Entry>> {

	private Collection<String> options;
	
	public CheckQuestion(String id, int weight, String prompt, Iterable<String> answers) {
		super(id, weight, prompt);
		options = new ArrayList<String>();
		for(String entry: answers)
		{
			options.add(entry);
		}
	}

	@Override
	public void accept(IQuestionVisitor visitor) {
		visitor.visit(this);
	}

	public Collection<String> getOptions() {
		return options;
	}
	
	public class Entry
	{
		private String text;
		private boolean checked;
		
		public Entry(String text)
		{
			this(text, false);
		}
		
		public Entry(String text, boolean checked)
		{
			 setText(text);
			 setChecked(checked);
		}
		
		/**
		 * Sets the row which this entry is in response to.
		 * @param val the option of the CheckQuestion that this is in response to.
		 */
		public void setText(String val)
		{
			if(!CheckQuestion.this.options.contains(text))
			{
				throw new IllegalArgumentException("Text must be one of the options of the parent question.");
			}
			text = val;			
		}
		
		/**
		 * Fetches which option this is responding to.
		 * @return Text that matches an option of the CheckQuestion.
		 */
		public String getText()
		{
			return text;
		}
		
		public boolean getChecked()
		{
			return checked;
		}
		
		public void setChecked(boolean checked)
		{
			this.checked = checked;
		}
	}
}
