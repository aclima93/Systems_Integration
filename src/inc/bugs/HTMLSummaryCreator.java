package inc.bugs;

import sun.security.krb5.internal.crypto.Des;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class HTMLSummaryCreator {
    private TopicConnectionFactory topicConnectionFactory = null;
    private TopicConnection topicConnection = null;
    private TopicSession topicSession = null;
    private Topic topic = null;

    /**
     * HTML Summary Creator
     *
     * Get xml from JMS Topic
     * Generate html based on xml and xslt
     *
     * @throws JMSException
     * @throws NamingException
     */
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

    public HTMLSummaryCreator() throws JMSException, NamingException {
        this.initialize();
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

    public void stop() throws JMSException {
        this.topicConnection.stop();
        this.topicSession.close();
        this.topicConnection.close();
    }

    public String receive() throws JMSException, NamingException {
        System.out.println("Begin receive");
        this.topicConnection.start();
        TopicSubscriber topicSubscriber = this.topicSession.createDurableSubscriber(this.topic, "HTMLGenerator");
        Message msg = topicSubscriber.receive();
        topicSubscriber.close();
        if(msg == null) {
            System.out.println("Timed out");
            return null;
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
        System.out.println(xmlFile);
        //TODO Generate HTML
        System.out.println("Gotta do this one");
    }
}
