package form.questions;

import form.User;

public class ComplexQuestionResponse extends QuestionResponse<String> {

	public ComplexQuestionResponse(Question<String> parent,
			User author) {
		super(parent, author);
	}
}
