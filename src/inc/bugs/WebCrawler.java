package inc.bugs;


import com.google.gson.Gson;
import generated.Smartphone;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.math.BigDecimal;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;

public class WebCrawler {

    private static final boolean DEBUG_URLS = true;
    private static final boolean DEBUG_SMARTPHONE = false;
    private static final boolean VERBOSE = true;

    private static final int MAX_ATTEMPTS = 6;
    private static int attemptCounter;

    private static boolean backupHMTLFiles;
    private static String startingURL;
    private static HashMap<String, Integer> visitedURLs = new HashMap<String, Integer>();
    private static String [] searchRegexes;
    private static String rootDir = "cache";
    private static String htmlDir = "html";
    private static String unpublishedFile = "unpublished.tmp";
    private static ArrayList<Smartphone> collectedSmartphones = new ArrayList<Smartphone>();
    private static ArrayList<String> createdXMLFiles = new ArrayList<String>();

    private static TopicConnectionFactory topicConnectionFactory = null;
    private static TopicConnection topicConnection = null;
    private static TopicSession topicSession = null;
    private static Topic topic = null;

    /**
     * Web Crawler
     *
     * Fetch the website
     * Parse it to a DOM
     * Create XML from DOM
     * Send XML message to JMS Topic
     *
     * Example input:
     http://www.pixmania.pt/ /Users/aclima/Documents/Repositories/Systems_Integration/src/inc/bugs/search_regexes.json
     *
     * @param args &lt;url&gt; [&lt;file:search_regexes.json&gt;] [&lt;boolean:backupHMTLFiles&gt;]
     */
    public static void main(String[] args) throws JMSException, NamingException {

        if (args.length > 0) {

            startingURL = args[0];

            if(args.length >= 2) {
                loadSearchRegexes(args[1]);
            }

            if(args.length == 3) {
                backupHMTLFiles = Boolean.parseBoolean(args[2]);
            }

            // Check if there are any unpublished messages to the JMS Topic and, if yes, send them
            loadUnpublishedMessages();

            // send XML message(s) to the JMS Topic
            publishXMLFilesToJMSTopic();

            // Fetch the website
            crawl(startingURL);

            // create XML file(s)
            createAndSaveXMLFiles();

            // send XML message(s) to the JMS Topic
            publishXMLFilesToJMSTopic();

            // Statistics for Geeks
            printStatistics();

            System.out.println("Finished crawling successfully.");
            System.exit(0);
        }
        else {
            System.err.println("Invalid number of arguments.\nSyntax: WebCrawler &lt;url&gt; [&lt;file:search_regexes.json&gt;] [&lt;boolean:backupHMTLFiles&gt;]");
            System.exit(1);
        }
    }

    /**
     * Delete Published XML Messages
     */
    private static void deletePublishedMessages() {

        // delete all files after they have been sent
        createdXMLFiles.clear();

        if(VERBOSE){
            System.out.println("Deleted Published Messages.");
        }
    }

    /**
     * Save Unpublished XML Messages
     */
    private static void saveUnpublishedMessages() {

        try {

            FileOutputStream fileOutputStream = new FileOutputStream(unpublishedFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(createdXMLFiles);
            objectOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if(VERBOSE){
            System.out.println("Saved Unpublished Messages.");
        }
    }

    /**
     * Load Unpublished XML Messages
     */
    private static void loadUnpublishedMessages() {

        try {

            File dirs = new File(rootDir);

            if (dirs.mkdirs()) {

                File file = new File(rootDir + File.separator + unpublishedFile);

                FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                createdXMLFiles = (ArrayList<String>) objectInputStream.readObject();
                objectInputStream.close();
                if(file.delete()) {
                    System.out.println("Deleted backup file.");
                }
            }

        } catch (IOException|ClassNotFoundException e) {
            System.out.println("There are no unpublished messages.");
        }

        if(VERBOSE){
            System.out.println("Loaded Unpublished Messages.");
        }

    }

    /**
     * Tries to publish the list of XML Files to the JMS Topic
     */
    private static void publishXMLFilesToJMSTopic() throws JMSException, NamingException {

        if(VERBOSE) {
            System.out.println("Publishing to JMS Topic");
        }

        // initialize the JMS Topic
        initializeJMSTopic();

        // Retry a couple fo times if it fails.
        for(attemptCounter = 1; attemptCounter < MAX_ATTEMPTS; attemptCounter++) {

            // send XML message(s) to the JMS Topic
            try {

                TopicPublisher topicPublisher = topicSession.createPublisher(topic);
                ObjectMessage message = topicSession.createObjectMessage(createdXMLFiles);
                topicPublisher.publish(message);
                break;
            } catch (JMSException e){
                // something went wrong while trying to publish
                System.out.println("Error trying to publish messages.");
            }
        }

        if(attemptCounter == MAX_ATTEMPTS){
            // if it was unsuccessful we save the files for the next time the crawler runs
            saveUnpublishedMessages();
        }
        else{
            // if it was successful we can delete the files we had saved
            deletePublishedMessages();
        }

        // stop the JMS Topic
        stopJMSTopic();

        if(VERBOSE) {
            System.out.println("All messages sent to JMS Topic");
        }

    }

    /**
     * Creates XML files and saves them just in case
     */
    private static void createAndSaveXMLFiles() {

        JAXBContext jaxbContext;
        Marshaller marshaller;

        try {

            jaxbContext = JAXBContext.newInstance(Smartphone.class);
            marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            for(Smartphone smartphone : collectedSmartphones) {

                StringWriter stringWriter = new StringWriter();
                marshaller.marshal(smartphone, stringWriter);
                createdXMLFiles.add(stringWriter.toString());
                stringWriter.flush();
            }

        } catch (JAXBException e) {
            // could not create Marshalled XML
            e.printStackTrace();
        }

        if(VERBOSE){
            System.out.println("Created and saved XML files from crawling.");
        }

    }

    /**
     * Simple function that checks if the url is worth our while
     * @param url evaluated url
     * @return isRelevant boolean value stating if the url is relevant
     */
    private static boolean isRelevantURL(String url) {

        // everything goes
        if( searchRegexes.length == 0)
            return true;

        // the first regex is the main/starting website
        if( url.equals(searchRegexes[0])) {
            return false;
        }

        for(String regex: searchRegexes){
            if( url.matches(regex) ) {
                return true;
            }
        }

        return false;
    }

    /**
     * Simple recursive function that crawls through the current url
     * and checks out all relevant sub urls.
     * @param url current url
     */
    private static void crawl(String url){

        // avoid revisiting urls
        if( visitedURLs.containsKey(url) && visitedURLs.get(url) != 0 ) {
            visitedURLs.put(url, visitedURLs.get(url) + 1);
            return;
        }

        visitedURLs.put(url, 1);

        if(DEBUG_URLS){
            System.out.println("url: "+ url);
        }

        try {
            Document doc = null;
            try {
                doc = Jsoup.connect(url).get();
            } catch (SocketTimeoutException e) {
                System.out.println("Couldn't load page: "+url);
                return;
            }

            if(backupHMTLFiles) {
                // save HTML page
                saveHTMLPage(doc, url);
            }

            // create Smartphone object and save it
            if(url.compareTo(startingURL) != 0) {
                collectedSmartphones.add(createSmartphone(url, doc));
            }

            // craw to other links on this page
            Elements links = doc.select("a[href]");
            for (Element link : links) {

                String subUrl = link.attr("abs:href");

                if( isRelevantURL(subUrl) ){
                    crawl(subUrl);
                }

            }

        } catch (IOException e) {
            // invalid url, file or something else
            e.printStackTrace();
        }

    }

    /**
     * Creates a Smartphone object from the data in doc
     * @param doc HTML page of smartphone
     */
    private static Smartphone createSmartphone(String url, Document doc) {

        Smartphone smartphone = new Smartphone();

        // URL
        smartphone.setUrl(url);

        Smartphone.TechnicalData technicalData = new Smartphone.TechnicalData();

        // Name and Brand
        Elements pageTitle = doc.select("h1[class=pageTitle]");

        smartphone.setName(pageTitle.select("span[itemprop=name]").text());
        smartphone.setBrand(pageTitle.select("span[itemprop=brand]").text());

        // Price and Currency
        try {

            String[] priceAndCurrency = doc.select("div[class=currentPrice]").select("ins[itemprop=price]").text().replace("\u00a0", " ").split("\\s+"); // pesky &no-break space
            NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN); // German locale has the same decimal and grouping separators as PT
            smartphone.setPrice( new BigDecimal(nf.parse(priceAndCurrency[0]).toString()));
            smartphone.setCurrency(priceAndCurrency[1]);

        } catch (ParseException e) {
            // invalid number format/unparsable number
            e.printStackTrace();
        }

        // Summary Data
        smartphone.setSummaryData(doc.select("ul[class=customList],ul[itemprop=description]").text());

        if(DEBUG_SMARTPHONE) {
            System.out.println("\n\nSmartphone");
            System.out.println("Url: " + smartphone.getUrl());
            System.out.println("Name: " + smartphone.getName());
            System.out.println("Brand: " + smartphone.getBrand());
            System.out.println("Price: " + smartphone.getPrice());
            System.out.println("Currency: " + smartphone.getCurrency());
            System.out.println("Summary: \n" + smartphone.getSummaryData());
        }

        // Technical Data
        Elements tablesFromDoc = doc.select("table[class=simpletable]");
        for(Element tableFromDoc : tablesFromDoc){

            Smartphone.TechnicalData.Table table = new Smartphone.TechnicalData.Table();
            table.setTableTitle(tableFromDoc.select("caption").text());

            if(DEBUG_SMARTPHONE) {
                System.out.println("\nTable");
                System.out.println("TableTitle: " + table.getTableTitle());
            }

            for(Element rowfromTable : tableFromDoc.select("tbody").select("tr")) {

                Smartphone.TechnicalData.Table.TableData tableData = new Smartphone.TechnicalData.Table.TableData();

                tableData.setDataName(rowfromTable.select("th").text());
                tableData.setDataValue(rowfromTable.select("td").text());

                if(DEBUG_SMARTPHONE) {
                    System.out.println("TableData name: " + tableData.getDataName());
                    System.out.println("TableData value: " + tableData.getDataValue());
                }

                table.getTableData().add( tableData);
            }

            technicalData.getTable().add(table);
        }
        if(!technicalData.getTable().isEmpty()) {
            smartphone.setTechnicalData(technicalData);
        }

        return smartphone;
    }

    /**
     * Additional report at the end of crawling containing statistics and information about encountered URLs
     */
    private static void printStatistics(){

        if(VERBOSE){
            System.out.println(
                "\n*----------------------*" +
                "\n| Statistics for Geeks |" +
                "\n*----------------------*"
            );
            System.out.println("Num. URLs Visited: " + visitedURLs.size());
            System.out.println("Num. URLs Visited Only Once: " + Collections.frequency(visitedURLs.values(), 1));
            System.out.println("Num. Smartphones collected: " + collectedSmartphones.size());
            System.out.println("Num. Attempts to publish to JMS Topic: " + attemptCounter);
        }
    }

    /**
     * Loads the regexes into memory by reading and treating the file as a json file
     * @param filename json file containing array of regex strings
     */
    private  static void loadSearchRegexes(String filename){

        String fileContent = "";
        searchRegexes = new String[]{};

        try {

            // read JSON file
            for (String line : Files.readAllLines(Paths.get(filename))) {
                fileContent += line;
            }

            // convert JSON Array of Strings to Java Array fo Strings
            Gson gson = new Gson();
            searchRegexes = gson.fromJson(fileContent, String[].class);

        } catch (IOException e) {
            // invalid file
            e.printStackTrace();
        }

        if(VERBOSE){
            System.out.println("Loaded Search Regexes.");
        }

    }

    /**
     * Saves the HTML pages just in case
     * @param doc html page
     * @param url URL of html page
     */
    private static void saveHTMLPage(Document doc, String url){


        String path = rootDir + File.separator + htmlDir;

        String[] parts = url.split("/");
        for(int i = 0; i < parts.length-1; i++){
            path += File.separator + parts[i];
        }

        // save the HTML page to a file
        saveFile(path, path + File.separator + parts[parts.length-1], doc.toString());
        
    }

    /**
     * Saves a fileContent to filePath whilst making sure that dirPath and all its necessary parent folders exist.
     * @param dirPath target directory path
     * @param filePath target file path
     * @param fileContent file content
     */
    private static void saveFile(String dirPath, String filePath, String fileContent) {

        try{
            File dirs = new File(dirPath);

            if (dirs.mkdirs()){

                File file = new File(filePath);

                FileOutputStream fileOutputStream = new FileOutputStream(file);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                Writer writer = new BufferedWriter(outputStreamWriter);

                writer.write(fileContent);

                writer.close();
            }

        } catch (IOException e) {
            // invalid url, file or something else
            e.printStackTrace();
        }
        
    }

    public static void initializeJMSTopic() throws JMSException, NamingException {

        System.setProperty("java.naming.factory.initial","org.jboss.naming.remote.client.InitialContextFactory");
        System.setProperty("java.naming.provider.url","http-remoting://localhost:8080");

        topicConnectionFactory = InitialContext.doLookup("jms/RemoteConnectionFactory");
        topicConnection = topicConnectionFactory.createTopicConnection("pjaneiro", "|Sisc00l");
        topic = InitialContext.doLookup("jms/topic/pixmania");
        topicSession = topicConnection.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
        topicConnection.start();

        if(VERBOSE){
            System.out.println("Initialized JMS Topic.");
        }

    }

    public static void stopJMSTopic() throws JMSException {

        topicConnection.stop();
        topicSession.close();
        topicConnection.close();

        if(VERBOSE){
            System.out.println("Stopped JMS Topic.");
        }
    }

}
