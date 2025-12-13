package model;

import java.io.*;
import java.util.ArrayList;

/**
 * Klasse zum Speichern und Laden von model.Zettelkasten objekten in binärer Form.
 * @author Max Gebert, 21513
 */
public class BinaryPersistency implements Persistency {

    @Override
    public void save(Zettelkasten _zk, String _dateiname) {
        if (_zk == null || _dateiname == null || _dateiname.isEmpty()){
            throw new IllegalArgumentException("Bitte gültige Parameter übergeben.");
        }

        String fileName = _dateiname + ".ser";

        // Erstellen der Datei
        File file = new File(fileName);

        try{
            if (file.createNewFile()){
                System.out.println("Datei wurde erfolgreich erstellt.");
            }
            else{
                System.out.println("Datei existiert bereits.");
            }
        } catch (IOException e){
            System.out.println("Ein fehler ist aufgetreten.");
        }

        // In Datei schreiben
        try{
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(_zk.getMyZettelkasten());
            System.out.println("Daten wurden erfolgreich gespeichert.");
        } catch (Exception e){
            System.out.println("Fehler beim schreiben der Datei: " + e.getMessage());
        }
    }

    @Override
    public Zettelkasten load(String _dateiname) {
        if (_dateiname == null || _dateiname.isBlank()){
            throw new IllegalArgumentException("Bitte gültigen Dateinamen übergeben.");
        }

        String fileName = _dateiname + ".ser";
        ArrayList<Medium> geladeneListe = null;

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(fileName))) {

            Object obj = ois.readObject();

            geladeneListe = (ArrayList<Medium>) obj;

        } catch (FileNotFoundException e) {
            System.out.println("Fehler: Datei nicht gefunden.");
            return null;
        } catch (IOException e) {
            System.out.println("Fehler beim Lesen der Datei: " + e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("Fehler: Eine der gespeicherten Klassen wurde nicht gefunden.");
            return null;
        }

        Zettelkasten neuerZettelkasten = new Zettelkasten();
        neuerZettelkasten.setMyZettelkasten(geladeneListe);

        System.out.println("model.Zettelkasten erfolgreich von '" + fileName + "' geladen.");
        return neuerZettelkasten;
    }
}
