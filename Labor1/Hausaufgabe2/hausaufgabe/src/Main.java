public class Main {

    public static void main(String[] args){


        // Performance Messung
        DrohnenSchwarm test = new DrohnenSchwarm();
        test.generiereSimulationsdaten(50, 1000);

        long startTime = System.currentTimeMillis();

        test.findeKollisionen(50.0);

        long endTime = System.currentTimeMillis();
        long dauer = endTime - startTime;

        System.out.println("Ausführungszeit: " + dauer + " ms");

        // Testbericht
        DrohnenSchwarm testBericht = new DrohnenSchwarm();
        testBericht.generiereSimulationsdaten(10, 1000);
        testBericht.erstelleBericht();
    }
}
