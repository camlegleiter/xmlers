package form.visitors;

import form.questions.CheckQuestion;
import form.questions.ComplexQuestion;
import form.questions.RadioQuestion;
import form.questions.SelectQuestion;
import form.questions.TextQuestion;

public class HTMLIndividualResponseVisitor extends HTMLVisitor {

	private String userID;
	
	public HTMLIndividualResponseVisitor(String user)
	{
		userID = user;
	}
	
	@Override
	public String visit(TextQuestion tq) {

		//TODO Update with response
		StringBuilder html = new StringBuilder(tq.getPrompt());

		html.append(INPUT_TYPE_TAG + "text");
		html.append(NAME_TAG + tq.getId() + MAXLENGTH_TAG + tq.getMaxLength());
		html.append(END_TAG);

		return html.toString();
	}

	@Override
	public String visit(RadioQuestion rq) {
		//TODO Update with response
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
	public String visit(CheckQuestion chq) {

		//TODO Update with response
		StringBuilder html = new StringBuilder(chq.getPrompt());

		for (String option : chq.getOptions()) {
			html.append(INPUT_TYPE_TAG + "checkbox");
			html.append(NAME_TAG + chq.getId() + VALUE_TAG + option);
			html.append(END_TAG);
			html.append(option);
		}
		return html.toString();
	}
	
	@Override
	public String visit(SelectQuestion sq) {

		//TODO Update with response
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
