package model;

/**
 * Die Klasse model.ParseUtils enthält statische Hilfsmethoden, welche für die Methode
 * parseBibTex in der abstrakten Klasse model.Medium benötigt werden.
 */
public class ParseUtils {

    /**
     * Prüft, ob ein gegebener String eine ausgeglichene Anzahl an geschweiften Klammern enthält.
     * @param _input Der zu überprüfende String
     * @return true, wenn alle geschweiften Klammern ausgeglichen und korrekt verschachtelt sind, sonst false
     */
    public static boolean hasBalancedBrackets(String _input) {
        int count = 0;
        for (char c : _input.toCharArray()) {
            if (c == '{') count++;
            else if (c == '}') count--;
            if (count < 0) return false;
        }
        return count == 0;
    }

    /**
     * Extrahiert den Wert eines Feldes in geschweiften Klammern aus einem Eintrag in der parseBibTex Methode.
     * @param _input Der String aus dem extrahiert werden soll
     * @param _field Der Name des Feldes (z.B "title")
     * @return Der extrahierte String innerhalb der geschweiften Klammer
     */
    public static String extractValue(String _input, String _field) {
        int fieldIndex = _input.indexOf(_field + " =");
        if (fieldIndex == -1) return null;

        int openIndex = _input.indexOf('{', fieldIndex);
        int closeIndex = _input.indexOf('}', openIndex);
        if (openIndex == -1 || closeIndex == -1) return null;

        return _input.substring(openIndex + 1, closeIndex);
    }

    /**
     * Extrahiert den Wert eines Feldes ohne geschweiften Klammern aus einem Eintrag in der parseBibTex Methode.
     * @param _input Der String aus dem extrahiert werden soll
     * @param _fieldName Der Name des Feldes (z.B "title")
     * @return Der extrahierte String
     */
    public static String extractRawValue(String _input, String _fieldName) {
        if (_input == null || _fieldName == null) return null;

        int fieldIndex = _input.indexOf(_fieldName + " =");
        if (fieldIndex == -1) return null;

        int equalsIndex = _input.indexOf('=', fieldIndex);
        int commaIndex = _input.indexOf(',', equalsIndex);
        if (equalsIndex == -1) return null;

        String value;
        if (commaIndex != -1) {
            value = _input.substring(equalsIndex + 1, commaIndex);
        } else {
            value = _input.substring(equalsIndex + 1);
        }

        // Entfernt '}' am ende (in @journal --> number = 6})
        value = value.trim();
        if (value.endsWith("}")) {
            value = value.substring(0, value.length() - 1).trim();
        }

        return value;
    }
}
