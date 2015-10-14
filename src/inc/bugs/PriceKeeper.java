package inc.bugs;

import javax.jms.*;
import javax.naming.NamingException;

/**
 * Created by pedro on 14-10-2015.
 */
public class PriceKeeper {
    private TopicConnectionFactory topicConnectionFactory = null;
    private TopicConnection topicConnection = null;
    private TopicSession topicSession = null;
    private Topic topic = null;

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

    
}
