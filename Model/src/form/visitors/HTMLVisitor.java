package form.visitors;

import form.*;
import form.questions.ComplexQuestion;
import form.questions.RadioQuestion;
import form.questions.SelectQuestion;
import form.questions.TextQuestion;

public class HTMLVisitor implements IQuestionVisitor {

	@Override
	public String visit(TextQuestion tq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String visit(RadioQuestion rq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String visit(SelectQuestion sq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String visit(ComplexQuestion cq) {
		// TODO Auto-generated method stub
		return null;
	}

}
