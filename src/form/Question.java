package form;

public abstract class Question implements IVisitable {

	/**
	 * The text that describes to an End User what the question that they are
	 * answering is.
	 */
	private String prompt;

	/**
	 * The relative priority of a question's position in a form.
	 */
	private int position;

	/**
	 * A unique identifier for this question. It will function as the input
	 * tag's name. This will allow for POST access to individual question's
	 * responses.
	 */
	private String id;

	/**
	 * Returns the text that the End User will respond to.
	 * 
	 * @return the text that the End User will respond to.
	 */
	public String getPrompt() {
		return this.prompt;
	}

	/**
	 * Updates the text that the End User will respond to.
	 * 
	 * @param s
	 *            The string the should be used as the prompt.
	 */
	public void setPrompt(String s) {
		this.prompt = s;
	}

	/**
	 * Retrieves the relative priority of the question to others.
	 * 
	 * @return the relative priority of the question to others.
	 */
	public final int getPosition() {
		return position;
	}

	/**
	 * Retrieves the relative priority of the question to others.
	 * 
	 * @return the relative priority of the question to others.
	 */
	public final void setPosition(int position) {
		this.position = position;
	}

	/**
	 * Retrieves the unique ID of this question.
	 * 
	 * @return the unique ID of this question.
	 */
	public final String getId() {
		return id;
	}

	/**
	 * Sets the unique ID of this function.
	 * 
	 * @param id
	 *            The string to use as the unique ID. This should normally
	 *            either be automatically generated, or
	 */
	public final void setId(String id) {
		this.id = id;
	}
}
