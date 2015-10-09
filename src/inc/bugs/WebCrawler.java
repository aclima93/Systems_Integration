package inc.bugs;


import com.google.gson.Gson;
import generated.Smartphone;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;

public class WebCrawler {

    private static final boolean DEBUG_URLS = false;
    private static final boolean DEBUG_SMARTPHONE = false;
    private static final boolean VERBOSE = true;
    private static final boolean SIMPLE_TEST = false;


    private static final int MAX_ATTEMPTS = 5;
    private static int attemptCounter;

    private static boolean backupFiles;
    private static String startingURL;
    private static HashMap<String, Integer> visitedURLs = new HashMap<String, Integer>();
    private static String [] searchRegexes;
    private static String rootDir = "cache";
    private static String htmlDir = "html";
    private static String unpublishedDir = "unpublished";
    private static ArrayList<Smartphone> collectedSmartphones = new ArrayList<Smartphone>();

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
     * @param args &lt;url&gt; [&lt;file:search_regexes.json&gt;] [&lt;boolean:backupFiles&gt;]
     */
    public static void main(String[] args) {

        if(SIMPLE_TEST){
            Document doc = null;
            try {
                doc = Jsoup.connect("http://www.pixmania.pt/smartphone/lg-g4-32-gb-4g-titanio-smartphone/22623277-a.html").get();
                createSmartphone(doc);
            } catch (IOException e) {
                e.printStackTrace();
            }

            createAndSaveXMLFiles();

            System.exit(0);
        }

        if (args.length > 0) {

            startingURL = args[0];

            if(args.length >= 2) {
                loadSearchRegexes(args[1]);
            }

            if(args.length == 3) {
                backupFiles = Boolean.parseBoolean(args[2]);
            }

            // TODO: Check if there are any unplublished messages to the JMS Topic and, if yes, send them


            // Fetch the website
            crawl(startingURL);

            // create XML file(s)
            createAndSaveXMLFiles();

            // Retry a couple fo times if it fails.
            for(attemptCounter = 0; attemptCounter < MAX_ATTEMPTS; attemptCounter++) {

                // send XML message(s) to the JMS Topic.
                if (publishXMLFilesToJMSTopic()){

                    // if it was successful we can delete the files we had saved
                    deletePublishedMessages();
                    break;
                }
            }

            // Statistics for Geeks
            printStatistics();

            System.exit(0);
        }
        else {
            System.err.println("Invalid number of arguments.\nSyntax: WebCrawler &lt;url&gt; [&lt;file:search_regexes.json&gt;] [&lt;boolean:backupFiles&gt;]");
            System.exit(1);
        }
    }

    /**
     * Delete Published XML Messages
     */
    private static void deletePublishedMessages() {

        // TODO: Delete Published XML Messages

    }

    /**
     * Tries to publish the list of XML Files to the JMS Topic
     * @return isSuccessful whether or not the XML File transfers were successful
     */
    private static boolean publishXMLFilesToJMSTopic() {

        // TODO: send XML message(s) to the JMS Topic.

        return false;
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
                marshaller.marshal(smartphone, System.out); // TODO: trocar este system.out e guardar o output
            }
        } catch (JAXBException e) {
            // could not create Marshalled XML
        }
    }

    /**
     * Simple function that checks if the url is worth our while
     * @param url evaluated url
     * @return isRelevant boolean value stating if the url is relevant
     */
    private static boolean isRelevantURL(String url){

        // everything goes
        if( searchRegexes.length == 0)
            return true;

        for(String regex: searchRegexes){
            // super regex
            //if( url.matches("(http://www\\.pixmania\\.pt/)((telefones/telemovel/)?(smartphone|iphone)/([a-z]|[A-Z]|-|_|\\d)+/(\\d+)-a\\.html)?") ) {
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

            Document doc = Jsoup.connect(url).get();

            if(backupFiles) {
                // save HTML page
                saveHTMLPage(doc, url);
            }

            // create Smartphone object and save it
            collectedSmartphones.add(createSmartphone(doc));

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
        }

    }

    /**
     * Creates a Smartphone object from the data in doc
     * @param doc HTML page of smartphone
     */
    private static Smartphone createSmartphone(Document doc) {

        Smartphone smartphone = new Smartphone();

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
        }

        // Summary Data
        smartphone.setSummaryData(doc.select("ul[class=customList],ul[itemprop=description]").html());

        if(DEBUG_SMARTPHONE) {
            System.out.println("\n\nSmartphone");
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

        smartphone.setTechnicalData(technicalData);

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
                "\n*----------------------* "
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
        }
        
    }

}
