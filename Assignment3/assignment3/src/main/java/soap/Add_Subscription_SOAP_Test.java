package soap;

import add_subscription_artifact.SubscriptionService;
import add_subscription_artifact.Subscription;

public class Add_Subscription_SOAP_Test {

	public static void main(String[] args) {
		SubscriptionService as = new SubscriptionService();
		Subscription asp = as.getSubscriptionPort();
		
		System.out.println(asp.setParams("aclspam@hotmail.com", "Generic Name", "Generic Brand", "0", "1000"));

	}

}
