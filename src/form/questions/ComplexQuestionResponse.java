package form.questions;

import java.util.Collection;

import form.User;

public class ComplexQuestionResponse extends QuestionResponse<Collection<Question<?>>> {

	public ComplexQuestionResponse(Question<Collection<Question<?>>> parent,

			User author) {
		super(parent, author);
	}
}
