import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Klasse mit Hauptmethode, um anhand eines Heruntergeladenen RSS-Feeds eine XML-Datei zu schreiben und zu Lesen.
 * @author Max Gebert, 21513
 */
public class Main {

    public static void main(String[] args){
        String fileName = "test";
        String absolutePath = "C:\\Users\\Lenovo\\Desktop\\Studium\\Labor Software\\Labor4\\Aufgabe3\\" + fileName + ".xml";
        String url = "https://rss.dw.com/xml/rss-de-all";

        constructXML(fileName, url);
        readXML(absolutePath);
    }

    /**
     * Lädt den Inhalt eines RSS-Feeds von einer URL herunter, speichert ihn in einem
     * StringBuilder und schreibt ihn anschließend in eine lokale XML-Datei.
     * @param _fileName Der Name der zu erstellenden xml Datei
     * @param _url Die URL des RSS-Feeds, der heruntergeladen werden soll
     */
    public static void constructXML(String _fileName, String _url){
        StringBuilder buildData = new StringBuilder();

        String fileName = _fileName + ".xml";

        try(
                InputStream inStream = new URL(_url).openStream();
                BufferedReader input = new BufferedReader(new InputStreamReader(inStream, StandardCharsets.UTF_8))
        ) {
            String line;

            while ((line = input.readLine()) != null){
                buildData.append(line).append("\n");
            }

            String finalData = buildData.toString();
            System.out.println(finalData);

            try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8))) {
                out.write(finalData);
            }

        } catch (Exception e){
            System.out.println("Folgender Fehler ist aufgetreten: " + e);
        }
    }

    /**
     * Liest eine lokale XML-Datei mithilfe eines SAX-Parsers und dem ChannelContentHandler.
     * Nach dem Parsen werden alle gesammelten Item-Titel auf der Konsole ausgegeben.
     * @param _absolutePath Der absolute Pfad zur XML-Datei
     */
    public static void readXML(String _absolutePath){

        ChannelContentHandler handler = new ChannelContentHandler();

        // FileInputStream statt FileReader verwenden für Korrekte Kodierung
        // FileReader trifft Annahme über die Kodierung basierend auf dem OS
        try (InputStream fileStream = new FileInputStream(_absolutePath)) {
            XMLReader xmlReader = XMLReaderFactory.createXMLReader();

            InputSource inputSource = new InputSource(fileStream);

            xmlReader.setContentHandler(handler);
            xmlReader.parse(inputSource);

            for (Item i : handler.getTitles()){
                System.out.println(i);
            }
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }
    }
}

