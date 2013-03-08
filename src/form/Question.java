package form;

public abstract class Question {
	
	/**
	 * The text that describes to an End User what the question that they
	 * are answering is.
	 */
	private String prompt;
	
	/**
	 * The relative priority of a question's position in a form. 
	 */
	private int position;
	
	/**
	 * Returns the text that the End User will respond to.
	 * @return the text that the End User will respond to.
	 */
	public String getPrompt()
	{
		return this.prompt;
	}
	
	/**
	 * Updates the text that the End User will respond to.
	 * @param s The string the should be used as the prompt.
	 */
	public void setPrompt(String s)
	{
		this.prompt = s;
	}
	
	/**
	 * Retrieves the relative priority of the question to others.
	 * @return the relative priority of the question to others.
	 */
	public final int getPosition() {
		return position;
	}

	/**
	 * Retrieves the relative priority of the question to others.
	 * @return the relative priority of the question to others.
	 */
	public final void setPosition(int position) {
		this.position = position;
	}

	/**
	 * Returns an HTML structure that represents this object.
	 * @return an HTML structure that represents this object.
	 */
	public abstract String getHTML();
}
