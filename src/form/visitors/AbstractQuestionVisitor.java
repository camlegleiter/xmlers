package form.visitors;



public abstract class AbstractQuestionVisitor implements IQuestionVisitor {

	protected String userID;
	
	/**
	 * Should be used for empty forms, i.e. no response has been made by
	 * the active user yet.
	 */
	public AbstractQuestionVisitor()
	{
		userID = null;
	}
	
	/**
	 * Configures the visitor to ask for the response from this user.
	 * @param user
	 */
	public AbstractQuestionVisitor(String user)
	{
		userID = user;
	}

}
