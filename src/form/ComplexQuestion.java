package form;

public class ComplexQuestion extends Question implements IVisitable {

	public ComplexQuestion(String id, int weight, String prompt) {
		super(id, weight, prompt);
		// TODO Auto-generated constructor stub
	}

	private int min;
	
	@Override
	public void accept(IQuestionVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String getResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setResponse(String ans) {
		// TODO Auto-generated method stub
		
	}

}
