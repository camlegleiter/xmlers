package form;

import net.sf.saxon.tree.linked.NodeImpl;

import com.saxonica.schema.sdoc.SchemaNodeFactory;
import com.saxonica.schema.sdoc.XSDAttribute;
import com.saxonica.schema.sdoc.XSDComplexType;
import com.saxonica.schema.sdoc.XSDElement;

public class SchemaVisitor implements IQuestionVisitor {
			
	@Override
	public String visit(TextQuestion tq) {
		XSDElement question = new XSDElement();
		XSDComplexType questionType = new XSDComplexType();
		XSDAttribute typeDef = new XSDAttribute();
				
		questionType.insertChildren(new NodeImpl[]{typeDef}, true, true);

		return question.getStringValue();
	}

	@Override
	public String visit(RadioQuestion rq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String visit(SelectQuestion sq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String visit(ComplexQuestion cq) {
		// TODO Auto-generated method stub
		return null;
	}
}
