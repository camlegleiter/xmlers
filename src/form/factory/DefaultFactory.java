package form.factory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dbconnect.DBManager;
import form.Form;
import form.ResponseForm;
import form.User;
import form.QuestionPriority;
import form.questions.CheckQuestion;
import form.questions.Question;
import form.questions.QuestionResponse;
import form.questions.RadioQuestion;
import form.questions.SelectQuestion;
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
	
	private static Map<String, IResponseBuilder> responseBuilderType = new HashMap<String, IResponseBuilder>();
	static {
		responseBuilderType.put("TEXTBOX", new TextResponseBuilder());
		responseBuilderType.put("CHECKBOX", new CheckResponseBuilder());
		responseBuilderType.put("RADIO", new RadioResponseBuilder());
		responseBuilderType.put("SELECT", new SelectResponseBuilder());
	}

	@Override
	public Form buildForm(JSONObject jsonObject) throws JSONException {
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
		for (int i = 0; i < participants.length(); ++i)
			f.addParticipant(DBManager.getInstance().fetchUserByEmail(participants.getString(i)));

		JSONArray questions = jsonObject.getJSONArray("formQuestions");
		for (int i = 0; i < questions.length(); ++i)
			f.add(buildQuestion(questions.getJSONObject(i), i));
		
		JSONArray responses = jsonObject.getJSONArray("responses");
		for (int i = 0; i < responses.length(); ++i)
			f.add(buildResponseForm(responses.getJSONObject(i), f.getQuestions(), f.getParticipants()));

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
			question.setPrompt(jsonObject.getString("prompt"));
			question.setMulti(jsonObject.getBoolean("isMulti"));
			question.setPosition(position);
			return question;
		}
	}
	@Override
	public ResponseForm buildResponseForm(JSONObject jsonObject, User user) throws JSONException {
		JSONArray questions = jsonObject.getJSONArray("formQuestions");
		Queue<Question<?>> questionObjects = new PriorityQueue<Question<?>>(1, new QuestionPriority());
		for (int i = 0; i < questions.length(); ++i)
			questionObjects.add(buildQuestion(questions.getJSONObject(i), i));
		return buildResponseForm(jsonObject, questionObjects, user);
	}
	private ResponseForm buildResponseForm(JSONObject jsonObject,
			Queue<Question<?>> questions, Collection<User> participants)  throws JSONException{
		int responseOwnerId = jsonObject.getInt("responseOwner");
		User responseOwner = null;
		for(User u: participants){
			if(u.getUserID() == responseOwnerId){
				responseOwner = u;
			}	
		}
		return buildResponseForm(jsonObject, questions, responseOwner);
	}
	private ResponseForm buildResponseForm(JSONObject jsonObject,
			Queue<Question<?>> questions, User user) throws JSONException{
		ResponseForm rf = new ResponseForm();
		rf.setResponseId(jsonObject.getInt("responseID"));
		int responseOwnerId = jsonObject.getInt("responseOwner");
		rf.setResponseOwnerId(responseOwnerId);
		rf.setResponseOwnerName(jsonObject.getString("responseOwnerName"));
		
		JSONArray responses = jsonObject.getJSONArray("responses");
		for (int i = 0; i < responses.length(); ++i){
			int questionId = responses.getJSONObject(i).getInt("questionID");
			Question<?> parentQuestion = null;
			for(Question<?> q: questions){
				if(q.getId() == questionId){
					parentQuestion = q;
				}
			}
			if(parentQuestion == null){
				throw new IllegalArgumentException("Can't find parent question in form");
			}
			rf.add(buildResponse(responses.getJSONObject(i), i, parentQuestion, user));
		}
		// TODO Auto-generated method stub
		return rf;
	}

	private QuestionResponse<?> buildResponse(JSONObject jsonObject, int position,
			Question<?> parentQuestion, User responseOwner) {
		String type = jsonObject.getString("type");
		IResponseBuilder builder = responseBuilderType.get(type.toUpperCase());
		if (builder == null)
			throw new IllegalArgumentException(
					"When building Question: Unrecognized question type.");
		return builder.buildQuestion(jsonObject, position, parentQuestion, responseOwner);
	}
	
	private interface IResponseBuilder {
		/**
		 * Creates a Response object from the given JSONObject at a specific
		 * position.
		 * 
		 * @param jsonObject
		 * @param position
		 * @param responseOwner 
		 * @param parentQuestion 
		 * @return
		 * @throws JSONException
		 */
		public QuestionResponse<?> buildQuestion(JSONObject jsonObject,
				int position, Question<?> parentQuestion, User responseOwner);
	}
	
	private static class TextResponseBuilder implements IResponseBuilder {
		@Override
		public QuestionResponse<?> buildQuestion(JSONObject jsonObject,
				int position, Question<?> parentQuestion, User responseOwner) {
			TextResponse response = new TextResponse(parentQuestion.getId(), (Question<String>)parentQuestion, responseOwner);
			response.setValue(jsonObject.getString("value"));
			return response;
		}
	}
	private static class CheckResponseBuilder implements IResponseBuilder {
		@Override
		public QuestionResponse<?> buildQuestion(JSONObject jsonObject,
				int position, Question<?> parentQuestion, User responseOwner) {
			//TODO
			return null;
		}
	}
	private static class RadioResponseBuilder implements IResponseBuilder {
		@Override
		public QuestionResponse<?> buildQuestion(JSONObject jsonObject,
				int position, Question<?> parentQuestion, User responseOwner) {
			//TODO
			return null;
		}
	}
	private static class SelectResponseBuilder implements IResponseBuilder {
		@Override
		public QuestionResponse<?> buildQuestion(JSONObject jsonObject,
				int position, Question<?> parentQuestion, User responseOwner) {
			//TODO
			return null;
		}
	}
}
