package form;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import utils.Utils;
import form.questions.CheckQuestion;
import form.questions.ComplexQuestion;
import form.questions.IVisitable;
import form.questions.RadioQuestion;
import form.questions.SelectQuestion;
import form.questions.TextQuestion;
import form.visitors.IQuestionVisitor;

public class VisitMechanism {
	
	
	public static <T extends IVisitable> String visit(IQuestionVisitor v, Iterator<T> q, String delimiter)
	{
		List<String> values = new ArrayList<String>();
		while (q.hasNext()) {
			values.add(visit(v, q.next()));
		}
		
		return Utils.join(delimiter, values);
	}
	
	public static <T extends IVisitable> String visit(IQuestionVisitor v, Iterable<T> q, String delimiter)
	{
		return visit(v, q.iterator(), delimiter);
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
		else if(q instanceof CheckQuestion)
		{
			retval = v.visit((CheckQuestion) q);
		}
		else
		{
			throw new IllegalArgumentException();
		}
		return retval;
	}
	
}
