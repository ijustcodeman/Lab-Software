package student;

/**
 * Die abstrakte Klasse Medium stellt ein allgemeines Medium dar.
 * Sie enthält grundlegende Eigenschaften wie den Titel
 * und eine abstrakte Methode zur Darstellung.
 * @author Max Gebert, 21513
 */
abstract class Medium {

    /**
     * Titel des Mediums
     */
    private String titel;

    /**
     * Konstruktor zum Erstellen eines Mediums mit einem Titel.
     * @param _titel Titel des Mediums
     */
    public Medium(String _titel){
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
     * Abstrakte Methode die eine Textbasierte Darstellung des Mediums liefert.
     * @return Textbasierte Darstellung des Mediums
     */
    public abstract String calculateRepresentation();
}
