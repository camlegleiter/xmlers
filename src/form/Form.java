package form;

import java.util.*;

import form.questions.*;
import form.visitors.*;

public class Form implements Iterable<Question>{
	
	private String title;
	private String key;
	private String description;
	
	public static final int ALL_BITS = -1;
	public static final int KEY_BIT = 0x1;
	public static final int DESCRIPTION_BIT = 0x2;
	public static final int TITLE_BIT = 0x4;
	public static final int QUESTIONS_BIT = 0x8;
	
	
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void add(Question q) {
		questions.add(q);
	}

	@Override
	public Iterator<Question> iterator() {
		return questions.iterator();
	}
	
	public String getHTML()
	{
		return getHTML(ALL_BITS, null);
	}
	
	public String getHTML(int settings, String user)
	{
		HTMLVisitor generator = new HTMLVisitor();
		StringBuilder output = new StringBuilder();
		output.append("<div class=\"form\">\n");
		if(bitSet(settings, TITLE_BIT))
		{
			output.append("\t<div class=\"formTitle\">" + this.getTitle() + "</div>\n");
		}
		if(bitSet(settings, KEY_BIT))
		{
			output.append("\t<div class=\"formID\">" + this.getKey() + "</div>\n");
		}
		if(bitSet(settings, Form.DESCRIPTION_BIT))
		{
			output.append("\t<div class=\"formDescription\">" + this.getDescription() + "</div>\n");
		}
		if(bitSet(settings, Form.QUESTIONS_BIT))
		output.append("\t<div class=\"formQuestions\">\n");
		output.append(VisitMechanism.visit(generator, this.iterator(), ""));
		output.append("\t</div>\n");
		output.append("</div>\n");
		
		return output.toString();
	}
	
	
	public String getJSON()
	{
		return getJSON(ALL_BITS, null);
	}
	
	public String getJSON(int settings, String user)
	{
		throw new RuntimeException("This function is not yet implemented");
	}
	
	private static boolean bitSet(int field, int mask)
	{
		return 0 != (field & mask);
	}
	
	/**
	 * Does some work that really should have been done by the language.
	 * We should port all of this to something with Linq ;)
	 * 
	 * @author mstrobel
	 *
	 */
	private class QuestionPriority implements Comparator<Question> {
		@Override
		public int compare(Question o1, Question o2) {
			return o1.getPosition() - o2.getPosition();
		}
	}

}
