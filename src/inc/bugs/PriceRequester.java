package inc.bugs;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class PriceRequester {

    private static int MAX_TRIES = 5;
    private static boolean VERBOSE = false;;
    private ConnectionFactory connectionFactory = null;
    private Destination destination = null;
    private TemporaryQueue temporaryQueue = null;

    /**
     * Price Requester
     * The Price Requester is a standalone application that communicates with the Price Keeper through a JMS Queue,
     * querying for smartphones with a given name, brand or price in a price range.
     *
     * @param args input arguments: [&lt;boolean:verbose&gt;]
     * @throws JMSException
     * @throws NamingException
     * @throws IOException
     */
    public static void main(String[] args) throws JMSException, NamingException, IOException {

        if(args.length > 0){
            VERBOSE = Boolean.parseBoolean(args[0]);
        }

        PriceRequester priceRequester = new PriceRequester();

        for (int i = 0; i < MAX_TRIES; i++) {

            if(priceRequester.initialize()) {
                priceRequester.mainLoop();
                return;
            }
        }
    }

    /**
     * Sets the necessary Java Naming and Directory Interface (JNDI) properties.
     * Initializes the communication with the JMS Queue.
     *
     * @return success state
     */
    private boolean initialize() {

        try {
            // set necessary Java Naming and Directory Interface (JNDI) properties
            System.setProperty("java.naming.factory.initial", "org.jboss.naming.remote.client.InitialContextFactory");
            System.setProperty("java.naming.provider.url", "http-remoting://localhost:8080");

            // initialize connection
            this.connectionFactory = InitialContext.doLookup("jms/RemoteConnectionFactory");
            this.destination = InitialContext.doLookup("jms/queue/queue");

        } catch (NamingException e) {
            if(VERBOSE) {
                System.out.println("Error setting JMS connection.");
            }
            return false;
        }

        return true;
    }

    /**
     * Create temporary JMS Queue and establish it as the destination of the messages.
     * Create and send ObjectMessage containing the search terms of the query through the temporary queue.
     * Retrieve response from temporary queue.
     *
     * @param searchTerms HashMap of Search_Mode and inputted Key-Value pairs.
     * @return Formatted String with Smartphones that satisfy the search criteria.
     */
    private String requestInfo(HashMap<SEARCH_MODES, String> searchTerms) {

        String result;

        try(JMSContext jmsContext = connectionFactory.createContext("pjaneiro","|Sisc00l")){

            if(VERBOSE) {
                System.out.println("Sending request.");
            }

            // create temporary JMS Queue and establish it as the destination of the messages
            temporaryQueue = jmsContext.createTemporaryQueue();
            JMSProducer jmsProducer = jmsContext.createProducer();

            // create and send ObjectMessage containing the search terms of the query through the temporary queue
            ObjectMessage objectMessage = jmsContext.createObjectMessage(searchTerms);
            objectMessage.setJMSReplyTo(temporaryQueue);
            jmsProducer.send(destination, objectMessage);

            if(VERBOSE) {
                System.out.println("Waiting for response.");
            }

            // retrieve response from temporary queue
            JMSConsumer jmsConsumer = jmsContext.createConsumer(temporaryQueue);
            Message message = jmsConsumer.receive();
            result = message.getBody(String.class);

            // close the connection
            jmsConsumer.close();
            temporaryQueue.delete();

        } catch (JMSException e) {

            if(VERBOSE) {
                System.err.println("Error sending/receiving request.");
                e.printStackTrace();
            }
            result = "";

        } catch (JMSRuntimeException e) {

            if(VERBOSE) {
                System.out.println("Connection to server lost. Attempting to reconnect.");
            }

            // try to reconnect some times
            for (int i = 0; i < MAX_TRIES; i++) {
                if(this.initialize()) {
                    return this.requestInfo(searchTerms);
                }
            }

            if(VERBOSE) {
                System.out.println("Couldn't reconnect to server. Shutting down.");
            }

            result = "";
            System.exit(0);
        }

        return result;
    }

    /**
     * List the available search options and listen to user input.
     * Print the search results.
     */
    private void mainLoop() {

        HashMap<SEARCH_MODES, String> searchTerm = new HashMap<>();
        String result;

        while (true) {

            // print search options
            System.out.println("Insert desired search term:");
            System.out.println("1 - Search by brand");
            System.out.println("2 - Search by brand and name");
            System.out.println("3 - Search by name");
            System.out.println("4 - Search by price range");
            System.out.println("0 - Exit");

            // scan user input
            Scanner scanner = new Scanner(System.in);
            int option;
            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Insert a valid option.");
                continue;
            }

            // continue querying the user accordingly
            switch (option) {

                // exit
                case 0:
                    System.out.println("Exiting.");
                    System.exit(0);
                    break;

                // Brand
                case 1:
                    System.out.println("Insert brand:");

                    String brand = scanner.nextLine();
                    searchTerm.put(SEARCH_MODES.BRAND, brand);
                    result = this.requestInfo(searchTerm);

                    if(result != null && result.length()!=0) {
                        System.out.println("The following smartphones have been found:");
                        System.out.print(result);
                    }
                    else {
                        System.out.println("No smartphones have been found.");
                    }

                    searchTerm.clear();
                    break;

                // Brand and Name
                case 2:
                    System.out.println("Insert brand and name as \"<brand> <name>\" (for example, \"Nokia verde\"):");

                    String brand_name = scanner.nextLine();
                    searchTerm.put(SEARCH_MODES.BRAND_NAME, brand_name);
                    result = this.requestInfo(searchTerm);

                    if(result != null && result.length()!=0) {
                        System.out.println("The following smartphones have been found:");
                        System.out.print(result);
                    }
                    else {
                        System.out.println("No smartphones have been found.");
                    }

                    searchTerm.clear();
                    break;

                // Name
                case 3:
                    System.out.println("Insert name:");

                    String name = scanner.nextLine();
                    searchTerm.put(SEARCH_MODES.NAME, name);
                    result = this.requestInfo(searchTerm);

                    if(result != null && result.length()!=0) {
                        System.out.println("The following smartphones have been found:");
                        System.out.print(result);
                    }
                    else {
                        System.out.println("No smartphones have been found.");
                    }

                    searchTerm.clear();
                    break;

                // Price Range
                case 4:
                    System.out.println("Insert minimum and maximum price as \"<min_price>[-]<max_price>\":");

                    String prices = scanner.nextLine();
                    searchTerm.put(SEARCH_MODES.PRICE_RANGE, prices);
                    result = this.requestInfo(searchTerm);

                    if(result != null && result.length()!=0) {
                        System.out.println("The following smartphones have been found:");
                        System.out.print(result);
                    }
                    else {
                        System.out.println("No smartphones have been found.");
                    }

                    searchTerm.clear();
                    break;

                // Any other thing
                default:
                    System.out.println("Insert a valid option.");
                    break;
            }
        }
    }
}
