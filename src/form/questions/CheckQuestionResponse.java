package form.questions;

import form.User;
import form.visitors.IResponseVisitor;

public class CheckQuestionResponse extends VariadicQuestionResponse {
	
	public CheckQuestionResponse(String key, AbstractVariadicQuestion parent, User author) {
		super(key, parent, author);
	}

	@Override
	public void accept(IResponseVisitor visitor) {
		visitor.visit(this);
	}
}
