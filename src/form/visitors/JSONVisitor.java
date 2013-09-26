package form.visitors;

import form.questions.ComplexQuestion;
import form.questions.RadioQuestion;
import form.questions.SelectQuestion;
import form.questions.TextQuestion;

public class JSONVisitor extends AbstractQuestionVisitor {

	private static final String TYPE = "{ \"type\":\"";
	private static final String NAME = "\"name\":\"";
	private static final String MAX_LEN = "\"max_length\":\"";
	private static final String POSITION = "\"position\":\"";
	private static final String PROMPT = "\"prompt\":\"";
	private static final String OPTIONS = "\"options\":";
	private static final String RESPONSE = "\"response\":\"";
	private static final String DL = "\" , "; // Delimiter

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
		StringBuilder json = new StringBuilder(TYPE + "text");

		json.append(DL + NAME + tq.getId());
		json.append(DL + MAX_LEN + tq.getMaxLength());
		json.append(DL + POSITION + tq.getPosition());
		json.append(DL + PROMPT + tq.getPrompt());
		json.append(DL + RESPONSE + tq.getResponse(this.userID));

		json.append("\" }");
		return json.toString();
	}

	@Override
	public String visit(RadioQuestion rq) {
		StringBuilder json = new StringBuilder(TYPE + "radio");

		json.append(DL + NAME + rq.getId());
		json.append(DL + POSITION + rq.getPosition());
		json.append(DL + PROMPT + rq.getPrompt());
		json.append(DL + RESPONSE + rq.getResponse(this.userID));
		
		json.append(DL + OPTIONS + "[");
		boolean first = true;
		for (String option : rq.getOptions()) {
			if(!first){
				json.append(",");
			}
			json.append("{ " + NAME + option + "\" }");
			first = false;			
		}
		json.append("]");
		
		json.append(" }");
		return json.toString();
	}

	@Override
	public String visit(SelectQuestion sq) {
		StringBuilder json = new StringBuilder(TYPE + "select");

		json.append(DL + NAME + sq.getId());
		json.append(DL + POSITION + sq.getPosition());
		json.append(DL + PROMPT + sq.getPrompt());
		json.append(DL + RESPONSE + sq.getResponse(this.userID));
		
		json.append(DL + OPTIONS + "[");
		boolean first = true;
		for (String option : sq.getOptions()) {
			if(!first){
				json.append(",");
			}
			json.append("{ " + NAME + option + "\" }");
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