package form.visitors;

import form.questions.ComplexQuestion;
import form.questions.RadioQuestion;
import form.questions.SelectQuestion;
import form.questions.TextQuestion;

public class JSONVisitor extends AbstractQuestionVisitor {

	private static final String TYPE = "{ \"type\":\"";
	private static final String MAX_LEN = "\"maxLength\":\"";
	private static final String PROMPT = "\"prompt\":\"";
	private static final String OPTIONS = "\"options\":";
	private static final String CHKBXS = "\"checkboxes\":";
	private static final String LABEL = "\"label\":\"";
	private static final String VALUE = "\"value\":\"";
	private static final String DL = "\" , "; // Delimiter
	//private static final String NAME = "\"name\":\"";
	//private static final String POSITION = "\"position\":\"";
	//private static final String RESPONSE = "\"response\":\"";

	public JSONVisitor()
	{
		super();
	}
	
	public JSONVisitor(String user)
	{
		super(user);
	}
	
	@Override
	public String visit(TextQuestion tq) {
		StringBuilder json = new StringBuilder(TYPE + "textbox");

		json.append(DL + MAX_LEN + tq.getMaxLength());
		json.append(DL + PROMPT + tq.getPrompt());
		//json.append(DL + NAME + tq.getId());
		//json.append(DL + POSITION + tq.getPosition());
		//json.append(DL + RESPONSE + tq.getResponse(tq.getId()));

		json.append("\" }");
		return json.toString();
	}

	@Override
	public String visit(RadioQuestion rq) {
		StringBuilder json = new StringBuilder(TYPE + "radio");
		
		json.append(DL + PROMPT + rq.getPrompt());
		//json.append(DL + NAME + rq.getId());
		//json.append(DL + POSITION + rq.getPosition());
		//json.append(DL + RESPONSE + rq.getResponse(rq.getId()));
		
		json.append(DL + CHKBXS + "[");
		boolean first = true;
		for (String option : rq.getOptions()) {
			if(!first){
				json.append(",");
			}
			json.append("{ " + LABEL + option + "\" }");
			first = false;			
		}
		json.append("]");
		
		json.append(" }");
		return json.toString();
	}

	@Override
	public String visit(SelectQuestion sq) {
		StringBuilder json = new StringBuilder(TYPE + "select");

		json.append(DL + PROMPT + sq.getPrompt());
		//json.append(DL + NAME + sq.getId());
		//json.append(DL + POSITION + sq.getPosition());
		//json.append(DL + RESPONSE + sq.getResponse(sq.getId()));
		
		json.append(DL + OPTIONS + "[");
		boolean first = true;
		for (String option : sq.getOptions()) {
			if(!first){
				json.append(",");
			}
			json.append("{ " + VALUE + option + "\" }");
			first = false;			
		}
		json.append("]");
		
		json.append(" }");
		return json.toString();
	}

	@Override
	public String visit(ComplexQuestion cq) {
		// TODO Auto-generated method stub
		return null;
	}

}
