import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Die Klasse Zettelkasten beschäftigt sich mit der Verwaltung von Medien.
 * Es stehen Methoden zur Verfügung, um Medien in einer Liste hinzuzufügen,
 * zu entfernen und ein Medium nach seinem Titel zu finden.
 * @author Max Gebert, 21513
 */
public class Zettelkasten implements Iterable<Medium>, Serializable {

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
     * Gibt den Zettelkasten zurück.
     * @return Der Zettelkasten
     */
    public ArrayList<Medium> getMyZettelkasten(){
        return this.myZettelkasten;
    }

    /**
     * Setzt den Zettelkasten.
     * @param _myZettelKasten Der neue Zettelkasten
     */
    public void setMyZettelkasten(ArrayList<Medium> _myZettelKasten){
        this.myZettelkasten = _myZettelKasten;
        this.currentSortOrder = null;
    }

    /**
     * Methode, um Medien zur Liste hinzuzufügen.
     * @param _medium Medium, welches zur Liste hinzugefügt werden soll
     * @return true, wenn das übergebene Medium erfolgreich hinzugefügt wurde, anderweitig false
     */
    public boolean addMedium(Medium _medium){
        if (_medium == null) {
            System.out.println("Fehler: Es wurde kein Medium übergeben (null).");
            return false;
        }
        this.myZettelkasten.add(_medium);
        this.currentSortOrder = null;
        return true;
    }

    /**
     * Methode, um Medien vom Zettelkasten zu entfernen.
     * @param _title Titel vom Medium, welches gelöscht werden soll
     * @param _removeDuplicates true, wenn alle Medien mit dem gleichen Titel gelöscht werden sollen
     * @param _ID Die spezifische ID des zu löschenden Mediums (wird nur geprüft, wenn _removeDuplicates=false)
     * @return true, wenn das Medium erfolgreich aus der Liste gelöscht wurde, anderweitig false
     */
    public boolean dropMedium(String _title, boolean _removeDuplicates, int _ID){

        if (_title == null || _title.isBlank()) {
            System.out.println("Titel darf nicht leer sein.");
            return false;
        }

        ArrayList<Medium> gefundeneMedien = findMedium(_title, "AUFSTEIGEND");

        if (gefundeneMedien.isEmpty()){
            return false;
        }

        if (gefundeneMedien.size() == 1){
            myZettelkasten.remove(gefundeneMedien.get(0));
            this.currentSortOrder = null;
            System.out.println("Medium mit der ID " + gefundeneMedien.get(0).getID() + " und dem Titel " + gefundeneMedien.get(0).getTitel() + " wurde erfolgreich entfernt.");
            return true;
        }

        else{
            if (_removeDuplicates){
                myZettelkasten.removeAll(gefundeneMedien);
                this.currentSortOrder = null;
                System.out.println("Es wurden erfolgreich alle Medien mit dem Titel " + _title + " entfernt.");
                return true;
            }
            else{
                for (int i = 0; i < gefundeneMedien.size(); i++){
                    if (gefundeneMedien.get(i).getID() == _ID){
                        myZettelkasten.remove(gefundeneMedien.get(i));
                        this.currentSortOrder = null;
                        System.out.println("Medium mit der ID " + _ID + " und dem Titel " + _title + " wurde erfolgreich entfernt.");
                        return true;
                    }
                }
                System.out.println("Es wurde kein Medium mit dem Titel " + _title + " und der ID " + _ID + " gefunden.");
                return false;
            }
        }
    }

    /**
     * Methode, um Medien nach dem übergebenen Titel zu finden.
     * @param _title Titel zum Finden des Mediums
     * @param _sortOrder Wie die zurückgegebene Liste sortiert werden soll
     * @return Die gefundenen Medien in Form einer Liste
     */
    public ArrayList<Medium> findMedium(String _title, String _sortOrder){

        if (_title == null || _title.isBlank()) {
            System.out.println("Fehler: Suchtitel darf nicht leer sein.");
            return new ArrayList<>();
        }

        ArrayList<Medium> gefundeneMedien = new ArrayList<>();

        for (Medium medium : myZettelkasten) {
            if (medium.getTitel().equals(_title)) {
                gefundeneMedien.add(medium);
            }
        }

        if (gefundeneMedien.isEmpty()) {
            System.out.println("Es wurden keine Medien mit dem Titel " + _title + " gefunden.");
            return gefundeneMedien;
        }

        Comparator<Medium> finalComparator = getMediumComparator();

        String requestedOrder = (_sortOrder != null) ? _sortOrder.toUpperCase() : "AUFSTEIGEND";

        if (requestedOrder.equals("ABSTEIGEND")) {
            gefundeneMedien.sort(finalComparator.reversed());
        } else {
            gefundeneMedien.sort(finalComparator);
        }
        return gefundeneMedien;
    }

    /**
     * Erstellt und gibt einen kombinierten Comparator für die Sortierung von Medien zurück.
     * Die Sortierung erfolgt nach der folgenden Hierarchie:
     * 1. Nach dem Titel (alphabetisch, basierend auf der compareTo Methode in Medium)
     * 2. Nach dem Medientyp (alphabetisch nach dem Klassennamen, z.B. "Buch" vor "CD")
     * 3. Nach der eindeutigen ID (aufsteigend)
     * * @return Ein Comparator, der die Sortierreihenfolge der Medium-Objekte definiert
     */
    private static Comparator<Medium> getMediumComparator() {
        Comparator<Medium> primarTitel = Comparator.naturalOrder();

        Comparator<Medium> typSorter = Comparator.comparing(m -> m.getClass().getSimpleName());

        Comparator<Medium> idSorter = Comparator.comparing(Medium::getID);

        Comparator<Medium> finalComparator = primarTitel
                .thenComparing(typSorter)
                .thenComparing(idSorter);

        return finalComparator;
    }

    /**
     * Methode, um die Medien in der Liste nach Titel zu sortieren.
     * @param _sortOrder AUFSTEIGEND für aufsteigende Sortierung der Liste und ABSTEIGEND für absteigende Sortierung der Liste
     * @return Die sortierte Liste
     */
    public boolean sort(String _sortOrder){
        if (_sortOrder == null || _sortOrder.isBlank()){
            System.out.println("Die Sortierrichtung darf nicht null oder leer sein sein.");
            return false;
        }

        Comparator<Medium> finalComparator = getMediumComparator();

        String requestedOrder = _sortOrder.toUpperCase();

        if (currentSortOrder != null) {
            if (requestedOrder.equals(currentSortOrder)){
                System.out.println("Die Liste ist bereits " + requestedOrder + " sortiert. Sortierung übersprungen.");
                return true;
            }
        }

        switch (requestedOrder){
            case "AUFSTEIGEND":
                myZettelkasten.sort(finalComparator);
                currentSortOrder = "AUFSTEIGEND";
                return true;

            case "ABSTEIGEND":
                myZettelkasten.sort(finalComparator.reversed());
                currentSortOrder = "ABSTEIGEND";
                return true;

            default:
                System.out.println("Fehler: Unbekannte Sortierrichtung.");
                return false;
        }
    }

    public boolean addWikiBook(String _titel) {

        String wikibooksUrl = "https://de.wikibooks.org/wiki/Spezial:Exportieren/";
        WikiBook wikiBook = new WikiBook(_titel, wikibooksUrl, "XML/Webseite", 0.0);

        wikiBook.readAndParseXML();

        return addMedium(wikiBook);
    }

    @Override
    public Iterator<Medium> iterator() {
        return myZettelkasten.iterator();
    }
}
