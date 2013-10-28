package form.questions;

import form.visitors.IResponseVisitor;

public interface IVisitableResponse {
	
	public void accept(IResponseVisitor visitor);
	
}
