package student;

/**
 * Die Abstrakte Klasse Fahrzeug stellt ein allgemeines Fahrzeug da.
 * Ein Fahrzeug hat ein Baujahr und kann (hoffentlich) fahren.
 * @author Max Gebert, 21513
 */

abstract class Fahrzeug {

    /**
     * Baujahr eines Fahrzeugs
     */
    private int baujahr;

    /**
     * Konstruktor zum Erstellen eines Fahrzeugs mit Baujahr.
     * @param _baujahr Baujahr des Fahrzeugs
     */
    public Fahrzeug(int _baujahr){
        this.baujahr = _baujahr;
    }

    /**
     * Gibt das Baujahr des Fahrzeugs zurück.
     * @return Baujahr des Fahrzeugs
     */
    public int getBaujahr(){
        return this.baujahr;
    }

    /**
     * Ein Fahrzeug kann fahren.
     */
    abstract void fahre();

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("Bj. ");
        sb.append(getBaujahr());
        return sb.toString();
    }
}
