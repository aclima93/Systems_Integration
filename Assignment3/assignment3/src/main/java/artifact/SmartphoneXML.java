
package artifact;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "SmartphoneXML", targetNamespace = "http://generated/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface SmartphoneXML {


    /**
     * 
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "setParameters", targetNamespace = "http://generated/", className = "artifact.SetParameters")
    @ResponseWrapper(localName = "setParametersResponse", targetNamespace = "http://generated/", className = "artifact.SetParametersResponse")
    public void setParameters(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

}