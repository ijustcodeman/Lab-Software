package student;

/**
 * Hauptklasse mit verschiedensten Testcases.
 * @author Max Gebert, 21513
 */
public class Main {

    /**
     * Hauptmethode.
     * @param args Keine Verwendung
     */
    public static void main(String[] args){

        /* Testcases für Präsenzaufgaben
        student.Kraftfahrzeug[] autoArr = new student.Kraftfahrzeug[2];
        //Der Focus verbraucht 6,5 Liter auf 100 km:
        autoArr[0] = new student.Kraftfahrzeug("Focus", 0.065);
        //Der Tesla verbraucht 0 Liter auf 100 km:
        autoArr[1] = new student.Kraftfahrzeug ("Tesla", 0.00);
        int km = 400;
        System.out.printf("Verbrauch auf %d km:%n", km);

        for (int i = 0; i < autoArr.length; i++) {
            System.out.printf("%s: %.0f Liter %n",
                    autoArr[i].getModell(),
                    autoArr[i].verbrauch(km));
        }

        student.Fahrzeug[] fahrzeugArr = new student.Fahrzeug[2];
        fahrzeugArr[0] = new student.Kraftfahrzeug ("Golf", 0.065);
        fahrzeugArr[1] = new student.Motorrad();
        for (int j = 0; j < 2; j++)
        {
            fahrzeugArr[j].fahre();
        }
         */

        // Testcases für 7. Hausaufgabe
        Fahrzeug golf = new Kraftfahrzeug("Golf", 0.065, 1995);
        Motorrad gurke = new Motorrad(280, 1974);
        Kraftfahrzeug audiA4 = new Kraftfahrzeug("Audi A4", 100, 2015);
        System.out.println(golf);
        System.out.println(gurke);
        System.out.println(audiA4);
    }
}
