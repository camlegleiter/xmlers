package form.visitors;

import form.questions.CheckQuestion;
import form.questions.ComplexQuestion;
import form.questions.RadioQuestion;
import form.questions.SelectQuestion;
import form.questions.TextQuestion;

public class XMLVisitor extends AbstractQuestionVisitor {

	private static final String MAX_LEN_ELEMENT_ST = "<max_length>";
	private static final String MAX_LEN_ELEMENT_END = "</max_length>";
	private static final String PROMPT_ELEMENT_ST = "<prompt>";
	private static final String PROMPT_ELEMENT_END = "</prompt>";
	private static final String RESPONSE_ELEMENT_ST = "<response>";
	private static final String RESPONSE_ELEMENT_END = "</response>";

	@Override
	public String visit(TextQuestion tq) {
		StringBuilder html = new StringBuilder("<textQuestion " + getIDPriority(tq.getId(), tq.getPosition()) + ">" );
		html.append(MAX_LEN_ELEMENT_ST + tq.getMaxLength() + MAX_LEN_ELEMENT_END);
		html.append(PROMPT_ELEMENT_ST + tq.getPrompt() + PROMPT_ELEMENT_END);
		html.append(RESPONSE_ELEMENT_ST + tq.getResponse(tq.getId())
				+ RESPONSE_ELEMENT_END);

		html.append("</textQuestion>");

		return html.toString();
	}

	@Override
	public String visit(RadioQuestion rq) {
		StringBuilder html = new StringBuilder("<radioQuestion " + getIDPriority(rq.getId(), rq.getPosition()) + ">" );

		html.append(PROMPT_ELEMENT_ST + rq.getPrompt() + PROMPT_ELEMENT_END);
		for (String option : rq.getOptions()) {
			html.append("<option>" + option + "</option>");
		}
		html.append(RESPONSE_ELEMENT_ST + rq.getResponse(rq.getId())
				+ RESPONSE_ELEMENT_END);

		html.append("</radioQuestion>");

		return html.toString();
	}
	
	@Override
	public String visit(CheckQuestion chq) {
		StringBuilder html = new StringBuilder("<checkQuestion " + getIDPriority(chq.getId(), chq.getPosition()) + ">" );

		html.append(PROMPT_ELEMENT_ST + chq.getPrompt() + PROMPT_ELEMENT_END);
		for (String option : chq.getOptions()) {
			html.append("<option>" + option + "</option>");
		}
		html.append(RESPONSE_ELEMENT_ST + chq.getResponse(chq.getId())
				+ RESPONSE_ELEMENT_END);

		html.append("</checkQuestion>");

		return html.toString();
	}

	@Override
	public String visit(SelectQuestion sq) {
		StringBuilder html = new StringBuilder("<selectQuestion " + getIDPriority(sq.getId(), sq.getPosition()) + ">");

		html.append(PROMPT_ELEMENT_ST + sq.getPrompt() + PROMPT_ELEMENT_END);
		for (String option : sq.getOptions()) {
			html.append("<option>" + option + "</option>");
		}
		html.append(RESPONSE_ELEMENT_ST + sq.getResponse(sq.getId())
				+ RESPONSE_ELEMENT_END);

		html.append("</selectQuestion>");

		return html.toString();
	}

	@Override
	public String visit(ComplexQuestion cq) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static String getIDPriority(int id, int priority){
		return "id=\"" + id + "\" priority=\"" + priority + "\"";
	}

}
