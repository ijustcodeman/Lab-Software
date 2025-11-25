import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;


public class WikiBookContentHandler implements ContentHandler {

    private WikiBook bookToFill;
    private StringBuilder currentValue = new StringBuilder();



    private boolean inRevisionContributor = false;
    private boolean inUsername = false;
    private boolean inIP = false;

    public WikiBookContentHandler(WikiBook book) {
        this.bookToFill = book;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (inUsername || inIP) {
            currentValue.append(ch, start, length);
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        currentValue.setLength(0);

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
        if (localName.equals("username")) {
            bookToFill.setLastUsername(currentValue.toString().trim());
            inUsername = false;
        } else if (localName.equals("ip")) {
            bookToFill.setLastIP(currentValue.toString().trim());
            inIP = false;
        } else if (localName.equals("contributor")) {
            inRevisionContributor = false; // Wir verlassen den Contributor-Block
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
