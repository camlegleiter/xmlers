package form.questions;

import java.util.ArrayList;
import java.util.Collection;

import form.visitors.IQuestionVisitor;

public class ComplexQuestion extends Question<Collection<Question<?>>> {

	public Collection<Question<?>> questions;
	
	public ComplexQuestion(String id, int weight, String prompt) {
		super(id, weight, prompt);
		this.questions = new ArrayList<Question<?>>();
	}
	
	public ComplexQuestion(String id, int weight, String prompt, Iterable<Question<?>> questions)
	{
		this(id, weight, prompt);
		for(Question<?> entry : questions)
		{
			this.questions.add(entry);
		}
	}

	private int min;
	
	@Override
	public void accept(IQuestionVisitor visitor) {
		visitor.visit(this);
	}
	
	
}
