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
		Form f = new Form();
		
		f.setKey(src.getString("formID"));
		f.setDescription(src.getString("formDesc"));
		f.setTitle(src.getString("formName"));
		
		JSONArray questions = src.getJSONArray("formQuestions");
		
		
		
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
		default:
			throw new IllegalArgumentException("When building Question: Unrecognized type of JSONObject");
		}
		
		return retval;
	}
	
	private static TextQuestion buildStringQuestion(JSONObject src)
	{
		TextQuestion tq;
		
		tq = new TextQuestion();
		
		return tq;
	}
	
	
	private static CheckQuestion buildCheckQuestion(JSONObject src)
	{
		//TODO
		return null;
	}

	private static RadioQuestion buildRadioQuestion(JSONObject src)
	{
		//TODO
		return null;
	}
	
	private static SelectQuestion buildSelectQuestion(JSONObject src)
	{
		//TODO
		return null;
	}
	
	
}
