package model;

import java.io.Serializable;

/**
 * Die abstrakte Klasse model.Medium stellt ein allgemeines model.Medium dar.
 * Sie enthält grundlegende Eigenschaften wie den Titel und eine abstrakte Methode zur Darstellung.
 * Außerdem verfügt sie über die Methode parseBibText, welche die Funktion hat Medien zu importieren.
 * @author Max Gebert, 21513
 */
abstract class Medium implements Comparable<Medium>, Serializable{

    /**
     * Titel des Mediums
     */
    private String titel;

    /**
     * Einzigartige ID eines Mediums
     */
    private final int id;

    /**
     * Die nächste zu vergebene ID eines Mediums
     */
    private static int nextID = 1;

    /**
     * Konstruktor zum Erstellen eines Mediums mit einem Titel.
     * @param _titel Titel des Mediums
     */
    public Medium(String _titel){
        if (_titel == null || _titel.isBlank()){
            throw new IllegalArgumentException("Titel darf nicht leer oder null sein.");
        }
        this.titel = _titel;
        this.id = nextID++;
    }

    /**
     * Gibt den Titel des Mediums zurück.
     * @return Der Titel
     */
    public String getTitel(){
        return this.titel;
    }

    /**
     * Setzt den Titel des Mediums.
     * @param _titel Der neue Titel
     */
    public void setTitel(String _titel){
        this.titel = _titel;
    }

    public int getID(){
        return this.id;
    }

    /**
     * Methode die eine Textbasierte Darstellung des Mediums liefert.
     * @return Textbasierte Darstellung des Mediums
     */
    public String calculateRepresentation(){
        StringBuilder sb = new StringBuilder();
        sb.append("model.Medium ID: ").append(getID()).append("\n");
        sb.append("Titel: ").append(getTitel()).append("\n");
        return sb.toString();
    }

    /*
    public static model.Medium parseBibTex(Scanner sc){
        System.out.println("Bitte Medien Importieren: ");
         if (!sc.hasNextLine()){
             return null;
         }
         String input = sc.nextLine();

         if (input == null || input.isBlank()) {
             System.out.println("Leere Eingabe.");
             return null;
         }

         if (!model.ParseUtils.hasBalancedBrackets(input)) {
             System.out.println("Ungültige Eingabe: Klammerung stimmt nicht (fehlende oder überzählige Klammer).");
             return null;
         }

         String before;
         String after;

         int index = input.indexOf('{');
         if (index != -1) {
             before = input.substring(0, index); // model.Medium suchen (parsen bis zum ersten '{')
             after = input.substring(index + 1); // String danach soll gefundenen '{' nicht mehr enthalten
         } else{
             System.out.println("Keine gültige Klammer nach dem model.Medium gefunden.");
             System.out.println("@book{...");
             return null;
         }
         switch(before){
             case "@book":
                 try{
                     String author = model.ParseUtils.extractValue(after, "author");
                     if (author == null) {
                         System.out.println("Fehlendes oder falsch formatiertes Feld: 'author = {...}'");
                         return null;
                     }

                     String title = model.ParseUtils.extractValue(after, "title");
                     if (title == null) {
                         System.out.println("Fehlendes oder falsch formatiertes Feld: 'title = {...}'");
                         return null;
                     }

                     String publisher = model.ParseUtils.extractValue(after, "publisher");
                     if (publisher == null) {
                         System.out.println("Fehlendes oder falsch formatiertes Feld: 'publisher = {...}'");
                         return null;
                     }

                     String isbn = model.ParseUtils.extractValue(after, "isbn");
                     if (isbn == null) {
                         System.out.println("Fehlendes oder falsch formatiertes Feld: 'isbn = {...}'");
                         return null;
                     }

                     String yearStr = model.ParseUtils.extractRawValue(after, "year");
                     if (yearStr == null) {
                         System.out.println("Fehlendes oder falsch formatiertes Feld: 'year = ...'");
                         return null;
                     }

                     // Erstellen vom model.Medium model.Buch und parsen restlicher Attribute
                     int parsedYear;
                     try{
                         parsedYear = Integer.parseInt(yearStr);
                     } catch (NumberFormatException e){
                         System.out.println("Dies ist kein richtiges Jahr: " + yearStr);
                         return null;
                     }

                     return new model.Buch(title, parsedYear, publisher, isbn, author);

                 } catch (Exception e) {
                     System.out.println("Folgender error: " + e);
                     System.out.println("Bitte halte dich an folgender Notation:");
                     System.out.println("@book{author = {...}, title = {...}, publisher = {...}, year = 2004, isbn = {...}}");
                     return null;
                 }

             case "@journal":
                 try{
                     String title = model.ParseUtils.extractValue(after, "title");
                     if (title == null){
                         System.out.println("Fehlendes oder falsch formatiertes Feld: 'title = {...}'");
                         return null;
                     }

                     String issn = model.ParseUtils.extractValue(after, "issn");
                     if (issn == null){
                         System.out.println("Fehlendes oder falsch formatiertes Feld: 'issn = {...}'");
                         return null;
                     }

                     String volumeStr = model.ParseUtils.extractRawValue(after, "volume");
                     if (volumeStr == null) {
                         System.out.println("Fehlendes oder falsch formatiertes Feld: 'volume = ...'");
                         return null;
                     }

                     String numberStr = model.ParseUtils.extractRawValue(after, "number");
                     if (numberStr == null) {
                         System.out.println("Fehlendes oder falsch formatiertes Feld: 'number = ...'");
                         return null;
                     }

                     // Erstellen vom model.Medium model.Zeitschrift und parsen restlicher Attribute
                     int parsedVolume;
                     int parsedNumber;

                     try{
                         parsedVolume = Integer.parseInt(volumeStr);
                         parsedNumber = Integer.parseInt(numberStr);
                     } catch (NumberFormatException e){
                         System.out.println("Bitte richtige Werte für Volume und Number nutzen.");
                         return null;
                     }

                     return new model.Zeitschrift(title, issn, parsedVolume, parsedNumber);

                 } catch (Exception e){
                     System.out.println("Folgender error: " + e);
                     System.out.println("Bitte halte dich an folgender Notation:");
                     System.out.println("@journal{title = {...}, issn = {...}, volume = ..., number = ...}");
                     return null;
                 }

             case "@cd":
                 try{
                     String title = model.ParseUtils.extractValue(after, "title");
                     if (title == null){
                         System.out.println("Fehlendes oder falsch formatiertes Feld: 'title = {...}'");
                         return null;
                     }

                     String artist = model.ParseUtils.extractValue(after, "artist");
                     if (artist == null){
                         System.out.println("Fehlendes oder falsch formatiertes Feld: 'artist = {...}'");
                         return null;
                     }

                     String label = model.ParseUtils.extractValue(after, "label");
                     if (label == null){
                         System.out.println("Fehlendes oder falsch formatiertes Feld: 'label = {...}'");
                         return null;
                     }

                     return new model.CD(title, label, artist);

                 } catch (Exception e){
                     System.out.println("Folgender error: " + e);
                     System.out.println("Bitte halte dich an folgender Notation:");
                     System.out.println("@cd{title = {...}, artist = {...}, label = {...}}");
                     return null;
                 }

             case "@elMed":
                 try{
                     String title = model.ParseUtils.extractValue(after, "title");
                     if (title == null){
                         System.out.println("Fehlendes oder falsch formatiertes Feld: 'title = {...}'");
                         return null;
                     }

                     String url = model.ParseUtils.extractValue(after, "URL");
                     if (url == null){
                         System.out.println("Fehlendes oder falsch formatiertes Feld: 'URL = {...}'");
                         return null;
                     }

                     return new model.ElektronischesMedium(title, url);

                 } catch (Exception e){
                     System.out.println("Folgender error: " + e);
                     System.out.println("Bitte halte dich an folgender Notation:");
                     System.out.println("@elMed{title = {...}, URL = {...}}");
                     return null;
                 }

             default:
                 System.out.println("model.Medium nicht gefunden.");
                 System.out.println("Folgende Notationen sind erlaubt:");
                 System.out.println("@book{author = {...}, title = {...}, publisher = {...}, year = 2004, isbn = {...}}");
                 System.out.println("@journal{title = {...}, issn = {...}, volume = ..., number = ...}");
                 System.out.println("@cd{title = {...}, artist = {...}, label = {...}}");
                 System.out.println("@elMed{title = {...}, URL = {...}}");
         }
         return null;
    }
     */


    /**
     * Methode, um ein model.Medium auszuleihen.
     */
    public void ausleihen() {

    }

    /**
     * Methode, um ein model.Medium zurückzugeben.
     */
    public void rueckgabe() {

    }

    /**
     * Methode, um die Eigentumsdauer eines Mediums zu verlängern.
     */
    public void verlaengern() {

    }

    @Override
    public int compareTo(Medium medium){
        return this.getTitel().compareTo(medium.getTitel());
    }
}

