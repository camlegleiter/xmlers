package form.questions;

import java.util.List;

import form.visitors.IQuestionVisitor;

public class RadioQuestion extends AbstractVariadicQuestion {
	
	public static final String TYPE_STRING = "radio";
	
	public RadioQuestion(int id, int weight, String prompt, Iterable<String> answers) {
		super(id, weight, prompt, answers, false, TYPE_STRING);
	}

	public RadioQuestion(AbstractVariadicQuestion buildCheckQuestion) {
		super(buildCheckQuestion.getId(), buildCheckQuestion.getPosition(), buildCheckQuestion.getPrompt(), buildCheckQuestion.getOptions(), buildCheckQuestion.getVariadic(), TYPE_STRING);
	}
	
	public RadioQuestion(Iterable<String> options) {
		super(options, TYPE_STRING);
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
	
	@Override
	public void setVariadic(boolean value)
	{
		if(value == true)
		{
			throw new IllegalArgumentException("RadioQuestions must not be variadic.");
		}
	}
}