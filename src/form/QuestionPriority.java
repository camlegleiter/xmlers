package form;

import java.util.Comparator;

import form.questions.Question;


/**
 * Does some work that really should have been done by the language. We
 * should port all of this to something with Linq ;)
 * 
 * @author mstrobel
 * 
 */
public class QuestionPriority implements Comparator<Question<?>> {
	@Override
	public int compare(Question<?> o1, Question<?> o2) {
		return o1.getPosition() - o2.getPosition();
	}
}