
package add_smartphone_artifact;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the add_smartphone_artifact package. 
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

    private final static QName _SetXMLResponse_QNAME = new QName("http://soap/", "setXMLResponse");
    private final static QName _SetXML_QNAME = new QName("http://soap/", "setXML");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: add_smartphone_artifact
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SetXML }
     * 
     */
    public SetXML createSetXML() {
        return new SetXML();
    }

    /**
     * Create an instance of {@link SetXMLResponse }
     * 
     */
    public SetXMLResponse createSetXMLResponse() {
        return new SetXMLResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetXMLResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap/", name = "setXMLResponse")
    public JAXBElement<SetXMLResponse> createSetXMLResponse(SetXMLResponse value) {
        return new JAXBElement<SetXMLResponse>(_SetXMLResponse_QNAME, SetXMLResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetXML }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap/", name = "setXML")
    public JAXBElement<SetXML> createSetXML(SetXML value) {
        return new JAXBElement<SetXML>(_SetXML_QNAME, SetXML.class, null, value);
    }

}
