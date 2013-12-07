package form.questions;

import form.visitors.IQuestionVisitor;

public class CheckQuestion extends AbstractVariadicQuestion {

	public static final String TYPE_STRING = "check";
	
	public CheckQuestion(Iterable<String> opts)
	{
		super(opts, TYPE_STRING);
	}
	
	/**
	 * 
	 * @param id The unique identifier associated with this question.
	 * @param weight The poisition in the form that this question occupies.
	 * @param prompt The string used to describe this question.
	 * @param options The enumerated options that may answer this question.
	 */
	public CheckQuestion(int id, int weight, String prompt, Iterable<String> options)
	{
		super(id, weight, prompt, options, true, TYPE_STRING);
	}

	
	@Override
	public CheckQuestion clone() {
		//TODO
		return null;
	}

	@Override
	public void accept(IQuestionVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public void setVariadic(boolean value)
	{
		if(value == false)
		{
			throw new IllegalArgumentException("A CheckQuestion is always variadic.");
		}
	}
}
