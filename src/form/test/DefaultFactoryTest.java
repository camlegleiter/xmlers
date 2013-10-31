package form.test;

import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import form.ResponseForm;
import form.User;
import form.factory.DefaultFactory;
/**
 * Tests the defaultFactory building abilities including building response forms. 
 * @author Dalia
 *
 */
public class DefaultFactoryTest {
	User mainUser = new User();
	User participant1 = new User();
	User participant2 = new User();
	/**
	 * A form that contains no questions
	 */
	JSONObject formNoQs = new JSONObject();
	
	JSONObject responseMetaText = new JSONObject();
	
	DefaultFactory df = new DefaultFactory();
	@Before
	public void init(){
		initalizeUsers();
		
		formNoQs.put("formID", 123456789);
		formNoQs.put("formName", "Bridge Keeper Questionnaire");
		formNoQs.put("formDescription", "Who would cross the Bridge of Death must answer me these questions three, ere the other side he see.");
		formNoQs.put("formOwner", 987654321);
		
		//ResponseMetadata basic Information
		responseMetaText.put("formID", 123456789);
		responseMetaText.put("formName", "Example Form");
		responseMetaText.put("formDescription", "Bacon ipsum dolor sit amet cow pork belly deserunt excepteur.");
		responseMetaText.put("formOwner", 987654321);
		//ResponseMetadata Questions
		JSONObject textQuestion = new JSONObject();
		textQuestion.put("questionID", 123456789);
		textQuestion.put("type", "Textbox");
		textQuestion.put("maxLength", 1000);
		textQuestion.put("prompt", "Duis pork belly salami aute ea, non dolore pork ullamco ut ham hock.");
		textQuestion.put("value", "I do enjoy a tasty salami");
		JSONArray questions = new JSONArray();
		questions.put(textQuestion);
		responseMetaText.put("formQuestions", questions);
		System.out.println(responseMetaText);
		
	}

	@Test
	public void test() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void noQsFormTest(){
		//df.buildForm(formNoQs);
	}
	
	@Test
	public void responseMetaTextTest(){
		responseMetaText.put("responseID", 888);
		responseMetaText.put("responseOwner", participant1.getUserID());
		responseMetaText.put("responseOwnerName", participant1.getFullName());
		ResponseForm responseform = new DefaultFactory().buildResponseForm(responseMetaText, participant1);
		System.out.println(responseform.getJSONString());

	}
	
	private void initalizeUsers() {
		mainUser.setFirstName("Test");
		mainUser.setLastName("User");
		mainUser.setUserName("testuser");
		mainUser.setEmail("testuser@example.com");
		mainUser.setPassword("password");
		mainUser.setUserID(123);
		participant1.setFirstName("T");
		participant1.setLastName("U");
		participant1.setUserName("t");
		participant1.setEmail("tp@example.com");
		participant1.setPassword("p");
		participant1.setUserID(456);
		participant2.setFirstName("T");
		participant2.setLastName("U");
		participant2.setUserName("tt");
		participant2.setEmail("tp@example.com");
		participant2.setPassword("p");
		participant2.setUserID(789);
	}

}
