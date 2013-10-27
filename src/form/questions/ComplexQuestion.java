package form.questions;

import java.util.ArrayList;
import java.util.Collection;

import form.visitors.IQuestionVisitor;

public class ComplexQuestion extends Question<Collection<Question<?>>> {

	public Collection<Question<?>> questions;
	
	public ComplexQuestion(int id, int weight, String prompt) {
		super(id, weight, prompt);
		this.questions = new ArrayList<Question<?>>();
	}
	
	public ComplexQuestion(int id, int weight, String prompt, Iterable<Question<?>> questions)
	{
		this(id, weight, prompt);
		for(Question<?> entry : questions)
		{
			this.questions.add(entry);
		}
	}
	
	@Override
	public void accept(IQuestionVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public Question<Collection<Question<?>>> clone() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
