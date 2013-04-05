package form;

public class ComplexQuestion extends Question implements IVisitable {

	private int min;
	public ComplexQuestion()
	{
		
	}
	
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
