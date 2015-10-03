package inc.bugs;


import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;

public class WebCrawler {

    private static final boolean DEBUG = true;

    private static String startingURL;
    private static HashMap<String, Integer> visitedURLs;
    private static String [] searchRegexes;

    /**
     * Web Crawler
     *
     * Fetch the website
     * Parse it to a DOM
     * Create XML from DOM
     * Send XML message to JMS Topic
     *
     * @param args <url> [<search_keywords.json>]
     */
    public static void main(String[] args) {

        if (args.length > 0) {

            if(args.length == 2) {
                loadSearchRegexes(args[1]);
            }

            // TODO: Check if there are any umplublished messages to the JMS Topic and, if yes, send them

            // Init our hashmap of visited websites
            visitedURLs = new HashMap<String, Integer>();

            // Fetch the website
            startingURL = args[0];
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
            //"http://www.pixmania.pt/smartphone/"lg-g4-32-gb-4g-titanio-smartphone/22623277-a.html

            //".*\/.*\/""((\d|\D)*(_|-))"

            //if( url.matches("(http://www\\.pixmania\\.pt/)((telefones/telemovel/)?(smartphone|iphone)/([a-z]|[A-Z]|-|_|\\d)+/(\\d+)-a\\.html)?") ) {
            if( url.matches(regex) ) {

                /*
                // FIXME: REGEX TAKES CARE OF THIS NOW!
                // remove the useless meta info at end of url
                if(url.contains(".html#")) {
                    String[] parts = url.split("/.html#");
                    url = parts[0] + ".html";
                }
                */
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

        if(DEBUG)
            System.out.println("url: "+ url);

        try {
            Document doc = Jsoup.connect(url).get();

            File file = new File("cache" + File.separator + url);
            if (file.getParentFile().mkdirs()){
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                Writer writer = new BufferedWriter(outputStreamWriter);
                writer.write(doc.toString());
                writer.close();
            }

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

}
