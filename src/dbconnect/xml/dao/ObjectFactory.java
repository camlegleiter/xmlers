//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.12.07 at 04:32:15 PM CST 
//


package dbconnect.xml.dao;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the dbconnect.xml.dao package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: dbconnect.xml.dao
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link Group }
     * 
     */
    public Group createGroup() {
        return new Group();
    }

    /**
     * Create an instance of {@link Form }
     * 
     */
    public Form createForm() {
        return new Form();
    }

    /**
     * Create an instance of {@link Form.Participants }
     * 
     */
    public Form.Participants createFormParticipants() {
        return new Form.Participants();
    }

    /**
     * Create an instance of {@link Forms }
     * 
     */
    public Forms createForms() {
        return new Forms();
    }

    /**
     * Create an instance of {@link Response }
     * 
     */
    public Response createResponse() {
        return new Response();
    }

    /**
     * Create an instance of {@link TextQuestion }
     * 
     */
    public TextQuestion createTextQuestion() {
        return new TextQuestion();
    }

    /**
     * Create an instance of {@link TextResponse }
     * 
     */
    public TextResponse createTextResponse() {
        return new TextResponse();
    }

    /**
     * Create an instance of {@link VariadicBooleanEntry }
     * 
     */
    public VariadicBooleanEntry createVariadicBooleanEntry() {
        return new VariadicBooleanEntry();
    }

    /**
     * Create an instance of {@link Permission }
     * 
     */
    public Permission createPermission() {
        return new Permission();
    }

    /**
     * Create an instance of {@link Question }
     * 
     */
    public Question createQuestion() {
        return new Question();
    }

    /**
     * Create an instance of {@link VariadicBooleanQuestion }
     * 
     */
    public VariadicBooleanQuestion createVariadicBooleanQuestion() {
        return new VariadicBooleanQuestion();
    }

    /**
     * Create an instance of {@link VariadicBooleanResponse }
     * 
     */
    public VariadicBooleanResponse createVariadicBooleanResponse() {
        return new VariadicBooleanResponse();
    }

    /**
     * Create an instance of {@link ComplexQuestionResponse }
     * 
     */
    public ComplexQuestionResponse createComplexQuestionResponse() {
        return new ComplexQuestionResponse();
    }

    /**
     * Create an instance of {@link ComplexQuestion }
     * 
     */
    public ComplexQuestion createComplexQuestion() {
        return new ComplexQuestion();
    }

    /**
     * Create an instance of {@link Users }
     * 
     */
    public Users createUsers() {
        return new Users();
    }

    /**
     * Create an instance of {@link User.Owns }
     * 
     */
    public User.Owns createUserOwns() {
        return new User.Owns();
    }

    /**
     * Create an instance of {@link User.Participating }
     * 
     */
    public User.Participating createUserParticipating() {
        return new User.Participating();
    }

    /**
     * Create an instance of {@link Group.Member }
     * 
     */
    public Group.Member createGroupMember() {
        return new Group.Member();
    }

    /**
     * Create an instance of {@link Form.Permissions }
     * 
     */
    public Form.Permissions createFormPermissions() {
        return new Form.Permissions();
    }

    /**
     * Create an instance of {@link Form.Questions }
     * 
     */
    public Form.Questions createFormQuestions() {
        return new Form.Questions();
    }

    /**
     * Create an instance of {@link Form.Responses }
     * 
     */
    public Form.Responses createFormResponses() {
        return new Form.Responses();
    }

    /**
     * Create an instance of {@link Form.Participants.Participant }
     * 
     */
    public Form.Participants.Participant createFormParticipantsParticipant() {
        return new Form.Participants.Participant();
    }

}
