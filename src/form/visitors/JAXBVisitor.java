package form.visitors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import form.questions.CheckQuestion;
import form.questions.ComplexQuestion;
import form.questions.RadioQuestion;
import form.questions.SelectQuestion;
import form.questions.TextQuestion;

public class JAXBVisitor extends AbstractQuestionVisitor {

	private static Marshaller FORM_MARSHALLER;
	
	static
	{
		try {
			JAXBContext formContext = JAXBContext.newInstance(dbconnect.xml.dao.Form.class);
			FORM_MARSHALLER = formContext.createMarshaller();
		} catch (JAXBException e) {
			e.printStackTrace();
			System.exit(1);
		}			
	}
	
	@Override
	public String visit(TextQuestion tq) {
		
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public String visit(CheckQuestion checkQuestion) {
		// TODO Auto-generated method stub
		return null;
	}

}
