package form.visitors;

import org.json.JSONArray;
import org.json.JSONObject;

import form.questions.CheckQuestion;
import form.questions.ComplexQuestion;
import form.questions.RadioQuestion;
import form.questions.SelectQuestion;
import form.questions.TextQuestion;

public class JSONVisitor extends AbstractQuestionVisitor {

	public JSONVisitor() {
		super();
	}

	public JSONVisitor(String user) {
		super(user);
	}

	@Override
	public String visit(TextQuestion tq) {
		JSONObject object = new JSONObject();

		object.put("questionID", tq.getId());
		object.put("type", "Textbox");
		object.put("prompt", tq.getPrompt());
		object.put("maxLength", tq.getMaxLength());
		
		return object.toString();
	}

	@Override
	public String visit(RadioQuestion rq) {
		JSONObject object = new JSONObject();
		
		object.put("questionID", rq.getId());
		object.put("type", "Radio");
		object.put("prompt", rq.getPrompt());
		JSONArray options = new JSONArray();
		for (String option : rq.getOptions()) {
			options.put(new JSONObject().put("label", option));
		}
		object.put("radios", options);
		return object.toString();
	}

	@Override
	public String visit(CheckQuestion chq) {
		JSONObject object = new JSONObject();
		
		object.put("questionID", chq.getId());
		object.put("type", "Checkbox");
		object.put("prompt", chq.getPrompt());
		JSONArray options = new JSONArray();
		for (String option : chq.getOptions()) {
			options.put(new JSONObject().put("label", option));
		}
		object.put("checkboxes", options);
		
		return object.toString();
	}

	@Override
	public String visit(SelectQuestion sq) {
		JSONObject object = new JSONObject();
		
		object.put("questionID", sq.getId());
		object.put("type", "Select");
		object.put("prompt", sq.getPrompt());
		object.put("isMulti", sq.getVariadic());
		JSONArray options = new JSONArray();
		for (String option : sq.getOptions()) {
			options.put(new JSONObject().put("value", option));
		}
		object.put("options", options);
		
		return object.toString();
	}

	@Override
	public String visit(ComplexQuestion cq) {
		// TODO Auto-generated method stub
		return null;
	}

}
