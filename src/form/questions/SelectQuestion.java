package form.questions;

import java.util.ArrayList;

import form.visitors.IQuestionVisitor;

public class SelectQuestion extends Question {
	
	private ArrayList<String> answers = new ArrayList<String>();

	public SelectQuestion(String id, int weight, String prompt, ArrayList<String> answers) {
		super(id, weight, prompt);
		this.answers = answers;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(IQuestionVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String getResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setResponse(String ans) {
		// TODO Auto-generated method stub
		
	}

	
	public ArrayList<String> getAnswers() {
		return answers;
	}
}
