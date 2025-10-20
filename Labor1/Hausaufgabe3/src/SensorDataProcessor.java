import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SensorDataProcessor {

    private static final int NUM_SENSORS = 50000;
    private static final int DATA_POINTS_PER_RUN = 1000;

    public void processSensorData() {
        for (int i = 0; i < DATA_POINTS_PER_RUN; i++) {
            List<String> dataChunk = new ArrayList<>();
            for (int j = 0; j < NUM_SENSORS; j++) {
                dataChunk.add(new UUID(0L, j).toString());
            }
            // dataChunk wird in der nächsten Zeile verarbeitet
            analyzeAndStore(dataChunk);
        }
        // ... weiterer Code ...
    }
    private void analyzeAndStore(List<String> data) {
        // Diese Methode simuliert die Verarbeitung und Speicherung der Daten
        // In dieser Methode werden keine Referenzen auf die übergebenen Daten
        // gespeichert, die über den Methodenaufruf hinausgehen
        System.out.println("Verarbeite Daten-Chunk mit " + data.size() + " Elementen.");
    }

    public static void main(String[] args) {
        SensorDataProcessor processor = new SensorDataProcessor();
        processor.processSensorData();

        // Was passiert mit den Objekten, die innerhalb von processSensorData erstellt wurden?
        System.gc(); // Freiwilliger Aufruf des Garbage Collectors
    }
}
