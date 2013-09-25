package form;

import java.util.Iterator;
import form.visitors.*;
import form.questions.*;

public class VisitMechanism {
	
	
	public static <T extends IVisitable> String visit(IQuestionVisitor v, Iterator<T> q, String delimeter)
	{
		StringBuilder builder = new StringBuilder();
		int buildSize;
		
		while(q.hasNext())
		{
			builder.append(visit(v, q.next()));
			builder.append(delimeter);
		}
		
		buildSize = builder.length();
		if(buildSize > 0)
		{
			builder.delete(buildSize - delimeter.length(), buildSize);
		}
		
		return builder.toString();
	}
	
	public static <T extends IVisitable> String visit(IQuestionVisitor v, Iterable<T> q, String delimeter)
	{
		return visit(v, q.iterator(), delimeter);
	}
	
	/**
	 * Given a question, it decides whether or not it is of a supported type, and
	 * if so, applies a visitor to it.
	 * @param v The visitor to apply if appropriate.
	 * @param q The question that should be evaluated.
	 * @return The result of the Visitor being called on the question.
	 * @note This is necessary because Java does not have multiple dispatch.
	 */
	public static <T extends IVisitable> String visit(IQuestionVisitor v, T q)
	{
		String retval;
		
		if(q instanceof TextQuestion)
		{
			retval = v.visit((TextQuestion) q);
		}
		else if(q instanceof RadioQuestion)
		{
			retval = v.visit((RadioQuestion) q);
		}
		else if(q instanceof SelectQuestion)
		{
			retval = v.visit((SelectQuestion) q);
		}
		else if(q instanceof ComplexQuestion)
		{
			retval = v.visit((ComplexQuestion) q);
		}
		else
		{
			throw new IllegalArgumentException();
		}
		return retval;
	}
	
}
