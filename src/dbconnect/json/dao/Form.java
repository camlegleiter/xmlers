package dbconnect.json.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.bson.BSONObject;
import org.bson.BasicBSONObject;

import com.mongodb.DBObject;

import form.questions.Question;

public class Form extends BasicBSONObject implements DBObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5754557223389543911L;
	
	public static final String FORM_ID = "formID";
	public static final String FORM_NAME = "formName";
	public static final String FORM_DESCRIPTION = "formDescription";
	public static final String FORM_OWNER = "formOwner";
	public static final String FORM_PARTICIPANTS = "formParticipants";
	public static final String PARTICIPANTS_CAN_SEE_ALL = "participantsCanSeeAll";
	public static final String PARTICIPANTS_CAN_EDIT_RESPONSE = "participantsCanEditResponse";
	public static final String PARTICIPANT_RESPONSE_IS_REQUIRED = "participantResponseIsRequired";
	public static final String QUERIES = "queries";
	public static final String FORM_QUESTIONS = "formQuestions";
	public static final String FORM_RESPONSES = "formResponses";
	
	public Form() {
	}
	
	public Form(String key, Object value) {
		put(key, value);
	}
	
	public Form(Map<?, ?> m) {
		super(m);
	}
	
	public int getFormId() {
		return getInt(FORM_ID);
	}
	
	public void setFormId(int formId) {
		put(FORM_ID, formId);
	}
	
	public String getFormName() {
		return getString(FORM_NAME);
	}
	
	public void setFormName(String formName) {
		put(FORM_NAME, formName);
	}
	
	public String getFormDescription() {
		return getString(FORM_DESCRIPTION);
	}
	
	public void setFormDescription(String formDescription) {
		put(FORM_DESCRIPTION, formDescription);
	}
	
	public int getFormOwner() {
		return getInt(FORM_OWNER);
	}
	
	public void setFormOwner(int formOwner) {
		put(FORM_OWNER, formOwner);
	}
	
	public boolean canParticipantsSeeAll() {
		return getBoolean(PARTICIPANTS_CAN_SEE_ALL);
	}
	
	public void setParticipantsCanSeeAll(boolean participantsCanSeeAll) {
		put(PARTICIPANTS_CAN_SEE_ALL, participantsCanSeeAll);
	}
	
	public boolean canParticipantsEditResponse() {
		return getBoolean(PARTICIPANTS_CAN_EDIT_RESPONSE);
	}
	
	public void setParticipantsCanEditResponse(boolean participantsCanEditResponse) {
		put(PARTICIPANTS_CAN_EDIT_RESPONSE, participantsCanEditResponse);
	}
	
	public boolean isParticipationRequired() {
		return getBoolean(PARTICIPANT_RESPONSE_IS_REQUIRED);
	}
	
	public void setParticipationRequired(boolean participationRequired) {
		put(PARTICIPANT_RESPONSE_IS_REQUIRED, participationRequired);
	}
	
	public List<?> getParticipants() {
		return (List<?>) get(FORM_PARTICIPANTS);
	}
	
	public void setParticipants(List<Integer> participants) {
		put(FORM_PARTICIPANTS, participants);
	}
	
	public String getQueries() {
		return getString(QUERIES);
	}
	
	public void setQueries(String queries) {
		put(QUERIES, queries);
	}
	
	public Queue<?> getFormQuestions() {
		return (Queue<?>) get(FORM_QUESTIONS);
	}
	
	public void setQuestions(Queue<Question<?>> questions) {
		put(FORM_QUESTIONS, questions);
	}

	@Override
	public void markAsPartialObject() {
		isPartial = true;
	}

	@Override
	public boolean isPartialObject() {
		return isPartial;
	}
	
	private boolean isPartial;
}
