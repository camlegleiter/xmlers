package form.factory;

import org.json.JSONArray;
import org.json.JSONObject;

import form.Form;
import form.questions.CheckQuestion;
import form.questions.Question;
import form.questions.RadioQuestion;
import form.questions.SelectQuestion;
import form.questions.TextQuestion;

public class DefaultFactory extends FormFactory {
	
	@Override
	public Form BuildForm(JSONObject src) {
		int numQuestions; //The number of questions contained in the form represented in 'src'
		Form f = new Form(); //The instantiation
		JSONObject currentObj; //The JSONObject depicting a question. Used as a cursor inside the loop below. 
		
		f.setKey(src.getString("formID"));
		f.setDescription(src.getString("formDesc"));
		f.setTitle(src.getString("formName"));
		
		JSONArray questions = src.getJSONArray("formQuestions");
		numQuestions = questions.length();
		
		for(int i = 0; i < numQuestions; ++i)
		{	
			currentObj = questions.getJSONObject(i);
			f.add(BuildQuestion(currentObj));
		}
		
		
		return f;
	}

	@Override
	public Question<?> BuildQuestion(JSONObject src) {
		Question<?> retval;
		String type = src.getString("type");
		
		switch(type)
		{
		case "Textbox":
			retval = buildStringQuestion(src);
			break;
		case "Checkbox":
			retval = DefaultFactory.buildCheckQuestion(src);
			break;
		case "Radio":
			retval = DefaultFactory.buildRadioQuestion(src);
			break;
		case "Select":
			retval = DefaultFactory.buildSelectQuestion(src);
			break;
		case "Datetime":
			//TODO Implement Datetime in factory
		default:
			throw new IllegalArgumentException("When building Question: Unrecognized type of JSONObject");
		}
		
		return retval;
	}
	
	private static TextQuestion buildStringQuestion(JSONObject src)
	{
		TextQuestion tq;
		int maxLength;
		String prompt;
		
		
		maxLength = src.getInt("maxLength");
		prompt = src.getString("prompt");

		tq = new TextQuestion();
		tq.setMaxLength(maxLength);
		tq.setPrompt(prompt);
		
		return tq;
	}
	
	/**
	 * Assuming an appropriate JSONObject is given to it, a CheckQuestion is
	 * built that matches that JSONObjecct.
	 * @param src
	 * @return
	 */
	private static CheckQuestion buildCheckQuestion(JSONObject src)
	{
		return buildCheckHelper(src, "checkboxes", "label");
	}

	private static RadioQuestion buildRadioQuestion(JSONObject src)
	{
		return new RadioQuestion(buildCheckHelper(src, "radios", "label"));
	}
	
	private static SelectQuestion buildSelectQuestion(JSONObject src)
	{
		return new SelectQuestion(buildCheckHelper(src, "options", "value"));
	}
	
	private static CheckQuestion buildCheckHelper(JSONObject src, String kind, String inner)
	{
		CheckQuestion cq;		
		JSONArray givenOptions;
		String prompt;
		String[] parsedOptions;
		int numOptions;
		
		
		givenOptions = src.getJSONArray(kind);
		numOptions = givenOptions.length();
		parsedOptions = new String[numOptions];
		prompt = src.getString("prompt");
		
		for(int i = 0; i < numOptions; i++)
		{
			JSONObject jo = givenOptions.getJSONObject(i);
			parsedOptions[i] = jo.getString(inner);
		}
		
		cq = new CheckQuestion(parsedOptions);
		cq.setPrompt(prompt);
		return cq;
	}
}
