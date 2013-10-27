package form.questions;

import form.visitors.IQuestionVisitor;

public class TextQuestion extends Question<String> {


	private int maxLength;

	public TextQuestion()
	{
		this(255);
	}
	
	public TextQuestion(int max){
		super();
		this.maxLength = max;
	}
	
	public TextQuestion(int id, int weight, String prompt, int max) {
		super(id, weight, prompt);
		this.maxLength = max;
	}

	@Override
	public void accept(IQuestionVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public void setResponse(int id, String response) {
		if (response.length() <= this.maxLength) {
			super.setResponse(id, response);
		} else {
			throw new IllegalArgumentException("The String \"" + response
					+ "\" is longer than the maximum of " + maxLength
					+ " characters for this field.");
		}
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		if (maxLength > 0) {
			this.maxLength = maxLength;
		} else {
			throw new IllegalArgumentException(
					"The maximum length of a string must be greater than zero.");
		}
	}

	@Override
	public TextQuestion clone() {
		TextQuestion retVal = new TextQuestion(this.getId(), this.getPosition(), this.getPrompt(), this.getMaxLength());
		retVal.maxLength = this.maxLength;
		
		return retVal;
		
	}
}
