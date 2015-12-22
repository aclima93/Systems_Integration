package soap;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class Subscription {
	@WebMethod
	public String setParams(String email, String name, String brand, String min_price, String max_price) {
		return "Success";
	}
}
