package form.factory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import form.Form;
import form.questions.CheckQuestion;
import form.questions.Question;
import form.questions.RadioQuestion;
import form.questions.SelectQuestion;
import form.questions.TextQuestion;

public class DefaultFactory extends FormFactory {
	
	@Override
	public Form BuildForm(JSONObject src) throws JSONException {
		Form f = new Form();
		f.setKey(src.getString("formID"));
		f.setDescription(src.getString("formDescription"));
		f.setTitle(src.getString("formName"));
		f.setOwner(src.getString("formOwner"));
		
		f.canParticipantsSeeAll(src.getBoolean("participantsCanSeeAll"));
		f.canParticipantsEditResponse(src.getBoolean("participantsCanEditResponse"));
		f.isParticipantResponseRequired(src.getBoolean("participantResponseIsRequired"));
		
		JSONArray questions = src.getJSONArray("formQuestions");
		for(int i = 0; i < questions.length(); ++i)
		{	
			JSONObject currentObj = questions.getJSONObject(i);
			f.add(BuildQuestion(currentObj));
		}
		
		return f;
	}

	@Override
	public Question<?> BuildQuestion(JSONObject src) throws JSONException {
		Question<?> retval;
		String type = src.getString("type");
		
		switch(type)
		{
		case "Textbox":
			retval = buildStringQuestion(src);
			break;
		case "Checkbox":
			retval = buildCheckQuestion(src);
			break;
		case "Radio":
			retval = buildRadioQuestion(src);
			break;
		case "Select":
			retval = buildSelectQuestion(src);
			break;
		default:
			throw new IllegalArgumentException("When building Question: Unrecognized type of JSONObject");
		}
		
		return retval;
	}
	
	private static TextQuestion buildStringQuestion(JSONObject src) throws JSONException
	{
		TextQuestion tq = new TextQuestion();
		tq.setMaxLength(src.getInt("maxLength"));
		tq.setPrompt(src.getString("prompt"));
		
		return tq;
	}
	
	/**
	 * Assuming an appropriate JSONObject is given to it, a CheckQuestion is
	 * built that matches that JSONObjecct.
	 * @param src
	 * @return
	 */
	private static CheckQuestion buildCheckQuestion(JSONObject src) throws JSONException
	{
		return buildCheckHelper(src, "checkboxes", "label");
	}

	private static RadioQuestion buildRadioQuestion(JSONObject src) throws JSONException
	{
		return new RadioQuestion(buildCheckHelper(src, "radios", "label"));
	}
	
	private static SelectQuestion buildSelectQuestion(JSONObject src) throws JSONException
	{
		return new SelectQuestion(buildCheckHelper(src, "options", "value"));
	}
	
	private static CheckQuestion buildCheckHelper(JSONObject src, String kind, String inner) throws JSONException
	{
		JSONArray givenOptions = src.getJSONArray(kind);
		
		String[] parsedOptions = new String[givenOptions.length()];
		for(int i = 0; i < parsedOptions.length; i++)
		{
			JSONObject jo = givenOptions.getJSONObject(i);
			parsedOptions[i] = jo.getString(inner);
		}
		
		CheckQuestion cq = new CheckQuestion(parsedOptions);
		cq.setPrompt(src.getString("prompt"));
		return cq;
	}
}
