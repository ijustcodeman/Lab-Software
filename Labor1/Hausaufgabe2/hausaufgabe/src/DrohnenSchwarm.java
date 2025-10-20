import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse DrohnenSchwarm simuliert die Bewegungs- und Betriebsdaten eines Schwarms
 * von Drohnen über eine definierte Simulationsdauer. Dabei werden zufällig generierte Positionsdaten
 * (X, Y, Z), Batteriestände und Statusinformationen für jede Drohne pro Zeitstempel gespeichert.
 * @author Max Gebert, 21513
 */
public class DrohnenSchwarm {

    /**
     * Kerndatenstruktur für einen Drohnenschwarm
     */
    private double[][] daten;

    /**
     * Anzahl der Drohnen für Simulation
     */
    private int anzahlDrohnen;

    /**
     * Simulationsdauer für den Drohnenschwarm
     */
    private int simulationsDauer;

    // 0 public int drohnenID;
    // 1 public int zeitstempel;
    // 2 public int xKoordinate;
    // 3 public int yKoordinate;
    // 4 public int zKoordinate;
    // 5 public int batteriestand;
    // 6 public int statusCode;

    /**
     * Gibt die generierten Daten zurück
     * @return generierte Simulationsdaten des Drohnenschwarms
     */
    public double[][] getDaten(){
        return this.daten;
    }

    /**
     * Diese Methode generiert Simulationsdaten für einen Drohnenschwarm.
     * @param _anzahlDrohnen Anzahl der Drohnen die Simuliert werden
     * @param _simulationsdauer Dauer der Simulation
     */
    public void generiereSimulationsdaten(int _anzahlDrohnen, int _simulationsdauer) {

        this.anzahlDrohnen = _anzahlDrohnen;
        this.simulationsDauer = _simulationsdauer;

        daten = new double[_anzahlDrohnen * _simulationsdauer][7];

        int index = 0; // Laufender Index für das 2d-Array

        for (int drohneID = 1; drohneID <= _anzahlDrohnen; drohneID++){
            double batterie = Math.random() * 100; // Starte mit zufälligen Batteriestand (0-100%)

            for (int zeit = 0; zeit < _simulationsdauer; zeit++){

                // alle andere Daten setzen
                daten[index][0] = drohneID;
                daten[index][1] = zeit;
                daten[index][2] = Math.random() * 1000;
                daten[index][3] = Math.random() * 1000;
                daten[index][4] = Math.random() * 1000;

                // simulierter Batteriestand pro Zeiteinheit
                batterie -= 0.1 + Math.random() * 0.4;
                daten[index][5] = batterie;

                // Zufälliger Statuscode (1-3)
                daten[index][6] = (int) (Math.random() * 3) + 1;

                index++;
            }
        }
    }

    /**
     * Analysiert die Positionen aller Drohnen zu jedem Zeitstempel und identifiziert potenzielle Kollisionen,
     * bei denen der Abstand zwischen zwei Drohnen kleiner als der angegebene kritische Abstand ist.
     * @param _kritischerAbstand Der minimale Sicherheitsabstand, unterhalb dessen eine Kollision angenommen wird
     * @return Eine Liste von Meldungen über erkannte Kollisionen
     */
    public List<String> findeKollisionen(double _kritischerAbstand) {
        List<String> kollisionen = new ArrayList<>();

        // Iteriere über alle Zeitstempel
        for (int zeit = 0; zeit < simulationsDauer; zeit++) {
            // Hole alle Datenzeilen für den aktuellen Zeitstempel
            List<double[]> drohnenZuZeit = new ArrayList<>();

            // Filtere alle Drohnendaten für den aktuellen Zeitstempel
            for (int i = 0; i < daten.length; i++) {
                if ((int) daten[i][1] == zeit) {
                    drohnenZuZeit.add(daten[i]);
                }
            }

            // Vergleiche jedes Paar von Drohnen (ohne Duplikate)
            for (int i = 0; i < drohnenZuZeit.size(); i++) {
                double[] d1 = drohnenZuZeit.get(i);
                double x1 = d1[2];
                double y1 = d1[3];
                double z1 = d1[4];
                int id1 = (int) d1[0];

                for (int j = i + 1; j < drohnenZuZeit.size(); j++) {
                    double[] d2 = drohnenZuZeit.get(j);
                    double x2 = d2[2];
                    double y2 = d2[3];
                    double z2 = d2[4];
                    int id2 = (int) d2[0];

                    // Berechne euklidischen Abstand
                    double abstand = Math.sqrt(
                            Math.pow(x2 - x1, 2) +
                                    Math.pow(y2 - y1, 2) +
                                    Math.pow(z2 - z1, 2)
                    );

                    // Wenn Abstand unter krtitischen Wert, als Kollision melden
                    if (abstand < _kritischerAbstand) {
                        String meldung = String.format(
                                "Kollision zwischen Drohne %d und Drohne %d bei Zeitstempel %d (Abstand: %.2f)",
                                id1, id2, zeit, abstand
                        );
                        kollisionen.add(meldung);
                    }
                }
            }
        }

        return kollisionen;
    }

    /**
     * Identifiziert alle Drohnen, deren Batteriestand am Ende der Simulation unter einem
     * angegebenen Schwellenwert liegt.
     * @param _schwellenwert Der Batterie-Schwellenwert in Prozent, unter dem eine Warnung ausgegeben wird
     * @return Eine Liste von Meldungen zu Drohnen mit niedrigem Batteriestand
     */
    public List<String> meldeNiedrigeBatterie(double _schwellenwert) {
        List<String> meldungen = new ArrayList<>();

        // Array für letzte bekannte Daten jeder Drohne
        double[][] letzteDaten = new double[anzahlDrohnen][7];

        // Suche für jede Drohne den letzten Eintrag
        for (double[] eintrag : daten) {
            int drohnenID = (int) eintrag[0]; // IDs beginnen bei 1
            letzteDaten[drohnenID - 1] = eintrag; // Überschreibt vorherige Werte, sodass am Ende der letzte bleibt
        }

        // Überprüfe die Batterie aller Drohnen anhand des letzten Eintrags
        for (int i = 0; i < anzahlDrohnen; i++) {
            double[] datenEintrag = letzteDaten[i];
            double batterie = datenEintrag[5];

            if (batterie < _schwellenwert) {
                int drohneID = (int) datenEintrag[0];
                String meldung = String.format(
                        "Drohne %d hat niedrigen Batteriestand (%.2f%%) bei Position (X: %.2f, Y: %.2f, Z: %.2f)",
                        drohneID, batterie, datenEintrag[2], datenEintrag[3], datenEintrag[4]
                );
                meldungen.add(meldung);
            }
        }

        return meldungen;
    }

    /**
     * Berechnet den geometrischen Mittelpunkt (Schwerpunkt) des gesamten Drohnenschwarms
     * für jeden Zeitstempel.
     * @return Ein Array mit den Schwerpunkt-Koordinaten (X, Y, Z) für jeden Zeitstempel der Simulation
     */
    public double[][] berechneSchwarmSchwerpunkt() {
        double[][] schwerpunktDaten = new double[simulationsDauer][3]; // [zeit][X,Y,Z]

        for (int zeit = 0; zeit < simulationsDauer; zeit++) {
            double sumX = 0, sumY = 0, sumZ = 0;
            int count = 0;

            // Summe der Koordinaten aller Drohnen zu diesem Zeitstempel
            for (double[] eintrag : daten) {
                if ((int) eintrag[1] == zeit) {
                    sumX += eintrag[2];
                    sumY += eintrag[3];
                    sumZ += eintrag[4];
                    count++;
                }
            }

            // Berechne Durchschnittsposition
            if (count > 0) {
                schwerpunktDaten[zeit][0] = sumX / count;
                schwerpunktDaten[zeit][1] = sumY / count;
                schwerpunktDaten[zeit][2] = sumZ / count;
            }
        }

        return schwerpunktDaten;
    }

    /**
     * Erstellt einen zusammenfassenden Bericht der Simulation und gibt ihn auf der Konsole aus.
     */
    public void erstelleBericht() {

        // 1. Kollisionen
        System.out.println("KOLLISIONEN:");
        List<String> kollisionen = findeKollisionen(100); // Beispiel: 100 Meter als kritischer Abstand
        if (kollisionen.isEmpty()) {
            System.out.println("Keine Kollisionen erkannt.");
        } else {
            for (String meldung : kollisionen) {
                System.out.println("- " + meldung);
            }
        }

        System.out.println();

        // 2. Batterieanalyse
        System.out.println("DROHNEN MIT NIEDRIGEM BATTERIESTAND (< 20%):");
        List<String> niedrigeBatterien = meldeNiedrigeBatterie(20); // Beispiel: 20% Schwelle
        if (niedrigeBatterien.isEmpty()) {
            System.out.println("Alle Drohnen haben ausreichend Batteriestand.");
        } else {
            for (String meldung : niedrigeBatterien) {
                System.out.println("- " + meldung);
            }
        }

        System.out.println();

        // 3. Schwarm-Schwerpunkt
        System.out.println("SCHWARM-SCHWERPUNKTE:");
        double[][] schwerpunkte = berechneSchwarmSchwerpunkt();
        for (int zeit = 0; zeit < schwerpunkte.length; zeit++) {
            System.out.printf("Zeit %2d: X=%.2f, Y=%.2f, Z=%.2f\n",
                    zeit, schwerpunkte[zeit][0], schwerpunkte[zeit][1], schwerpunkte[zeit][2]);
        }
    }
}
