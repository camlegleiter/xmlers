package form.questions;

import java.util.Collection;

import form.visitors.IQuestionVisitor;

public class RadioQuestion extends CheckQuestion {

	private Collection<String> options;
	
	public RadioQuestion(String id, int weight, String prompt, Iterable<String> answers) {
		super(id, weight, prompt, answers);
	}

	@Override
	public void accept(IQuestionVisitor visitor) {
		visitor.visit(this);
	}

	public Collection<String> getOptions() {
		return options;
	}
	
}