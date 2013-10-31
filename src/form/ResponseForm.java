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
	public static final int RESPONSE_OWNERNAME_BIT = 0x4;
	public static final int RESPONSES_BIT = 0x8;

	private int id;
	private int responseOwnerId;
	private String responseOwnerName;
	private int parentFormId;
	private ArrayList<QuestionResponse<?>> responses;

	public ResponseForm() {
		responses = new ArrayList<QuestionResponse<?>>();
	}
	public ResponseForm(int id, int responseOwnerId, String responseOwnerName, int parentFormId) {
		this();
		this.id = id;
		this.responseOwnerId = responseOwnerId;
		this.setParentFormId(parentFormId);
		this.setResponseOwnerName(responseOwnerName);
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

	public String getResponseOwnerName() {
		return responseOwnerName;
	}
	public void setResponseOwnerName(String responseOwnerName) {
		this.responseOwnerName = responseOwnerName;
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
		if (bitSet(settings, RESPONSE_OWNERNAME_BIT)) {
			form.put("responseOwnerName", this.getResponseOwnerName());
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
	public int getParentFormId() {
		return parentFormId;
	}
	public void setParentFormId(int parentFormId) {
		this.parentFormId = parentFormId;
	}
}
