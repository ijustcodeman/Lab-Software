package student;

import java.util.ArrayList;
import java.util.Iterator;

public class Zettelkasten implements Iterable<Medium>{
    private ArrayList<Medium> myZettelkasten;

    public Zettelkasten() {
        this.myZettelkasten = new ArrayList<>();
    }

    public boolean addMedium(Medium medium){
        if (medium == null) {
            System.out.println("Fehler: Es wurde kein Medium übergeben (null).");
            return false;
        }
        this.myZettelkasten.add(medium);
        return true;
    }

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
