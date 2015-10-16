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

    /**
     * Price Keeper
     *
     * Get xml from JMS Topic
     * Keep prices in memory
     * Listen to requests from JMS Queue
     * Send answers to Price Requester
     *
     * @throws JMSException
     * @throws NamingException
     */
    public static void main(String[] args) throws JMSException, NamingException, JAXBException, InterruptedException {
        if (args.length == 1) {
            xsdURL = args[0];
        } else {
            System.err.println("Invalid number of arguments.\nSyntax: PriceKeeper &lt;xsd&gt;");
        }
        PriceKeeper priceKeeper = new PriceKeeper();
        priceKeeper.mainLoop();
    }

    public PriceKeeper() throws JMSException, NamingException, JAXBException, InterruptedException {
        this.smartphones = new ConcurrentSkipListSet<>();
        this.topicListener = new TopicListener(this);
        this.queueListener = new QueueListener(this);
    }

    private void mainLoop() throws JMSException, NamingException, JAXBException, InterruptedException {
        this.topicListener.start();
        this.queueListener.start();
        this.topicListener.join();
        this.queueListener.join();
    }

    public void updatePrices(ArrayList<String> arrayList) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Smartphone.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            for (String string : arrayList) {
                Smartphone smartphone = (Smartphone) unmarshaller.unmarshal(new StringReader(string));
                this.smartphones.add(smartphone);
            }
        } catch (JAXBException e) {
            System.err.println("Error updating prices database.");
        }
    }

    public ArrayList<Smartphone> search(HashMap<SEARCH_MODES, String> searchTerms) {
        ArrayList<Smartphone> result = new ArrayList<>();
        if(searchTerms.containsKey(SEARCH_MODES.BRAND)) {
            for(Smartphone current : smartphones) {
                if(current.getBrand().compareToIgnoreCase(searchTerms.get(SEARCH_MODES.BRAND)) == 0) {
                    result.add(current);
                }
            }
        } else if(searchTerms.containsKey(SEARCH_MODES.BRAND_NAME)) {
            String[] brandName = searchTerms.get(SEARCH_MODES.BRAND_NAME).trim().split("-|\\s+");
            for(Smartphone current : smartphones) {
                if(current.getBrand().toUpperCase().compareTo(brandName[0].toUpperCase()) == 0 && current.getName().toUpperCase().contains(brandName[1].toUpperCase())) {
                    result.add(current);
                }
            }
        } else if(searchTerms.containsKey(SEARCH_MODES.NAME)) {
            String nome = searchTerms.get(SEARCH_MODES.NAME);
            for(Smartphone current : smartphones) {
                if(current.getName().toUpperCase().contains(nome.toUpperCase())) {
                    result.add(current);
                }
            }
        } else if(searchTerms.containsKey(SEARCH_MODES.PRICE_RANGE)) {
            String[] preco = searchTerms.get(SEARCH_MODES.PRICE_RANGE).trim().split("-|\\s+");
            for(Smartphone current : smartphones) {
                if(current.getPrice().doubleValue()>=Double.parseDouble(preco[0]) && current.getPrice().doubleValue()<=Double.parseDouble(preco[1])) {
                    result.add(current);
                }
            }
        }
        return  result;
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
                    System.out.println("Begin listening to topic");
                    while (true) {
                        getPrices();
                    }
                }
            }
        }

        public boolean initialize() {
            try {
                /*System.setProperty("java.naming.factory.initial", "org.jboss.naming.remote.client.InitialContextFactory");
                System.setProperty("java.naming.provider.url", "http-remoting://localhost:8080");*/
                this.topicConnectionFactory = InitialContext.doLookup("jms/RemoteConnectionFactory");
                return true;
            } catch (NamingException e) {
                System.err.println("Error setting JMS connection.");
                return false;
            }
        }

        public void getPrices() {

            System.out.println("Checking for prices");
            ArrayList<String> result = null;

            try(JMSContext jmsContext = topicConnectionFactory.createContext("pjaneiro","|Sisc00l")) {
                Topic topic = InitialContext.doLookup("jms/topic/pixmania");
                JMSConsumer jmsConsumer = jmsContext.createDurableConsumer(topic, "PriceKeeper");
                Runtime.getRuntime().addShutdownHook(new Thread() {
                    public void run() {
                        jmsConsumer.close();
                        this.interrupt();
                    }
                });
                Message message = jmsConsumer.receive();
                if(message != null) {
                    result = message.getBody(ArrayList.class);
                } else {
                    System.err.println("Error receiving data from topic.");
                    return;
                }
            } catch (JMSException|NamingException|JMSRuntimeException e) {
                System.err.println("Error receiving data from topic.");
                return;
            } catch (NullPointerException e) {
                System.err.println("Error receiving data from topic.");
            }
            System.out.println("Evaluating data validity");
            XMLValidator xmlValidator = new XMLValidator();
            for(String xml : result) {
                if(!xmlValidator.isValidXML(xml, xsdURL)) {
                    System.err.println("Invalid XML. Ignorig received values.");
                    System.out.println(xsdURL);
                    System.out.println(xml);
                    return;
                }
            }
            System.out.println("Prices received");
            this.priceKeeper.updatePrices(result);
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
                    System.out.println("Begin listening to queue");
                    while (true) {
                        HashMap<SEARCH_MODES,String> hashMap = new HashMap<>();
                        Destination replyTo;
                        try(JMSContext jmsContext = connectionFactory.createContext("pjaneiro","|Sisc00l")){
                            JMSConsumer consumer = jmsContext.createConsumer(destination);
                            Message message = consumer.receive();
                            replyTo = message.getJMSReplyTo();
                            hashMap = message.getBody(hashMap.getClass());
                        }
                        catch (JMSException e) {
                            System.err.println("Error receiving request.");
                            continue;
                        }
                        ArrayList<Smartphone> searchResult = this.priceKeeper.search(hashMap);
                        System.out.println(searchResult);
                        try (JMSContext jmsContext = connectionFactory.createContext("pjaneiro","|Sisc00l")){
                            ObjectMessage objectMessage = jmsContext.createObjectMessage(searchResult);
                            JMSProducer jmsProducer = jmsContext.createProducer();
                            jmsProducer.send(replyTo, objectMessage);
                        } catch (JMSRuntimeException e) {
                            System.err.println("Error sending answer.");
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        public boolean initialize() {
            try {
                /*System.setProperty("java.naming.factory.initial", "org.jboss.naming.remote.client.InitialContextFactory");
                System.setProperty("java.naming.provider.url", "http-remoting://localhost:8080");*/
                this.connectionFactory = InitialContext.doLookup("jms/RemoteConnectionFactory");
                this.destination = InitialContext.doLookup("jms/queue/queue");
                return true;
            } catch (NamingException e) {
                e.printStackTrace();
                System.err.println("Error setting JMS connection.");
                return false;
            }
        }
    }
}
