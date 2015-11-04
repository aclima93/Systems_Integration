package inc.bugs;

import generated.Smartphone;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class PriceKeeper {

    private static int MAX_TRIES = 5;
    private ConcurrentSkipListSet<Smartphone> smartphones = null;
    private TopicListener topicListener = null;
    private QueueListener queueListener = null;
    private static String xsdURL;
    private static boolean VERBOSE = false;

    /**
     * Price Keeper
     *
     * Get xml from JMS Topic
     * Keep prices in memory
     * Listen to requests from JMS Queue
     * Send answers to Price Requester
     *
     * @param args input arguments: &lt;xsd&gt; [&lt;boolean:verbose&gt;]
     * @throws JMSException
     * @throws NamingException
     */
    public static void main(String[] args) throws JMSException, NamingException, JAXBException, InterruptedException {

        if (args.length > 0) {
            xsdURL = args[0];

            if (args.length == 2){
                VERBOSE = Boolean.parseBoolean(args[1]);
            }

        } else {
            System.err.println("Invalid number of arguments.\nSyntax: PriceKeeper &lt;xsd&gt; [&lt;boolean:verbose&gt;]");
        }

        // Start the main loop
        new InitialContext();
        PriceKeeper priceKeeper = new PriceKeeper();
        priceKeeper.mainLoop();
    }

    /**
     * Instantiate the List of Smartphones
     */
    public PriceKeeper() throws JMSException, NamingException, JAXBException, InterruptedException {
        this.smartphones = new ConcurrentSkipListSet<>();
        this.topicListener = new TopicListener(this);
        this.queueListener = new QueueListener(this);
    }

    /**
     * Start the Listners for the JSM Topic and JMS Queue and join them
     */
    private void mainLoop() throws JMSException, NamingException, JAXBException, InterruptedException {
        this.topicListener.start();
        this.queueListener.start();
        this.topicListener.join();
        this.queueListener.join();
    }

    /**
     * Adds the received SmartPhones
     *
     * @param arrayListOfSmartphones ArrayList of Strings that contain the Smartphone XMLs
     */
    public void updateSmartphones(ArrayList<String> arrayListOfSmartphones) {

        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(Smartphone.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            for (String smartphoneContent : arrayListOfSmartphones) {
                Smartphone smartphone = (Smartphone) unmarshaller.unmarshal(new StringReader(smartphoneContent));

                // because we use a ConcurrentSkipList adding will also check for already existing equal smartphones
                this.smartphones.add(smartphone);
            }

        } catch (JAXBException e) {
            if(VERBOSE) {
                System.out.println("Error updating prices database.");
            }
        }
    }

    /**
     * Iterates over the gathered Smartphones and returns meaningful information
     * for those that satisfy the search criteria.
     *
     * @param searchTerms HashMap of Search_Mode and inputted Key-Value pairs.
     * @return Formatted String with Smartphones that satisfy the search criteria.
     */
    public String search(HashMap<SEARCH_MODES, String> searchTerms) {

        String result = "";

        // Brand
        if(searchTerms.containsKey(SEARCH_MODES.BRAND)) {
            for(Smartphone current : smartphones) {
                if(current.getBrand().compareToIgnoreCase(searchTerms.get(SEARCH_MODES.BRAND)) == 0) {
                    result += current;
                }
            }
        }
        // Brand and Name
        else if(searchTerms.containsKey(SEARCH_MODES.BRAND_NAME)) {
            String[] brandName = searchTerms.get(SEARCH_MODES.BRAND_NAME).trim().split("-|\\s+");
            for(Smartphone current : smartphones) {
                if(current.getBrand().toUpperCase().compareTo(brandName[0].toUpperCase()) == 0 && current.getName().toUpperCase().contains(brandName[1].toUpperCase())) {
                    result += current;
                }
            }
        }
        // Name
        else if(searchTerms.containsKey(SEARCH_MODES.NAME)) {
            String nome = searchTerms.get(SEARCH_MODES.NAME);
            for(Smartphone current : smartphones) {
                if(current.getName().toUpperCase().contains(nome.toUpperCase())) {
                    result += current;
                }
            }
        }
        // Price Range
        else if(searchTerms.containsKey(SEARCH_MODES.PRICE_RANGE)) {
            String[] preco = searchTerms.get(SEARCH_MODES.PRICE_RANGE).trim().split("-|\\s+");
            for(Smartphone current : smartphones) {
                if(current.getPrice().doubleValue()>=Double.parseDouble(preco[0]) && current.getPrice().doubleValue()<=Double.parseDouble(preco[1])) {
                    result += current;
                }
            }
        }

        return result;
    }


    class TopicListener extends Thread {
        private PriceKeeper priceKeeper = null;
        private TopicConnectionFactory topicConnectionFactory = null;

        public TopicListener(PriceKeeper priceKeeper) throws JMSException, NamingException, JAXBException {
            super();
            this.priceKeeper = priceKeeper;
        }

        @Override
        public void run(){

            for (int i = 0; i < MAX_TRIES ; i++) {
                if(this.initialize()) {

                    if(VERBOSE) {
                        System.out.println("Begin listening to topic");
                    }
                    while (true) {
                        getPrices();
                    }
                }
            }
        }

        /**
         * Sets the necessary Java Naming and Directory Interface (JNDI) properties.
         * Initializes the communication with the JMS Queue.
         *
         * @return success state
         */
        public boolean initialize() {

            try {

                // set necessary Java Naming and Directory Interface (JNDI) properties
                System.setProperty("java.naming.factory.initial", "org.jboss.naming.remote.client.InitialContextFactory");
                System.setProperty("java.naming.provider.url", "http-remoting://localhost:8080");

                // initialize connection
                this.topicConnectionFactory = InitialContext.doLookup("jms/RemoteConnectionFactory");

            } catch (NamingException e) {
                if(VERBOSE) {
                    System.err.println("Error setting JMS connection.");
                }
                return false;
            }

            return true;
        }

        /**
         * Receive the XML messages from the JMS Topic
         */
        public void getPrices() {

            System.out.println("Checking for prices");
            ArrayList<String> result = null;

            try(JMSContext jmsContext = topicConnectionFactory.createContext("pjaneiro","|Sisc00l")) {

                Topic topic = InitialContext.doLookup("jms/topic/pixmania");
                final JMSConsumer jmsConsumer = jmsContext.createDurableConsumer(topic, "PriceKeeper");
                Runtime.getRuntime().addShutdownHook(new Thread() {
                    public void run() {
                        jmsConsumer.close();
                        this.interrupt();
                    }
                });

                // receive the XML message from the JMS Topic
                Message message = jmsConsumer.receive();
                if(message != null) {
                    result = message.getBody(ArrayList.class);
                } else {
                    if(VERBOSE) {
                        System.out.println("Error receiving data from topic.");
                    }
                    return;
                }
            } catch (JMSException|NamingException|NullPointerException e) {
                if(VERBOSE) {
                    System.out.println("Error receiving data from topic.");
                }
                return;
            } catch (JMSRuntimeException e) {
                if(VERBOSE) {
                    System.out.println("Error connecting to topic. Trying to re-connect");
                }

                // try to reconnect some times
                for (int i = 0; i < MAX_TRIES; i++) {
                    if(this.initialize()) {
                        getPrices();
                        return;
                    }
                }
                if(VERBOSE) {
                    System.out.println("Couldn't reconnect to server. Shutting down.");
                }

                System.exit(0);
                return;
            }

            if(VERBOSE) {
                System.out.println("Evaluating data validity");
            }

            // Check the validity of the received XML file against the XSD
            XMLValidator xmlValidator = new XMLValidator();
            for(String xml : result) {
                if(!xmlValidator.isValidXML(xml, xsdURL)) {
                    if(VERBOSE) {
                        System.out.println("Invalid XML. Ignorig received values.");
                    }
                    return;
                }
            }

            if(VERBOSE) {
                System.out.println("Prices received");
            }

            // update the smartphone's information
            this.priceKeeper.updateSmartphones(result);
        }
    }

    class QueueListener extends Thread {
        private PriceKeeper priceKeeper = null;
        private ConnectionFactory connectionFactory = null;
        private Destination destination;

        public QueueListener(PriceKeeper priceKeeper) throws JMSException, NamingException, JAXBException {
            super();
            this.priceKeeper = priceKeeper;
        }

        @Override
        public void run() {

            for (int i = 0; i < MAX_TRIES; i++) {
                if(this.initialize()) {

                    if(VERBOSE) {
                        System.out.println("Begin listening to queue");
                    }

                    while (true) {

                        HashMap<SEARCH_MODES,String> hashMap = new HashMap<>();
                        Destination replyTo = null;

                        // try to create the consumer and receive the message a couple of times
                        try(JMSContext jmsContext = connectionFactory.createContext("pjaneiro","|Sisc00l")){

                            JMSConsumer consumer = jmsContext.createConsumer(destination);
                            Message message = consumer.receive();
                            replyTo = message.getJMSReplyTo();
                            hashMap = message.getBody(hashMap.getClass());

                        }
                        catch (JMSException|NullPointerException e) {
                            if(VERBOSE) {
                                System.out.println("Error receiving request.");
                            }
                            continue;
                        } catch (JMSRuntimeException e) {
                            if(VERBOSE) {
                                System.out.println("Error connecting to topic. Trying to re-connect");
                            }

                            // retry a couple of times
                            int j;
                            for (j = 0; j < MAX_TRIES; j++) {
                                if(this.initialize()) {
                                    try(JMSContext jmsContext = connectionFactory.createContext("pjaneiro","|Sisc00l")){

                                        JMSConsumer consumer = jmsContext.createConsumer(destination);
                                        Message message = consumer.receive();
                                        replyTo = message.getJMSReplyTo();
                                        hashMap = message.getBody(hashMap.getClass());

                                        break;
                                    }
                                    catch (JMSException e1) {
                                        if(VERBOSE) {
                                            System.out.println("Error receiving request.");
                                        }
                                    }
                                }
                            }
                            if(j == MAX_TRIES) {
                                if(VERBOSE) {
                                    System.out.println("Couldn't reconnect to server. Shutting down.");
                                }
                                System.exit(0);
                            }
                        }

                        // perform search and output the result
                        String searchResult = this.priceKeeper.search(hashMap);
                        System.out.println(searchResult);

                        // try to connect to the JMS Topic and send the results
                        try (JMSContext jmsContext = connectionFactory.createContext("pjaneiro","|Sisc00l")){

                            TextMessage textMessage = jmsContext.createTextMessage(searchResult);
                            JMSProducer jmsProducer = jmsContext.createProducer();
                            jmsProducer.send(replyTo, textMessage);

                        } catch (JMSRuntimeException e) {
                            if(VERBOSE) {
                                System.out.println("Error connecting to topic. Trying to re-connect");
                            }

                            // retry a couple fo times
                            int j;
                            for ( j = 0; j < MAX_TRIES; j++) {
                                if(this.initialize()) {
                                    try (JMSContext jmsContext = connectionFactory.createContext("pjaneiro","|Sisc00l")) {

                                        TextMessage textMessage = jmsContext.createTextMessage(searchResult);
                                        JMSProducer jmsProducer = jmsContext.createProducer();
                                        jmsProducer.send(replyTo, textMessage);

                                        break;
                                    } catch (JMSRuntimeException e1) {
                                        if(VERBOSE) {
                                            System.out.println("Error sending reply.");
                                        }
                                    }
                                }
                            }
                            if(j == MAX_TRIES) {
                                if(VERBOSE) {
                                    System.out.println("Couldn't reconnect to server. Shutting down.");
                                }
                                System.exit(0);
                            }
                        }
                    }
                }
            }
        }

        /**
         * Sets the necessary Java Naming and Directory Interface (JNDI) properties.
         * Initializes the communication with the JMS Queue.
         *
         * @return success state
         */
        public boolean initialize() {

            try {
                // set necessary Java Naming and Directory Interface (JNDI) properties
                System.setProperty("java.naming.factory.initial", "org.jboss.naming.remote.client.InitialContextFactory");
                System.setProperty("java.naming.provider.url", "http-remoting://localhost:8080");

                // initialize connection
                this.connectionFactory = InitialContext.doLookup("jms/RemoteConnectionFactory");
                this.destination = InitialContext.doLookup("jms/queue/queue");

            } catch (NamingException e) {
                System.err.println("Error setting JMS connection.");
                return false;
            }

            return true;
        }
    }
}
