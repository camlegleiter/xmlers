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
		StringBuilder html = new StringBuilder(tq.getPrompt());

		html.append(INPUT_TYPE_TAG + "text");
		html.append(NAME_TAG + tq.getId() + MAXLENGTH_TAG + tq.getMaxLength());
		html.append(END_TAG);

		return html.toString();
	}

	@Override
	public String visit(RadioQuestion rq) {
		StringBuilder html = new StringBuilder(rq.getPrompt());

		for (String option : rq.getOptions()) {
			html.append(INPUT_TYPE_TAG + "radio");
			html.append(NAME_TAG + rq.getId() + VALUE_TAG + option);
			html.append(END_TAG);
			html.append(option);
		}
		return html.toString();
	}

	@Override
	public String visit(SelectQuestion sq) {
		StringBuilder html = new StringBuilder(sq.getPrompt() + SELECT_TAG);

		for (String option : sq.getOptions()) {
			html.append(OPTION_VALUE_TAG + option);
			html.append(END_TAG + option);
			html.append(OPTION_END_TAG);
		}
		
		html.append(SELECT_END_TAG);
		return html.toString();
	}

	@Override
	public String visit(ComplexQuestion cq) {
		// TODO Auto-generated method stub
		return null;
	}

}
