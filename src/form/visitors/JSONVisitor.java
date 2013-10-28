package form.visitors;

import java.util.ArrayList;

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

		object.put("type", "Textbox");
		object.put("prompt", tq.getPrompt());
		object.put("maxLength", tq.getMaxLength());
		
		return object.toString();
	}

	@Override
	public String visit(RadioQuestion rq) {
		JSONObject object = new JSONObject();
		
		object.put("type", "Radio");
		object.put("prompt", rq.getPrompt());
		ArrayList<JSONObject> options = new ArrayList<JSONObject>();
		for (String option : rq.getOptions()) {
			options.add(new JSONObject().put("label", option));
		}
		object.put("radios", options);
		return object.toString();
	}

	@Override
	public String visit(CheckQuestion chq) {
		JSONObject object = new JSONObject();
		
		object.put("type", "Checkbox");
		object.put("prompt", chq.getPrompt());
		ArrayList<JSONObject> options = new ArrayList<JSONObject>();
		for (String option : chq.getOptions()) {
			options.add(new JSONObject().put("label", option));
		}
		object.put("checkboxes", options);
		
		return object.toString();
	}

	@Override
	public String visit(SelectQuestion sq) {
		JSONObject object = new JSONObject();
		
		object.put("type", "Select");
		object.put("prompt", sq.getPrompt());
		ArrayList<JSONObject> options = new ArrayList<JSONObject>();
		for (String option : sq.getOptions()) {
			options.add(new JSONObject().put("label", option));
		}
		object.put("value", options);
		
		return object.toString();
	}

	@Override
	public String visit(ComplexQuestion cq) {
		// TODO Auto-generated method stub
		return null;
	}

}
