import java.util.ArrayList;

/**
 * Diese Klasse parst einen String in XML-Darstellung und repräsentiert
 * die extrahierten Werte auf der Konsole.
 * @author Max Gebert, 21513
 */
public class Main {

    /**
     * Der übrig bleibende String nach entsprechenden Operationen
     */
    static String remainingString = "";

    /**
     * Zugelassene Tags innerhalb von einem Personen tag
     */
    static String[] personTags = {"vorname", "nachname", "alias"};

    /**
     * Der extrahierte Vorname aus der XML-Darstellung
     */
    static String vorname = "";

    /**
     * Der extrahierte Nachname aus der XML-Darstellung
     */
    static String nachname = "";

    /**
     * Der extrahierte Alias aus der XML-Darstellung
     */
    static String alias = "";

    /**
     * Hauptmethode zum Parsen des Strings
     * @param args Wird nicht genutzt
     */
    public static void main (String[] args){
        String testString1 = "<team><person><vorname>Peter</vorname><nachname>Quill</nachname><alias>Starlord</alias></person><person><vorname>Rocket</vorname><nachname>Racoon</nachname><alias>---</alias></person></team>";
        String testString2 = "<team><person><nachname>Quill</nachname><vorname>Peter</vorname><vorname>Albert</vorname><alias>Starlord</alias></person><person><vorname>Rocket</vorname><alias>---</alias><nachname>Racoon</nachname></person></team>";
        // String testString3 = "<team><person><nachname>Spengler</nachname><titel>Dr.</alias><vorname>Egon</vorname></person><mensch><vorname>Peter</vorname><nachname>Venkman</nachname><title>-</title></person></team>";

        parseString(testString1);
        System.out.println();
        parseString(testString2);
        // System.out.println();
        // parseString(testString3);
    }

    /**
     * Methode zum Parsen des Strings
     * @param _toParse Der zu parsende String
     */
    public static void parseString(String _toParse){

        ArrayList<String> savedInformation = new ArrayList<>();

        remainingString = checkTeam(_toParse);

        if (remainingString == null){
            System.out.println("Das Team Tag wurde nicht richtig eingebunden.");
            return;
        }

        while (!remainingString.isEmpty()) {

            String personInformation = savePersonInformation(remainingString);

            if (personInformation == null){
                System.out.println("Das Personen Tag wurde nicht richtig eingebunden.");
                break;
            }

            savedInformation.add(personInformation);
        }

        for (String info: savedInformation){
            System.out.println(processPersonInformation(info));
        }

    }

    /**
     * Methode, um ein Wert aus einem String mithilfe von 2 Indexen zu extrahieren
     * @param _toExtract Der String aus dem der Wert extrahiert werden soll
     * @param _firstIndex Der Startindex
     * @param _secondIndex Der Endindex
     * @return Der extrahierte Wert
     */
    public static String extractValue(String _toExtract, int _firstIndex, int _secondIndex){
        return _toExtract.substring(_firstIndex + 1, _secondIndex);
    }

    /**
     * Methode um zu prüfen, ob ein team tag richtig in dem String vorhanden ist.
     * Wenn ein team tag richtig vorhanden ist, wird ein substring gebildet ohne das team tag.
     * @param _toParse Der zu prüfende String
     * @return Ein Substring ohne das team tag
     */
    public static String checkTeam(String _toParse){
        int openTagFirst = _toParse.indexOf("<");
        int openTagLast = _toParse.indexOf(">");

        int closeTagFirst = _toParse.lastIndexOf("<");
        int closeTagLast = _toParse.lastIndexOf(">");

        String openTag = extractValue(_toParse, openTagFirst, openTagLast);
        String closeTag = extractValue(_toParse, closeTagFirst, closeTagLast);

        if (openTag.equals("team") && closeTag.equals("/team")){
            return _toParse.substring(openTagLast + 1, closeTagFirst);
        }
        else{
            return null;
        }
    }

    /**
     * Methode, um für in jeweils richtig gesetzte personen tags die Informationen
     * als Substring zu extrahieren (ohne das person tag).
     * @param _toParse Der String, aus dem die Informationen extrahiert werden sollen
     * @return Ein substring mit den extrahierten Informationen
     */
    public static String savePersonInformation(String _toParse){
        int openTagFirst = _toParse.indexOf("<");
        int openTagLast = _toParse.indexOf(">");

        int closeTag = _toParse.indexOf("</person>");

        int personSize = 9;

        String openTag = extractValue(_toParse, openTagFirst, openTagLast);

        if (openTag.equals("person") && closeTag != -1){
            remainingString = _toParse.substring(closeTag + personSize);
            return _toParse.substring(openTagLast + 1, closeTag);
        }
        else{
            return null;
        }

    }

    /**
     * Methode, um einen String zu analysieren und für jeweils gültige tags
     * den enthaltenden Wert zu extrahieren.
     * @param information Der zu analysierende String
     * @return Eine lesbare Repräsentation von den extrahierten Werten
     */
    public static String processPersonInformation(String information){

        remainingString = information;
        vorname = "";
        nachname = "";
        alias = "";

        while (!remainingString.isEmpty()){
            int openTagFirst = remainingString.indexOf("<");
            int openTagLast = remainingString.indexOf(">");

            int closeTagFirst = remainingString.indexOf("</");
            int closeTagLast = remainingString.indexOf(">", openTagLast + 1);

            String openTag = extractValue(remainingString, openTagFirst, openTagLast);
            String closeTag = extractValue(remainingString, closeTagFirst + 1, closeTagLast);

            if (!openTag.equals(closeTag)){
                System.out.println("Der open tag darf nicht anders als der entsprechende close tag sein.");
                System.out.println("Open tag: " + openTag);
                System.out.println("Close tag: " + closeTag);
                return "";
            }

            if (validTag(openTag) == null){
                System.out.println("Dieser tag ist in dieser Form nicht gültig: " + openTag);
                return "";
            }

            String value = extractValue(remainingString, openTagLast, closeTagFirst);
            appendValue(openTag, value);
            remainingString = remainingString.substring(closeTagLast + 1);
        }
        return buildRepresentation();
    }

    /**
     * Diese Methode prüft, ob ein tag jeweils gültig ist, also im Datensatz vorhanden ist.
     * @param tag Das zu Prüfende tag
     * @return Wenn das tag gültig ist wird der tag zurückgegeben, sonst null
     */
    public static String validTag(String tag){

        for (String s : personTags) {
            if (tag.equals(s)) {
                return tag;
            }
        }
        return null;
    }

    /**
     * Diese Methode weist statischen Strings extrahierte Werte zu
     * @param tag Das tag zu dem der Wert gehört
     * @param value Der extrahierte Wert
     */
    public static void appendValue(String tag, String value){
        if (tag.equals("vorname")){
            vorname += " " + value;
        }

        if (tag.equals("nachname")){
            nachname += " " + value;
        }

        if (tag.equals("alias")){
            alias += " " + value;
        }
    }

    /**
     * Diese Methode baut eine lesbäre Repräsentation aus den statischen Strings.
     * @return Eine lesbare Repräsentation
     */
    public static String buildRepresentation(){
        if (vorname.isBlank()){
            vorname = "Vorname nicht angegeben";
        }

        if (nachname.isBlank()){
            nachname = "Nachname nicht angegeben";
        }

        if (alias.isBlank()){
            alias = "Alias nicht angegeben.";
        }

        return vorname +  nachname + " -" + alias;
    }
}
