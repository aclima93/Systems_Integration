package inc.bugs;

import javax.jms.*;
import javax.jms.IllegalStateException;
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
     * The HTML Summary Creator is a class that is constantly fetching XML files from a JMS Topic.
     *
     * <p>When it reads an XML file, the HTML Summary Creator validates it against an XSD file (path given as the first program argument).
     *
     * <p>Then, it generates an HTML file per XML file, using an XSL file (path given as the second program argument).
     *
     * <p>To create an HTML file per XML file, the MD5 hash of the XML file is computed, and used as the name for the file.
     *
     * <p>This way, if an XML file is sent multiple times, the MD5 hash will be the same, and the HTML file will be overwritten, avoiding copies.
     *
     * <p>The HTML files are stored in a path given as the third program argument.
     *
     * Example input:
     /home/pedro/Documents/Programming/Systems_Integration/src/inc/bugs/XML/smartphone.xsd /home/pedro/Documents/Programming/Systems_Integration/src/inc/bugs/XML/smartphone.xsl /home/pedro/Documents/Programming/Systems_Integration/html/
     *
     * @param args &lt;XSD file path&gt; &lt;XSL file path&gt; &lt;Desired output directory&gt;
     *
     */
    public static void main(String[] args) {

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

    /**
     * After attempting to connect to the JBOSS Server (MAX_TRIES attempts), keeps the process reading from the topic and generating HTML files,
     * through a while(true) loop
     */
    public void run() {
        for (int i = 0; i < MAX_TRIES; i++) {
            if(this.initialize()) {
                while (true) {
                    this.generateHTML();
                }
            }
        }
    }

    /**
     * Tries to connect to the JBOSS Server, by creating a TopicConnectionFactory
     *
     * @return true if successful, false otherwise
     */
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

    /**
     * Connects to the JMS Topic, waits for any message to be available, reads it, and returns it.
     *
     * @return ArrayList&lt;String&gt; containing all the received XML files
     */
    public ArrayList receive() {

        System.out.println("Begin receive");
        ArrayList<String> result = null;

        try(JMSContext jmsContext = topicConnectionFactory.createContext("pjaneiro","|Sisc00l")) {
            Topic topic = InitialContext.doLookup("jms/topic/pixmania");
            JMSConsumer jmsConsumer = jmsContext.createDurableConsumer(topic, "HTMLGenerator");
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
        } catch (JMSException|NamingException e) {
            System.err.println("Error receiving data from topic");
        } catch (JMSRuntimeException e) {
            for (int i = 0; i < MAX_TRIES; i++) {
                if(this.initialize()) {
                    return this.receive();
                }
            }
            System.out.println("Couldn't reconnect to server. Shutting down.");
            System.exit(0);
        }
        return result;
    }

    /**
     * Main function, calls the receive() function, and for each XML file:
     *
     * <p> - Generates the MD5 hash, using it as file name
     *
     * <p> - Generates the HTML file, using a Transformer and the XSL file given as the second program argument
     *
     * @see this.receive()
     * @see this.getMD5hash()
     */
    public void generateHTML() {

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

                    String url = outputDirectory + getMD5hash(xml);
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer(new StreamSource(xslURL));
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

    /**
     * Given a string, returns the corresponding hexadecimal hash as a string
     *
     * @param input String to hash
     *
     * @return Hexadecimal hash of the given string
     */
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
