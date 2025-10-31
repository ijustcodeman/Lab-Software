package student;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

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

        } catch (IllegalArgumentException e){
            System.out.println("Fehler beim Hinzufügen eines Mediums: " + e.getMessage());
        }

        // zettelkasten.sort();

        for (Medium medium : zettelkasten){
            System.out.println(medium.calculateRepresentation());
        }


    }
}
