package inc.bugs;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        /*
         * Fetch the Wikipedia homepage, parse it to a DOM, and select the headlines from the In the news section
         * into a list of Elements.
         */

        Document doc = null;
        try {
            doc = Jsoup.connect("http://en.wikipedia.org/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements newsHeadlines = doc.select("#mp-itn b a");

        System.out.println(newsHeadlines.toString());

    }
}
