package student;

/**
 * Die Klasse CD ist eine Unterklasse von Medium
 * und repräsentiert eine Musik-CD.
 * Eine CD hat neben dem Titel (geerbt von Medium) auch ein Label
 * und einen Künstler.
 * @author Max Gebert, 21513
 */
public class CD extends Medium {

    /**
     * Label der CD.
     */
    private String label;

    /**
     * Künstler der CD.
     */
    private String kuenstler;

    private double gesamtdauer;

    private int altersfreigabe;

    private boolean status;




    /**
     * Konstruktor zum Erstellen einer CD.
     * @param _titel Titel der CD
     * @param _label Label der CD
     * @param _kuenstler Künstler der CD
     */
    public CD(String _titel, String _label, String _kuenstler){
        super(_titel);
        this.label = _label;
        this.kuenstler = _kuenstler;
    }

    /**
     * Gibt das Label der CD zurück.
     * @return Label der CD
     */
    public String getLabel(){
        return this.label;
    }

    /**
     * Setzt das Label der CD.
     * @param _label Das neue Label der CD
     */
    public void setLabel(String _label){
        this.label = _label;
    }

    /**
     * Gibt den Künstler der CD zurück.
     * @return Künstler der CD
     */
    public String getKuenstler(){
        return this.kuenstler;
    }

    /**
     * Setzt den Künstler der CD.
     * @param _kuenstler Der neue Künstler der CD
     */
    public void setKuenstler(String _kuenstler){
        this.kuenstler = _kuenstler;
    }

    /**
     * Gibt eine textuelle Darstellung von der CD zurück.
     * Sie überschreibt die abstrakte Methode aus der Oberklasse Medium.
     * @return Ein String mit Titel, Label und Künstler der CD
     */
    @Override
    public String calculateRepresentation(){
        StringBuilder sb = new StringBuilder(super.calculateRepresentation());
        sb.append("Label: ").append(getLabel()).append("\n");
        sb.append("Kuenstler: ").append(getKuenstler()).append("\n");
        return sb.toString();
    }

    @Override
    public void ausleihen() {
        if (this.status){
            this.status = false;
        }
        else{
            System.out.println("Diese CD ist bereits ausgeliehen.");
        }
    }

    @Override
    public void rueckgabe() {
        if (!this.status){
            this.status = true;
        }
        else{
            System.out.println("Du kannst eine CD die du nicht besitzt, nicht zurückgeben.");
        }
    }

    @Override
    public void verlaengern() {

    }
}
