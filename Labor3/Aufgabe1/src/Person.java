import java.io.Serializable;

/**
 * Diese Klasse repräsentiert eine Person mit Name und Adresse.
 * @author Max Gebert, 21513
 */
public class Person implements Serializable{

    /**
     * Name der Person
     */
    private String name;

    /**
     * Adresse der Person
     */
    private Adresse adresse;

    /**
     * Gibt den Namen der Person zurück.
     * @return Name der Person
     */
    public String getName(){
        return name;
    }

    /**
     * Setzt den Namen der Person.
     * @param name Name der gesetzt werden soll
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Gibt die Adresse der Person zurück.
     * @return Adresse der Person
     */
    public Adresse getAdresse(){
        return adresse;
    }

    /**
     * Setzt die Adresse der Person.
     * @param adresse Die gesetzte Adresse
     */
    public void setAdresse(Adresse adresse){
        this.adresse = adresse;
    }

    /**
     * Lesbare Ausgabe von Name und Adresse.
     * @return Lesbare Ausgabe als String
     */
    public String toString(){
        return new StringBuilder()
                .append(name).append(", ").append(adresse.toString())
                .toString();
    }
}