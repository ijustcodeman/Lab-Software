import java.io.*;

/**
 * Die Klasse ElektronischesMedium ist eine Unterklasse von Medium.
 * Sie besitzt einen Titel (von Medium) und eine URL.
 * @author Max Gebert, 21513
 */
public class ElektronischesMedium extends Medium implements Serializable{

    /**
     * URL des elektronischen Mediums.
     */
    private String url;

    /**
     * Dateiformat des Elektronischen Mediums.
     */
    private String dateiformat;

    /**
     * Groesse des Elektronischen Mediums.
     */
    private double groesse;

    /**
     * Status des Elektronischen Mediums.
     */
    private boolean status;

    /**
     * Konstruktor zum Erstellen eines elektronischen Mediums.
     * @param _titel Titel des elektronischen Mediums
     * @param _url URL des elektronischen Mediums
     * @param _dateiformat Dateiformat des elektronischen Mediums
     * @param _groesse Groesse des elektronischen Mediums
     */
    public ElektronischesMedium(String _titel, String _url, String _dateiformat, double _groesse){
        super(_titel);
        setUrl(_url);

        if (_dateiformat == null || _dateiformat.isBlank()){
            throw new IllegalArgumentException("Bitte korrekte Parameter übergeben.");
        }

        if (_groesse < 0){
            throw new IllegalArgumentException("Du darfst keine negative Größe als Parameter übergeben.");
        }

        this.dateiformat =_dateiformat;
        this.groesse = _groesse;
        this.status = true;
    }

    /**
     * Gibt die URL des elektronischen Mediums zurück.
     * @return URL des elektronischen Mediums
     */
    public String getUrl(){
        return this.url;
    }

    /**
     * Setzt die URL des elektronischen Mediums, wenn sie gültig ist.
     * @param _url Neue URL des elektronischen Mediums
     */
    public void setUrl(String _url){
        if (URLUtils.checkURL(_url)){
            this.url = _url;
        }
        else{
            throw new IllegalArgumentException("Ungültige URL.");
        }
    }

    /**
     * Gibt das Dateiformat eines elektronischen Mediums zurück.
     * @return Dateiformat des elektronischen Mediums
     */
    public String getDateiformat(){
        return this.dateiformat;
    }

    /**
     * Setzt das Dateiformat des elektronischen Mediums.
     * @param _dateiformat Das neue Dateiformat des elektronischen Mediums
     */
    public void setDateiformat(String _dateiformat){
        this.dateiformat = _dateiformat;
    }

    /**
     * Gibt die Groesse des elektronischen Mediums zurück.
     * @return Groesse des elektronischen Mediums
     */
    public double getGroesse(){
        return this.groesse;
    }

    /**
     * Setzt die Groesse des elektronischen Mediums.
     * @param _groesse Die neue Groesse des elektronischen Mediums
     */
    public void setGroesse(double _groesse){
        this.groesse = _groesse;
    }

    /**
     * Gibt eine textuelle Darstellung vom elektronischen Medium zurück.
     * Sie überschreibt die abstrakte Methode aus der Oberklasse Medium.
     * @return Ein String mit Titel, URL, Dateiformat und Groesse des elektronischen Mediums
     */
    @Override
    public String calculateRepresentation(){
        StringBuilder sb = new StringBuilder(super.calculateRepresentation());
        sb.append("URL: ").append(getUrl()).append("\n");
        sb.append("Dateiformat: ").append(getDateiformat()).append("\n");
        sb.append("Groesse: ").append(getGroesse()).append("\n");
        return sb.toString();
    }

    @Override
    public void ausleihen() {
        if (this.status){
            this.status = false;
        }
        else{
            System.out.println("Dieses Elektronische Medium ist bereits ausgeliehen.");
        }
    }

    @Override
    public void rueckgabe() {
        if (!this.status){
            this.status = true;
        }
        else{
            System.out.println("Du kannst ein Elektronisches Mediums das du nicht besitzt, nicht zurückgeben.");
        }
    }

    @Override
    public void verlaengern() {
        if (!this.status){
            System.out.println("Du kannst den Eigentumszeitraum eines nicht besitzenden Elektronischen Mediums nicht verlängern.");
        }
        else{
            System.out.println("Eigentumszeitraum verlängert.");
        }
    }

}
