package form.questions.tests;

import form.questions.ComplexQuestion;
import form.questions.RadioQuestion;
import form.questions.SelectQuestion;
import form.questions.TextQuestion;
import form.visitors.IQuestionVisitor;

public class StubVisitor implements IQuestionVisitor {

	@Override
	public String visit(TextQuestion tq) {
		return "Text Question: " + tq.toString();
	}

	@Override
	public String visit(RadioQuestion rq) {
		return "Radio Question: " + rq.toString();
	}

	@Override
	public String visit(SelectQuestion sq) {
		return "Select Question: " + sq.toString();
	}

	@Override
	public String visit(ComplexQuestion cq) {
		return "Complext Question: " + cq.toString();
	}

}
