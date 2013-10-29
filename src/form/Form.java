package form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.json.JSONArray;
import org.json.JSONObject;

import form.questions.Question;
import form.visitors.HTMLVisitor;
import form.visitors.JSONVisitor;

public class Form implements Iterable<Question<?>>, Cloneable {

	public static final int ALL_BITS = -1;
	public static final int KEY_BIT = 0x1;
	public static final int DESCRIPTION_BIT = 0x2;
	public static final int TITLE_BIT = 0x4;
	public static final int QUESTIONS_BIT = 0x8;
	public static final int OWNER_BIT = 0x16;
	public static final int PARTICIPANTS_CAN_SEE_ALL_BIT = 0x32;
	public static final int PARTICIPANTS_CAN_EDIT_RESPONSE_BIT = 0x64;
	public static final int PARTICIPANT_RESPONSE_IS_REQUIRED_BIT = 0x128;
	public static final int FORM_PARTICIPANTS_BIT = 0x256;
	public static final int RESPONSES_BIT = 0x512;

	private String title;
	private int id;
	private String description;
	private Queue<Question<?>> questions;
	private List<User> participants;
	private int ownerId;
	private List<ResponseForm> responses;
	
	private boolean participantsCanSeeAll;
	private boolean participantsCanEditResponse;
	private boolean participantResponseIsRequired;

	public Form() {
		questions = new PriorityQueue<Question<?>>(1, new QuestionPriority());
		participants = new ArrayList<User>();
		responses = new ArrayList<ResponseForm>();
	}

	public Form(int id, String title, String description, int ownerId) {
		this();
		this.title = title;
		this.id = id;
		this.description = description;
		this.ownerId = ownerId;
		
		this.participantsCanSeeAll = false;
		this.participantsCanEditResponse = false;
		this.participantResponseIsRequired = true;
	}

	/**
	 * Creates a shallow copy of a form.
	 * 
	 * @param other
	 */
	public Form(Form other) {
		this();

		this.title = other.title;
		this.id = other.id;
		this.description = other.description;
		this.ownerId = other.ownerId;
		
		this.participantsCanSeeAll = other.participantsCanSeeAll;
		this.participantsCanEditResponse = other.participantsCanEditResponse;
		this.participantResponseIsRequired = other.participantResponseIsRequired;

		for (User u : other.getParticipants())
			this.participants.add(u);
		
		for (Question<?> q : other)
			this.questions.add(q);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getFormId() {
		return id;
	}

	public void setFormId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void canParticipantsSeeAll(boolean flag) {
		this.participantsCanSeeAll = flag;
	}
	
	public boolean participantsCanSeeAll() {
		return this.participantsCanSeeAll;
	}
	
	public void canParticipantsEditResponse(boolean flag) {
		this.participantsCanEditResponse = flag;
	}
	
	public boolean participantsCanEditResponse() {
		return this.participantsCanEditResponse;
	}
	
	public void isParticipantResponseRequired(boolean flag) {
		this.participantResponseIsRequired = flag;
	}
	
	public boolean participantResponseIsRequired() {
		return this.participantResponseIsRequired;
	}

	public void add(Question<?> q) {
		questions.add(q);
	}
	
	public void add(ResponseForm r) {
		responses.add(r);
	}

	@Override
	public Iterator<Question<?>> iterator() {
		return questions.iterator();
	}

	public String getHTML() {
		return getHTML(ALL_BITS, null);
	}

	/**
	 * The people who may respond to this Form.
	 * 
	 * @return A copy of the list of those people who may respond to this form.
	 */
	public Collection<User> getParticipants() {
		return new ArrayList<User>(participants);
	}

	public void addParticipant(User u) {
		participants.add(u);
	}

	/**
	 * No longer allow someone to respond. Does not remove their response if
	 * applicable.
	 * 
	 * @param u
	 */
	public void removeParticipant(User u) {
		participants.remove(u);
	}
	
	public boolean containsParticipant(int userId) {
		for (User u : participants)
			if (u.getUserID() == userId)
				return true;
		
		return false;
	}

	public String getHTML(int settings, String user) {
		HTMLVisitor generator = new HTMLVisitor();
		StringBuilder output = new StringBuilder();
		output.append("<div class=\"form\">\n");
		if (bitSet(settings, TITLE_BIT)) {
			output.append("\t<div class=\"formTitle\">" + this.getTitle()
					+ "</div>\n");
		}
		if (bitSet(settings, KEY_BIT)) {
			output.append("\t<div class=\"formID\">" + this.getFormId()
					+ "</div>\n");
		}
		if (bitSet(settings, Form.DESCRIPTION_BIT)) {
			output.append("\t<div class=\"formDescription\">"
					+ this.getDescription() + "</div>\n");
		}
		if (bitSet(settings, Form.QUESTIONS_BIT))
			output.append("\t<div class=\"formQuestions\">\n");
		output.append(VisitMechanism.visit(generator, this.iterator(), ""));
		output.append("\t</div>\n");
		output.append("</div>\n");

		return output.toString();
	}

	public JSONObject getJSON() {
		return getJSON(ALL_BITS, null);
	}

	public JSONObject getJSON(int settings, String user) {
		JSONVisitor generator = new JSONVisitor();
		JSONObject form = new JSONObject();
		
		if (bitSet(settings, TITLE_BIT)) {
			form.put("formName", this.getTitle());
		}
		if (bitSet(settings, KEY_BIT)) {
			form.put("formID", this.getFormId());
		}
		if (bitSet(settings, Form.DESCRIPTION_BIT)) {
			form.put("formDescription", this.getDescription());
		}
		if (bitSet(settings, Form.OWNER_BIT)) {
			form.put("formOwner", this.getOwnerId());
		}
		if (bitSet(settings, Form.PARTICIPANTS_CAN_SEE_ALL_BIT)) {
			form.put("participantsCanSeeAll", this.participantsCanSeeAll());
		}
		if (bitSet(settings, Form.PARTICIPANTS_CAN_EDIT_RESPONSE_BIT)) {
			form.put("participantsCanEditResponse", this.participantsCanEditResponse());
		}
		if (bitSet(settings, Form.PARTICIPANT_RESPONSE_IS_REQUIRED_BIT)) {
			form.put("participantResponseIsRequired", this.participantResponseIsRequired());
		}
		if (bitSet(settings, Form.FORM_PARTICIPANTS_BIT)) {
			JSONArray array = new JSONArray();
			for (User u : this.participants)
				array.put(u.getEmail());
		}
		if (bitSet(settings, Form.QUESTIONS_BIT)) {
			JSONArray array = new JSONArray("["
					+ VisitMechanism.visit(generator, this.iterator(), ",")
					+ "]");
			form.put("formQuestions", array);
		}
		if (bitSet(settings, Form.RESPONSES_BIT)) {
			JSONArray array = new JSONArray();
			for(ResponseForm r: responses){
				array.put(r.getJSON());
			}
			form.put("responses", array);
		}
		return form;
	}
	
	public List<ResponseForm> getResponses() {
		return responses;
	}

	public void setResponses(List<ResponseForm> responses) {
		this.responses = responses;
	}

	public String getJSONString() {
		return getJSONString(ALL_BITS, null);
	}
	
	public String getJSONString(int settings, String user) {
		return getJSON(settings, user).toString();
	}

	@Override
	public Form clone() {
		// Returns a deep copy of this form
		Form other = null;
		try {
			other = (Form) super.clone();
			
			other.id = this.id;
			other.description = this.description;
			other.title = this.title;
			other.participantsCanSeeAll = this.participantsCanSeeAll;
			other.participantsCanEditResponse = this.participantsCanEditResponse;
			other.participantResponseIsRequired = this.participantResponseIsRequired;

			other.participants = new ArrayList<User>(this.participants.size());
			for (User u : participants)
				other.participants.add(u);
			
			other.questions = new PriorityQueue<Question<?>>(
					this.questions.size(), new QuestionPriority());
			for (Question<?> q : questions) {
				other.questions.add(q.clone());
			}
		} catch (CloneNotSupportedException e) {
		}

		return other;
	}

	private static boolean bitSet(int field, int mask) {
		return 0 != (field & mask);
	}

	/**
	 * Does some work that really should have been done by the language. We
	 * should port all of this to something with Linq ;)
	 * 
	 * @author mstrobel
	 * 
	 */
	private class QuestionPriority implements Comparator<Question<?>> {
		@Override
		public int compare(Question<?> o1, Question<?> o2) {
			return o1.getPosition() - o2.getPosition();
		}
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public int getOwnerId() {
		return ownerId;
	}
}
