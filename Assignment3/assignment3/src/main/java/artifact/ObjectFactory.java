
package artifact;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the artifact package. 
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

    private final static QName _SetParameters_QNAME = new QName("http://generated/", "setParameters");
    private final static QName _SetParametersResponse_QNAME = new QName("http://generated/", "setParametersResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: artifact
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SetParameters }
     * 
     */
    public SetParameters createSetParameters() {
        return new SetParameters();
    }

    /**
     * Create an instance of {@link SetParametersResponse }
     * 
     */
    public SetParametersResponse createSetParametersResponse() {
        return new SetParametersResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetParameters }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://generated/", name = "setParameters")
    public JAXBElement<SetParameters> createSetParameters(SetParameters value) {
        return new JAXBElement<SetParameters>(_SetParameters_QNAME, SetParameters.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetParametersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://generated/", name = "setParametersResponse")
    public JAXBElement<SetParametersResponse> createSetParametersResponse(SetParametersResponse value) {
        return new JAXBElement<SetParametersResponse>(_SetParametersResponse_QNAME, SetParametersResponse.class, null, value);
    }

}
