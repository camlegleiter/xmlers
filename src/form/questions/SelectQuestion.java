package form.questions;

import java.util.ArrayList;

import form.visitors.IQuestionVisitor;

public class SelectQuestion extends Question {
	
	private String response;
	
	private ArrayList<String> options = new ArrayList<String>();

	public SelectQuestion(String id, int weight, String prompt, ArrayList<String> answers) {
		super(id, weight, prompt);
		this.options = answers;
		response = "";
	}

	@Override
	public void accept(IQuestionVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String getResponse() {
		return response;
	}

	@Override
	public void setResponse(String ans) {
		this.response = ans;
	}

	
	public ArrayList<String> getOptions() {
		return options;
	}
}
