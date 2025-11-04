/**
 * Dieses Interface beinhaltet Methoden zum Speichern und Laden von einem Zettelkasten.
 */
public interface Persistency {

    /**
     * Diese Methode speichert einen Zettelkasten.
     * @param _zk Der zu speichernde Zettelkasten
     * @param _dateiname Der Dateiname für den zum speichernden Zettelkasten (dateityp automatisch festgelegt)
     */
    void save(Zettelkasten _zk, String _dateiname);

    /**
     * Diese Methode lädt einen gespeicherten Zettelkasten anhand vom Dateinamen.
     * @param _dateiname Der Dateiname des Zettelkastens
     */
    void load(String _dateiname);
}
