package form.questions;

import java.util.ArrayList;
import java.util.List;

import form.User;
import form.questions.CheckQuestion.Entry;
import form.visitors.IResponseVisitor;

public class CheckQuestionResponse extends QuestionResponse<List<CheckQuestion.Entry>> {
	
	public CheckQuestionResponse(String key, Question<List<Entry>> parent, User author) {
		super(key, parent, author);
		CheckQuestion realParent;
		if(!(parent instanceof CheckQuestion))
		{
			throw new IllegalArgumentException("CheckQuestionResponse only responds to CheckQuestions");
		}
		realParent = (CheckQuestion) parent;
		
		value = new ArrayList<CheckQuestion.Entry>();
		for(String option : realParent.getOptions())
		{
			value.add(realParent.new Entry(option));
		}
	}

	@Override
	public void accept(IResponseVisitor visitor) {
		visitor.visit(this);
	}
}
