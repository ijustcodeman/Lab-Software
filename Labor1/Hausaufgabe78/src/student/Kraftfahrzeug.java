package student;

/**
 * Die Klasse Kraftfahrzeug ist eine Unterklasse von Fahreug.
 * @author Max Gebert, 21513
 */
public class Kraftfahrzeug extends Fahrzeug {

    /**
     * Modell eines Kraftfahrzeugs
     */
    private final String modell;

    /**
     * Verbrauch pro Kilometer eines Kraftfahrzeugs
     */
    private double verbrauchProKilometer;

    /**
     * Konstruktor um ein Kraftfahrzeug zu erstellen.
     * @param _modell Modell des Kraftfahrzeugs
     * @param _verbrauchProKilometer Verbrauch pro kilometer des Kraftfahrzeugs
     * @param _baujahr Baujahr des Kraftfahrzeugs
     */
    public Kraftfahrzeug(String _modell, double _verbrauchProKilometer, int _baujahr){
        this.modell = _modell;
        this.verbrauchProKilometer = _verbrauchProKilometer;
        super(_baujahr);
    }

    /**
     * Gibt das Modell des Kraftfahrzeugs zurück.
     * @return Modell des Kraftfahrzeugs
     */
    public String getModell(){
        return this.modell;
    }

    /**
     * Methode, um den Verbrauch eines Kraftfahrzeugs bei einer bestimmten Strecke zu berechnen.
     * @param _kilometer Gefahrene Kilometer
     * @return Gesamtverbrauch
     */
    public double verbrauch(int _kilometer){
        return verbrauchProKilometer * _kilometer;
    }

    @Override
    void fahre() {
        System.out.println("Gas geben");
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("Bj. ");
        sb.append(getBaujahr()).append(", ").append(getModell()).append(", ").append(verbrauchProKilometer).append(" l/km");
        return sb.toString();
    }

}

