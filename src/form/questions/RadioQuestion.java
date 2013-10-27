package form.questions;

import form.visitors.IQuestionVisitor;

public class RadioQuestion extends CheckQuestion {
	
	public RadioQuestion(int id, int weight, String prompt, Iterable<String> answers) {
		super(id, weight, prompt, answers);
	}

	public RadioQuestion(CheckQuestion buildCheckQuestion) {
		super(buildCheckQuestion.getId(), buildCheckQuestion.getPosition(), buildCheckQuestion.getPrompt(), buildCheckQuestion.getOptions());
	}

	@Override
	public void accept(IQuestionVisitor visitor) {
		visitor.visit(this);
	}
}