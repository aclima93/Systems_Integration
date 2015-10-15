package inc.bugs;

import generated.Smartphone;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by pedro on 15-10-2015.
 */
public class PriceRequester {
    private ConnectionFactory connectionFactory = null;
    private Session session = null;
    private Connection connection = null;
    private Destination destination = null;
    private TemporaryQueue temporaryQueue = null;

    public static void main(String[] args) throws JMSException, NamingException, IOException {
        PriceRequester priceRequester = new PriceRequester();
        priceRequester.mainLoop();
    }

    public PriceRequester() throws JMSException, NamingException {
        this.initialize();
    }

    private void initialize() throws JMSException, NamingException {
        System.setProperty("java.naming.factory.initial","org.jboss.naming.remote.client.InitialContextFactory");
        System.setProperty("java.naming.provider.url","http-remoting://localhost:8080");
        this.connectionFactory = InitialContext.doLookup("jms/RemoteConnectionFactory");
        this.destination = InitialContext.doLookup("jms/queue/queue");
    }

    private ArrayList<Smartphone> requestInfo(HashMap<SEARCH_MODES, String> searchTerms) {
        ArrayList<Smartphone> result = new ArrayList<Smartphone>();
        System.out.println("Sending request.");
        try {
            connection = connectionFactory.createConnection("pjaneiro", "|Sisc00l");
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            temporaryQueue = session.createTemporaryQueue();
            ObjectMessage objectMessage = session.createObjectMessage(searchTerms);
            objectMessage.setJMSReplyTo(temporaryQueue);
            session.createProducer(destination).send(objectMessage);
        } catch (JMSException e) {
            System.err.println("Error sending request.");
            return null;
        }
        System.out.println("Waiting for response.");
        try {
            MessageConsumer messageConsumer = session.createConsumer(temporaryQueue);
            Message message = messageConsumer.receive();
            result = message.getBody(result.getClass());
            messageConsumer.close();
            temporaryQueue.delete();
            connection.stop();
            session.close();
            connection.close();
        } catch (JMSException e) {
            System.err.println("Error receiving answer.");
            return null;
        }
        return  result;
    }

    private void mainLoop() throws IOException {
        HashMap<SEARCH_MODES, String> searchTerm = new HashMap<SEARCH_MODES, String>();
        ArrayList<Smartphone> result = null;
        while (true) {
            System.out.println("Insert desired search term:");
            System.out.println("1 - Search by brand");
            System.out.println("2 - Search by brand and model");
            System.out.println("3 - Search by name");
            System.out.println("4 - Search by price range");
            System.out.println("0 - Exit");
            Scanner scanner = new Scanner(System.in);
            int option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 0:
                    System.out.println("Exiting.");
                    System.exit(0);
                    break;
                case 1:
                    System.out.println("Insert brand:");
                    String brand = scanner.nextLine();
                    searchTerm.put(SEARCH_MODES.MARCA, brand);
                    result = this.requestInfo(searchTerm);
                    if(result != null) {
                        System.out.println("The following smartphones have been found:");
                        for(Smartphone smartphone : result) {
                            System.out.println("Name: "+smartphone.getName());
                            System.out.println("Brand: "+smartphone.getBrand());
                            System.out.println("Price: "+smartphone.getPrice());
                            System.out.println();
                        }
                    }
                    searchTerm.clear();
                    break;
                case 2:
                    System.out.println("Insert brand and model as \"<brand> <model>\":");
                    String brand_model = scanner.nextLine();
                    searchTerm.put(SEARCH_MODES.MARCA_MODELO, brand_model);
                    result = this.requestInfo(searchTerm);
                    if(result != null) {
                        System.out.println("The following smartphones have been found:");
                        for(Smartphone smartphone : result) {
                            System.out.println("Name: "+smartphone.getName());
                            System.out.println("Brand: "+smartphone.getBrand());
                            System.out.println("Price: "+smartphone.getPrice());
                            System.out.println();
                        }
                    }
                    searchTerm.clear();
                    break;
                case 3:
                    System.out.println("Insert name:");
                    String name = scanner.nextLine();
                    searchTerm.put(SEARCH_MODES.NOME, name);
                    result = this.requestInfo(searchTerm);
                    if(result != null) {
                        System.out.println("The following smartphones have been found:");
                        for(Smartphone smartphone : result) {
                            System.out.println("Name: "+smartphone.getName());
                            System.out.println("Brand: "+smartphone.getBrand());
                            System.out.println("Price: "+smartphone.getPrice());
                            System.out.println();
                        }
                    }
                    searchTerm.clear();
                    break;
                case 4:
                    System.out.println("Insert minimum and maximum price as \"<min_price>[-]<max_price>\":");
                    String prices = scanner.nextLine();
                    searchTerm.put(SEARCH_MODES.INTERVALO_PRECOS, prices);
                    result = this.requestInfo(searchTerm);
                    if(result != null) {
                        System.out.println("The following smartphones have been found:");
                        for(Smartphone smartphone : result) {
                            System.out.println("Name: "+smartphone.getName());
                            System.out.println("Brand: "+smartphone.getBrand());
                            System.out.println("Price: "+smartphone.getPrice());
                            System.out.println();
                        }
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
