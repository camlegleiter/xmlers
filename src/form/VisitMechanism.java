package form;

import java.util.Iterator;

public class VisitMechanism {
	
	
	public static String visit(IQuestionVisitor v, Iterator<Question> q, String delimeter)
	{
		StringBuilder builder = new StringBuilder();

		while(q.hasNext())
		{
			
		}
		
		return builder.toString();
	}
	
	public static String visit(IQuestionVisitor v, Iterable<Question> q, String delimeter)
	{
		StringBuilder builder = new StringBuilder();
		int buildSize;
		
		for(Question ques : q)
		{
			builder.append(visit(v, ques));
			builder.append(delimeter);
		}
		
		buildSize = builder.length();
		builder.delete(buildSize - delimeter.length(), buildSize);
		
		return builder.toString();
	}
	
	public static String visit(IQuestionVisitor v, Question q)
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
