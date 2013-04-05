package form;

public class TextQuestion extends Question {

	private String response;
	
	private int maxLength;
	
	public TextQuestion(int max)
	{
		this.maxLength = max;
	}
	
	
	@Override
	public void accept(IQuestionVisitor visitor) {
		visitor.visit(this);
	}

	public String getResponse() {
		return response;
	}


	public void setResponse(String response) {
		if(response.length() <= this.maxLength)
		{
			this.response = response;
		}
		else
		{
			throw new IllegalArgumentException("The String \"" + response + "\" is longer than the maximum of " + maxLength + " characters for this field.");
		}
	}


	public int getMaxLength() {
		return maxLength;
	}


	public void setMaxLength(int maxLength) {
		if(maxLength > 0)
		{
			this.maxLength = maxLength;
		}
		else
		{
			throw new IllegalArgumentException("The maximum length of a string must be greater than zero.");
		}
	}


	@Override
	public String getAnswer() {
		return response;
	}

}
