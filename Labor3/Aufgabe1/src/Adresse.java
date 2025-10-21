import java.io.Serializable;

/**
 * Diese Klasse Repräsentiert eine Adresse mit Strasse und Ort.
 * @author Max Gebert, 21513
 */
public class Adresse implements Serializable{

    /**
     * Strasse der Adresse
     */
    private String strasse;

    /**
     * Ort der Adresse
     */
    private String ort;

    /**
     * Gibt die Strasse der Adresse zurück.
     * @return Strasse der Adresse
     */
    public String getStrasse(){
        return this.strasse;
    }

    /**
     * Setzt die Strasse der Adresse.
     * @param strasse Gesetzte Strasse der Adresse
     */
    public void setStrasse(String strasse){
        this.strasse = strasse;
    }

    /**
     * Gibt den Ort der Adresse zurück.
     * @return Ort der Adresse
     */
    public String getOrt(){
        return ort;
    }

    /**
     * Setzt den Ort der Adresse.
     * @param ort Gesetzte Ort der Adresse
     */
    public void setOrt(String ort){
        this.ort = ort;
    }

    /**
     * Lesbare Ausgabe von Strasse und Ort
     * @return Lesbare Ausgabe als String
     */
    public String toString(){
        return new StringBuilder().append(strasse).append(", ")
                .append(ort).toString();
    }
}
