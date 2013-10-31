package form.questions;

import java.util.List;

import form.User;
import form.questions.CheckQuestion.Entry;

public class SelectQuestionResponse extends CheckQuestionResponse {

	public SelectQuestionResponse(int key, Question<List<Entry>> parent,
			User author) {
		super(key, parent, author);
	}

	/**
	 * Validates that there is no more than one option selected for the select
	 * button.
	 */
	@Override
	public void setValue(List<CheckQuestion.Entry> answers) {
		boolean encountered;

		encountered = false;

		for (CheckQuestion.Entry entry : answers) {
			if (encountered) {
				if (entry.getChecked()) {
					throw new IllegalArgumentException(
							"Select buttons are not allowed to have more than one response.");
				}
				encountered = true;
			}
		}
		super.setValue(answers);
	}
}
