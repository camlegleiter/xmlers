package form.questions;

import dbconnect.dao.UserDAO;

/**
 * Represents all of the metadata about a response, without holding the actual
 * data.
 * 
 * @author mstrobel
 *
 * @param <T> The type of question that this is a response to.
 * @param <E>
 */
public abstract class QuestionResponse<E> 
{	
	/**
	 * An object that encapsulates what the user meant.
	 */
	protected E value;
	
	/**
	 * A string that uniquely identifies this response.
	 */
	private String key;
	
	private Question<E> parent;
	
	private UserDAO author;
	


	public QuestionResponse(String key, Question<E> parent, UserDAO author)
	{
		this.key = key;
		this.parent = parent;
		this.author = author;
	}
		
	
	/**
	 * Fetches the current object that is a response. 
	 * @return The object that encapsulates a response.
	 */
	public E getValue()
	{
		return value;
	}
	
	/**
	 * The value that this response should be evaluated to.
	 * @param val
	 */
	public void setValue(E val)
	{
		value = val;
	}
	
	/**
	 * The unique identifier string associated with this QuestionResponse.
	 * @return A string that uniquely identifies this QuestionResponse
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Updates the unique identifier of this object.
	 * @param key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * While this is quasi redundant,it gives questions access to their parents.
	 * For trivial types, this is likely unnecessary. However, as types grow more
	 * complex, the more necessary this becomes.
	 * 
	 * @return The question that this is in response to.
	 */
	public Question<E> getParent() {
		return parent;
	}

	/**
	 * Updates the question that this object is in response to.
	 * @param parent The Question that this is responding to.
	 */
	public void setParent(Question<E> parent) {
		this.parent = parent;
	}

}

