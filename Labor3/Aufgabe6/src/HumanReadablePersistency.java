import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Klasse zum Speichern und Laden von Zettelkasten objekten in menschenlesbarer Form.
 */
public class HumanReadablePersistency implements Persistency{

    @Override
    public void save(Zettelkasten _zk, String _dateiname) {

        if (_zk == null || _dateiname == null || _dateiname.isEmpty()){
            throw new IllegalArgumentException("Bitte gültige Parameter übergeben.");
        }

        String fileName = _dateiname + ".txt";

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
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {

            for (Medium medium : _zk) {
                String humanReadable = medium.calculateRepresentation();
                writer.write(humanReadable);
                writer.newLine();
                writer.newLine();
            }
            System.out.println("Daten wurden erfolgreich gespeichert.");
        } catch (IOException e) {
            System.out.println("Fehler beim Schreiben der Datei: " + e.getMessage());
        }
    }

    @Override
    public void load(String _dateiname) {
        throw new UnsupportedOperationException();
    }
}
