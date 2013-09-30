package form.questions;

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
	


	public QuestionResponse(String key, Question<E> parent)
	{
		this.key = key;
		this.parent = parent;
	}
		
	
	/**
	 * Fetches the current object that is a response. 
	 * @return The object that encapsulates a response.
	 */
	public E getValue()
	{
		return value;
	}
	
	public void setValue(E val)
	{
		value = val;
	}
	
	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	public Question<E> getParent() {
		return parent;
	}


	public void setParent(Question<E> parent) {
		this.parent = parent;
	}

}

