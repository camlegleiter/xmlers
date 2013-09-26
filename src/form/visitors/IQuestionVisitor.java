package form.visitors;

import form.questions.CheckQuestion;
import form.questions.ComplexQuestion;
import form.questions.RadioQuestion;
import form.questions.SelectQuestion;
import form.questions.TextQuestion;

public interface IQuestionVisitor {
	public String visit(TextQuestion tq);

	public String visit(RadioQuestion rq);

	public String visit(SelectQuestion sq);
	
	public String visit(ComplexQuestion cq);

	public String visit(CheckQuestion checkQuestion);
}
