package form.questions;

import java.util.HashMap;

/**
 * 
 * @author Martin Strobel
 *
 */
public abstract class Question<T> implements IVisitable {

	
	private HashMap<String, QuestionResponse<T>> responses;
	
	public Question(String id, int weight, String prompt)
	{
		this.id = id;
		this.setPrompt(prompt);
		this.setPosition(weight);
		responses = new HashMap<String, QuestionResponse<T>>();
	}
	
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
	 * @param s The string the should be used as the prompt.
	 */
	public void setPrompt(String s) {
		this.prompt = s;
	}

	/**
	 * Fetches a textual representation of how an end user responded.
	 * @param userID The unique string that identifies a user.
	 * @return The response that the specified end user provided.
	 */
	public T getResponse(String userID)
	{
		return responses.get(userID).getValue();
	}
	
	public Iterable<QuestionResponse<T>> getResponse()
	{
		return responses.values();
	}
	
	/**
	 * Updates the response from the end user. 
	 * @param id the id for the question the response is tied to. 
	 * @param ans
	 */
	public void setResponse(String id, T ans)
	{
		responses.get(id).setValue(ans);
	}
	
	public void insertResponse(String id, QuestionResponse<T> resp)
	{
		responses.put(id, resp);
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
	
	@Override
	public abstract Question<T> clone();
}
