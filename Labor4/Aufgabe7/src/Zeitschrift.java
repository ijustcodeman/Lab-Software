import java.io.Serializable;

/**
 * Die Klasse Zeitschrift ist eine konkrete Unterklasse von Medium.
 * Eine Zeitschrift besitzt einen Titel (von der Oberklasse geerbt), eine ISSN,
 * sowie Angaben zum Volume und zur Ausgabe-Nummer.
 * @author Max Gebert, 21513
 */
public class Zeitschrift extends Medium implements Serializable{

    /**
     * ISSN der Zeitschrift.
     */
    private String issn;

    /**
     * Volume der Zeitschrift.
     */
    private int volume;

    /**
     * Nummer der Zeitschrift.
     */
    private int nummer;

    /**
     * Auflage der Zeitschrift.
     */
    private int auflage;

    /**
     * Seitenanzahl der Zeitschrift.
     */
    private int seitenanzahl;

    private boolean status;

    /**
     * Konstruktor zum Erstellen einer Zeitschrift.
     * @param _titel Titel der Zeitschrift
     * @param _issn ISSN der Zeitschrift
     * @param _volume Volume der Zeitschrift
     * @param _nummer Nummer der Zeitschrift
     * @param _auflage Auflage der Zeitschrift
     * @param _seitenanzahl Seitenanzahl der Zeitschrift
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
     * Gibt die ISSN der Zeitschrift zurück.
     * @return ISSN der Zeitschrift
     */
    public String getIssn() {
        return this.issn;
    }

    /**
     * Setzt die ISSN der Zeitschrift.
     * @param _issn Die neue ISSN
     */
    public void setIssn(String _issn) {
        this.issn = _issn;

    }

    /**
     * Gibt das Volume der Zeitschrift zurück.
     * @return Volume der Zeitschrift
     */
    public int getVolume(){
        return this.volume;
    }

    /**
     * Setzt das Volume der Zeitschrift.
     * @param _volume Das neue Volume
     */
    public void setVolume(int _volume){
        this.volume = _volume;
    }

    /**
     * Gibt die Nummer der Zeitschrift zurück.
     * @return Nummer der Zeitschrift
     */
    public int getNummer(){
        return this.nummer;
    }

    /**
     * Setzt die Nummer der Zeitschrift.
     * @param _nummer Die neue Nummer
     */
    public void setNummer(int _nummer){
        this.nummer = _nummer;
    }

    /**
     * Gibt die Auflage der Zeitschrift zurück.
     * @return Auflage der Zeitschrift
     */
    public int getAuflage(){
        return this.auflage;
    }

    /**
     * Setzt die Auflage der Zeitschrift.
     * @param _auflage Die neue Auflage der Zeitschrift
     */
    public void setAuflage(int _auflage){
        this.auflage = _auflage;
    }

    /**
     * Gibt die Seitenzahl der Zeitschrift zurück.
     * @return Seitenanzahl der Zeitschrift
     */
    public int getSeitenanzahl(){
        return this.seitenanzahl;
    }

    /**
     * Setzt die Seitenanzahl der Zeitschrift.
     * @param _seitenanzahl Die neue Seitenanzahl der Zeitschrift
     */
    public void setSeitenanzahl(int _seitenanzahl){
        this.seitenanzahl = _seitenanzahl;
    }

    /**
     * Gibt eine textuelle Darstellung der Zeitschrift zurück.
     * Sie überschreibt die abstrakte Methode aus der Oberklasse Medium.
     * @return Ein String mit Titel, ISSN, Volume, Nummer, Auflage und Seitenanzahl der Zeitschrift
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
            System.out.println("Diese Zeitschrift ist bereits ausgeliehen.");
        }
    }

    @Override
    public void rueckgabe() {
        if (!this.status){
            this.status = true;
        }
        else{
            System.out.println("Du kannst eine Zeitschrift die du nicht besitzt, nicht zurückgeben.");
        }
    }

    @Override
    public void verlaengern() {
        if (!this.status){
            System.out.println("Du kannst den Eigentumszeitraum einer nicht besitzenden Zeitschrift nicht verlängern.");
        }
        else{
            System.out.println("Eigentumszeitraum verlängert.");
        }
    }
}
