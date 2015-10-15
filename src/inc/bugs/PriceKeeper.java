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
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by pedro on 14-10-2015.
 */
public class PriceKeeper {
    private ConcurrentHashMap<Integer, Smartphone> smartphones = null;
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
    public static void main(String[] args) throws JMSException, NamingException{
        PriceKeeper priceKeeper = new PriceKeeper();
    }

    public void updatePrices(ArrayList<String> arrayList) throws JAXBException{
        JAXBContext jaxbContext = JAXBContext.newInstance(Smartphone.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        for(String string : arrayList) {
            Smartphone smartphone = (Smartphone)unmarshaller.unmarshal(new StringReader(string));
            this.smartphones.put(smartphone.hashCode(), smartphone);
        }
    }

    public PriceKeeper() {
        this.smartphones = new ConcurrentHashMap<Integer, Smartphone>();
    }



    class TopicListener extends Thread {
        private PriceKeeper priceKeeper = null;
        private TopicConnectionFactory topicConnectionFactory = null;
        private TopicConnection topicConnection = null;
        private TopicSession topicSession = null;
        private Topic topic = null;

        public TopicListener(PriceKeeper priceKeeper) throws JMSException, NamingException {
            super();
            this.priceKeeper = priceKeeper;
            this.initialize();
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
        }

        @Override
        public void run() {

        }

        @Override
        public void start() {
            System.out.println("Begin listening to topic");
        }

        public void initialize() throws JMSException, NamingException {
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
                System.out.println("Prices received");
                this.priceKeeper.updatePrices(result);
            }
        }
    }
}
