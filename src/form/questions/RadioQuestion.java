package form.questions;

import java.util.ArrayList;

import form.visitors.IQuestionVisitor;

public class RadioQuestion extends Question {

	private ArrayList<String> answers = new ArrayList<String>();
	
	public RadioQuestion(String id, int weight, String prompt, ArrayList<String> answers) {
		super(id, weight, prompt);
		this.answers = answers;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(IQuestionVisitor visitor) {
		visitor.visit(this);
	}

	public ArrayList<String> getAnswers() {
		return answers;
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
}
