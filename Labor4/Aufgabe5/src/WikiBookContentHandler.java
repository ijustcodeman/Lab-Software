import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 * Diese Klasse ist für das Parsen eines XML-Dokuments von einem WikiBook zuständig.
 * @author Max Gebert, 21513
 */
public class WikiBookContentHandler implements ContentHandler {

    /**
     * Das WikiBook Objekt, in das die geparsten Daten geschrieben werden.
     */
    private WikiBook bookToFill;

    /**
     * Speichert den aktuellen Zeichenwert eines XML-Tags.
     */
    private StringBuilder currentValue = new StringBuilder();

    /**
     * Speichert den Titel des Zielmediums, falls eine Weiterleitung (<redirect>) gefunden wurde.
     */
    private String redirectTitle;

    /**
     * Flag, welcher true gesetzt wird, wenn der Parser ein redirect tag findet
     */
    private boolean isRedirect = false;

    /**
     * Flag, welcher true gesetzt wird, wenn der Parser sich innerhalb eines contributor tags befindet
     */
    private boolean inRevisionContributor = false;

    /**
     * Flag, welcher true gesetzt wird, wenn der Parser sich innerhalb eines username tags befindet
     */
    private boolean inUsername = false;

    /**
     * Flag, welcher true gesetzt wird, wenn der Parser sich innerhalb eines ip tags befindet
     */
    private boolean inIP = false;

    /**
     * Konstruktor zur Initialisierung des Handlers mit dem zu befüllenden WikiBook Objekt.
     * @param book Das WikiBook-Objekt, in das die Daten geschrieben werden
     */
    public WikiBookContentHandler(WikiBook book) {
        this.bookToFill = book;
    }

    /**
     * Gibt den gefundenen Redirect Titel zurück, damit die aufrufende Logik eine erneute Suche starten kann.
     * @return Der gefundene Ziel Titel oder null
     */
    public String getRedirectTitle() {
        return redirectTitle;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (!isRedirect && (inUsername || inIP)) {
            currentValue.append(ch, start, length);
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        currentValue.setLength(0);

        if (localName.equals("redirect")) {
            String targetTitle = atts.getValue("title");

            if (targetTitle != null && !targetTitle.isBlank()) {
                this.redirectTitle = targetTitle;
                this.isRedirect = true;
            }
        }

        if (isRedirect) {
            return;
        }

        if (inRevisionContributor && localName.equals("username")) {
            inUsername = true;
        } else if (inRevisionContributor && localName.equals("ip")) {
            inIP = true;
        } else if (localName.equals("contributor")) {
            inRevisionContributor = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (isRedirect) {
            return;
        }
        if (localName.equals("username")) {
            bookToFill.setLastUsername(currentValue.toString().trim());
            inUsername = false;
        } else if (localName.equals("ip")) {
            bookToFill.setLastIP(currentValue.toString().trim());
            inIP = false;
        } else if (localName.equals("contributor")) {
            inRevisionContributor = false;
        }
    }


    @Override
    public void setDocumentLocator(Locator locator) {

    }

    @Override
    public void startDocument() throws SAXException {

    }

    @Override
    public void endDocument() throws SAXException {

    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {

    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {

    }


    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {

    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException {

    }

    @Override
    public void skippedEntity(String name) throws SAXException {

    }
}
