package soap;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class Smartphone {
	@WebMethod
	public String setXML(String xml) {
		return "Success";
	}

}
