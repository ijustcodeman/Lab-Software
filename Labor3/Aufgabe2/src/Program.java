/**
 * Hauptklasse zum Testen verschiedenster Testfälle.
 * @author Max Gebert, 21513
 */
public class Program {

    /**
     * Hauptmethode für das Instanziieren und ausgeben auf der Konsole.
     * @param args Wird hier nicht genutzt
     */
    public static void main(String[] args){

        Familie schmidt = new Familie("Robert Schmidt", "Andrea Schmidt");
        schmidt.addKind("Noah Schmidt");
        schmidt.addKind("Alex Schmidt");

        Familie mueller = new Familie("Rene Mueller", "Sandra Mueller");

        // Familie mit Kinder
        for (Familie.Familienmitglied mitglied: Familie.Familienmitglied.values()){
            System.out.println(schmidt.getMitglied(mitglied));
        }
        System.out.println();

        // Familie ohne Kinder
        for (Familie.Familienmitglied mitglied: Familie.Familienmitglied.values()){
            System.out.println(mueller.getMitglied(mitglied));
        }

        // Alleinerziehender Vater mit Kinder
        Familie mustermann = new Familie("Max Mustermann", true);
        mustermann.addKind("Nico Mustermann");
        mustermann.addKind("Lisa Mustermann");

        for (Familie.Familienmitglied mitglied: Familie.Familienmitglied.values()){
            System.out.println(mustermann.getMitglied(mitglied));
        }

        // Eine Frau
        Familie schulz = new Familie("Julie Schulz", false);
        for (Familie.Familienmitglied mitglied: Familie.Familienmitglied.values()){
            System.out.println(schulz.getMitglied(mitglied));
        }
    }
}
