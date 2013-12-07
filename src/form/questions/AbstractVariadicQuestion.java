package form.questions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractVariadicQuestion extends Question<List<AbstractVariadicQuestion.Entry>> {
	
	private boolean isMulti;
	
	private String type;

	/**
	 * All of the lines of text that may either be checked or not checked.
	 */
	private Collection<String> options;
	
	public AbstractVariadicQuestion(String type)
	{
		this(new ArrayList<String>(), type);
	}
	
	public AbstractVariadicQuestion(Iterable<String> opts, String type)
	{
		this(-1, -1, "", opts, true, type);
	}
	
	public AbstractVariadicQuestion(int id, int weight, String prompt, Iterable<String> options, boolean multiQuestion, String type)
	{
		super(id, weight, prompt);
		isMulti = multiQuestion;
		this.options = new ArrayList<String>();
		
		for(String opt : options)
		{
			this.options.add(opt);
		}
		
		this.type = type;
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
	
	public String getType()
	{
		return type;
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
			if(!AbstractVariadicQuestion.this.options.contains(val))
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
	
	public boolean getVariadic()
	{
		return isMulti;
	}
	
	public void setVariadic(boolean value)
	{
		this.isMulti = value;
	}

}
