package inc.bugs;

import sun.security.krb5.internal.crypto.Des;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class HTMLSummaryCreator {
    private TopicConnectionFactory topicConnectionFactory = null;
    private TopicConnection topicConnection = null;
    private TopicSession topicSession = null;
    private Topic topic = null;
    private static String xslURL;
    private static String outputDirectory;

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
        if(args.length == 2) {
            xslURL = args[0];
            outputDirectory = args[1];
        }
        else {
            System.err.println("Invalid number of arguments.\nSyntax: HTMLSummaryCreator &lt;xsl&gt;");
        }
        xslURL = args[0];
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

    public ArrayList<String> receive() throws JMSException, NamingException {
        System.out.println("Begin receive");
        this.topicConnection.start();
        TopicSubscriber topicSubscriber = this.topicSession.createDurableSubscriber(this.topic, "HTMLGenerator");
        Message objectMessage = topicSubscriber.receive();
        topicSubscriber.close();
        if(objectMessage == null) {
            System.out.println("Error checking prices");
            return null;
        }
        else {
            ArrayList<String> result = new ArrayList<String>();
            result = objectMessage.getBody(result.getClass());
            System.out.println("Received");
            return result;
        }
    }

    public void generateHTML() throws JMSException, NamingException {
        String url;
        TransformerFactory transformerFactory = null;
        Transformer transformer = null;
        ArrayList<String> xmlFile = this.receive();
        if(xmlFile == null) {
            System.out.println("Can't generate HTML file");
            return;
        }
        for (String xml : xmlFile) {
            try {
                url = outputDirectory+getMD5hash(xml);
                transformerFactory = TransformerFactory.newInstance();
                transformer = transformerFactory.newTransformer(new StreamSource(xslURL));
                transformer.transform(new StreamSource(new StringReader(xml)), new StreamResult(new FileOutputStream(url+".html")));
            } catch (TransformerConfigurationException e) {
                System.err.println("The given xsl file address is not valid.");
                System.exit(1);
            } catch (TransformerException e) {
                System.err.println("Error reading xml file.");
            } catch (FileNotFoundException e) {
                System.err.println("Error opening file for output.");
            }
        }
    }

    public String getMD5hash(String input) {
        byte[] bytes = null;
        try {
            bytes = input.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            bytes = input.getBytes();
        }
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e) {
            return input;
        }
        byte[] digest = messageDigest.digest(bytes);
        BigInteger bigInteger = new BigInteger(1,digest);
        String result = bigInteger.toString(16);
        return result;
    }
}
