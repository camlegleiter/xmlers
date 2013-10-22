package form;

import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

import form.questions.*;
import form.visitors.*;

public class Form implements Iterable<Question<?>> {

	public static final int ALL_BITS = -1;
	public static final int KEY_BIT = 0x1;
	public static final int DESCRIPTION_BIT = 0x2;
	public static final int TITLE_BIT = 0x4;
	public static final int QUESTIONS_BIT = 0x8;
	public static final int OWNER_BIT = 0x16;

	private String title;
	private String key;
	private String description;
	private Queue<Question<?>> questions;
	private Collection<User> participants;
	private String owner;
	
	private boolean participantsCanSeeAll;
	private boolean participantsCanEditResponse;
	private boolean participantResponseIsRequired;

	public Form() {
		questions = new PriorityQueue<Question<?>>(1, new QuestionPriority());
		participants = new ArrayList<User>();
	}

	public Form(String key, String title, String description, String owner) {
		this();
		this.title = title;
		this.key = key;
		this.description = description;
		this.owner = owner;
		
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
		this.key = other.key;
		this.description = other.description;
		
		this.participantsCanSeeAll = other.participantsCanSeeAll;
		this.participantsCanEditResponse = other.participantsCanEditResponse;
		this.participantResponseIsRequired = other.participantResponseIsRequired;

		this.questions = new PriorityQueue<Question<?>>(1,
				new QuestionPriority());

		for (Question<?> q : other) {
			this.questions.add(q);
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

	/**
	 * Returns a copy
	 * 
	 * @param u
	 */
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

	public String getHTML(int settings, String user) {
		HTMLVisitor generator = new HTMLVisitor();
		StringBuilder output = new StringBuilder();
		output.append("<div class=\"form\">\n");
		if (bitSet(settings, TITLE_BIT)) {
			output.append("\t<div class=\"formTitle\">" + this.getTitle()
					+ "</div>\n");
		}
		if (bitSet(settings, KEY_BIT)) {
			output.append("\t<div class=\"formID\">" + this.getKey()
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

	public String getJSON() {
		return getJSON(ALL_BITS, null);
	}

	public String getJSON(int settings, String user) {
		JSONVisitor generator = new JSONVisitor();
		JSONObject form = new JSONObject();
		
		if (bitSet(settings, TITLE_BIT)) {
			form.put("formName", this.getTitle());
		}
		if (bitSet(settings, KEY_BIT)) {
			form.put("formID", this.getKey());
		}
		if (bitSet(settings, Form.DESCRIPTION_BIT)) {
			form.put("formDescription", this.getDescription());
		}
		if (bitSet(settings, Form.OWNER_BIT)) {
			form.put("formOwner", this.getDescription());
		}
		if (bitSet(settings, Form.QUESTIONS_BIT)) {
			JSONArray array = new JSONArray("["
					+ VisitMechanism.visit(generator, this.iterator(), ",")
					+ "]");
			form.put("formQuestions", array);
		}
		return form.toString();
	}

	@Override
	public Form clone() {
		// Returns a deep copy of this form
		Form other = new Form();
		other.key = this.key;
		other.description = this.description;
		other.title = this.title;
		
		other.participantsCanSeeAll = this.participantsCanSeeAll;
		other.participantsCanEditResponse = this.participantsCanEditResponse;
		other.participantResponseIsRequired = this.participantResponseIsRequired;

		other.questions = new PriorityQueue<Question<?>>(this.questions.size(),
				new QuestionPriority());

		for (Question<?> q : questions) {
			other.questions.add(q.clone());
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

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getOwner() {
		return owner;
	}

}
