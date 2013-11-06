package form.questions;

import java.util.Collection;

import form.User;
import form.visitors.IResponseVisitor;

public class ComplexQuestionResponse extends QuestionResponse<Collection<Question<?>>> {

	public ComplexQuestionResponse(String key, Question<Collection<Question<?>>> parent,
			User author) {
		super(key, parent, author);
	}

	@Override
	public void accept(IResponseVisitor visitor) {
		visitor.visit(this);
	}
}
