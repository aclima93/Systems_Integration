package inc.bugs;


import com.google.gson.Gson;
import generated.Smartphone;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class WebCrawler {

    private static final boolean DEBUG = true;

    private static boolean backupFiles;
    private static String startingURL;
    private static HashMap<String, Integer> visitedURLs;
    private static String [] searchRegexes;
    private static String rootDir = "cache";
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
     * @param args &lt;url&gt; [&lt;search_regexes.json&gt;]
     */
    public static void main(String[] args) {

        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.pixmania.pt/smartphone/lg-g4-32-gb-4g-titanio-smartphone/22623277-a.html").get();
            addSmartphone(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);


        if (args.length > 0) {

            startingURL = args[0];

            if(args.length == 2) {
                loadSearchRegexes(args[1]);
            }

            if(args.length == 3) {
                backupFiles = Boolean.parseBoolean(args[2]);
            }

            // TODO: Check if there are any umplublished messages to the JMS Topic and, if yes, send them

            // Init our hashmap of visited websites
            visitedURLs = new HashMap<String, Integer>();

            // Fetch the website
            crawl(startingURL);

            // TODO: create file(s) and send XML message(s) to the JMS Topic. Retry a couple fo times if they fail.

            // Statistics for Geeks
            printStatistics();

            System.exit(0);
        }
        else {
            System.err.println("Invalid number of arguments.\nSyntax: WebCrawler <url> [<search_keywords.json>]");
            System.exit(1);
        }
    }

    /**
     * Simple function that checks if the url is worth our while
     * @param url evaluated url
     * @return boolean value stating if the url is relevant
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

        if(DEBUG){
            System.out.println("url: "+ url);
        }

        try {

            Document doc = Jsoup.connect(url).get();

            if(backupFiles) {
                // save HTML page
                saveHTMLPage(doc, url);
            }

            // create Smartphone object and save it
            addSmartphone(doc);

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
     * Adds the object to the arraylist of collected smartphones
     * @param doc HTML page of smartphone
     */
    private static void addSmartphone(Document doc) {

        //<table class="simpleTable">
        Elements tables = doc.select("table[class=simpletable]");
        for(Element table : tables){

            System.out.println("\n\nTable\n" + table.toString());
        }

    }

    private static void printStatistics(){
        if(DEBUG){
            System.out.println(
                "\n*----------------------*" +
                "\n| Statistics for Geeks |" +
                "\n*----------------------* "
            );
            System.out.println("Num. URLs Visited: " + visitedURLs.size());
            System.out.println("Num. URLs Visited Only Once: " + Collections.frequency(visitedURLs.values(), 1));
        }
    }

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

        try{

            // remove the startingURL part from the name
            //String name = url.replace(startingURL, "");
            //String[] parts = name.split("(.*)/(.*\\.html)");

            String[] parts = url.split("/");
            String path = rootDir;

            for(int i = 0; i < parts.length-1; i++){
                path += File.separator + parts[i];
            }

            File dirs = new File(path);

            if (dirs.mkdirs()){

                File file = new File(path + File.separator + parts[parts.length-1]);

                FileOutputStream fileOutputStream = new FileOutputStream(file);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                Writer writer = new BufferedWriter(outputStreamWriter);

                writer.write(doc.toString());

                writer.close();
            }

        } catch (IOException e) {
            // invalid url, file or something else
        }
    }

}
