package form.visitors;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import form.questions.AbstractVariadicQuestion.Entry;
import form.questions.CheckQuestion;
import form.questions.ComplexQuestionResponse;
import form.questions.RadioQuestion;
import form.questions.SelectQuestion;
import form.questions.TextQuestion;

public class JSONVisitorResponse extends JSONVisitor {

	private int userID;

	public JSONVisitorResponse(int userID) {
		this.userID = userID;
	}

	public String visit(TextQuestion tr) {
		JSONObject object = new JSONObject();
		object.put("questionID", tr.getId());
		object.put("type", "Textbox");
		
		String resp = tr.getResponse(userID);
		if (resp == null) {
			object.put("value", "");
		} else {
			object.put("value", resp);
		}

		return object.toString();
	}

	public String visit(RadioQuestion rr) {
		JSONObject object = new JSONObject();
		
		object.put("questionID", rr.getId());
		object.put("type", "Radio");
		
		List<Entry> resp = rr.getResponse(userID);
		if (resp == null) {
			object.put("value", "");
		} else {
			for (Entry option : resp) {
				if(option.getChecked()){
					object.put("value", option.getText());
					break;
				}
			}
		}
		
		return object.toString();
	}

	public String visit(SelectQuestion sr) {
		JSONObject object = new JSONObject();
		object.put("questionID", sr.getId());
		object.put("type", "Select");
		
		List<Entry> resp = sr.getResponse(userID);
		ArrayList<String> options = new ArrayList<String>();
		if (resp != null) {
			for (Entry option : resp) {
				if(option.getChecked()){
					options.add(option.getText());
				}
			}
		}
		object.put("values", options);
		return object.toString();
	}

	public String visit(ComplexQuestionResponse cr) {
		// TODO Auto-generated method stub
		return null;
	}

	public String visit(CheckQuestion chr) {
		JSONObject object = new JSONObject();
		object.put("questionID", chr.getId());
		object.put("type", "Checkbox");

		List<Entry> resp = chr.getResponse(userID);
		ArrayList<String> options = new ArrayList<String>();
		if (resp != null) {
			for (Entry option : resp) {
				if(option.getChecked()){
					options.add(option.getText());
				}
			}
		}
		object.put("values", options);
		
		return object.toString();
	}
}
