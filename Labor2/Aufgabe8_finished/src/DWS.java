import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Das Programm analysiert historische Schlusskurse eines Aktienfonds aus einer CSV-Datei.
 * Es vergleicht den ersten und letzten Schlusskurs eines bestimmten Jahres und gibt aus,
 * ob der Kurs gestiegen ("bull") oder gefallen ("bear") ist.
 * @author Max Gebert, 21513
 */
public class DWS {

    /**
     * Hauptmethode zum Starten der Analyse.
     *
     * @param args nicht verwendet
     */
    public static void main(String[] args) {

        // Voraussetzungen:
        // CSV-Datei ist im Format: "Datum;...;Schlusskurs;..."
        // Datumsformat ist "dd.MM.yyyy"
        // "Schlusskurs" befindet sich in einer Spaltenüberschrift (Zeile 7, also Index 6)
        // Der Datenteil beginnt direkt nach der Kopfzeile

        String csvName = "DWS-TOP-DIVIDENDE.csv";
        int suchjahr = 2018;
        ArrayList<String[]> daten = new ArrayList<>();

        try (BufferedReader in = new BufferedReader(new FileReader(csvName))) {
            String line;

            while ((line = in.readLine()) != null) {
                String[] values = line.split(";");
                daten.add(values);
            }

            if (daten.isEmpty()) {
                System.err.println("CSV-Datei ist leer.");
                return;
            }

            // Spalte für Schlusskurs suchen
            String suchendeSpalte = "Schlusskurs";
            int spalte = -1;
            int startZeile = 0;

            for (int i = 0; i < daten.size(); i++) {
                String[] row = daten.get(i);
                for (int j = 0; j < row.length; j++) {
                    if (row[j].equalsIgnoreCase(suchendeSpalte)) {
                        spalte = j;
                        startZeile = i + 1;
                        break;
                    }
                }
                if (spalte != -1) break;
            }

            if (spalte == -1) {
                System.err.println("Spalte 'Schlusskurs' nicht gefunden.");
                return;
            }

            // Alle Kurszeilen des angegebenen Jahres sammeln
            ArrayList<String[]> zeilenImJahr = new ArrayList<>();

            for (int i = startZeile; i < daten.size(); i++) {
                String[] row = daten.get(i);
                if (row.length <= spalte) continue;

                String datum = row[0];

                if (datum.length() >= 10) {
                    try {
                        String jahrStr = datum.substring(6, 10); // "dd.mm.yyyy"
                        int jahr = Integer.parseInt(jahrStr);
                        if (jahr == suchjahr) {
                            zeilenImJahr.add(row);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Ungültiges Jahr in Zeile " + (i + 1) + ": " + datum);
                    } catch (StringIndexOutOfBoundsException e) {
                        System.err.println("Datumsformat fehlerhaft in Zeile " + (i + 1));
                    }
                }
            }

            if (zeilenImJahr.isEmpty()) {
                System.out.println("Keine Daten für das Jahr " + suchjahr + " gefunden.");
                return;
            }

            // Ersten und letzten Kurs extrahieren
            String letzterKursStr = zeilenImJahr.get(0)[spalte].replace(",", ".");
            String ersterKursStr = zeilenImJahr.get(zeilenImJahr.size() - 1)[spalte].replace(",", ".");

            double letzterKurs = Double.parseDouble(letzterKursStr);
            double ersterKurs = Double.parseDouble(ersterKursStr);

            System.out.printf("Erster Kurs %d: %.2f%n", suchjahr, ersterKurs);
            System.out.printf("Letzter Kurs %d: %.2f%n", suchjahr, letzterKurs);

            if (letzterKurs > ersterKurs) {
                System.out.println("bull");
            } else {
                System.out.println("bear");
            }

        } catch (IOException e) {
            System.err.println("Fehler beim Lesen der Datei: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Fehler beim Parsen eines Kurswerts: " + e.getMessage());
        }
    }
}


