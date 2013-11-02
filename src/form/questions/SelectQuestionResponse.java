package form.questions;
import form.User;
import form.visitors.IResponseVisitor;

public class SelectQuestionResponse extends VariadicQuestionResponse {

	public SelectQuestionResponse(String key, AbstractVariadicQuestion parent,
			User author) {
		super(key, parent, author);
	}

	@Override
	public void accept(IResponseVisitor visitor) {
		visitor.visit(this);
	}

}
