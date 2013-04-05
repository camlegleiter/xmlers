package form;

public class RadioQuestion extends Question {

	@Override
	public void accept(IQuestionVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String getAnswer() {
		// TODO Auto-generated method stub
		return null;
	}
}
