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

        ArrayList<Medium> mediumSammlung = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        while (true){
            try{
                Medium medium = Medium.parseBibTex(sc);
                if (medium != null) {
                    System.out.println(medium.calculateRepresentation());
                    mediumSammlung.add(medium);
                } else {
                    System.out.println("Kein Medium erstellt. Eingabe war ungültig.");
                    break;
                }
            } catch (IllegalArgumentException e){
                System.out.println("Fehler beim erstellen vom Medium: " + e);
            }
        }

    }
}
