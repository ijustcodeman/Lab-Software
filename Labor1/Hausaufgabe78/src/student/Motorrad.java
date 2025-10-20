package student;

/**
 * Die Klasse Motorrad ist eine Unterklasse von Fahrzeug.
 * Ein Motorrad hat ein Baujahr und eine bestimmte Länge.
 * @author Max Gebert, 21513
 */
public class Motorrad extends Fahrzeug {

    /**
     * Länge eines Motorrads in Zoll
     */
    private int laengeInZoll;

    /**
     * Konstruktor zum erstellen eines Motorrads.
     * @param _laengeInZoll Länge des Motorrads in Zoll
     * @param _baujahr Baujahr des Motorrads
     */
    public Motorrad(int _laengeInZoll, int _baujahr){
        this.laengeInZoll = _laengeInZoll;
        super(_baujahr);
    }

    /**
     * Gibt die Länge in Zoll eines Motorrads zurück.
     * @return Länge in Zoll des Motorrads
     */
    public double getLaengeInZoll(){
        return this.laengeInZoll;
    }

    /**
     * Setzt die Länge in Zoll des Motorrads.
     * @param _laengeInZoll Neue Länge in Zoll
     */
    public void setLaengeInZoll(int _laengeInZoll){
        this.laengeInZoll = _laengeInZoll;
    }

    @Override
    void fahre() {
        System.out.println("Antreten");
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("Bj. ");
        sb.append(getBaujahr()).append(", ").append(getLaengeInZoll()).append(" Zoll");
        return sb.toString();
    }
}
