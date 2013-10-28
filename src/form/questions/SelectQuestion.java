package form.questions;

/**
 * A set of radio buttons and a select question resolve the same logically.
 * However, their appearances differ. For that reason, we have a basic extension
 * to allow for visitors to treat them differently.
 * 
 * @author mstrobel
 * 
 */
public class SelectQuestion extends RadioQuestion {

	private boolean isMulti;
	
	public SelectQuestion(int id, int weight, String prompt, boolean isMulti,
			Iterable<String> answers) {
		super(id, weight, prompt, answers);
		this.isMulti = isMulti;
	}

	public SelectQuestion(CheckQuestion buildCheckQuestion) {
		super(buildCheckQuestion.getId(), buildCheckQuestion.getPosition(),
				buildCheckQuestion.getPrompt(), buildCheckQuestion.getOptions());
		this.isMulti = false;
	}
	
	public SelectQuestion(Iterable<String> options) {
		super(options);
		this.isMulti = false;
	}
	
	public boolean isMulti() {
		return isMulti;
	}
	
	public void setMulti(boolean isMulti) {
		this.isMulti = isMulti;
	}
}
