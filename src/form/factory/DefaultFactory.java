package form.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dbconnect.DBManager;
import dbconnect.IDBController;
import form.Form;
import form.User;
import form.questions.AbstractVariadicQuestion.Entry;
import form.questions.CheckQuestion;
import form.questions.CheckQuestionResponse;
import form.questions.Question;
import form.questions.RadioQuestion;
import form.questions.RadioQuestionResponse;
import form.questions.SelectQuestion;
import form.questions.SelectQuestionResponse;
import form.questions.TextQuestion;
import form.questions.TextResponse;

public class DefaultFactory extends FormFactory {

	private static Map<String, IQuestionBuilder> builderType = new HashMap<String, IQuestionBuilder>();
	static {
		builderType.put("TEXTBOX", new TextboxQuestionBuilder());
		builderType.put("CHECKBOX", new CheckboxQuestionBuilder());
		builderType.put("RADIO", new RadioQuestionBuilder());
		builderType.put("SELECT", new SelectQuestionBuilder());
	}
	private static Map<String, IResponseInserter> responseBuilderType = new HashMap<String, IResponseInserter>();
	static {
		responseBuilderType.put("TEXTBOX", new TextboxResponseInserter());
		responseBuilderType.put("CHECKBOX", new CheckboxResponseInserter());
		responseBuilderType.put("RADIO", new RadioResponseInserter());
		responseBuilderType.put("SELECT", new SelectResponseInserter());
	}

	@Override
	public Form buildForm(JSONObject jsonObject, IDBController controller)
			throws JSONException {
		Form f = new Form();
		f.setFormId(jsonObject.getInt("formID"));
		f.setDescription(jsonObject.getString("formDescription"));
		f.setTitle(jsonObject.getString("formName"));
		f.setOwnerId(jsonObject.getInt("formOwner"));

		f.canParticipantsSeeAll(jsonObject.getBoolean("participantsCanSeeAll"));
		f.canParticipantsEditResponse(jsonObject
				.getBoolean("participantsCanEditResponse"));
		f.isParticipantResponseRequired(jsonObject
				.getBoolean("participantResponseIsRequired"));

		JSONArray participants = jsonObject.getJSONArray("formParticipants");
		for (int i = 0; i < participants.length(); ++i) {
			User u;
			try {
				// Attempt to parse user ID
				u = DBManager.getInstance().fetchUser(participants.getInt(i));
			} catch (JSONException e) {
				// Attempt to parse user email instead
				u = DBManager.getInstance().fetchUserByEmail(
						participants.getString(i));
			}
			if (u == null) {
				User newUser = new User(participants.getString(i));
				DBManager.getInstance().upsertUser(newUser);
				u = newUser;
			}
			f.addParticipant(u);
		}

		JSONArray questions = jsonObject.getJSONArray("formQuestions");
		for (int i = 0; i < questions.length(); ++i) {
			Question<?> question = buildQuestion(questions.getJSONObject(i), i);
			if (question.getId() == -1)
				question.setId(controller.getNewId());
			f.add(question);
		}
		
		if (jsonObject.has("respondedParticipants")) {
			JSONArray responded = jsonObject.getJSONArray("respondedParticipants");
			for (int i = 0; i < responded.length(); ++i) {
				String userEmail = responded.getString(i);
				for (User u : f.getParticipants()) {
					if (u.getEmail().equals(userEmail)) {
						f.addRespondedParticipant(u);
					}
				}
			}
		}

		if (jsonObject.has("responses")) {
			JSONArray allResponses = jsonObject.getJSONArray("responses");
			for (int i = 0; i < allResponses.length(); ++i) {
				JSONObject userResponses = allResponses.getJSONObject(i);
				User u = f
						.getParticipant(userResponses.getInt("responseOwner"));

				/*
				 * In the case that the participant submitted a response, and
				 * the administrator removes them as a participant from the edit
				 * page, remove the responses.
				 * 
				 * Additionally, a participant who is unparticipating will have
				 * his/her responses removed
				 */
				if (u == null) {
					break;
				}
				JSONArray responses = userResponses.getJSONArray("responses");
				for (int j = 0; j < responses.length(); ++j) {
					insertResponse(f, responses.getJSONObject(j), u);
				}
			}
		}

		return f;
	}

	@Override
	public Question<?> buildQuestion(JSONObject jsonObject, int position)
			throws JSONException {
		String type = jsonObject.getString("type");
		IQuestionBuilder builder = builderType.get(type.toUpperCase());
		if (builder == null)
			throw new IllegalArgumentException(
					"When building Question: Unrecognized question type.");
		return builder.buildQuestion(jsonObject, position);
	}

	private interface IQuestionBuilder {
		/**
		 * Creates a Question object from the given JSONObject at a specific
		 * position.
		 * 
		 * @param jsonObject
		 * @param position
		 * @return
		 * @throws JSONException
		 */
		public Question<?> buildQuestion(JSONObject jsonObject, int position)
				throws JSONException;
	}

	private static class TextboxQuestionBuilder implements IQuestionBuilder {
		@Override
		public Question<?> buildQuestion(JSONObject jsonObject, int position)
				throws JSONException {
			TextQuestion question = new TextQuestion();
			question.setId(jsonObject.getInt("questionID"));
			question.setPrompt(jsonObject.getString("prompt"));
			question.setMaxLength(jsonObject.getInt("maxLength"));
			question.setPosition(position);
			return question;
		}
	}

	private static class CheckboxQuestionBuilder implements IQuestionBuilder {
		@Override
		public Question<?> buildQuestion(JSONObject jsonObject, int position)
				throws JSONException {
			CheckQuestion question = new CheckQuestion(buildOptionsArray(
					jsonObject, "checkboxes", "label"));
			question.setId(jsonObject.getInt("questionID"));
			question.setPrompt(jsonObject.getString("prompt"));
			question.setPosition(position);

			return question;
		}

		/**
		 * Creates a List of different options available for a question from the
		 * given JSONObject.
		 * 
		 * @param jsonObject
		 * @param arrayName
		 *            - the name of the array in the JSONObject to get the
		 *            different values from.
		 * @param labelName
		 *            - the key name of each object in the array.
		 * @return
		 * @throws JSONException
		 */
		protected List<String> buildOptionsArray(JSONObject jsonObject,
				String arrayName, String labelName) throws JSONException {
			JSONArray options = jsonObject.getJSONArray(arrayName);
			List<String> parsedOptions = new ArrayList<String>(options.length());
			for (int i = 0; i < options.length(); i++) {
				JSONObject jo = options.getJSONObject(i);
				parsedOptions.add(jo.getString(labelName));
			}
			return parsedOptions;
		}
	}

	private static class RadioQuestionBuilder extends CheckboxQuestionBuilder {
		@Override
		public Question<?> buildQuestion(JSONObject jsonObject, int position)
				throws JSONException {
			RadioQuestion question = new RadioQuestion(buildOptionsArray(
					jsonObject, "radios", "label"));
			question.setId(jsonObject.getInt("questionID"));
			question.setPrompt(jsonObject.getString("prompt"));
			question.setPosition(position);
			return question;
		}
	}

	private static class SelectQuestionBuilder extends CheckboxQuestionBuilder {
		@Override
		public Question<?> buildQuestion(JSONObject jsonObject, int position)
				throws JSONException {
			SelectQuestion question = new SelectQuestion(buildOptionsArray(
					jsonObject, "options", "value"));
			question.setId(jsonObject.getInt("questionID"));
			question.setPrompt(jsonObject.getString("prompt"));
			question.setVariadic(jsonObject.getBoolean("isMulti"));
			question.setPosition(position);
			return question;
		}
	}

	/**
	 * Takes in the response metadata (see response_metadata), fetches the form
	 * from our database and inserts the responses in our metadata into the
	 * appropriate form.
	 * 
	 * @param jsonObject
	 *            The response metadata with all the responses
	 * @param user
	 *            The response owner
	 * @return The form that was updated
	 */
	public Form insertResponse(JSONObject jsonObject, User user, boolean isNew) {
		JSONArray responses = jsonObject.getJSONArray("formQuestions");
		Form f = DBManager.getInstance().fetchForm(jsonObject.getInt("formID"));
		f.addRespondedParticipant(user);
		for (int i = 0; i < responses.length(); ++i) {
			JSONObject response = responses.getJSONObject(i);
			if (isNew) {
				insertResponse(f, response, user);
			} else {
				
				User u = f.getParticipant(response.getInt("responseOwner"));
				insertResponse(f, response, u);
			}
		}
		return f;
	}

	private void insertResponse(Form f, JSONObject response, User user) {
		for (Question<?> q : f.getQuestions()) {
			if (q.getId() == response.getInt("questionID")) {
				String type = response.getString("type");
				IResponseInserter inserter = responseBuilderType.get(type
						.toUpperCase());
				if (inserter == null)
					throw new IllegalArgumentException(
							"When building Response: Unrecognized question type.");
				inserter.insertResponse(response, user, q);
				break;
			}
		}
	}

	private interface IResponseInserter {
		/**
		 * Converts the JSONObject that includes the response and inserts it in
		 * the owner form.
		 * 
		 * @param jsonObject
		 * @param q
		 * @param user
		 * @return
		 * @throws JSONException
		 */
		public void insertResponse(JSONObject jsonObject, User user,
				Question<?> q) throws JSONException;
	}

	private static class TextboxResponseInserter implements IResponseInserter {
		@Override
		public void insertResponse(JSONObject jsonObject, User user,
				Question<?> q) throws JSONException {
			TextQuestion question = (TextQuestion) q;
			TextResponse tr = new TextResponse(question, user);

			tr.setValue(jsonObject.has("value") ? jsonObject.getString("value")
					: "");
			question.insertResponse(user.getUserID(), tr);
		}
	}

	private static class CheckboxResponseInserter implements IResponseInserter {
		@Override
		public void insertResponse(JSONObject jsonObject, User user,
				Question<?> q) throws JSONException {
			CheckQuestion question = (CheckQuestion) q;
			List<Entry> answers = new ArrayList<Entry>();
			CheckQuestionResponse chr = new CheckQuestionResponse(question,
					user);
			if (jsonObject.has("values")) {
				JSONArray responses = jsonObject.getJSONArray("values");
				for (int i = 0; i < responses.length(); ++i) {
					answers.add(question.new Entry(responses.getString(i), true));
				}
			}
			chr.setValue(answers);
			question.insertResponse(user.getUserID(), chr);
		}
	}

	private static class RadioResponseInserter implements IResponseInserter {
		@Override
		public void insertResponse(JSONObject jsonObject, User user,
				Question<?> q) throws JSONException {
			RadioQuestion question = (RadioQuestion) q;
			RadioQuestionResponse rr = new RadioQuestionResponse(question, user);
			List<Entry> answer = new ArrayList<Entry>();
			if (jsonObject.has("value"))
				answer.add(question.new Entry(jsonObject.getString("value"),
						true));
			rr.setValue(answer);
			question.insertResponse(user.getUserID(), rr);
		}
	}

	private static class SelectResponseInserter implements IResponseInserter {
		@Override
		public void insertResponse(JSONObject jsonObject, User user,
				Question<?> q) throws JSONException {
			SelectQuestion question = (SelectQuestion) q;
			SelectQuestionResponse sr = new SelectQuestionResponse(question,
					user);
			ArrayList<Entry> answers = new ArrayList<Entry>();
			if (jsonObject.has("values")) {
				JSONArray responses = jsonObject.getJSONArray("values");
				for (int i = 0; i < responses.length(); ++i) {
					answers.add(question.new Entry(responses.getString(i), true));
				}
			}
			sr.setValue(answers);
			question.insertResponse(user.getUserID(), sr);
		}
	}
}
