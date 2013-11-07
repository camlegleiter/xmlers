package form.questions;

import form.User;

public class TextResponse extends QuestionResponse<String> {

	public TextResponse(Question<String> parent, User author) {
		super(parent, author);
	}
}