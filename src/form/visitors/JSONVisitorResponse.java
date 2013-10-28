package form.visitors;

import java.util.ArrayList;

import org.json.JSONObject;

import form.questions.CheckQuestionResponse;
import form.questions.ComplexQuestionResponse;
import form.questions.RadioQuestionResponse;
import form.questions.SelectQuestionResponse;
import form.questions.TextResponse;
import form.questions.CheckQuestion.Entry;

public class JSONVisitorResponse implements IResponseVisitor {

	@Override
	public String visit(TextResponse tr) {
		JSONObject object = new JSONObject();

		object.put("questionID", tr.getParent().getId());
		object.put("type", "Textbox");
		object.put("value", tr.getValue());

		return object.toString();
	}

	@Override
	public String visit(RadioQuestionResponse rr) {
		JSONObject object = new JSONObject();

		object.put("questionID", rr.getParent().getId());
		object.put("type", "Radio");
		for (Entry option : rr.getValue()) {
			if (option.getChecked()) {
				object.put("value", option.getText());
			}
		}
		return object.toString();
	}

	@Override
	public String visit(SelectQuestionResponse sr) {
		JSONObject object = new JSONObject();

		object.put("questionID", sr.getParent().getId());
		object.put("type", "Select");
		for (Entry option : sr.getValue()) {
			if (option.getChecked()) {
				object.put("value", option.getText());
			}
		}
		return object.toString();
	}

	@Override
	public String visit(ComplexQuestionResponse cr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String visit(CheckQuestionResponse checkQuestionResponse) {
		JSONObject object = new JSONObject();

		object.put("questionID", checkQuestionResponse.getParent().getId());
		object.put("type", "Select");
		ArrayList<JSONObject> options = new ArrayList<JSONObject>();
		for (Entry option : checkQuestionResponse.getValue()) {
			options.add(new JSONObject().put("label", option.getText()));
		}
		object.put("value", options);
		return object.toString();
	}
}
