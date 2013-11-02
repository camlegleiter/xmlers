package form.questions;

import java.util.List;

import form.visitors.IQuestionVisitor;

/**
 * A set of radio buttons and a select question resolve the same logically.
 * However, their appearances differ. For that reason, we have a basic extension
 * to allow for visitors to treat them differently.
 * 
 * @author mstrobel
 * 
 */
public class SelectQuestion extends AbstractVariadicQuestion {
	
	public SelectQuestion(int id, int weight, String prompt, boolean isMulti,
			Iterable<String> answers) {
		super(id, weight, prompt, answers, isMulti);
	}

	public SelectQuestion(AbstractVariadicQuestion buildCheckQuestion, boolean isMulti) {
		super(buildCheckQuestion.getId(), buildCheckQuestion.getPosition(),
				buildCheckQuestion.getPrompt(), buildCheckQuestion.getOptions(), isMulti);
	}
	
	public SelectQuestion(Iterable<String> options) {
		super(options);
	}

	@Override
	public void accept(IQuestionVisitor visitor) {
		visitor.visit(this);		
	}
	
	@Override
	public Question<List<Entry>> clone() {
		// TODO Auto-generated method stub
		return null;
	}
}
