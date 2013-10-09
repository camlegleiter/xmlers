package form.questions;

import form.User;

public class TextResponse extends QuestionResponse<String> {

	public TextResponse(String key, Question<String> parent, User author) {
		super(key, parent, author);
	}
	
	
}