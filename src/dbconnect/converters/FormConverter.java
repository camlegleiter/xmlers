package dbconnect.converters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dbconnect.dao.TextQuestion;
import dbconnect.dao.TextResponse;
import dbconnect.dao.VariadicBooleanEntry;
import dbconnect.dao.VariadicBooleanQuestion;
import dbconnect.dao.VariadicBooleanResponse;
import form.Form;
import form.questions.AbstractVariadicQuestion;
import form.questions.Question;
import form.questions.AbstractVariadicQuestion.Entry;

public class FormConverter implements IConverter<Form, dbconnect.dao.Form> {

	private final static IConverter<Form, dbconnect.dao.Form> INSTANCE;
		
	static
	{
		INSTANCE = new FormConverter();
	}
	
	private FormConverter()
	{
		//Intentionally Left Blank.
	}
	
	public static IConverter<Form, dbconnect.dao.Form> getInstance()
	{
		return INSTANCE;
	}
	
	@Override
	public Form convert(dbconnect.dao.Form other) {
		
		Form retval;
		retval = new Form();
		Map<Integer, form.questions.Question<?>> loadedQuestions;

		retval.setFormId(Integer.parseInt(other.getId()));
		retval.setDescription(other.getDescription());
		
		loadedQuestions = new HashMap<Integer, form.questions.Question<?>>();
		
		//Convert the questions individually
		for(dbconnect.dao.Question q : other.getQuestions().getTextQuestionOrVariadicBooleanQuestionOrComplexQuestion())
		{
			form.questions.Question<?> cq;
			
			if(q instanceof dbconnect.dao.VariadicBooleanQuestion)
			{
				cq = VariadicQuestionConverter.getInstance().convert((VariadicBooleanQuestion) q);
			}
			else if(q instanceof dbconnect.dao.TextQuestion)
			{
				cq = TextQuestionConverter.getInstance().convert((TextQuestion) q);
			}
			else
			{
				throw new RuntimeException("Questions of type \"" + q.getClass().getName() + "\" are unsupported at the moment."); 
			}
			
			retval.add(cq);
		}
		
		//Load and link responses to questions.
		for(dbconnect.dao.Response resp : other.getResponses().getTextResponseOrVariadicBooleanResponseOrComplexQuestionResponse())
		{
			Integer parentID = Integer.parseInt(resp.getParent());			
			Question<?> parentQuestion = loadedQuestions.get(parentID);
			Integer authorID = Integer.parseInt(resp.getAuthor());
			
			if(resp instanceof dbconnect.dao.TextResponse)
			{
				dbconnect.dao.TextResponse tr = (TextResponse) resp;
				((form.questions.TextQuestion) parentQuestion).setResponse(authorID, tr.getValue());
			}
			else if(resp instanceof dbconnect.dao.VariadicBooleanResponse)
			{
				List<Entry> optionList;
				EntryConverter ec;
				dbconnect.dao.VariadicBooleanResponse vbr = (VariadicBooleanResponse) resp;
				form.questions.AbstractVariadicQuestion realParent = (form.questions.AbstractVariadicQuestion) parentQuestion;
				
				ec = new EntryConverter(realParent);
				List<VariadicBooleanEntry> unprocessedList = vbr.getOptionResponse();
				
				optionList = new ArrayList<Entry>();
				
				for(VariadicBooleanEntry vbe : unprocessedList)
				{
					optionList.add(ec.convert(vbe));
				}
				
				(realParent).setResponse(authorID, optionList);
			}
			else
			{
				throw new RuntimeException("Response of type \"" + resp.getClass().getName() + "\" are unsupported at the moment.");
			}
		}
		
		return retval;
	}

	@Override
	public dbconnect.dao.Form unconvert(Form other) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Like the rest of the classes in this package, converts between DAOs and other objects.
	 * @author mstrobel
	 * @note This is placed here because it is assumed that the implementation of an Entry DAO is 
	 * 	fundamentally hinged with the implementation of a VariadicBooleanQuestion.
	 *
	 */
	public static class EntryConverter implements IConverter<Entry, dbconnect.dao.VariadicBooleanEntry>
	{
		/**
		 * Links to the question with the list of options that the Entry relies on.
		 */
		private AbstractVariadicQuestion parent;
		
		private EntryConverter(AbstractVariadicQuestion parent)
		{
			this.parent = parent;
		}

		@Override
		public Entry convert(VariadicBooleanEntry other) {
			return parent.new Entry(other.getOption(), other.isResponse());			
		}

		@Override
		public VariadicBooleanEntry unconvert(Entry other) {
			VariadicBooleanEntry retVal = new VariadicBooleanEntry();
			retVal.setOption(other.getText());
			retVal.setResponse(other.getChecked());
			return retVal;
		}
		
	}
}
