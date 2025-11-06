/**
 * Die Klasse ISBNUtils enthält statische Hilfsmethoden zur Validierung von ISBN-Nummern.
 */
public class ISBNUtils {

    /**
     * Methode zum Prüfen, ob eine 10-stellige ISBN valide ist.
     * @param _isbn ISBN vom Buch
     * @return true wenn die ISBN Valide ist, false wenn nicht
     */
    public static boolean checkISBN10(int[] _isbn) {
        int sum = 0;
        for (int i = 1; i <= _isbn.length; i++) {
            sum += i * _isbn[i - 1];
        }
        if (sum % 11 == 0) {
            return true;
        } else {

            return false;
        }
    }

    /**
     * Methode zum prüfen, ob eine 13-stellige ISBN valide ist.
     * @param _isbn ISBN vom Buch.
     * @return true wenn die ISBN Valide ist, false wenn nicht
     */
    public static boolean checkISBN13(int[] _isbn) {
        int sum = 0;
        for (int i = 1; i < _isbn.length; i++) {
            if (i % 2 == 0) {
                sum += _isbn[i - 1] * 3;
            } else {
                sum += _isbn[i - 1];
            }
        }

        int lastDigit = sum % 10;

        int check = (10 - lastDigit) % 10;

        if (_isbn[_isbn.length - 1] == check) {
            return true;
        } else {
            return false;
        }
    }
}
