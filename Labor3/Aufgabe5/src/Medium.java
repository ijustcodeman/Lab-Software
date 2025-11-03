
/**
 * Die abstrakte Klasse Medium stellt ein allgemeines Medium dar.
 * Sie enthält grundlegende Eigenschaften wie den Titel und eine abstrakte Methode zur Darstellung.
 * Außerdem verfügt sie über die Methode parseBibText, welche die Funktion hat Medien zu importieren.
 * @author Max Gebert, 21513
 */
abstract class Medium implements Comparable<Medium>{

    /**
     * Titel des Mediums
     */
    private String titel;

    /**
     * Konstruktor zum Erstellen eines Mediums mit einem Titel.
     * @param _titel Titel des Mediums
     */
    public Medium(String _titel){
        if (_titel == null || _titel.isBlank()){
            throw new IllegalArgumentException("Titel darf nicht leer oder null sein.");
        }
        this.titel = _titel;
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

    /**
     * Methode die eine Textbasierte Darstellung des Mediums liefert.
     * @return Textbasierte Darstellung des Mediums
     */
    public String calculateRepresentation(){
        StringBuilder sb = new StringBuilder();
        sb.append("Titel: ").append(getTitel()).append("\n");
        return sb.toString();
    }

    /*
    public static Medium parseBibTex(Scanner sc){
        System.out.println("Bitte Medien Importieren: ");
         if (!sc.hasNextLine()){
             return null;
         }
         String input = sc.nextLine();

         if (input == null || input.isBlank()) {
             System.out.println("Leere Eingabe.");
             return null;
         }

         if (!ParseUtils.hasBalancedBrackets(input)) {
             System.out.println("Ungültige Eingabe: Klammerung stimmt nicht (fehlende oder überzählige Klammer).");
             return null;
         }

         String before;
         String after;

         int index = input.indexOf('{');
         if (index != -1) {
             before = input.substring(0, index); // Medium suchen (parsen bis zum ersten '{')
             after = input.substring(index + 1); // String danach soll gefundenen '{' nicht mehr enthalten
         } else{
             System.out.println("Keine gültige Klammer nach dem Medium gefunden.");
             System.out.println("@book{...");
             return null;
         }
         switch(before){
             case "@book":
                 try{
                     String author = ParseUtils.extractValue(after, "author");
                     if (author == null) {
                         System.out.println("Fehlendes oder falsch formatiertes Feld: 'author = {...}'");
                         return null;
                     }

                     String title = ParseUtils.extractValue(after, "title");
                     if (title == null) {
                         System.out.println("Fehlendes oder falsch formatiertes Feld: 'title = {...}'");
                         return null;
                     }

                     String publisher = ParseUtils.extractValue(after, "publisher");
                     if (publisher == null) {
                         System.out.println("Fehlendes oder falsch formatiertes Feld: 'publisher = {...}'");
                         return null;
                     }

                     String isbn = ParseUtils.extractValue(after, "isbn");
                     if (isbn == null) {
                         System.out.println("Fehlendes oder falsch formatiertes Feld: 'isbn = {...}'");
                         return null;
                     }

                     String yearStr = ParseUtils.extractRawValue(after, "year");
                     if (yearStr == null) {
                         System.out.println("Fehlendes oder falsch formatiertes Feld: 'year = ...'");
                         return null;
                     }

                     // Erstellen vom Medium Buch und parsen restlicher Attribute
                     int parsedYear;
                     try{
                         parsedYear = Integer.parseInt(yearStr);
                     } catch (NumberFormatException e){
                         System.out.println("Dies ist kein richtiges Jahr: " + yearStr);
                         return null;
                     }

                     return new Buch(title, parsedYear, publisher, isbn, author);

                 } catch (Exception e) {
                     System.out.println("Folgender error: " + e);
                     System.out.println("Bitte halte dich an folgender Notation:");
                     System.out.println("@book{author = {...}, title = {...}, publisher = {...}, year = 2004, isbn = {...}}");
                     return null;
                 }

             case "@journal":
                 try{
                     String title = ParseUtils.extractValue(after, "title");
                     if (title == null){
                         System.out.println("Fehlendes oder falsch formatiertes Feld: 'title = {...}'");
                         return null;
                     }

                     String issn = ParseUtils.extractValue(after, "issn");
                     if (issn == null){
                         System.out.println("Fehlendes oder falsch formatiertes Feld: 'issn = {...}'");
                         return null;
                     }

                     String volumeStr = ParseUtils.extractRawValue(after, "volume");
                     if (volumeStr == null) {
                         System.out.println("Fehlendes oder falsch formatiertes Feld: 'volume = ...'");
                         return null;
                     }

                     String numberStr = ParseUtils.extractRawValue(after, "number");
                     if (numberStr == null) {
                         System.out.println("Fehlendes oder falsch formatiertes Feld: 'number = ...'");
                         return null;
                     }

                     // Erstellen vom Medium Zeitschrift und parsen restlicher Attribute
                     int parsedVolume;
                     int parsedNumber;

                     try{
                         parsedVolume = Integer.parseInt(volumeStr);
                         parsedNumber = Integer.parseInt(numberStr);
                     } catch (NumberFormatException e){
                         System.out.println("Bitte richtige Werte für Volume und Number nutzen.");
                         return null;
                     }

                     return new Zeitschrift(title, issn, parsedVolume, parsedNumber);

                 } catch (Exception e){
                     System.out.println("Folgender error: " + e);
                     System.out.println("Bitte halte dich an folgender Notation:");
                     System.out.println("@journal{title = {...}, issn = {...}, volume = ..., number = ...}");
                     return null;
                 }

             case "@cd":
                 try{
                     String title = ParseUtils.extractValue(after, "title");
                     if (title == null){
                         System.out.println("Fehlendes oder falsch formatiertes Feld: 'title = {...}'");
                         return null;
                     }

                     String artist = ParseUtils.extractValue(after, "artist");
                     if (artist == null){
                         System.out.println("Fehlendes oder falsch formatiertes Feld: 'artist = {...}'");
                         return null;
                     }

                     String label = ParseUtils.extractValue(after, "label");
                     if (label == null){
                         System.out.println("Fehlendes oder falsch formatiertes Feld: 'label = {...}'");
                         return null;
                     }

                     return new CD(title, label, artist);

                 } catch (Exception e){
                     System.out.println("Folgender error: " + e);
                     System.out.println("Bitte halte dich an folgender Notation:");
                     System.out.println("@cd{title = {...}, artist = {...}, label = {...}}");
                     return null;
                 }

             case "@elMed":
                 try{
                     String title = ParseUtils.extractValue(after, "title");
                     if (title == null){
                         System.out.println("Fehlendes oder falsch formatiertes Feld: 'title = {...}'");
                         return null;
                     }

                     String url = ParseUtils.extractValue(after, "URL");
                     if (url == null){
                         System.out.println("Fehlendes oder falsch formatiertes Feld: 'URL = {...}'");
                         return null;
                     }

                     return new ElektronischesMedium(title, url);

                 } catch (Exception e){
                     System.out.println("Folgender error: " + e);
                     System.out.println("Bitte halte dich an folgender Notation:");
                     System.out.println("@elMed{title = {...}, URL = {...}}");
                     return null;
                 }

             default:
                 System.out.println("Medium nicht gefunden.");
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
     * Methode, um ein Medium auszuleihen.
     */
    public void ausleihen() {

    }

    /**
     * Methode, um ein Medium zurückzugeben.
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

