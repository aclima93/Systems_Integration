
package add_subscription_artifact;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the add_subscription_artifact package. 
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

    private final static QName _SetParamsResponse_QNAME = new QName("http://soap/", "setParamsResponse");
    private final static QName _SetParams_QNAME = new QName("http://soap/", "setParams");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: add_subscription_artifact
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SetParamsResponse }
     * 
     */
    public SetParamsResponse createSetParamsResponse() {
        return new SetParamsResponse();
    }

    /**
     * Create an instance of {@link SetParams }
     * 
     */
    public SetParams createSetParams() {
        return new SetParams();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetParamsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap/", name = "setParamsResponse")
    public JAXBElement<SetParamsResponse> createSetParamsResponse(SetParamsResponse value) {
        return new JAXBElement<SetParamsResponse>(_SetParamsResponse_QNAME, SetParamsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetParams }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soap/", name = "setParams")
    public JAXBElement<SetParams> createSetParams(SetParams value) {
        return new JAXBElement<SetParams>(_SetParams_QNAME, SetParams.class, null, value);
    }

}
