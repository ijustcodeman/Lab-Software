import java.io.Serializable;

/**
 * Die Klasse Buch ist eine Unterklasse von Medium
 * und repräsentiert ein physisches Buch mit typischen Eigenschaften wie ISBN, Verlag, Erscheinungsjahr und Verfasser.
 * @author Max Gebert, 21513
 */
public class Buch extends Medium implements Serializable {

    /**
     * Erscheinungsjahr vom Buch.
     */
    private int erscheinungsjahr;

    /**
     * Verlag vom Buch.
     */
    private String verlag;

    /**
     * ISBN vom Buch.
     */
    private String isbn;

    /**
     * Verfasser vom Buch.
     */
    private String verfasser;

    /**
     * Auflage vom Buch.
     */
    private int auflage;

    /**
     * Seitenanzahl vom Buch.
     */
    private int seitenanzahl;

    /**
     * Status vom Buch.
     */
    private boolean status;

    /**
     * Konstruktor zum Erstellen eines Buchs.
     * @param _titel Titel vom Buch
     * @param _erscheinungsjahr Erscheinungsjahr vom Buch
     * @param _verlag Verlag vom Buch
     * @param _isbn ISBN vom Buch
     * @param _verfasser Verfasser vom Buch
     * @param _auflage Auflage vom Buch
     * @param _seitenanzahl Seitenanzahl vom Buch
     */
    public Buch(String _titel, int _erscheinungsjahr, String _verlag, String _isbn, String _verfasser, int _auflage, int _seitenanzahl){
        super(_titel);

        if ((_verlag == null || _verlag.isBlank()) || (_verfasser == null || _verfasser.isBlank())){
            throw new IllegalArgumentException("Bitte korrekte Parameter übergeben.");
        }

        if (_erscheinungsjahr < 0 || _auflage < 0 || _seitenanzahl < 0){
            throw new IllegalArgumentException("Du darfst keine negativen Zahlen als Parameter übergeben.");
        }

        this.erscheinungsjahr = _erscheinungsjahr;
        this.verlag = _verlag;
        setIsbn(_isbn);
        this.verfasser = _verfasser;
        this.auflage =_auflage;
        this.seitenanzahl = _seitenanzahl;
        this.status = true;
    }

    /**
     * Gibt das Erscheinungsjahr vom Buch zurück.
     * @return Erscheinungsjahr vom Buch
     */
    public int getErscheinungsjahr(){
        return this.erscheinungsjahr;
    }

    /**
     * Setzt das Erscheinungsjahr vom Buch.
     * @param _erscheinungsjahr Neues Erscheinungsjahr vom Buch
     */
    public void setErscheinungsjahr(int _erscheinungsjahr){
        this.erscheinungsjahr = _erscheinungsjahr;
    }

    /**
     * Gibt den Verlag vom Buch zurück.
     * @return Verlag vom Buch
     */
    public String getVerlag(){
        return this.verlag;
    }

    /**
     * Setzt den Verlag vom Buch.
     * @param _verlag Neuer Verlag vom Buch
     */
    public void setVerlag(String _verlag){
        this.verlag = _verlag;
    }

    /**
     * Gibt die ISBN vom Buch zurück.
     * @return ISBN vom Buch
     */
    public String getIsbn(){
        return this.isbn;
    }

    /**
     * Setzt die ISBN vom Buch, wenn sie Valide ist.
     * Es werden sowohl ISBN-10 als auch ISBN-13 unterstützt.
     * @param _isbn Neue ISBN vom Buch
     */
    public void setIsbn(String _isbn){

        String cleanISBN = _isbn.replace("-", "");

        if (!cleanISBN.matches("[0-9]+")){ // Referenz: https://stackoverflow.com/questions/10575624/how-do-i-check-if-a-string-contains-only-numbers-and-not-letters
            throw new IllegalArgumentException("Ungültige ISBN");
        }
        int isbnLength = cleanISBN.length();
        int[] intISBN = new int[isbnLength];

        for (int i = 0; i < isbnLength; i++){
            intISBN[i] = Character.getNumericValue(cleanISBN.charAt(i));
        }

        if (intISBN.length == 10 && ISBNUtils.checkISBN10(intISBN) || intISBN.length == 13 && ISBNUtils.checkISBN13(intISBN)){
            this.isbn = _isbn;
        }

        else{
            throw new IllegalArgumentException("Ungültige ISBN.");
        }
    }

    /**
     * Gibt den Verfasser vom Buch zurück.
     * @return Verfasser vom Buch
     */
    public String getVerfasser(){
        return this.verfasser;
    }

    /**
     * Setzt den Verfasser vom Buch.
     * @param _verfasser Neuer Verfasser vom Buch
     */
    public void setVerfasser(String _verfasser){
        this.verfasser = _verfasser;
    }

    /**
     * Gibt die Auflage vom Buch zurück.
     * @return Auflage vom Buch
     */
    public int getAuflage(){
        return this.auflage;
    }

    /**
     * Setzt die Auflage vom Buch.
     * @param _auflage Neue Auflage vom Buch
     */
    public void setAuflage(int _auflage){
        this.auflage = _auflage;
    }

    /**
     * Gibt die Seitenanzahl vom Buch zurück.
     * @return Seitenanzahl vom Buch
     */
    public int getSeitenanzahl(){
        return this.seitenanzahl;
    }

    /**
     * Setzt die Seitenanzahl vom Buch.
     * @param _seitenanzahl Neue Seitenanzahl vom Buch
     */
    public void setSeitenanzahl(int _seitenanzahl){
        this.seitenanzahl = _seitenanzahl;
    }

    /**
     * Gibt eine textuelle Darstellung vom Buch zurück.
     * Sie überschreibt die abstrakte Methode aus der Oberklasse Medium.
     * @return Ein String mit Titel, Erscheinungsjahr, Verlag, ISBN, Verfasser, Auflage und Seitenanzahl
     */
    @Override
    public String calculateRepresentation(){
        StringBuilder sb = new StringBuilder(super.calculateRepresentation());
        sb.append("Erscheinungsjahr: ").append(getErscheinungsjahr()).append("\n");
        sb.append("Verlag: ").append(getVerlag()).append("\n");
        sb.append("ISBN: ").append(getIsbn()).append("\n");
        sb.append("Verfasser: ").append(getVerfasser()).append("\n");
        sb.append("Auflage: ").append(getAuflage()).append("\n");
        sb.append("Seitenanzahl: ").append(getSeitenanzahl()).append("\n");
        return sb.toString();
    }

    @Override
    public void ausleihen() {
        if (this.status){
            this.status = false;
        }
        else{
            System.out.println("Dieses Buch ist bereits ausgeliehen.");
        }
    }

    @Override
    public void rueckgabe() {
        if (!this.status){
            this.status = true;
        }
        else{
            System.out.println("Du kannst ein Buch das du nicht besitzt, nicht zurückgeben.");
        }
    }

    @Override
    public void verlaengern() {
        if (!this.status){
            System.out.println("Du kannst den Eigentumszeitraum eines nicht besitzenden buches nicht verlängern.");
        }
        else{
            System.out.println("Eigentumszeitraum verlängert.");
        }
    }

}
