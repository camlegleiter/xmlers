package form;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import form.questions.QuestionResponse;
import form.visitors.JSONVisitorResponse;

public class ResponseForm implements Iterable<QuestionResponse<?>> {

	public static final int ALL_BITS = -1;
	public static final int KEY_BIT = 0x1;
	public static final int RESPONSE_OWNER_BIT = 0x2;
	public static final int RESPONSES_BIT = 0x4;

	private int id;
	private int responseOwnerId;
	private Form parentForm;
	private ArrayList<QuestionResponse<?>> responses;

	public ResponseForm(int id, int responseOwnerId, Form parentForm) {
		this.id = id;
		this.setResponseOwnerId(responseOwnerId);
		this.setParentForm(parentForm);
		responses = new ArrayList<QuestionResponse<?>>();
	}

	public void add(QuestionResponse<?> r) {
		responses.add(r);
	}

	public int getResponseId() {
		return id;
	}

	public void setResponseId(int id) {
		this.id = id;
	}

	public int getResponseOwnerId() {
		return responseOwnerId;
	}

	public void setResponseOwnerId(int responseOwnerId) {
		this.responseOwnerId = responseOwnerId;
	}

	public JSONObject getJSON() {
		return getJSON(ALL_BITS, null);
	}

	@Override
	public Iterator<QuestionResponse<?>> iterator() {
		return responses.iterator();
	}

	public JSONObject getJSON(int settings, String user) {
		JSONVisitorResponse generator = new JSONVisitorResponse();
		JSONObject form = new JSONObject();

		if (bitSet(settings, KEY_BIT)) {
			form.put("responseID", this.getResponseId());
		}
		if (bitSet(settings, RESPONSE_OWNER_BIT)) {
			form.put("responseOwner", this.getResponseOwnerId());
		}
		if (bitSet(settings, RESPONSES_BIT)) {
			JSONArray array = new JSONArray("["
					+ VisitMechanism.visit(generator, this.iterator(), ",")
					+ "]");
			form.put("responses", array);
		}
		return form;
	}

	public String getJSONString() {
		return getJSONString(ALL_BITS, null);
	}

	public String getJSONString(int settings, String user) {
		return getJSON(settings, user).toString();
	}

	private static boolean bitSet(int field, int mask) {
		return 0 != (field & mask);
	}

	public Form getParentForm() {
		return parentForm;
	}

	public void setParentForm(Form parentForm) {
		this.parentForm = parentForm;
	}

}
