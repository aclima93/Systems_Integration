package inc.bugs;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by aclima on 16/10/15.
 */
public class SearchForm {

    private static int window_width;
    private static int window_width_2;
    private static int window_width_4;
    private static int window_height;

    private static int margin = 10;
    private static int item_height = 25;
    private static int starting_height = margin;

    private static HashMap<String, JTextField> textFields;

    public static void main(String[] args) {

        textFields = new HashMap<String, JTextField>();

        JFrame frame = new JFrame("Price Requester");

        DisplayMode displayMode = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDisplayMode();

        window_width = displayMode.getWidth() - 50;
        window_height = displayMode.getHeight() - 50;

        window_width_2 = window_width / 2;
        window_width_4 = window_width / 4;

        frame.setSize(window_width, window_height);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {

        panel.setLayout(null);

        addLabelTextFieldPair(panel, "Nome");
        addLabelTextFieldPair(panel, "Marca");
        addLabelTextFieldPair(panel, "Preço Mínimo");
        addLabelTextFieldPair(panel, "Preço Máximo");

        starting_height += item_height;

        JButton loginButton = new JButton("Submit");
        loginButton.setBounds(margin, starting_height, window_width - margin, item_height);
        loginButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {

            }
        });
        panel.add(loginButton);

        starting_height += item_height;

        URL url = null;
        try {
            url = new URL("http://www.pixmania.pt/smartphone/lg-g4-32-gb-4g-titanio-smartphone/22623277-a.html");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        File file = new File("/Users/aclima/Documents/Repositories/Systems_Integration/src/inc/bugs/XML/1d285842992196bc6e7356b5fec7aa68.html");

        final JEditorPane editorPane = new JEditorPane();
        setupJEditorPane(editorPane, url);

        JScrollPane scrollPane = new JScrollPane(editorPane);
        scrollPane.setBounds(margin, starting_height, window_width - margin, window_height - (starting_height + margin*2));
        panel.add(scrollPane);

    }

    private static JEditorPane setupJEditorPane(final JEditorPane editorPane, URL url) {

        editorPane.setEditable(false);

        HTMLEditorKit kit = new HTMLEditorKit();
        editorPane.setEditorKit(kit);

        try {
            kit.getStyleSheet().importStyleSheet( new URL( "file:///Users/aclima/Documents/Repositories/Systems_Integration/src/inc/bugs/XML/css.css" ) );
        } catch( MalformedURLException ex ) {
            ex.printStackTrace();
        }

        Document doc = kit.createDefaultDocument();
        editorPane.setDocument(doc);
        //editorPane.setText(stupid);



        try {
            editorPane.setPage(new File("/Users/aclima/Documents/Repositories/Systems_Integration/src/inc/bugs/XML/1d285842992196bc6e7356b5fec7aa68.html").toURI().toURL());
        }
        catch (IOException e) {
            editorPane.setContentType("text/html");
            editorPane.setText("<html>Could not load webpage</html>");
        }

        // handle HyperLinks
        editorPane.addHyperlinkListener(new HyperlinkListener() {
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {

                    //setupJEditorPane(editorPane, e.getURL());

                    //open in browser
                    if(Desktop.isDesktopSupported()) {
                        try {
                            Desktop.getDesktop().browse(e.getURL().toURI());
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } catch (URISyntaxException e1) {
                            e1.printStackTrace();
                        }
                    }

                    /*
                    try {
                        editorPane.setPage(e.getURL());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    */
                }
            }
        });

        return editorPane;

    }

    private static void addLabelTextFieldPair(JPanel panel, String labelText){

        JLabel label = new JLabel(labelText);
        label.setBounds(margin, starting_height, window_width_2 - margin, item_height);
        panel.add(label);

        JTextField textField = new JPasswordField(20);
        textField.setBounds(window_width_4, starting_height, window_width_2 - margin, item_height);
        panel.add(textField);

        textFields.put(labelText, textField);

        starting_height += item_height;
    }

    static String stupid = "<html>\n" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "<link rel=\"stylesheet\" href=\"http://www.w3schools.com/lib/w3.css\">\n" +
            "<body>\n" +
            "<div>\n" +
            "<header class=\"w3-container w3-black\">\n" +
            "<h2>Integra&ccedil;&atilde;o de Sistemas - Projecto 1 - 2015/2016</h2>\n" +
            "</header>\n" +
            "</div>\n" +
            "<title>Smartphone: X - vermelho - Smartphone Dual SIM</title>\n" +
            "<div>\n" +
            "<header class=\"w3-container w3-blue\">\n" +
            "<h2>Informa&ccedil;&atilde;o Principal</h2>\n" +
            "</header>\n" +
            "<div class=\"w3-container w3-row-padding w3-margin\">\n" +
            "<div class=\"w3-third\" align=\"center\">\n" +
            "<div class=\"w3-card-4\">\n" +
            "<header class=\"w3-container w3-light-blue\">\n" +
            "<h3>Nome</h3>\n" +
            "</header>\n" +
            "<div class=\"w3-container\">\n" +
            "<p>X - vermelho - Smartphone Dual SIM</p>\n" +
            "</div>\n" +
            "</div>\n" +
            "</div>\n" +
            "<div class=\"w3-third\" align=\"center\">\n" +
            "<div class=\"w3-card-4\">\n" +
            "<header class=\"w3-container w3-light-blue\">\n" +
            "<h3>Marca</h3>\n" +
            "</header>\n" +
            "<div class=\"w3-container\">\n" +
            "<p>NOKIA</p>\n" +
            "</div>\n" +
            "</div>\n" +
            "</div>\n" +
            "<div class=\"w3-third\" align=\"center\">\n" +
            "<div class=\"w3-card-4\">\n" +
            "<header class=\"w3-container w3-light-blue\">\n" +
            "<h3>Pre&ccedil;o</h3>\n" +
            "</header>\n" +
            "<div class=\"w3-container\">\n" +
            "<p>119&nbsp;&euro;</p>\n" +
            "</div>\n" +
            "</div>\n" +
            "</div>\n" +
            "</div>\n" +
            "<div class=\"w3-container w3-row-padding w3-margin\">\n" +
            "<div class=\"w3-card-4\">\n" +
            "<div align=\"center\">\n" +
            "<header class=\"w3-container w3-light-blue\">\n" +
            "<h3>Sum&aacute;rio</h3>\n" +
            "</header>\n" +
            "</div>\n" +
            "<div class=\"w3-container\">\n" +
            "<p>processador: Processador Qualcomm Dual Core Snapdragon 1 GHz 512 Mb RAM Tecnologia do ecr&atilde;: T&aacute;til capacitivo IPS LCD Tamanho do ecr&atilde;: 4\" (800 x 480 pixeis) Autonomia em conversa&ccedil;&atilde;o: 2G: At&eacute; 13,3 horas 3G: At&eacute; 10,3 horas</p>\n" +
            "<div align=\"center\">\n" +
            "<p>For more information please follow this <a href=\"http://www.pixmania.pt/smartphone/nokia-x-vermelho-smartphone-dual-sim/22104208-a.html\" target=\"_blank\">link</a>.</p>\n" +
            "</div>\n" +
            "</div>\n" +
            "</div>\n" +
            "</div>\n" +
            "</div>\n" +
            "<div class=\"w3-margin-top\">\n" +
            "<header class=\"w3-container w3-green\">\n" +
            "<h2>Ficha T&eacute;cnica</h2>\n" +
            "</header>\n" +
            "<div class=\"w3-row-padding w3-margin\">\n" +
            "<div class=\"w3-card-4\">\n" +
            "<header class=\"w3-container w3-light-green\">\n" +
            "<h4>Informa&ccedil;&otilde;es gerais</h4>\n" +
            "</header>\n" +
            "<table class=\"w3-table w3-bordered w3-striped\">\n" +
            "<col width=\"25%\">\n" +
            "<col width=\"75%\">\n" +
            "<tr>\n" +
            "<td><b>sistema operativo</b></td><td>Nokia X Software Platform</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td><b>processador</b></td><td>Processador Qualcomm Dual Core Snapdragon 1 GHz 512 Mb RAM</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</div>\n" +
            "</div>\n" +
            "<div class=\"w3-row-padding w3-margin\">\n" +
            "<div class=\"w3-card-4\">\n" +
            "<header class=\"w3-container w3-light-green\">\n" +
            "<h4>Ecr&atilde;</h4>\n" +
            "</header>\n" +
            "<table class=\"w3-table w3-bordered w3-striped\">\n" +
            "<col width=\"25%\">\n" +
            "<col width=\"75%\">\n" +
            "<tr>\n" +
            "<td><b>Tecnologia do ecr&atilde;</b></td><td>T&aacute;til capacitivo IPS LCD</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td><b>Tamanho do ecr&atilde;</b></td><td>4\" (800 x 480 pixeis)</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</div>\n" +
            "</div>\n" +
            "<div class=\"w3-row-padding w3-margin\">\n" +
            "<div class=\"w3-card-4\">\n" +
            "<header class=\"w3-container w3-light-green\">\n" +
            "<h4>Comunica&ccedil;&otilde;es</h4>\n" +
            "</header>\n" +
            "<table class=\"w3-table w3-bordered w3-striped\">\n" +
            "<col width=\"25%\">\n" +
            "<col width=\"75%\">\n" +
            "<tr>\n" +
            "<td><b>Frequ&ecirc;ncias</b></td><td>EGSM 850 / 900 / 1800 / 1900 WCDMA 900 / 2100</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td><b>Redes</b></td><td>3G</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</div>\n" +
            "</div>\n" +
            "<div class=\"w3-row-padding w3-margin\">\n" +
            "<div class=\"w3-card-4\">\n" +
            "<header class=\"w3-container w3-light-green\">\n" +
            "<h4>Personaliza&ccedil;&atilde;o/entretenimento</h4>\n" +
            "</header>\n" +
            "<table class=\"w3-table w3-bordered w3-striped\">\n" +
            "<col width=\"25%\">\n" +
            "<col width=\"75%\">\n" +
            "<tr>\n" +
            "<td><b>Plataforma de download</b></td><td>Nokia Store Compat&iacute;vel com as aplica&ccedil;&otilde;es Android</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td><b>Aplica&ccedil;&otilde;es integradas</b></td><td>Facebook Skype Vine</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</div>\n" +
            "</div>\n" +
            "<div class=\"w3-row-padding w3-margin\">\n" +
            "<div class=\"w3-card-4\">\n" +
            "<header class=\"w3-container w3-light-green\">\n" +
            "<h4>M&aacute;quina fotogr&aacute;fica</h4>\n" +
            "</header>\n" +
            "<table class=\"w3-table w3-bordered w3-striped\">\n" +
            "<col width=\"25%\">\n" +
            "<col width=\"75%\">\n" +
            "<tr>\n" +
            "<td><b>Sensor</b></td><td>3 Megapixeis Focus fixo</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</div>\n" +
            "</div>\n" +
            "<div class=\"w3-row-padding w3-margin\">\n" +
            "<div class=\"w3-card-4\">\n" +
            "<header class=\"w3-container w3-light-green\">\n" +
            "<h4>Sistema GPS</h4>\n" +
            "</header>\n" +
            "<table class=\"w3-table w3-bordered w3-striped\">\n" +
            "<col width=\"25%\">\n" +
            "<col width=\"75%\">\n" +
            "<tr>\n" +
            "<td><b>Navega&ccedil;&atilde;o GPS</b></td><td>Sim</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</div>\n" +
            "</div>\n" +
            "<div class=\"w3-row-padding w3-margin\">\n" +
            "<div class=\"w3-card-4\">\n" +
            "<header class=\"w3-container w3-light-green\">\n" +
            "<h4>Mem&oacute;ria</h4>\n" +
            "</header>\n" +
            "<table class=\"w3-table w3-bordered w3-striped\">\n" +
            "<col width=\"25%\">\n" +
            "<col width=\"75%\">\n" +
            "<tr>\n" +
            "<td><b>Mem&oacute;ria interna</b></td><td>4 GB eMMC</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td><b>Slot para cart&otilde;es de mem&oacute;ria externo</b></td><td>Sim</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td><b>Tipos de cart&atilde;o de mem&oacute;ria lidos</b></td><td>microSD at&eacute; 32 GB</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</div>\n" +
            "</div>\n" +
            "<div class=\"w3-row-padding w3-margin\">\n" +
            "<div class=\"w3-card-4\">\n" +
            "<header class=\"w3-container w3-light-green\">\n" +
            "<h4>Fun&ccedil;&otilde;es de voz</h4>\n" +
            "</header>\n" +
            "<table class=\"w3-table w3-bordered w3-striped\">\n" +
            "<col width=\"25%\">\n" +
            "<col width=\"75%\">\n" +
            "<tr>\n" +
            "<td><b>M&atilde;os-livres</b></td><td>Sim</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</div>\n" +
            "</div>\n" +
            "<div class=\"w3-row-padding w3-margin\">\n" +
            "<div class=\"w3-card-4\">\n" +
            "<header class=\"w3-container w3-light-green\">\n" +
            "<h4>Autonomia</h4>\n" +
            "</header>\n" +
            "<table class=\"w3-table w3-bordered w3-striped\">\n" +
            "<col width=\"25%\">\n" +
            "<col width=\"75%\">\n" +
            "<tr>\n" +
            "<td><b>Autonomia em conversa&ccedil;&atilde;o</b></td><td>2G: At&eacute; 13,3 horas 3G: At&eacute; 10,3 horas</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td><b>Autonomia em stand-by</b></td><td>2G: At&eacute; 28,5 dias 3G: At&eacute; 22 dias</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</div>\n" +
            "</div>\n" +
            "<div class=\"w3-row-padding w3-margin\">\n" +
            "<div class=\"w3-card-4\">\n" +
            "<header class=\"w3-container w3-light-green\">\n" +
            "<h4>Ergonomia</h4>\n" +
            "</header>\n" +
            "<table class=\"w3-table w3-bordered w3-striped\">\n" +
            "<col width=\"25%\">\n" +
            "<col width=\"75%\">\n" +
            "<tr>\n" +
            "<td><b>Dimens&otilde;es</b></td><td>115,5 x 63 x 10,4 mm</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td><b>Peso</b></td><td>128,6 g</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</div>\n" +
            "</div>\n" +
            "<div class=\"w3-row-padding w3-margin\">\n" +
            "<div class=\"w3-card-4\">\n" +
            "<header class=\"w3-container w3-light-green\">\n" +
            "<h4>Conte&uacute;do da embalagem</h4>\n" +
            "</header>\n" +
            "<table class=\"w3-table w3-bordered w3-striped\">\n" +
            "<col width=\"25%\">\n" +
            "<col width=\"75%\">\n" +
            "<tr>\n" +
            "<td><b>Acess&oacute;rios fornecidos</b></td><td>Bateria 1500 mAh BN-01, carregador Nokia AC-20, kit m&atilde;os livres est&eacute;reo Nokia WH-108</td>\n" +
            "</tr>\n" +
            "</table>\n" +
            "</div>\n" +
            "</div>\n" +
            "</div>\n" +
            "<div class=\"w3-margin-top\">\n" +
            "<header class=\"w3-container w3-black\">\n" +
            "<h2>Developers</h2>\n" +
            "</header>\n" +
            "<div class=\"w3-row-padding w3-margin-top\">\n" +
            "<div class=\"w3-half\" align=\"center\">\n" +
            "<div class=\"w3-card-8\">\n" +
            "<header class=\"w3-container w3-grey\">\n" +
            "<h4>Ant&oacute;nio Lima</h4>\n" +
            "</header>\n" +
            "<div class=\"w3-container\">\n" +
            "<p>uc2011166926</p>\n" +
            "</div>\n" +
            "</div>\n" +
            "</div>\n" +
            "<div class=\"w3-half\" align=\"center\">\n" +
            "<div class=\"w3-card-8\">\n" +
            "<header class=\"w3-container w3-grey\">\n" +
            "<h4>Pedro Janeiro</h4>\n" +
            "</header>\n" +
            "<div class=\"w3-container\">\n" +
            "<p>uc2012143629</p>\n" +
            "</div>\n" +
            "</div>\n" +
            "</div>\n" +
            "</div>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>\n";

}
