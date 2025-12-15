package model;

import java.io.Serializable;

/**
 * Die Klasse model.Zeitschrift ist eine konkrete Unterklasse von model.Medium.
 * Eine model.Zeitschrift besitzt einen Titel (von der Oberklasse geerbt), eine ISSN,
 * sowie Angaben zum Volume und zur Ausgabe-Nummer.
 * @author Max Gebert, 21513
 */
public class Zeitschrift extends Medium implements Serializable{

    /**
     * ISSN der model.Zeitschrift.
     */
    private String issn;

    /**
     * Volume der model.Zeitschrift.
     */
    private int volume;

    /**
     * Nummer der model.Zeitschrift.
     */
    private int nummer;

    /**
     * Auflage der model.Zeitschrift.
     */
    private int auflage;

    /**
     * Seitenanzahl der model.Zeitschrift.
     */
    private int seitenanzahl;

    private boolean status;

    /**
     * Konstruktor zum Erstellen einer model.Zeitschrift.
     * @param _titel Titel der model.Zeitschrift
     * @param _issn ISSN der model.Zeitschrift
     * @param _volume Volume der model.Zeitschrift
     * @param _nummer Nummer der model.Zeitschrift
     * @param _auflage Auflage der model.Zeitschrift
     * @param _seitenanzahl Seitenanzahl der model.Zeitschrift
     */
    public Zeitschrift(String _titel, String _issn, int _volume, int _nummer, int _auflage, int _seitenanzahl){
        super(_titel);

        if (_issn == null || _issn.isBlank()){
            throw new IllegalArgumentException("Bitte korrekte Parameter übergeben.");
        }

        if (_volume < 0 || _nummer < 0 || _auflage < 0 || _seitenanzahl < 0){
            throw new IllegalArgumentException("Du darfst keine negativen Zahlen als Parameter übergeben.");
        }

        this.issn = _issn;
        this.volume = _volume;
        this.nummer = _nummer;
        this.auflage = _auflage;
        this.seitenanzahl = _seitenanzahl;
        this.status = true;
    }

    /**
     * Gibt die ISSN der model.Zeitschrift zurück.
     * @return ISSN der model.Zeitschrift
     */
    public String getIssn() {
        return this.issn;
    }

    /**
     * Setzt die ISSN der model.Zeitschrift.
     * @param _issn Die neue ISSN
     */
    public void setIssn(String _issn) {
        this.issn = _issn;

    }

    /**
     * Gibt das Volume der model.Zeitschrift zurück.
     * @return Volume der model.Zeitschrift
     */
    public int getVolume(){
        return this.volume;
    }

    /**
     * Setzt das Volume der model.Zeitschrift.
     * @param _volume Das neue Volume
     */
    public void setVolume(int _volume){
        this.volume = _volume;
    }

    /**
     * Gibt die Nummer der model.Zeitschrift zurück.
     * @return Nummer der model.Zeitschrift
     */
    public int getNummer(){
        return this.nummer;
    }

    /**
     * Setzt die Nummer der model.Zeitschrift.
     * @param _nummer Die neue Nummer
     */
    public void setNummer(int _nummer){
        this.nummer = _nummer;
    }

    /**
     * Gibt die Auflage der model.Zeitschrift zurück.
     * @return Auflage der model.Zeitschrift
     */
    public int getAuflage(){
        return this.auflage;
    }

    /**
     * Setzt die Auflage der model.Zeitschrift.
     * @param _auflage Die neue Auflage der model.Zeitschrift
     */
    public void setAuflage(int _auflage){
        this.auflage = _auflage;
    }

    /**
     * Gibt die Seitenzahl der model.Zeitschrift zurück.
     * @return Seitenanzahl der model.Zeitschrift
     */
    public int getSeitenanzahl(){
        return this.seitenanzahl;
    }

    /**
     * Setzt die Seitenanzahl der model.Zeitschrift.
     * @param _seitenanzahl Die neue Seitenanzahl der model.Zeitschrift
     */
    public void setSeitenanzahl(int _seitenanzahl){
        this.seitenanzahl = _seitenanzahl;
    }

    /**
     * Gibt eine textuelle Darstellung der model.Zeitschrift zurück.
     * Sie überschreibt die abstrakte Methode aus der Oberklasse model.Medium.
     * @return Ein String mit Titel, ISSN, Volume, Nummer, Auflage und Seitenanzahl der model.Zeitschrift
     */
    @Override
    public String calculateRepresentation(){
        StringBuilder sb = new StringBuilder(super.calculateRepresentation());
        sb.append("ISSN: ").append(getIssn()).append("\n");
        sb.append("Volume: ").append(getVolume()).append("\n");
        sb.append("Nummer: ").append(getNummer()).append("\n");
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
            System.out.println("Diese model.Zeitschrift ist bereits ausgeliehen.");
        }
    }

    @Override
    public void rueckgabe() {
        if (!this.status){
            this.status = true;
        }
        else{
            System.out.println("Du kannst eine model.Zeitschrift die du nicht besitzt, nicht zurückgeben.");
        }
    }

    @Override
    public void verlaengern() {
        if (!this.status){
            System.out.println("Du kannst den Eigentumszeitraum einer nicht besitzenden model.Zeitschrift nicht verlängern.");
        }
        else{
            System.out.println("Eigentumszeitraum verlängert.");
        }
    }
}
