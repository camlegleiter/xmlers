package form.questions;

import form.visitors.IQuestionVisitor;

public class ComplexQuestion extends Question {

	public ComplexQuestion(String id, int weight, String prompt) {
		super(id, weight, prompt);
		// TODO Auto-generated constructor stub
	}

	private int min;
	
	@Override
	public void accept(IQuestionVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String getResponse(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setResponse(String id, String ans) {
		// TODO Auto-generated method stub
		
	}

}
