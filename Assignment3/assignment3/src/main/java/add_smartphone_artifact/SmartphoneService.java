
package add_smartphone_artifact;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "SmartphoneService", targetNamespace = "http://soap/", wsdlLocation = "http://localhost:8081/soap_add_smartphone?wsdl")
public class SmartphoneService
    extends Service
{

    private final static URL SMARTPHONESERVICE_WSDL_LOCATION;
    private final static WebServiceException SMARTPHONESERVICE_EXCEPTION;
    private final static QName SMARTPHONESERVICE_QNAME = new QName("http://soap/", "SmartphoneService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8081/soap_add_smartphone?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        SMARTPHONESERVICE_WSDL_LOCATION = url;
        SMARTPHONESERVICE_EXCEPTION = e;
    }

    public SmartphoneService() {
        super(__getWsdlLocation(), SMARTPHONESERVICE_QNAME);
    }

    public SmartphoneService(WebServiceFeature... features) {
        super(__getWsdlLocation(), SMARTPHONESERVICE_QNAME, features);
    }

    public SmartphoneService(URL wsdlLocation) {
        super(wsdlLocation, SMARTPHONESERVICE_QNAME);
    }

    public SmartphoneService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, SMARTPHONESERVICE_QNAME, features);
    }

    public SmartphoneService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SmartphoneService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns Smartphone
     */
    @WebEndpoint(name = "SmartphonePort")
    public Smartphone getSmartphonePort() {
        return super.getPort(new QName("http://soap/", "SmartphonePort"), Smartphone.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Smartphone
     */
    @WebEndpoint(name = "SmartphonePort")
    public Smartphone getSmartphonePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://soap/", "SmartphonePort"), Smartphone.class, features);
    }

    private static URL __getWsdlLocation() {
        if (SMARTPHONESERVICE_EXCEPTION!= null) {
            throw SMARTPHONESERVICE_EXCEPTION;
        }
        return SMARTPHONESERVICE_WSDL_LOCATION;
    }

}
