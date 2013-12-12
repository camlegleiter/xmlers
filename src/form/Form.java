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
import form.visitors.JSONVisitorResponse;

public class Form implements Iterable<Question<?>>, Cloneable {
	public static final int ALL_BITS = -1;
	public static final int KEY_BIT = 1 << 1;
	public static final int DESCRIPTION_BIT = 1 << 2;
	public static final int TITLE_BIT = 1 << 3;
	public static final int QUESTIONS_BIT = 1 << 4;
	public static final int OWNER_BIT = 1 << 5;
	public static final int PARTICIPANTS_CAN_SEE_ALL_BIT = 1 << 6;
	public static final int PARTICIPANTS_CAN_EDIT_RESPONSE_BIT = 1 << 7;
	public static final int PARTICIPANT_RESPONSE_IS_REQUIRED_BIT = 1 << 8;
	public static final int FORM_PARTICIPANTS_BIT = 1 << 9;
	public static final int RESPONSES_BIT = 1 << 10;
	public static final int RESPONSE_OWNER_NAME_BIT = 1 << 11;
	public static final int RESPONSE_OWNER_BIT = 1 << 12;
	public static final int RESPONSE_BIT = 1 << 13;
	public static final int QUERIES_BIT = 1 << 14;
	public static final int RESPONDED_PARTICIPANTS = 1 << 15;
	
	private String title;
	private int id;
	private String description;
	private Queue<Question<?>> questions;
	private List<User> participants;
	private int ownerId;
	private List<User> respondedParticipants;
	
	private boolean participantsCanSeeAll;
	private boolean participantsCanEditResponse;
	private boolean participantResponseIsRequired;
	
	private String queries;

	public Form() {
		questions = new PriorityQueue<Question<?>>(1, new QuestionPriority());
		participants = new ArrayList<User>();
		respondedParticipants = new ArrayList<User>();
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

	@Override
	public Iterator<Question<?>> iterator() {
		return questions.iterator();
	}

	/**
	 * @deprecated HTML generation not used; see getJSON()
	 * @return
	 */
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
		if(u != null){
			participants.add(u);
		}
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
		return getParticipant(userId) != null;
	}
	
	public User getParticipant(int userId) {
		for (User u : participants)
			if (u.getUserID() == userId)
				return u;
		return null;
	}
	
	public String getQueries() {
		return queries;
	}
	
	public void setQueries(String queries) {
		this.queries = queries;
	}

	/**
	 * @deprecated using getJSON to retrieve form data for the front end.
	 * @param settings
	 * @param user
	 * @return
	 */
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
		return getJSON(ALL_BITS);
	}

	public JSONObject getJSON(int settings) {
		JSONVisitor questionGenerator = new JSONVisitor();
		JSONObject form = new JSONObject();
		
		if (bitSet(settings, TITLE_BIT)) {
			form.put("formName", this.getTitle());
		}
		if (bitSet(settings, KEY_BIT)) {
			form.put("formID", this.getFormId());
		}
		if (bitSet(settings, DESCRIPTION_BIT)) {
			form.put("formDescription", this.getDescription());
		}
		if (bitSet(settings, OWNER_BIT)) {
			form.put("formOwner", this.getOwnerId());
		}
		if (bitSet(settings, PARTICIPANTS_CAN_SEE_ALL_BIT)) {
			form.put("participantsCanSeeAll", this.participantsCanSeeAll());
		}
		if (bitSet(settings, PARTICIPANTS_CAN_EDIT_RESPONSE_BIT)) {
			form.put("participantsCanEditResponse", this.participantsCanEditResponse());
		}
		if (bitSet(settings, PARTICIPANT_RESPONSE_IS_REQUIRED_BIT)) {
			form.put("participantResponseIsRequired", this.participantResponseIsRequired());
		}
		if (bitSet(settings, FORM_PARTICIPANTS_BIT)) {
			JSONArray array = new JSONArray();
			for (User u : this.participants)
				array.put(u.getEmail());
			form.put("formParticipants", array);
		}
		if (bitSet(settings, QUESTIONS_BIT)) {
			JSONArray array = new JSONArray("["
					+ VisitMechanism.visit(questionGenerator, this.iterator(), ",")
					+ "]");
			form.put("formQuestions", array);
		}
		if (bitSet(settings, RESPONSES_BIT)) {
			JSONArray responseList = new JSONArray();
			for (User u : this.participants){
				JSONObject reponse = new JSONObject();
				if (bitSet(settings, RESPONSE_OWNER_BIT)) {
					reponse.put("responseOwner", u.getUserID());
				}
				if (bitSet(settings, RESPONSE_OWNER_NAME_BIT)) {
					reponse.put("responseOwnerName", u.getEmail());
				}
				if (bitSet(settings, RESPONSE_BIT)) {
					JSONArray array = new JSONArray("["
							+ VisitMechanism.visit(
									new JSONVisitorResponse(u.getUserID()),
									this.iterator(), ",") + "]");
					reponse.put("responses", array);
					responseList.put(reponse);
				}
			}
			form.put("responses", responseList);
		}
		if (bitSet(settings, QUERIES_BIT)) {
			form.put("queries", this.queries);
		}
		if (bitSet(settings, RESPONDED_PARTICIPANTS)) {
			JSONArray responded = new JSONArray();
			for (User u : respondedParticipants) {
				responded.put(u.getEmail());
			}
			form.put("respondedParticipants", responded);
		}
		return form;
	}

	public String getJSONString() {
		return getJSONString(ALL_BITS);
	}
	
	public String getJSONString(int settings) {
		return getJSON(settings).toString();
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

			other.participants = new ArrayList<User>(this.participants);
			
			other.questions = new PriorityQueue<Question<?>>(
					this.questions.size(), new QuestionPriority());
			for (Question<?> q : questions) {
				other.questions.add(q.clone());
			}
			other.respondedParticipants = new ArrayList<User>(this.respondedParticipants);
			other.queries = new String(queries);
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

	public Queue<Question<?>> getQuestions() {
		return questions;
	}

	public List<User> getRespondedParticipants() {
		return respondedParticipants;
	}

	public void addRespondedParticipant(User particpant) {
		respondedParticipants.add(particpant);
	}
}
