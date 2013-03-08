package form;

public class TextQuestion extends Question {

	@Override
	public void accept(IQuestionVisitor visitor) {
		visitor.visit(this);
	}

}
