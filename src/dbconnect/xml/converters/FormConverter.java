package dbconnect.xml.converters;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dbconnect.IConverter;
import dbconnect.xml.dao.ComplexQuestion;
import dbconnect.xml.dao.Form.Participants.Participant;
import dbconnect.xml.dao.TextQuestion;
import dbconnect.xml.dao.TextResponse;
import dbconnect.xml.dao.VariadicBooleanEntry;
import dbconnect.xml.dao.VariadicBooleanQuestion;
import dbconnect.xml.dao.VariadicBooleanResponse;
import form.Form;
import form.User;
import form.questions.AbstractVariadicQuestion;
import form.questions.Question;
import form.questions.AbstractVariadicQuestion.Entry;

public class FormConverter implements IConverter<Form, dbconnect.xml.dao.Form> {

	private final static IConverter<Form, dbconnect.xml.dao.Form> INSTANCE;
		
	static
	{
		INSTANCE = new FormConverter();
	}
	
	private FormConverter()
	{
		//Intentionally Left Blank.
	}
	
	public static IConverter<Form, dbconnect.xml.dao.Form> getInstance()
	{
		return INSTANCE;
	}
	
	@Override
	public Form convert(dbconnect.xml.dao.Form other) {
		
		Form retval;
		retval = new Form();
		Map<Integer, form.questions.Question<?>> loadedQuestions;

		retval.setFormId(other.getId().intValue());
		retval.setDescription(other.getDescription());
		retval.setOwnerId(other.getOwner().intValue());
		
		loadedQuestions = new HashMap<Integer, form.questions.Question<?>>();
		
		//Convert the questions individually
		for(dbconnect.xml.dao.Question q : other.getQuestions().getTextQuestionOrVariadicBooleanQuestionOrComplexQuestion())
		{
			form.questions.Question<?> cq;
			
			if(q instanceof dbconnect.xml.dao.VariadicBooleanQuestion)
			{
				cq = VariadicQuestionConverter.getInstance().convert((VariadicBooleanQuestion) q);
			}
			else if(q instanceof dbconnect.xml.dao.TextQuestion)
			{
				cq = TextQuestionConverter.getInstance().convert((TextQuestion) q);
			}
			else if(q instanceof dbconnect.xml.dao.ComplexQuestion)
			{
				cq = ComplexQuestionConverter.getInstance().convert((ComplexQuestion) q);
			}
			else
			{
				throw new RuntimeException("Questions of type \"" + q.getClass().getName() + "\" are unsupported at the moment."); 
			}
			
			retval.add(cq);
		}
		
		//Load and link responses to questions.
		for(dbconnect.xml.dao.Response resp : other.getResponses().getTextResponseOrVariadicBooleanResponseOrComplexQuestionResponse())
		{
			Integer parentID = Integer.parseInt(resp.getParent());			
			Question<?> parentQuestion = loadedQuestions.get(parentID);
			Integer authorID = Integer.parseInt(resp.getAuthor());
			
			if(resp instanceof dbconnect.xml.dao.TextResponse)
			{
				dbconnect.xml.dao.TextResponse tr = (TextResponse) resp;
				((form.questions.TextQuestion) parentQuestion).setResponse(authorID, tr.getValue());
			}
			else if(resp instanceof dbconnect.xml.dao.VariadicBooleanResponse)
			{
				List<Entry> optionList;
				EntryConverter ec;
				dbconnect.xml.dao.VariadicBooleanResponse vbr = (VariadicBooleanResponse) resp;
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
	public dbconnect.xml.dao.Form unconvert(Form other) {
		dbconnect.xml.dao.Form retval;
		
		retval = new dbconnect.xml.dao.Form();
		
		retval.setId(BigInteger.valueOf(other.getFormId()));
		retval.setTitle(other.getTitle());
		retval.setDescription(other.getDescription());
		retval.setOwner(BigInteger.valueOf(other.getOwnerId()));
		List<Participant> liveParticipantList = retval.getParticipants().getParticipant();
		
		for(User u : other.getParticipants())
		{
			Participant beingAdded = new Participant();
			beingAdded.setId(BigInteger.valueOf(u.getUserID()));
			liveParticipantList.add(beingAdded);
		}

		for(Question<?> q : other)
		{
			dbconnect.xml.dao.Question newQuestion;
			
			newQuestion = null;
			
			if(q instanceof AbstractVariadicQuestion)
			{								
				newQuestion = VariadicQuestionConverter.getInstance().unconvert((AbstractVariadicQuestion) q);
				
				
				((VariadicBooleanQuestion) newQuestion).setAllowMultiple(((AbstractVariadicQuestion) q).getVariadic());
				
				
				((VariadicBooleanQuestion) newQuestion).setType(((AbstractVariadicQuestion) q).getType());
				
				List<String> optionList = ((VariadicBooleanQuestion) newQuestion).getOption();				
				optionList.addAll(((AbstractVariadicQuestion) q).getOptions());				
			}
			else if(q instanceof form.questions.TextQuestion)
			{
				newQuestion = TextQuestionConverter.getInstance().unconvert((form.questions.TextQuestion) q);
			}
			else if(q instanceof form.questions.ComplexQuestion)
			{
				newQuestion = ComplexQuestionConverter.getInstance().unconvert((form.questions.ComplexQuestion) q);
			}
			newQuestion.setId(BigInteger.valueOf(q.getId()));
			newQuestion.setPrompt(q.getPrompt());			
			newQuestion.setPriority(Long.valueOf(q.getPosition()));
			
			retval.getQuestions().getTextQuestionOrVariadicBooleanQuestionOrComplexQuestion().add(newQuestion);
		}
		
		//TODO Convert Responses.
		
		return retval;
	}

	/**
	 * Like the rest of the classes in this package, converts between DAOs and other objects.
	 * @author mstrobel
	 * @note This is placed here because it is assumed that the implementation of an Entry DAO is 
	 * 	fundamentally hinged with the implementation of a VariadicBooleanQuestion.
	 *
	 */
	public static class EntryConverter implements IConverter<Entry, dbconnect.xml.dao.VariadicBooleanEntry>
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
