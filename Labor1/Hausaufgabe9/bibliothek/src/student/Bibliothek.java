package student;

/**
 * Die Klasse Bibliothek enthält statische Hilfsmethoden zur Validierung von ISBN-Nummern
 * sowie eine Testmethode zum Erzeugen und Anzeigen verschiedener Medientypen.
 * @author Max gebert, 21513
 */
public class Bibliothek {

    /**
     * Methode zum Prüfen, ob eine 10-stellige ISBN valide ist.
     * @param _isbn ISBN vom Buch
     * @return true wenn die ISBN Valide ist, false wenn nicht
     */
    public static boolean checkISBN10(int[] _isbn) {
        int sum = 0;
        for (int i = 1; i <= _isbn.length; i++) {
            sum += i * _isbn[i - 1];
        }
        if (sum % 11 == 0) {
            return true;
        } else {

            return false;
        }
    }

    /**
     * Methode zum prüfen, ob eine 13-stellige ISBN valide ist.
     * @param _isbn ISBN vom Buch.
     * @return true wenn die ISBN Valide ist, false wenn nicht
     */
    public static boolean checkISBN13(int[] _isbn) {
        int sum = 0;
        for (int i = 1; i < _isbn.length; i++) {
            if (i % 2 == 0) {
                sum += _isbn[i - 1] * 3;
            } else {
                sum += _isbn[i - 1];
            }
        }

        int lastDigit = sum % 10;

        int check = (10 - lastDigit) % 10;

        if (_isbn[_isbn.length - 1] == check) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Hauptmethode zum Testen.
     * @param args bis jetzt noch keine verwendung
     */
    public static void main(String[] args){

        // Instanziieren mit jeweiliger Ausgabe auf der Konsole
        Medium[] mediums = new Medium[4];
        mediums[0] = new Buch("Duden 01. Die deutsche Rechtschreibung", 2004, "Bibliographisches Institut, Mannheim", "3-411-04013-0", "-");
        mediums[1] = new CD("1", "Apple (Bea(EMI))", "The Beatles");
        mediums[2] = new Zeitschrift("Der Spiegel", "0038-7452", 54, 6);
        mediums[3] = new ElektronischesMedium("Hochschule Stralsund", "http://www.hochschule-stralsund.de");

        for (int i = 0; i < mediums.length; i++){
            System.out.println(mediums[i].calculateRepresentation());
        }

        // Tests zur ISBN-Validierung

        // Ungültige ISBN (wirft Exception):
        /*
        Buch buch = new Buch("A", 2000, "B", "3-411-04013-0", "Max");
        buch.setIsbn("3-411-04012-0");
        */

        // Gültige ISBN-10:
        /*
        int[] isbn10 = new int[] { 3, 8, 6, 6, 8, 0, 1, 9, 2, 7 };
        System.out.println("ISBN-10 gültig: " + checkISBN10(isbn10));
        */

        // Gültige ISBN-13:
        /*
        int[] isbn13 = new int[] { 9, 7, 8, 3, 7, 6, 5, 7, 2, 7, 8, 1, 8 };
        System.out.println("ISBN-13 gültig: " + checkISBN13(isbn13));
        */

        // Tests zur URL-Validierung

        //Ungültige URL
        /*
        ElektronischesMedium em = new ElektronischesMedium("Test", "http://www.hochschule-stralsund.de");
        em.setUrl("htp://ungültig");
        */

        // Gültige URL
        /*
        ElektronischesMedium em1 = new ElektronischesMedium("Test2", "http://www.hochschule-stralsund.de");
        em1.setUrl("https://de.wikipedia.org/wiki/Wikipedia:Hauptseite");
         */
    }
}
