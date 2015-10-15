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
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by pedro on 14-10-2015.
 */
public class PriceKeeper {
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
    }

    public PriceKeeper() throws JMSException, NamingException, JAXBException, InterruptedException {
        this.smartphones = new ConcurrentSkipListSet<Smartphone>();
        this.topicListener = new TopicListener(this);
        this.queueListener = new QueueListener(this);
        this.topicListener.start();
        this.queueListener.start();
        this.topicListener.join();
        this.queueListener.join();

    }

    public void updatePrices(ArrayList<String> arrayList) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Smartphone.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        for(String string : arrayList) {
            Smartphone smartphone = (Smartphone)unmarshaller.unmarshal(new StringReader(string));
            this.smartphones.add(smartphone);
        }
    }

    public ArrayList<Smartphone> search(HashMap<SEARCH_MODES, String> searchTerms) {
        ArrayList<Smartphone> result = new ArrayList<Smartphone>();
        if(searchTerms.containsKey(SEARCH_MODES.MARCA)) {
            for(Smartphone current : smartphones) {
                if(current.getBrand().compareToIgnoreCase(searchTerms.get(SEARCH_MODES.MARCA)) == 0) {
                    result.add(current);
                }
            }
        } else if(searchTerms.containsKey(SEARCH_MODES.MARCA_MODELO)) {
            String[] marcaModelo = searchTerms.get(SEARCH_MODES.MARCA_MODELO).trim().split("-|\\s+");
            for(Smartphone current : smartphones) {
                if(current.getBrand().toUpperCase().compareTo(marcaModelo[0].toUpperCase()) == 0 && current.getName().toUpperCase().contains(marcaModelo[1].toUpperCase())) {
                    result.add(current);
                }
            }
        } else if(searchTerms.containsKey(SEARCH_MODES.NOME)) {
            for(Smartphone current : smartphones) {
                if(current.getName().compareToIgnoreCase(searchTerms.get(SEARCH_MODES.NOME)) == 0) {
                    result.add(current);
                }
            }
        } else if(searchTerms.containsKey(SEARCH_MODES.INTERVALO_PRECOS)) {
            String[] preco = searchTerms.get(SEARCH_MODES.INTERVALO_PRECOS).trim().split("-|\\s+");
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
        private TopicConnection topicConnection = null;
        private TopicSession topicSession = null;
        private Topic topic = null;

        public TopicListener(PriceKeeper priceKeeper) throws JMSException, NamingException, JAXBException {
            super();
            this.priceKeeper = priceKeeper;
        }

        @Override
        public void run(){
            try {
                this.initialize();
            } catch (JMSException|NamingException e) {
                System.err.println("Error creating connection to topic.");
                System.exit(1);
            }
            System.out.println("Begin listening to topic");
            while (true) {
                try {
                    getPrices();
                } catch (JMSException|NamingException|JAXBException e) {
                    System.err.println("Error getting prices from topic.");
                }
            }
        }

        public void initialize() throws JMSException, NamingException {
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    try {
                        topicConnection.stop();
                        topicSession.close();
                        topicConnection.close();
                    } catch (JMSException e) {
                        System.err.println("Error closing connections.");
                    }
                }
            });
            System.setProperty("java.naming.factory.initial","org.jboss.naming.remote.client.InitialContextFactory");
            System.setProperty("java.naming.provider.url","http-remoting://localhost:8080");
            this.topicConnectionFactory = InitialContext.doLookup("jms/RemoteConnectionFactory");
            this.topicConnection = this.topicConnectionFactory.createTopicConnection("pjaneiro", "|Sisc00l");
            this.topic = InitialContext.doLookup("jms/topic/pixmania");
            this.topicSession = this.topicConnection.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
            this.topicConnection.start();
        }

        public void getPrices() throws JMSException, NamingException, JAXBException{
            System.out.println("Checking for prices");
            this.topicConnection.start();
            TopicSubscriber topicSubscriber = this.topicSession.createDurableSubscriber(this.topic, "PriceKeeper");
            Message objectMessage = topicSubscriber.receive();
            topicSubscriber.close();
            if(objectMessage == null) {
                System.out.println("Error checking prices");
            } else {
                ArrayList<String> result = new ArrayList<String>();
                result = objectMessage.getBody(result.getClass());
                System.out.println("Evaluating data validity");
                XMLValidator xmlValidator = new XMLValidator();
                for(String xml : result) {
                    if(!xmlValidator.isValidXML(xml, xsdURL)) {
                        System.err.println("Invalid XML. Ignorig received values.");
                        return;
                    }
                }
                System.out.println("Prices received");
                this.priceKeeper.updatePrices(result);
            }
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
            try {
                this.initialize();
            } catch (JMSException|NamingException e) {
                System.err.println("Error creating connection to queue.");
                System.exit(1);
            }
            System.out.println("Begin listening to queue");
            while (true) {
                HashMap<SEARCH_MODES,String> hashMap = new HashMap<SEARCH_MODES,String>();
                Destination replyTo = null;
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
                try (JMSContext jmsContext = connectionFactory.createContext("pjaneiro","|Sisc00l")){
                    ObjectMessage objectMessage = jmsContext.createObjectMessage(searchResult);
                    JMSProducer jmsProducer = jmsContext.createProducer();
                    jmsProducer.send(replyTo, objectMessage);
                } catch (JMSRuntimeException e) {
                    System.err.println("Error sending answer.");
                }
            }
        }

        public void initialize() throws JMSException, NamingException {
            System.setProperty("java.naming.factory.initial","org.jboss.naming.remote.client.InitialContextFactory");
            System.setProperty("java.naming.provider.url","http-remoting://localhost:8080");
            this.connectionFactory = InitialContext.doLookup("jms/RemoteConnectionFactory");
            this.destination = InitialContext.doLookup("jms/queue/queue");
        }
    }
}
