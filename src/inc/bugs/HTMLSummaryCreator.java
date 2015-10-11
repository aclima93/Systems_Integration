package inc.bugs;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Created by pedro on 10-10-2015.
 */
public class HTMLSummaryCreator {
    TopicConnection topicConnection;
    TopicConnectionFactory topicConnectionFactory;
    TopicSession topicSession;
    Topic topic;

    public HTMLSummaryCreator() throws JMSException, NamingException {
        this.topicConnectionFactory = InitialContext.doLookup("ConnectionFactory");
        this.topicConnection = this.topicConnectionFactory.createTopicConnection();
        this.topicSession = this.topicConnection.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
        this.topic = InitialContext.doLookup("topic/pixmania");
    }

    public String receive() throws JMSException, NamingException {
        System.out.println("Begin receive");
        this.topicConnection.start();
        TopicSubscriber topicSubscriber = this.topicSession.createDurableSubscriber(this.topic, "HTML Summary Creator");
        Message msg = topicSubscriber.receive(5000);
        topicSubscriber.close();
        if(msg == null) {
            System.out.println("Timed out");
            throw new JMSRuntimeException("No input");
        }
        else {
            System.out.println("Received");
            return msg.getBody(String.class);
        }
    }

    public void generateHTML() throws JMSException, NamingException {
        String xmlFile = this.receive();
        if(xmlFile == null) {
            System.out.println("Can't generate HTML file");
            return;
        }
        //TODO Generate HTML
        System.out.println("Gotta do this one");
    }

    public void stop() throws JMSException {
        this.topicConnection.stop();
        this.topicSession.close();
        this.topicConnection.close();
    }

    public static void main(String[] args) throws JMSException, NamingException {
        HTMLSummaryCreator htmlSummaryCreator = new HTMLSummaryCreator();
        try {
            while (true) {
                htmlSummaryCreator.generateHTML();
            }
        } catch (JMSRuntimeException e) {
            System.out.println("No files for 5 seconds");
            htmlSummaryCreator.stop();
        }
    }
}
