import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Diese Klasse stellt ein WikiBook dar, welches eigenschaften von einem Elektronischen
 * Medium erbt.
 * @author Max Gebert, 21513
 */
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
     * Speichert das letzte Änderungsdatum
     */
    private String lastModifiedDate;

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
        this.lastModifiedDate = null;
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
     * Setzt das letzte Änderungsdatum eines WikiBooks.
     * @param _lastModifiedDate Das letzte Änderungsdatum
     */
    public void setLastModifiedDate(String _lastModifiedDate) {
        this.lastModifiedDate = _lastModifiedDate;
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

                // System.out.println("XML-Daten erfolgreich geparst.");


                return handler.getRedirectTitle();

            }
        } catch (IOException e) {
            throw new IOException("Verbindungsfehler bei der URL: " + finalURL + " | " + e.getMessage());
        }
    }

    /**
     * Konvertiert den UTC-Zeitstempel in die lokale Zeitzone des Systems und formatiert ihn.
     * Genutzt wurde hierfür die Java Date-Time API.
     * @return Der formatierte Zeitstempel
     */
    private String getFormattedLastModifiedDate() {
        if (this.lastModifiedDate == null || this.lastModifiedDate.isBlank()) {
            return "N/A";
        }

        try {
            // UTC-String als Instant parsen (behandelt das abschließende Z)
            Instant instant = Instant.parse(this.lastModifiedDate);

            ZonedDateTime localTime = instant.atZone(java.time.ZoneId.systemDefault());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy 'um' HH:mm 'Uhr'", Locale.GERMAN);

            return localTime.format(formatter);

        } catch (DateTimeParseException e) {
            System.err.println("Fehler beim Parsen des Datums: " + this.lastModifiedDate);
            return "N/A (Fehler)";
        }
    }

    /**
     * Erzeugt eine menschlich Lesbare repräsentation von einem WikiBook.
     * @return Die Lesbare repräsentation als String
     */
    public String printWikiBook(){
        String dateInfo = "Letzte Änderung: " + getFormattedLastModifiedDate();

        String contributorInfo;
        if (this.lastUsername != null && !this.lastUsername.isBlank()){
            contributorInfo = "Urheber: " + this.lastUsername;
        }
        else if (this.lastIP != null && !this.lastIP.isBlank()){
            contributorInfo = "Urheber: " + this.lastIP;
        }
        else{
            contributorInfo = "Urheber: N/A";
        }

        return dateInfo + "\n" + contributorInfo + "\n";
    }

    @Override
    public String calculateRepresentation(){
        StringBuilder sb = new StringBuilder(super.calculateRepresentation());
        sb.append("Letzte Änderung (Lokal): ").append(getFormattedLastModifiedDate()).append("\n");
        sb.append("Letzter Bearbeiter (User): ").append(this.lastUsername != null ? this.lastUsername : "N/A").append("\n");
        sb.append("Letzter Bearbeiter (IP): ").append(this.lastIP != null ? this.lastIP : "N/A").append("\n");
        return sb.toString();
    }
}
