package soap;

import unsubscribe_artifact.UnsubscriptionService;
import unsubscribe_artifact.Unsubscription;

public class Unsubscribe_SOAP_Test {

	public static void main(String[] args) {
		UnsubscriptionService as = new UnsubscriptionService();
		Unsubscription asp = as.getUnsubscriptionPort();
		
		System.out.println(asp.setParams("aclspam@hotmail.com"));

	}

}
