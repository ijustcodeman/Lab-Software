import java.io.*;
import java.util.ArrayList;

/**
 * Diese Klasse testet das De-/und Serialisieren von Objekten.
 * @author Max Gebert
 */
public class Program
{
    /**
     * Methode zum testen.
     * @param args Wird nicht genutzt
     */
    public static void main(String[] args)
    {
        Adresse adresse = new Adresse();
        adresse.setStrasse("Ringstr. 1");
        adresse.setOrt("Musterstadt");
        Person hugo = new Person();
        hugo.setName("Hugo Schmidt");
        hugo.setAdresse(adresse);
        Person erika = new Person();
        erika.setName("Erika Schmidt");
        erika.setAdresse(adresse);

        // Erstellte Liste mit Hugo und Erika
        ArrayList<Person> personen = new ArrayList<>();
        personen.add(hugo);
        personen.add(erika);

        // Serialisieren
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("persons.txt"))){
            oos.writeObject(personen);
        } catch (IOException e){
            System.out.println("Objekte konnten nicht Serialisiert werden.");
        }

        // Deserialisierung
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("persons.txt"))){
            ArrayList<Person> gelesenePersonen = (ArrayList<Person>) ois.readObject();
        } catch (IOException | ClassNotFoundException e){
            System.out.println("Objekte konnten nicht Deserialisiert werden.");
        }

        // Test, ob Adresse tatsächlich nur einmal deserialisiert wurde
        if (personen.get(0).getAdresse() == personen.get(1).getAdresse()){
            System.out.println("Referenz ist gleich.");
        }
        else{
            System.out.println("Referenz ist unterschiedlich.");
        }
    }
}
