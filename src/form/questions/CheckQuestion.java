package form.questions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import form.visitors.IQuestionVisitor;

public class CheckQuestion extends Question<List<CheckQuestion.Entry>> {

	/**
	 * All of the lines of text that may either be checked or not checked.
	 */
	private Collection<String> options;
	
	public CheckQuestion()
	{
		super();
		options = new ArrayList<String>();
	}
	
	public CheckQuestion(Iterable<String> answers)
	{
		this();
		for(String s : answers)
		{
			options.add(s);
		}
	}
	
	public CheckQuestion(String[] answers)
	{
		this();
		for(String s : answers)
		{
			options.add(s);
		}
	}
	
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
		return new ArrayList<String>(options);
	}
	
	public void addOption(String option)
	{
		options.add(option);
	}
	
	public void removeOption(String option)
	{
		options.remove(option);
	}
	
	/**
	 * Represents one line of a response to a question where one must
	 * select or not select each option.
	 * @author mstrobel
	 *
	 */
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

	@Override
	public Question<List<Entry>> clone() {
		return null;
	}
}
