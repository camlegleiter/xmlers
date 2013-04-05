package form;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

public class Form implements Iterable<Question>{
	private Queue<Question> questions;

	/**
	 * Finds an existing form in the file system, and loads it into
	 * a new instance of the form. 
	 * @param formID
	 * @return
	 */
	public static Form fetchForm(String formID)
	{
		//TODO
		return null;
	}
	
	public Form() {
		questions = new PriorityQueue<Question>(1, new QuestionPriority());
	}

	public void add(Question q) {
		questions.add(q);
	}

	@Override
	public Iterator<Question> iterator() {
		return questions.iterator();
	}
	
	private class QuestionPriority implements Comparator<Question> {
		@Override
		public int compare(Question o1, Question o2) {
			return o1.getPosition() - o2.getPosition();
		}

	}

}
