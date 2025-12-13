package model;

/**
 * Dieses Interface beinhaltet Methoden zum Speichern und Laden von einem model.Zettelkasten.
 * @author Max Gebert, 21513
 */
public interface Persistency {

    /**
     * Diese Methode speichert einen model.Zettelkasten.
     * @param _zk Der zu speichernde model.Zettelkasten
     * @param _dateiname Der Dateiname für den zum speichernden model.Zettelkasten (dateityp automatisch festgelegt)
     */
    void save(Zettelkasten _zk, String _dateiname);

    /**
     * Diese Methode lädt einen gespeicherten model.Zettelkasten anhand vom Dateinamen.
     * @param _dateiname Der Dateiname des Zettelkastens
     */
    Zettelkasten load(String _dateiname);
}
