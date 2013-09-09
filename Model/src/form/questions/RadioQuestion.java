package form.questions;

import form.visitors.*;
import form.*;

public class RadioQuestion extends Question {

	public RadioQuestion(String id, int weight, String prompt) {
		super(id, weight, prompt);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(IQuestionVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String getResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setResponse(String ans) {
		// TODO Auto-generated method stub
		
	}
}
