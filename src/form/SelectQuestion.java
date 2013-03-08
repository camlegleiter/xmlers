package form;

public class SelectQuestion extends Question {

	@Override
	public void accept(IQuestionVisitor visitor) {
		visitor.visit(this);
	}
}
