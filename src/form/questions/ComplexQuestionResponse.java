package form.questions;

import form.User;
import form.visitors.IResponseVisitor;

public class ComplexQuestionResponse extends QuestionResponse<String> {

	public ComplexQuestionResponse(int key, Question<String> parent,
			User author) {
		super(key, parent, author);
	}

	@Override
	public void accept(IResponseVisitor visitor) {
		visitor.visit(this);
	}
}
