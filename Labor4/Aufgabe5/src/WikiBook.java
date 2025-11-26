import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class WikiBook extends ElektronischesMedium {

    /**
     * Speichert den Benutzernamen des letzten Bearbeiters (falls vorhanden).
     */
    private String lastUsername;

    /**
     * Speichert die IP-Adresse des letzten Bearbeiters (falls vorhanden).
     */
    private String lastIP;

    /**
     * Speichert den Titel des Zielmediums, falls eine Weiterleitung gefunden wurde.
     */
    private String redirectTitle;

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
        this.redirectTitle = null;
    }

    /**
     * Setzt den Benutzernamen des letzten Bearbeiters.
     * @param _lastUsername Der Benutzername
     */
    public void setLastUsername(String _lastUsername) {
        this.lastUsername = _lastUsername;
    }

    /**
     * Setzt die IP-Adresse des letzten Bearbeiters.
     * @param _lastIP Die IP-Adresse
     */
    public void setLastIP(String _lastIP) {
        this.lastIP = _lastIP;
    }

    /**
     * Setzt den Titel der Weiterleitung, falls vom Parser gefunden.
     * @param _redirectTitle Der Ziel-Titel
     */
    public void setRedirectTitle(String _redirectTitle){
        this.redirectTitle = _redirectTitle;
    }

    /**
     * Führt die Netzwerkanfrage durch und parst das XML-Dokument mithilfe des SAX-Parsers.
     * @return Der umgeleitete Titel, falls eine Weiterleitung gefunden wurde, ansonsten null.
     */
    public String fetchAndParseWikiBook() throws Exception {
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

                System.out.println("XML-Daten erfolgreich geparst.");


                return handler.getRedirectTitle();

            }
        } catch (IOException e) {
            throw new IOException("Verbindungsfehler bei der URL: " + finalURL + " | " + e.getMessage());
        }
    }

    /**
     * Erzeugt eine menschlich Lesbare repräsentation von einem WikiBook.
     * @return Die Lesbare repräsentation als String
     */
    public String printWikiBook(){
        if (this.lastUsername != null && !this.lastUsername.isBlank()){
            return "Urheber: " + this.lastUsername;
        }
        if (this.lastIP != null && !this.lastIP.isBlank()){
            return "Urheber: " + this.lastIP;
        }
        else{
            return "Urheber: N/A";
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
