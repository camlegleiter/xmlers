package form.visitors;

import form.questions.CheckQuestionResponse;
import form.questions.ComplexQuestionResponse;
import form.questions.RadioQuestionResponse;
import form.questions.SelectQuestionResponse;
import form.questions.TextResponse;

public interface IResponseVisitor {
	public String visit(TextResponse tr);

	public String visit(RadioQuestionResponse rr);

	public String visit(SelectQuestionResponse sr);

	public String visit(ComplexQuestionResponse cr);

	public String visit(CheckQuestionResponse checkQuestionResponse);
}
