package form.questions;

import form.User;

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
	
	private Question<E> parent;
	
	private User author;
	


	public QuestionResponse(Question<E> parent, User author)
	{
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

	/**
	 * Fetches the user that created this response.
	 * @return
	 */
	public User getAuthor()
	{
		return author;
	}
	
	public void setAuthor(User u)
	{
		author = u;
	}
}

