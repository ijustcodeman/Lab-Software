import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import java.util.ArrayList;

/**
 * Implementiert den SAX ContentHandler, um einen RSS-Feed zu parsen.
 * @author Max Gebert, 21513
 */
public class ChannelContentHandler implements ContentHandler {

    /**
     * Liste zum Speichern von gefundenen Titeln innerhalb eines Item-Tags
     */
    ArrayList<Item> titles = new ArrayList<Item>();

    /**
     * Variable zum Speichern des Inhalts eines bestimmten XML-Tags
     */
    private String currentValue;

    /**
     * Das zu bearbeitende Item Objekt
     */
    private Item item;

    /**
     * Ein Flag, das anzeigt, ob sich der Parser gerade innerhalb eines
     * title-Tags befindet.
     */
    private boolean inTitleTag;

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (inTitleTag){
            currentValue = new String(ch, start, length);
        }
        else{
            currentValue = null;
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        if (localName.equals("item")){
            item = new Item();
        }

        if (localName.equals("title")){
            inTitleTag = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (localName.equals("title") && item != null){
            item.setTitle(currentValue);
        }

        if (localName.equals("title")){
            inTitleTag = false;
        }

        if (localName.equals("item")){
            titles.add(item);
        }
    }

    public ArrayList<Item> getTitles(){
        return titles;
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
