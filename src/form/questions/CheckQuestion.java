package form.questions;

import form.visitors.IQuestionVisitor;

public class CheckQuestion extends AbstractVariadicQuestion {

	public CheckQuestion(Iterable<String> opts)
	{
		super(opts);
	}
	
	public CheckQuestion(int id, int weight, String prompt, Iterable<String> options)
	{
		super(id, weight, prompt, options, true);
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
