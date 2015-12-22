package main;

import java.util.Scanner;

import add_smartphone_artifact.SmartphoneService;
import add_smartphone_artifact.Smartphone;
import add_subscription_artifact.SubscriptionService;
import add_subscription_artifact.Subscription;
import unsubscribe_artifact.UnsubscriptionService;
import unsubscribe_artifact.Unsubscription;

public class SOAP_Client {

	public static void main(String[] args) {
		int option = 0;
		Scanner sc = new Scanner(System.in);
		while(true) {
			do {
				try {
					System.out.println("Insert wanted option: ");
					System.out.println("1 - Add subscription");
					System.out.println("2 - Delete subscription");
					System.out.println("3 - Add smartphone");
					System.out.println("4 - Exit");
					option = Integer.parseInt(sc.nextLine());
				} catch(NumberFormatException e) {}
				
			} while(option < 1 || option > 4);

			switch(option) {
				case 1: {
					String email, name, brand, min_price, max_price;
					System.out.println("Insert your e-mail: ");
					email = sc.nextLine();
					System.out.println("Insert smartphone name: ");
					name = sc.nextLine();
					System.out.println("Insert smartphone brand: ");
					brand = sc.nextLine();
					System.out.println("Insert minimum price: ");
					min_price = sc.nextLine();
					System.out.println("Insert maximum price: ");
					max_price = sc.nextLine();
					subscribe(email, name, brand, min_price, max_price);
					break;
				} case 2: {
					String email;
					System.out.println("Insert your e-mail: ");
					email = sc.nextLine();
					unsubscribe(email);
					break;
				} case 3: {
					String xml;
					System.out.println("Insert smartphone xml as a single line string: ");
					xml = sc.nextLine();
					addSmartphone(xml);
					break;
				}case 4: {
					System.out.println("Bye bye, now!");
					System.exit(0);
					break;
				} default: {
					System.out.println("Oops, something went wrong!");
					break;
				}
			}
			System.out.println();
			System.out.println();
			option = 0;
		}

	}

	public static String subscribe(String email, String name, String brand, String min_price, String max_price) {
		SubscriptionService as = new SubscriptionService();
		Subscription asp = as.getSubscriptionPort();
		
		String result = asp.setParams(email, name, brand, min_price, max_price);

		System.out.println(result);

		return result;
	}

	public static String unsubscribe(String email) {
		UnsubscriptionService as = new UnsubscriptionService();
		Unsubscription asp = as.getUnsubscriptionPort();
		
		String result = asp.setParams(email);

		if(result.compareTo("'1'") == 0) {
			System.out.println("Successfully deleted subscription");
		} else {
			System.out.println("Error removing subscription");
		}

		return result;
	}

	public static String addSmartphone(String xml) {
		SmartphoneService as = new SmartphoneService();
		Smartphone asp = as.getSmartphonePort();
		
		String result = asp.setXML(xml);

		System.out.println(result);

		return result;
	}
}
