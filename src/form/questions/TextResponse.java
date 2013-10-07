package form.questions;

import dbconnect.dao.UserDAO;

public class TextResponse extends QuestionResponse<String> {

	public TextResponse(String key, Question<String> parent, UserDAO author) {
		super(key, parent, author);
	}
	
	
}