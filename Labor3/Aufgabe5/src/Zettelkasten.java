import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Die Klasse Zettelkasten beschäftigt sich mit der Verwaltung von Medien.
 * Es stehen Methoden zur Verfügung, um Medien in einer Liste hinzuzufügen,
 * zu entfernen und ein Medium nach seinem Titel zu finden.
 */
public class Zettelkasten implements Iterable<Medium>{

    /**
     * Liste, um Medien zu speichern.
     */
    private ArrayList<Medium> myZettelkasten;

    /**
     * String zum Prüfen, ob eine Liste neu sortiert werden muss.
     */
    private String currentSortOrder;

    /**
     * Konstruktor, um die Liste sowie die currentSortOrder zu initialisieren.
     */
    public Zettelkasten() {
        this.myZettelkasten = new ArrayList<>();
        this.currentSortOrder = null;
    }

    /**
     * Methode, um Medien zur Liste hinzuzufügen.
     * @param medium Medium, welches zur Liste hinzugefügt werden soll
     * @return true, wenn das übergebene Medium erfolgreich hinzugefügt wurde, anderweitig false
     */
    public boolean addMedium(Medium medium){
        if (medium == null) {
            System.out.println("Fehler: Es wurde kein Medium übergeben (null).");
            return false;
        }
        this.myZettelkasten.add(medium);
        this.currentSortOrder = null;
        return true;
    }

    /**
     * Methode, um Medien von der Liste mit einen bestimmten Titel zu entfernen.
     * @param _title Titel vom Medium, welches gelöscht werden soll
     * @return true, wenn das Medium erfolgreich aus der Liste gelöscht wurde, anderweitig false
     */
    public boolean dropMedium(String _title){

        if (_title == null || _title.isBlank()) {
            System.out.println("Titel darf nicht leer sein.");
            return false;
        }

        Medium mediumZumLoeschen = findMedium(_title);
        if (mediumZumLoeschen != null) {
            myZettelkasten.remove(mediumZumLoeschen);
            currentSortOrder = null;
            return true;
        } else {
            System.out.println("Medium mit Titel '" + _title + "' nicht gefunden.");
            return false;
        }
    }

    /**
     * Methode, um ein Medium nach dem übergebenen Titel zu finden.
     * @param _title Titel zum Finden des Mediums
     * @return Das gefundene Medium
     */
    public Medium findMedium(String _title){
        for (int i = 0; i < myZettelkasten.size(); i++){
            if (myZettelkasten.get(i).getTitel().equals(_title)){
                return myZettelkasten.get(i);
            }
        }
        return null;
    }

    /**
     * Methode, um die Medien in der Liste nach Titel zu sortieren.
     * @param sortOrder AUFSTEIGEND für aufsteigende Sortierung der Liste und ABSTEIGEND für absteigende Sortierung der Liste
     * @return Die sortierte Liste
     */
    public boolean sort(String sortOrder){
        if (sortOrder == null || sortOrder.isBlank()){
            System.out.println("Die Sortierrichtung darf nicht null oder leer sein sein.");
            return false;
        }

        String requestedOrder = sortOrder.toUpperCase();

        if (currentSortOrder != null) {
            if (requestedOrder.equals(currentSortOrder)){
                System.out.println("Die Liste ist bereits " + requestedOrder + " sortiert. Sortierung übersprungen.");
                return true;
            }
        }

        switch (requestedOrder){
            case "AUFSTEIGEND":
                myZettelkasten.sort(Comparator.naturalOrder());
                currentSortOrder = "AUFSTEIGEND";
                return true;

            case "ABSTEIGEND":
                myZettelkasten.sort(Comparator.reverseOrder());
                currentSortOrder = "ABSTEIGEND";
                return true;

            default:
                System.out.println("Fehler: Unbekannte Sortierrichtung.");
                return false;
        }
    }

    @Override
    public Iterator<Medium> iterator() {
        return myZettelkasten.iterator();
    }
}
