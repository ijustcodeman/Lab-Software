package student;

import java.util.ArrayList;
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
     * Konstruktor, um die Liste "myZettelkasten" zu initialisieren.
     */
    public Zettelkasten() {
        this.myZettelkasten = new ArrayList<>();
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

    @Override
    public Iterator<Medium> iterator() {
        return myZettelkasten.iterator();
    }
}
