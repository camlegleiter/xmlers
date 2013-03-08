package form;

public interface IQuestionVisitor {
	public void visit(TextQuestion tq);
	public void visit(RadioQuestion rq);
	public void visit(SelectQuestion sq);
}
