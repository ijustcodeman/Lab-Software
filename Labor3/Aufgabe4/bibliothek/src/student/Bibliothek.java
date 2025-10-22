package student;

import java.util.Objects;

/**
 * Die Klasse Bibliothek enthält Testmethoden zum Erzeugen und Anzeigen verschiedener Medientypen.
 * @author Max gebert, 21513
 */
public class Bibliothek {


    /**
     * Hauptmethode zum Testen.
     * @param args bis jetzt noch keine verwendung
     */
    public static void main(String[] args){

        // Instanziieren mit jeweiliger Ausgabe auf der Konsole
        /*
        Medium[] mediums = new Medium[4];
        mediums[0] = new Buch("Duden 01. Die deutsche Rechtschreibung", 2004, "Bibliographisches Institut, Mannheim", "3-411-04013-0", "-");
        mediums[1] = new CD("1", "Apple (Bea(EMI))", "The Beatles");
        mediums[2] = new Zeitschrift("Der Spiegel", "0038-7452", 54, 6);
        mediums[3] = new ElektronischesMedium("Hochschule Stralsund", "http://www.hochschule-stralsund.de");

        for (int i = 0; i < mediums.length; i++){
            System.out.println(mediums[i].calculateRepresentation());
        }

         */

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

        // Eingaben zum Importieren von Medien:
        // @book{author = {-}, title = {Duden 01. Die deutsche Rechtschreibung}, publisher = {Bibliographisches Institut, Mannheim}, year = 2004, isbn = {3-411-04013-0}}
        // @journal{title = {Der Spiegel}, issn = {0038-7452}, volume = 54, number = 6}
        // @cd{title = {1}, artist = {Die Beatles}, label = { Apple (Bea (EMI))}}
        // @elMed{title = {Hochschule Stralsund}, URL = {http://www.hochschule-stralsund.de}}

        Medium medium = Medium.parseBibTex();
        if (medium != null) {
            System.out.println(medium.calculateRepresentation());
        } else {
            System.out.println("Kein Medium erstellt. Eingabe war ungültig.");
        }
    }
}
