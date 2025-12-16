package model;

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
import java.util.ArrayList;
import java.util.Locale;

/**
 * Diese Klasse stellt ein model.WikiBook dar, welches eigenschaften von einem Elektronischen
 * model.Medium erbt.
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
     * Liste der gefundenen Regale/Kategorien
     */
    private ArrayList<String> regale;

    /**
     * Liste der gefundenen Kapitel Titel
     */
    private ArrayList<String> kapitel;

    private static String wikiBookLink = "http://de.wikibooks.org/wiki/";

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
        this.regale = new ArrayList<>();
        this.kapitel = new ArrayList<>();
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

    public void setRegale(ArrayList<String> _regale) {
        this.regale = _regale;
    }

    public void setKapitel(ArrayList<String> _kapitel) {
        this.kapitel = _kapitel;
    }

    public static String getWikiBookLink(){
        return wikiBookLink;
    }

    public static String getURLTitle(String _searchTerm){
        return _searchTerm.replace(" ", "_");
    }

    public static String getUrl(String _searchTerm){
        return wikiBookLink + getURLTitle(_searchTerm);
    }

    public String getLastModifiedDate(){
        return this.lastModifiedDate;
    }

    public String getLastUsername(){
        return this.lastUsername;
    }

    public String getRedirectTitle(){
        return this.redirectTitle;
    }

    public String getLastIP(){
        return this.lastIP;
    }

    public ArrayList<String> getRegale(){
        return this.regale;
    }

    /**
     * Führt die Netzwerkanfrage durch und parst das XML-Dokument mithilfe des SAX-Parsers.
     * @return Der umgeleitete Titel, falls eine Weiterleitung gefunden wurde, ansonsten null.
     */
    public String fetchAndParseWikiBook() throws MyWebException {

        String finalURL = this.getUrl() + getTitel();

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

                return handler.getRedirectTitle();

            } catch (IOException e) {
                String errorMessage = "Verbindungsfehler bei der URL: " + finalURL;
                throw new MyWebException(errorMessage, e);
            } catch (Exception e) {
                String errorMessage = "Fehler beim Parsen oder unerwarteter Fehler beim Abrufen: " + finalURL;
                throw new MyWebException(errorMessage, e);
            }
        } catch (Exception e) {
            // Fängt alle restlichen (äußeren) Exceptions ab
            String errorMessage = "Ein unerwarteter Fehler im I/O-Prozess trat auf: " + finalURL;
            throw new MyWebException(errorMessage, e);
        }
    }

    /**
     * Konvertiert den UTC-Zeitstempel in die lokale Zeitzone des Systems und formatiert ihn.
     * Genutzt wurde hierfür die Java Date-Time API.
     * @return Der formatierte Zeitstempel
     */
    public String getFormattedLastModifiedDate() {
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
     * Analysiert den übergebenen Wikitext, um Regale und Kapitel zu extrahieren.
     * @param _wikitext Inhalt des text Tags
     */
    public void parseWikitextContent(String _wikitext) {
        ArrayList<String> extractedRegale = extractRegale(_wikitext);
        this.setRegale(extractedRegale);

        ArrayList<String> extractedKapitel = extractKapitel(_wikitext);
        this.setKapitel(extractedKapitel);
    }

    /**
     * Sucht nach den Mustern {{Regal|...}} und {{Regalhinweis|...}} (mit oder ohne "ort=") und extrahiert den Regalnamen.
     * @param _wikitext Der zu durchsuchende Wikitext
     * @return Eine Liste der gefundenen Regalnamen
     */
    private ArrayList<String> extractRegale(String _wikitext) {
        ArrayList<String> foundRegale = new ArrayList<>();

        String[] templateNames = {"Regal", "Regalhinweis"};

        for (String templateName : templateNames) {
            String searchPattern = "{{" + templateName + "|";
            int searchStart = 0;

            while(searchStart < _wikitext.length()) {
                int start = _wikitext.indexOf(searchPattern, searchStart);
                if (start == -1) break;

                int contentStart = start + searchPattern.length();

                int end = _wikitext.indexOf("}}", contentStart);
                if (end == -1) {
                    searchStart = start + 1;
                    continue;
                }

                String rawRegal = _wikitext.substring(contentStart, end).trim();

                String regal = rawRegal;

                if (regal.toLowerCase().startsWith("ort=")) {
                    regal = regal.substring(4).trim();
                }

                int firstPipeInContent = regal.indexOf('|');
                if (firstPipeInContent != -1) {
                    regal = regal.substring(0, firstPipeInContent).trim();
                }

                if (!regal.isBlank() && !foundRegale.contains(regal)) {
                    foundRegale.add(regal);
                }

                searchStart = end + 2;
            }
        }
        return foundRegale;
    }

    /**
     * Sucht nach Kapitel im Wikitext.
     * @param _wikitext Der zu durchsuchende Wikitext
     * @return Eine Liste der gefundenen Kapitel Titel
     */
    private ArrayList<String> extractKapitel(String _wikitext) {
        ArrayList<String> foundKapitel = new ArrayList<>();

        String[] lines = _wikitext.split("\n");

        for (String line : lines) {
            String trimmedLine = line.trim();
            if (trimmedLine.startsWith("==") && trimmedLine.endsWith("==") && trimmedLine.length() > 4) {
                if (trimmedLine.charAt(2) != '=' && trimmedLine.charAt(trimmedLine.length() - 3) != '=') {
                    String chapterTitle = trimmedLine.substring(2, trimmedLine.length() - 2).trim();
                    if (!chapterTitle.equalsIgnoreCase("__TOC__")) {
                        foundKapitel.add(chapterTitle);
                    }
                }
            }
        }
        return foundKapitel;
    }

    /**
     * Gibt den Benutzernamen des letzten Bearbeiters oder die IP-Adresse zurück.
     * @return Der am besten geeignete Bearbeitername oder "N/A"
     */
    public String getDisplayContributor() {
        if (this.lastUsername != null && !this.lastUsername.isBlank()){
            return this.lastUsername;
        }
        else if (this.lastIP != null && !this.lastIP.isBlank()){
            return this.lastIP;
        }
        else{
            return "N/A";
        }
    }

    /**
     * Erzeugt eine menschlich Lesbare repräsentation von einem model.WikiBook.
     * @return Die Lesbare repräsentation als String
     */
    public String printWikiBook(){
        StringBuilder sb = new StringBuilder(this.getTitel().replace(' ', '_')).append("\n");

        sb.append("Regal: ");
        if (!regale.isEmpty()) {
            sb.append(String.join(", ", regale));
        } else {
            sb.append("N/A");
        }
        sb.append("\n");

        sb.append("Kapitel:\n");
        if (!kapitel.isEmpty()) {
            for (int i = 0; i < kapitel.size(); i++) {
                sb.append(String.format("%d %s\n", i + 1, kapitel.get(i)));
            }
        } else {
            sb.append("Keine Kapitel gefunden.\n");
        }

        sb.append("Letzte Änderung: ").append(getFormattedLastModifiedDate()).append("\n");

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
        sb.append(contributorInfo).append('\n');

        return sb.toString();
    }

    @Override
    public String calculateRepresentation(){
        StringBuilder sb = new StringBuilder(super.calculateRepresentation());

        sb.append("Regale: ");
        if (this.regale.isEmpty()) {
            sb.append("N/A").append("\n");
        } else {
            sb.append(String.join(", ", this.regale)).append("\n");
        }

        sb.append("Kapitel:\n");
        if (!this.kapitel.isEmpty()) {
            for (String kapitelTitle : kapitel) {
                sb.append(String.format("  - %s\n", kapitelTitle));
            }
        } else {
            sb.append("  - Keine Kapitel gefunden.\n");
        }

        sb.append("Letzte Änderung: ").append(getFormattedLastModifiedDate()).append("\n");

        sb.append("Letzter Bearbeiter (User): ");
        if (this.lastUsername != null && !this.lastUsername.isBlank()) {
            sb.append(this.lastUsername).append("\n");
        } else {
            sb.append("N/A").append("\n");
        }

        sb.append("Letzter Bearbeiter (IP): ");
        if (this.lastIP != null && !this.lastIP.isBlank()) {
            sb.append(this.lastIP).append("\n");
        } else {
            sb.append("N/A").append("\n");
        }

        return sb.toString();
    }
}
