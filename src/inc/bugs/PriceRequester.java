package inc.bugs;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class PriceRequester {
    private static int MAX_TRIES = 5;
    private ConnectionFactory connectionFactory = null;
    private Destination destination = null;
    private TemporaryQueue temporaryQueue = null;

    public static void main(String[] args) throws JMSException, NamingException, IOException {
        PriceRequester priceRequester = new PriceRequester();
        for (int i = 0; i < MAX_TRIES; i++) {
            if(priceRequester.initialize()) {
                priceRequester.mainLoop();
                return;
            }
        }
    }

    private boolean initialize() {
        try {
            System.setProperty("java.naming.factory.initial", "org.jboss.naming.remote.client.InitialContextFactory");
            System.setProperty("java.naming.provider.url", "http-remoting://localhost:8080");
            this.connectionFactory = InitialContext.doLookup("jms/RemoteConnectionFactory");
            this.destination = InitialContext.doLookup("jms/queue/queue");
            return true;
        } catch (NamingException e) {
            System.err.println("Error setting JMS connection.");
            return false;
        }
    }

    private String requestInfo(HashMap<SEARCH_MODES, String> searchTerms) {
        String result;
        try(JMSContext jmsContext = connectionFactory.createContext("pjaneiro","|Sisc00l")){
            System.out.println("Sending request.");
            temporaryQueue = jmsContext.createTemporaryQueue();
            JMSProducer jmsProducer = jmsContext.createProducer();
            ObjectMessage objectMessage = jmsContext.createObjectMessage(searchTerms);
            objectMessage.setJMSReplyTo(temporaryQueue);
            jmsProducer.send(destination, objectMessage);

            System.out.println("Waiting for response.");
            JMSConsumer jmsConsumer = jmsContext.createConsumer(temporaryQueue);
            Message message = jmsConsumer.receive();
            result = message.getBody(String.class);
            jmsConsumer.close();
            temporaryQueue.delete();
        } catch (JMSException e) {
            e.printStackTrace();
            System.err.println("Error sending/receiving request.");
            result = "";
        }
        return result;
    }

    private void mainLoop() {
        HashMap<SEARCH_MODES, String> searchTerm = new HashMap<>();
        String result;
        while (true) {
            System.out.println("Insert desired search term:");
            System.out.println("1 - Search by brand");
            System.out.println("2 - Search by brand and name");
            System.out.println("3 - Search by name");
            System.out.println("4 - Search by price range");
            System.out.println("0 - Exit");
            Scanner scanner = new Scanner(System.in);
            int option;
            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Insert a valid option.");
                continue;
            }
            switch (option) {
                case 0:
                    System.out.println("Exiting.");
                    System.exit(0);
                    break;
                case 1:
                    System.out.println("Insert brand:");
                    String brand = scanner.nextLine();
                    searchTerm.put(SEARCH_MODES.BRAND, brand);
                    result = this.requestInfo(searchTerm);
                    if(result != null && result.length()!=0) {
                        System.out.println("The following smartphones have been found:");
                        System.out.print(result);
                    } else {
                        System.out.println("No smartphones have been found.");
                    }
                    searchTerm.clear();
                    break;
                case 2:
                    System.out.println("Insert brand and model as \"<brand> <name>\" (for example, \"Nokia verde\"):");
                    String brand_model = scanner.nextLine();
                    searchTerm.put(SEARCH_MODES.BRAND_NAME, brand_model);
                    result = this.requestInfo(searchTerm);
                    if(result != null && result.length()!=0) {
                        System.out.println("The following smartphones have been found:");
                        System.out.print(result);
                    } else {
                        System.out.println("No smartphones have been found.");
                    }
                    searchTerm.clear();
                    break;
                case 3:
                    System.out.println("Insert name:");
                    String name = scanner.nextLine();
                    searchTerm.put(SEARCH_MODES.NAME, name);
                    result = this.requestInfo(searchTerm);
                    if(result != null && result.length()!=0) {
                        System.out.println("The following smartphones have been found:");
                        System.out.print(result);
                    } else {
                        System.out.println("No smartphones have been found.");
                    }
                    searchTerm.clear();
                    break;
                case 4:
                    System.out.println("Insert minimum and maximum price as \"<min_price>[-]<max_price>\":");
                    String prices = scanner.nextLine();
                    searchTerm.put(SEARCH_MODES.PRICE_RANGE, prices);
                    result = this.requestInfo(searchTerm);
                    if(result != null && result.length()!=0) {
                        System.out.println("The following smartphones have been found:");
                        System.out.print(result);
                    } else {
                        System.out.println("No smartphones have been found.");
                    }
                    searchTerm.clear();
                    break;
                default:
                    System.out.println("Insert a valid option.");
                    break;
            }
        }
    }
}
