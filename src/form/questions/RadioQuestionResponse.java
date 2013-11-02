package form.questions;

import form.User;
import form.visitors.IResponseVisitor;

public class RadioQuestionResponse extends VariadicQuestionResponse {

	public RadioQuestionResponse(String key, AbstractVariadicQuestion parent, User author) {
		super(key, parent, author);
	}

	@Override
	public void accept(IResponseVisitor visitor) {
		visitor.visit(this);
	}
	
}
