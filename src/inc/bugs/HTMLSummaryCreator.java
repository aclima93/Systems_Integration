package inc.bugs;

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
    private static int MAX_TRIES = 5;
    private TopicConnectionFactory topicConnectionFactory = null;

    private static String xsdURL;
    private static String xslURL;
    private static String outputDirectory;

    /**
     * HTML Summary Creator
     *
     * Get XML from JMS Topic
     * Generate HTML based on XML, XSD and XSLT
     *
     * @throws JMSException
     * @throws NamingException
     */
    public static void main(String[] args) throws JMSException, NamingException {

        if(args.length == 3) {

            xsdURL = args[0];
            xslURL = args[1];
            outputDirectory = args[2];
        }
        else {

            System.err.println("Invalid number of arguments.\nSyntax: HTMLSummaryCreator &lt;xsd&gt; &lt;xsl&gt;");
        }

        xsdURL = args[0];
        xslURL = args[1];

        HTMLSummaryCreator htmlSummaryCreator = new HTMLSummaryCreator();
        htmlSummaryCreator.run();
    }

    public void run() {
        for (int i = 0; i < MAX_TRIES; i++) {
            if(this.initialize()) {
                while (true) {
                    this.generateHTML();
                }
            }
        }
    }

    public boolean initialize() {
        try {
            System.setProperty("java.naming.factory.initial", "org.jboss.naming.remote.client.InitialContextFactory");
            System.setProperty("java.naming.provider.url", "http-remoting://localhost:8080");
            this.topicConnectionFactory = InitialContext.doLookup("jms/RemoteConnectionFactory");
            return true;
        } catch (NamingException e) {
            System.err.println("Error setting JMS connection.");
            return false;
        }
    }

    public ArrayList receive() {

        System.out.println("Begin receive");
        ArrayList<String> result = null;

        try(JMSContext jmsContext = topicConnectionFactory.createContext("pjaneiro","|Sisc00l")) {
            Topic topic = InitialContext.doLookup("jms/topic/pixmania");
            final JMSConsumer jmsConsumer = jmsContext.createDurableConsumer(topic, "HTMLGenerator");
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    jmsConsumer.close();
                    this.interrupt();
                }
            });
            Message message = jmsConsumer.receive();
            if(message != null) {
                result = message.getBody(ArrayList.class);
            }
        } catch (JMSException|NamingException|JMSRuntimeException e) {
            System.err.println("Error receiving data from topic");
        }
        return result;
    }

    public void generateHTML() {

        String url;
        TransformerFactory transformerFactory = null;
        Transformer transformer = null;
        ArrayList<String> xmlFiles = this.receive();

        if(xmlFiles == null) {
            System.out.println("Can't generate HTML file");
            return;
        }

        XMLValidator xmlValidator = new XMLValidator();

        for (String xml : xmlFiles) {

            // Checks if the XML conforms to the XSD
            if(xmlValidator.isValidXML(xml, xsdURL)) {

                try {

                    url = outputDirectory + getMD5hash(xml);
                    transformerFactory = TransformerFactory.newInstance();
                    transformer = transformerFactory.newTransformer(new StreamSource(xslURL));
                    transformer.transform(new StreamSource(new StringReader(xml)), new StreamResult(new FileOutputStream(url + ".html")));

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
