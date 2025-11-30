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

        // Eingaben zum Importieren von Medien:
        // @book{author = {-}, title = {Duden 01. Die deutsche Rechtschreibung}, publisher = {Bibliographisches Institut, Mannheim}, year = 2004, isbn = {3-411-04013-0}}
        // @journal{title = {Der Spiegel}, issn = {0038-7452}, volume = 54, number = 6}
        // @cd{title = {1}, artist = {Die Beatles}, label = { Apple (Bea (EMI))}}
        // @elMed{title = {Hochschule Stralsund}, URL = {http://www.hochschule-stralsund.de}}

        Zettelkasten zettelkasten = new Zettelkasten();

        try{
            zettelkasten.addMedium(new CD("Live At Wembley","Parlophone (EMI)", "Queen", 110.0, 6));
            zettelkasten.addMedium(new Buch("Duden 01", 2004, "Bib. Institut","3-411-04013-0", "Redaktion", 24, 960));
            zettelkasten.addMedium(new ElektronischesMedium("Hochschule Stralsund", "http://www.hochschule-stralsund.de", "Nicht Vorhanden", 100.0));
            zettelkasten.addMedium(new Zeitschrift("Der Spiegel", "0038-7452", 54, 6, 1, 200));
            zettelkasten.addMedium(new Zeitschrift("Der Spiegel", "0038-7452", 54, 6, 1, 200));

        } catch (IllegalArgumentException e){
            System.out.println("Fehler beim Hinzufügen eines Mediums: " + e.getMessage());
        }

        /*

        zettelkasten.sort("AUFSTEIGEND");

        // HP
        HumanReadablePersistency test1 = new HumanReadablePersistency();
        test1.save(zettelkasten, "test1");
        test1.load("test.txt");

        // BP
        BinaryPersistency test2 = new BinaryPersistency();
        test2.save(zettelkasten, "test2");

        Zettelkasten zk_geladen = test2.load("test2");

        if (zk_geladen != null) {
            for (Medium m : zk_geladen) {
                System.out.println("Geladen: " + m.calculateRepresentation());
            }
        }

         */

        zettelkasten.addWikiBook("Die_Sprache_der_Mathematik");

        for (String arg : args) {
            zettelkasten.addWikiBook(arg);
        }

    }
}
