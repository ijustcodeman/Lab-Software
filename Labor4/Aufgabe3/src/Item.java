/**
 * Diese Klasse repräsentiert ein einzelnes item eines RSS-Feeds.
 * @author Max Gebert, 21513
 */
public class Item {

    /**
     * Standardkonstruktor für ein Item Objekt
     */
    public Item(){

    }

    /**
     * Der Titel des Nachrichtenartikels
     */
    private String title;

    /**
     * Gibt den Titel des Artikels zurück.
     * @return Titel des Artikels
     */
    public String getTitle(){
        return this.title;
    }

    /**
     * Setzt den Titel des Artikels.
     * @param _title Der neue Titel des Artikels
     */
    public void setTitle(String _title){
        this.title = _title;
    }

    @Override
    public String toString() {
        return "Titel: " + this.title;
    }
}
