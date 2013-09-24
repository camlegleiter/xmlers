package form.visitors;

import form.questions.ComplexQuestion;
import form.questions.RadioQuestion;
import form.questions.SelectQuestion;
import form.questions.TextQuestion;

public class XMLVisitor implements IQuestionVisitor {

	private static final String NAME_ELEMENT_ST = "<name>";
	private static final String NAME_ELEMENT_END = "</name>";
	private static final String MAX_LEN_ELEMENT_ST = "<max_length>";
	private static final String MAX_LEN_ELEMENT_END = "</max_length>";
	private static final String POSITION_ELEMENT_ST = "<position>";
	private static final String POSITION_ELEMENT_END = "</position>";
	private static final String PROMPT_ELEMENT_ST = "<prompt>";
	private static final String PROMPT_ELEMENT_END = "</prompt>";
	private static final String RESPONSE_ELEMENT_ST = "<response>";
	private static final String RESPONSE_ELEMENT_END = "</response>";

	@Override
	public String visit(TextQuestion tq) {
		StringBuilder html = new StringBuilder("<text_question>");

		html.append(NAME_ELEMENT_ST + tq.getId() + NAME_ELEMENT_END);
		html.append(MAX_LEN_ELEMENT_ST + tq.getMaxLength() + MAX_LEN_ELEMENT_END);
		html.append(POSITION_ELEMENT_ST + tq.getPosition()
				+ POSITION_ELEMENT_END);
		html.append(PROMPT_ELEMENT_ST + tq.getPrompt() + PROMPT_ELEMENT_END);
		html.append(RESPONSE_ELEMENT_ST + tq.getResponse()
				+ RESPONSE_ELEMENT_END);

		html.append("</text_question>");

		return html.toString();
	}

	@Override
	public String visit(RadioQuestion rq) {
		StringBuilder html = new StringBuilder("<radio_question>");

		html.append(NAME_ELEMENT_ST + rq.getId() + NAME_ELEMENT_END);
		html.append(POSITION_ELEMENT_ST + rq.getPosition()
				+ POSITION_ELEMENT_END);
		html.append(PROMPT_ELEMENT_ST + rq.getPrompt() + PROMPT_ELEMENT_END);
		for (String option : rq.getOptions()) {
			html.append("<option>" + option + "</option>");
		}
		html.append(RESPONSE_ELEMENT_ST + rq.getResponse()
				+ RESPONSE_ELEMENT_END);

		html.append("</radio_question>");

		return html.toString();
	}

	@Override
	public String visit(SelectQuestion sq) {
		StringBuilder html = new StringBuilder("<select_question>");

		html.append(NAME_ELEMENT_ST + sq.getId() + NAME_ELEMENT_END);
		html.append(POSITION_ELEMENT_ST + sq.getPosition()
				+ POSITION_ELEMENT_END);
		html.append(PROMPT_ELEMENT_ST + sq.getPrompt() + PROMPT_ELEMENT_END);
		for (String option : sq.getOptions()) {
			html.append("<option>" + option + "</option>");
		}
		html.append(RESPONSE_ELEMENT_ST + sq.getResponse()
				+ RESPONSE_ELEMENT_END);

		html.append("</select_question>");

		return html.toString();
	}

	@Override
	public String visit(ComplexQuestion cq) {
		// TODO Auto-generated method stub
		return null;
	}

}
