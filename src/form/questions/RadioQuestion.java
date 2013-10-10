package form.questions;

import form.visitors.IQuestionVisitor;

public class RadioQuestion extends CheckQuestion {
	
	public RadioQuestion(String id, int weight, String prompt, Iterable<String> answers) {
		super(id, weight, prompt, answers);
	}

	@Override
	public void accept(IQuestionVisitor visitor) {
		visitor.visit(this);
	}
}