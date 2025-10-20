import java.io.*;
import java.util.Scanner;

/**
 * Das Main-Programm liest eine Textdatei Byte für Byte ein und gibt deren Inhalt als Zeichen auf der Konsole aus.
 * Der Benutzer wird zur Eingabe des Dateinamens aufgefordert.
 * Die Datei wird auf Existenz geprüft, bevor sie eingelesen wird.
 * @author Max Gebert, 21513
 */
public class Main {

    /**
     * Wandelt ein Zeichen in seinen Ganzzahlwert (Unicode-Wert) um.
     * @param c Das umzuwandelnde Zeichen
     * @return Der Unicode-Wert des Zeichens als int
     */
    public static int decodeChar(char c) {
        return (int) c;
    }

    /**
     * Wandelt einen Ganzzahlwert in das entsprechende Zeichen um.
     * @param i Der zu kodierende Ganzzahlwert
     * @return Das Zeichen, das dem Ganzzahlwert entspricht
     */
    public static char encodeChar(int i) {
        return (char) i;
    }

    /**
     * Der Einstiegspunkt des Programms. Fragt den Benutzer nach dem Dateinamen,
     * prüft, ob die Datei existiert, liest sie dann byteweise ein und gibt die Zeichen aus.
     *
     * @param args Kommandozeilenargumente (werden hier nicht verwendet)
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Bitte Dateinamen eingeben: ");
        String filename = scanner.nextLine();
        scanner.close();

        File file = new File(filename);


        // Hinweis: Die Existenzprüfung wurde entfernt, da die Dateiöffnung per Exception behandelt wird.

        /*
        if (!file.exists()) {
            System.out.println("Fehler: Die Datei \"" + filename + "\" existiert nicht.");
            return;
        }
         */

        try (FileInputStream inputStream = new FileInputStream(file)) {
            int byteRead;
            while ((byteRead = inputStream.read()) != -1) {
                char c = encodeChar(byteRead);
                System.out.print(c);
            }
        } catch (IOException e) {
            System.out.println("Fehler beim Lesen der Datei: " + e.getMessage());
        }
    }
}
