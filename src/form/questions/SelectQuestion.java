package form.questions;

/**
 * A set of radio buttons and a select question resolve the same logically.
 * However, their appearances differ. For that reason, we have a basic
 * extension to allow for visitors to treat them differently.
 * 
 * @author mstrobel
 *
 */
public class SelectQuestion extends RadioQuestion {

	public SelectQuestion(String id, int weight, String prompt, Iterable<String> answers) {
		super(id, weight, prompt, answers);
	}

	public SelectQuestion(CheckQuestion buildCheckQuestion) 
	{
		super(buildCheckQuestion.getId(), buildCheckQuestion.getPosition(), buildCheckQuestion.getPrompt(), buildCheckQuestion.getOptions());
	}
}
