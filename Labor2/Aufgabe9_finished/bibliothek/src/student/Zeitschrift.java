package student;

/**
 * Die Klasse Zeitschrift ist eine konkrete Unterklasse von Medium.
 * Eine Zeitschrift besitzt einen Titel (von der Oberklasse geerbt), eine ISSN,
 * sowie Angaben zum Volume und zur Ausgabe-Nummer.
 * @author Max Gebert, 21513
 */
public class Zeitschrift extends Medium {

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
     * Konstruktor zum Erstellen einer Zeitschrift.
     * @param _titel Titel der Zeitschrift
     * @param _issn ISSN der Zeitschrift
     * @param _volume Volume der Zeitschrift
     * @param _nummer Nummer der Zeitschrift
     */
    public Zeitschrift(String _titel, String _issn, int _volume, int _nummer){
        super(_titel);
        this.issn = _issn;
        this.volume = _volume;
        this.nummer = _nummer;
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
     * Gibt eine textuelle Darstellung der Zeitschrift zurück.
     * Sie überschreibt die abstrakte Methode aus der Oberklasse Medium.
     * @return Ein String mit Titel, ISSN, Volume und Nummer der Zeitschrift
     */
    @Override
    public String calculateRepresentation(){
        StringBuilder sb = new StringBuilder(super.calculateRepresentation());
        sb.append("ISSN: ").append(getIssn()).append("\n");
        sb.append("Volume: ").append(getVolume()).append("\n");
        sb.append("Nummer: ").append(getNummer()).append("\n");
        return sb.toString();
    }
}
