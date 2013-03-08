package form;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

public class Form {
	private Queue<Question> questions;

	public Form() {
		questions = new PriorityQueue<Question>(1, new QuestionPriority());
	}

	public void add(Question q) {
		questions.add(q);
	}

	public Iterator<Question> getQuestions() {
		// TODO
		return null;
	}

	private class QuestionPriority implements Comparator<Question> {
		@Override
		public int compare(Question o1, Question o2) {
			return o1.getPosition() - o2.getPosition();
		}

	}
}
