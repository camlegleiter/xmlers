//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.12.07 at 05:46:42 PM CST 
//


package dbconnect.xml.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for form complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="form">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="permissions">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="group" type="{http://www.example.org/Form}permission" maxOccurs="unbounded"/>
 *                   &lt;element name="user" type="{http://www.example.org/Form}permission" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="owner" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="participants">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="participant" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="questions">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;choice maxOccurs="unbounded" minOccurs="0">
 *                     &lt;element name="textQuestion" type="{http://www.example.org/Form}textQuestion"/>
 *                     &lt;element name="VariadicBooleanQuestion" type="{http://www.example.org/Form}VariadicBooleanQuestion"/>
 *                     &lt;element name="ComplexQuestion" type="{http://www.example.org/Form}ComplexQuestion"/>
 *                   &lt;/choice>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="responses">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;choice maxOccurs="unbounded" minOccurs="0">
 *                     &lt;element name="textResponse" type="{http://www.example.org/Form}textResponse"/>
 *                     &lt;element name="VariadicBooleanResponse" type="{http://www.example.org/Form}VariadicBooleanResponse"/>
 *                     &lt;element name="ComplexQuestionResponse" type="{http://www.example.org/Form}ComplexQuestionResponse"/>
 *                   &lt;/choice>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="title" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "form", propOrder = {
    "description",
    "permissions",
    "owner",
    "participants",
    "questions",
    "responses"
})
public class Form {

    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true)
    protected Form.Permissions permissions;
    @XmlElement(required = true)
    protected BigInteger owner;
    @XmlElement(required = true)
    protected Form.Participants participants;
    @XmlElement(required = true)
    protected Form.Questions questions;
    @XmlElement(required = true)
    protected Form.Responses responses;
    @XmlAttribute(name = "id")
    protected BigInteger id;
    @XmlAttribute(name = "title")
    protected String title;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the permissions property.
     * 
     * @return
     *     possible object is
     *     {@link Form.Permissions }
     *     
     */
    public Form.Permissions getPermissions() {
        return permissions;
    }

    /**
     * Sets the value of the permissions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Form.Permissions }
     *     
     */
    public void setPermissions(Form.Permissions value) {
        this.permissions = value;
    }

    /**
     * Gets the value of the owner property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOwner() {
        return owner;
    }

    /**
     * Sets the value of the owner property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOwner(BigInteger value) {
        this.owner = value;
    }

    /**
     * Gets the value of the participants property.
     * 
     * @return
     *     possible object is
     *     {@link Form.Participants }
     *     
     */
    public Form.Participants getParticipants() {
        return participants;
    }

    /**
     * Sets the value of the participants property.
     * 
     * @param value
     *     allowed object is
     *     {@link Form.Participants }
     *     
     */
    public void setParticipants(Form.Participants value) {
        this.participants = value;
    }

    /**
     * Gets the value of the questions property.
     * 
     * @return
     *     possible object is
     *     {@link Form.Questions }
     *     
     */
    public Form.Questions getQuestions() {
        return questions;
    }

    /**
     * Sets the value of the questions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Form.Questions }
     *     
     */
    public void setQuestions(Form.Questions value) {
        this.questions = value;
    }

    /**
     * Gets the value of the responses property.
     * 
     * @return
     *     possible object is
     *     {@link Form.Responses }
     *     
     */
    public Form.Responses getResponses() {
        return responses;
    }

    /**
     * Sets the value of the responses property.
     * 
     * @param value
     *     allowed object is
     *     {@link Form.Responses }
     *     
     */
    public void setResponses(Form.Responses value) {
        this.responses = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setId(BigInteger value) {
        this.id = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="participant" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}integer" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "participant"
    })
    public static class Participants {

        protected List<Form.Participants.Participant> participant;

        /**
         * Gets the value of the participant property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the participant property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getParticipant().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Form.Participants.Participant }
         * 
         * 
         */
        public List<Form.Participants.Participant> getParticipant() {
            if (participant == null) {
                participant = new ArrayList<Form.Participants.Participant>();
            }
            return this.participant;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}integer" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Participant {

            @XmlAttribute(name = "id")
            protected BigInteger id;

            /**
             * Gets the value of the id property.
             * 
             * @return
             *     possible object is
             *     {@link BigInteger }
             *     
             */
            public BigInteger getId() {
                return id;
            }

            /**
             * Sets the value of the id property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigInteger }
             *     
             */
            public void setId(BigInteger value) {
                this.id = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="group" type="{http://www.example.org/Form}permission" maxOccurs="unbounded"/>
     *         &lt;element name="user" type="{http://www.example.org/Form}permission" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "group",
        "user"
    })
    public static class Permissions {

        @XmlElement(required = true)
        protected List<Permission> group;
        protected List<Permission> user;

        /**
         * Gets the value of the group property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the group property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getGroup().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Permission }
         * 
         * 
         */
        public List<Permission> getGroup() {
            if (group == null) {
                group = new ArrayList<Permission>();
            }
            return this.group;
        }

        /**
         * Gets the value of the user property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the user property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getUser().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Permission }
         * 
         * 
         */
        public List<Permission> getUser() {
            if (user == null) {
                user = new ArrayList<Permission>();
            }
            return this.user;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;choice maxOccurs="unbounded" minOccurs="0">
     *           &lt;element name="textQuestion" type="{http://www.example.org/Form}textQuestion"/>
     *           &lt;element name="VariadicBooleanQuestion" type="{http://www.example.org/Form}VariadicBooleanQuestion"/>
     *           &lt;element name="ComplexQuestion" type="{http://www.example.org/Form}ComplexQuestion"/>
     *         &lt;/choice>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "textQuestionOrVariadicBooleanQuestionOrComplexQuestion"
    })
    public static class Questions {

        @XmlElements({
            @XmlElement(name = "textQuestion", type = TextQuestion.class),
            @XmlElement(name = "VariadicBooleanQuestion", type = VariadicBooleanQuestion.class),
            @XmlElement(name = "ComplexQuestion", type = ComplexQuestion.class)
        })
        protected List<Question> textQuestionOrVariadicBooleanQuestionOrComplexQuestion;

        /**
         * Gets the value of the textQuestionOrVariadicBooleanQuestionOrComplexQuestion property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the textQuestionOrVariadicBooleanQuestionOrComplexQuestion property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTextQuestionOrVariadicBooleanQuestionOrComplexQuestion().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TextQuestion }
         * {@link VariadicBooleanQuestion }
         * {@link ComplexQuestion }
         * 
         * 
         */
        public List<Question> getTextQuestionOrVariadicBooleanQuestionOrComplexQuestion() {
            if (textQuestionOrVariadicBooleanQuestionOrComplexQuestion == null) {
                textQuestionOrVariadicBooleanQuestionOrComplexQuestion = new ArrayList<Question>();
            }
            return this.textQuestionOrVariadicBooleanQuestionOrComplexQuestion;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;choice maxOccurs="unbounded" minOccurs="0">
     *           &lt;element name="textResponse" type="{http://www.example.org/Form}textResponse"/>
     *           &lt;element name="VariadicBooleanResponse" type="{http://www.example.org/Form}VariadicBooleanResponse"/>
     *           &lt;element name="ComplexQuestionResponse" type="{http://www.example.org/Form}ComplexQuestionResponse"/>
     *         &lt;/choice>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "textResponseOrVariadicBooleanResponseOrComplexQuestionResponse"
    })
    public static class Responses {

        @XmlElements({
            @XmlElement(name = "textResponse", type = TextResponse.class),
            @XmlElement(name = "VariadicBooleanResponse", type = VariadicBooleanResponse.class),
            @XmlElement(name = "ComplexQuestionResponse", type = ComplexQuestionResponse.class)
        })
        protected List<Response> textResponseOrVariadicBooleanResponseOrComplexQuestionResponse;

        /**
         * Gets the value of the textResponseOrVariadicBooleanResponseOrComplexQuestionResponse property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the textResponseOrVariadicBooleanResponseOrComplexQuestionResponse property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTextResponseOrVariadicBooleanResponseOrComplexQuestionResponse().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TextResponse }
         * {@link VariadicBooleanResponse }
         * {@link ComplexQuestionResponse }
         * 
         * 
         */
        public List<Response> getTextResponseOrVariadicBooleanResponseOrComplexQuestionResponse() {
            if (textResponseOrVariadicBooleanResponseOrComplexQuestionResponse == null) {
                textResponseOrVariadicBooleanResponseOrComplexQuestionResponse = new ArrayList<Response>();
            }
            return this.textResponseOrVariadicBooleanResponseOrComplexQuestionResponse;
        }

    }

}
