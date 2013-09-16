package form.visitors;

import form.questions.ComplexQuestion;
import form.questions.RadioQuestion;
import form.questions.SelectQuestion;
import form.questions.TextQuestion;

public class HTMLVisitor implements IQuestionVisitor {

	final String INPUT_TYPE_TAG = "<input type=\"";
	final String SELECT_TAG = "<select>";
	final String OPTION_VALUE_TAG = "<option value=\"";
	final String NAME_TAG = "\" name=\"";
	final String MAXLENGTH_TAG = "\" maxlength=\"";
	final String VALUE_TAG = "\" value=\"";
	final String SELECT_END_TAG = "</select>";
	final String OPTION_END_TAG = "</option>";
	final String END_TAG = "\">";

	@Override
	public String visit(TextQuestion tq) {
		String html = tq.getPrompt();

		html += INPUT_TYPE_TAG + "text";
		html += NAME_TAG + tq.getId() + MAXLENGTH_TAG + tq.getMaxLength();
		html += END_TAG;

		return html;
	}

	@Override
	public String visit(RadioQuestion rq) {
		String html = rq.getPrompt();

		for (String answer : rq.getAnswers()) {
			html += INPUT_TYPE_TAG + "radio";
			html += NAME_TAG + rq.getId() + VALUE_TAG + answer;
			html += END_TAG;
			html += answer;
		}
		// TODO Auto-generated method stub
		return html;
	}

	@Override
	public String visit(SelectQuestion sq) {
		String html = sq.getPrompt() + SELECT_TAG;

		for (String answer : sq.getAnswers()) {
			html += OPTION_VALUE_TAG + answer;
			html += END_TAG + answer;
			html += OPTION_END_TAG;
		}
		
		html += SELECT_END_TAG;
		// TODO Auto-generated method stub
		return html;
	}

	@Override
	public String visit(ComplexQuestion cq) {
		// TODO Auto-generated method stub
		return null;
	}

}
