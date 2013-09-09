package form;

public interface IQuestionVisitor {
	public String visit(TextQuestion tq);

	public String visit(RadioQuestion rq);

	public String visit(SelectQuestion sq);
	
	public String visit(ComplexQuestion cq);
}
