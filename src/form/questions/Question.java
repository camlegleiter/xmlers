package form.questions;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Martin Strobel
 *
 */
public abstract class Question<T> implements IVisitable {

	
	private Map<Integer, QuestionResponse<T>> responses;
	
	
	public Question()
	{
		this(-1, -1, "");
	}
	
	public Question(int id, int weight, String prompt)
	{
		this.id = id;
		this.setPrompt(prompt);
		this.setPosition(weight);
		responses = new HashMap<Integer, QuestionResponse<T>>();
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
	private int id;

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
	public T getResponse(int userID)
	{
		if(responses.isEmpty()){
			return null;
		}
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
	public void setResponse(int id, T ans)
	{
		responses.get(id).setValue(ans);
	}
	
	public void insertResponse(int id, QuestionResponse<T> resp)
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
	public final int getId() {
		return id;
	}

	/**
	 * Sets the unique ID of this function.
	 * 
	 * @param id
	 *            The string to use as the unique ID. This should normally
	 *            either be automatically generated, or
	 */
	public final void setId(int id) {
		this.id = id;
	}
	
	@Override
	public abstract Question<T> clone();
}
