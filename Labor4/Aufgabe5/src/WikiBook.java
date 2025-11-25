import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class WikiBook extends ElektronischesMedium {

    private String lastUsername;
    private String lastIP;

    /**
     * Konstruktor zum Erstellen eines elektronischen Mediums.
     *
     * @param _titel       Titel des elektronischen Mediums
     * @param _url         URL des elektronischen Mediums
     * @param _dateiformat Dateiformat des elektronischen Mediums
     * @param _groesse     Groesse des elektronischen Mediums
     */
    public WikiBook(String _titel, String _url, String _dateiformat, double _groesse) {
        super(_titel, _url, _dateiformat, _groesse);
        this.lastUsername = null;
        this.lastIP = null;
    }

    public String getLastUsername(){
        return this.lastUsername;
    }


    public void setLastUsername(String lastUsername) {
        this.lastUsername = lastUsername;
    }

    public void setLastIP(String lastIP) {
        this.lastIP = lastIP;
    }

    public void readAndParseXML() {
        String finalURL = getUrl() + getTitel();
        try {
            URL url = new URL(finalURL);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (...)");

            try (InputStream inStream = connection.getInputStream()) {

                XMLReader xmlReader = XMLReaderFactory.createXMLReader();

                WikiBookContentHandler handler = new WikiBookContentHandler(this);
                xmlReader.setContentHandler(handler);

                InputSource inputSource = new InputSource(inStream);

                xmlReader.parse(inputSource);

                System.out.println("XML-Daten erfolgreich geparst und letzter Bearbeiter ermittelt.");

            } catch (Exception e) {
                System.out.println("Fehler beim Parsen der XML-Daten: " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Fehler bei der URL-Verbindung: " + e.getMessage());
        }
    }

    @Override
    public String calculateRepresentation(){

        StringBuilder sb = new StringBuilder(super.calculateRepresentation());
        sb.append("Letzter Bearbeiter (User): ").append(this.lastUsername != null ? this.lastUsername : "N/A").append("\n");
        sb.append("Letzter Bearbeiter (IP): ").append(this.lastIP != null ? this.lastIP : "N/A").append("\n");
        return sb.toString();
    }
}
