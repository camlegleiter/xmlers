//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.11.13 at 04:48:52 PM CST 
//


package dbconnect.xml.dao;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VariadicBooleanEntry complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VariadicBooleanEntry">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="option" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="response" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VariadicBooleanEntry")
public class VariadicBooleanEntry {

    @XmlAttribute(name = "option")
    protected String option;
    @XmlAttribute(name = "response")
    protected Boolean response;

    /**
     * Gets the value of the option property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOption() {
        return option;
    }

    /**
     * Sets the value of the option property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOption(String value) {
        this.option = value;
    }

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setResponse(Boolean value) {
        this.response = value;
    }

}
