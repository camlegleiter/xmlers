package form;

public class RadioQuestion extends Question {

	@Override
	public void accept(IQuestionVisitor visitor) {
		visitor.visit(this);
	}
}
