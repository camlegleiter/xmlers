package form;

import form.visitors.IQuestionVisitor;

public interface IVisitable {

	public void accept(IQuestionVisitor visitor);

}
