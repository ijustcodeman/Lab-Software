import java.io.*;

/**
 * Klasse zum Speichern und Laden von Zettelkasten objekten in binärer Form.
 */
public class BinaryPersistency implements Persistency{

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

            for (Medium medium : _zk){
                oos.writeObject(medium);
            }
            oos.close();
            System.out.println("Daten wurden erfolgreich gespeichert.");
        } catch (Exception e){
            System.out.println("Fehler beim schreiben der Datei: " + e.getMessage());
        }
    }

    @Override
    public void load(String _dateiname) {

    }
}
