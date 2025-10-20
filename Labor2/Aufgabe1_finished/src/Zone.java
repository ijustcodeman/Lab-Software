import java.util.Locale;

/**
 * Repräsentiert die verfügbaren Tarifzonen, die bei der Preisberechnung für Tickets verwendet werden.
 * Jede Zone besitzt eine festgelegte Grundgebühr, die mit dem Preisfaktor eines TicketType multipliziert wird.
 * Implementiert das Printable-Interface zur strukturierten Ausgabe der Zonendetails.
 * @author Max Gebert, 21513
 */
public enum Zone implements Printable {

    /**
     * Zone für den Innenstadtbereich mit einer Gebühr von 2.00 €.
     */
    INNER_CITY(2.0),

    /**
     * Zone für die äußeren Stadtbereiche mit einer Gebühr von 3.00 €.
     */
    OUTER_CITY(3.0),

    /**
     * Zone für den Regionalverkehr mit einer Gebühr von 5.00 €.
     */
    REGIONAL(5.0);

    private final double gebuehr;

    /**
     * Konstruktor zur Initialisierung der Zone mit einer bestimmten Gebühr.
     * @param _gebuehr Die Grundgebühr der Zone
     */
    Zone(double _gebuehr){
        this.gebuehr = _gebuehr;
    }

    /**
     * Gibt die Grundgebühr der Zone zurück.
     * @return Grundgebühr der Zone
     */
    public double getGebuehr(){
        return this.gebuehr;
    }

    /**
     * Gibt den Namen der Enum-Konstante zurück.
     * @return Name der Enum-Konstante
     */
    @Override
    public String toString(){
        return name();
    }

    /**
     * Gibt die vollständigen Details der Zone auf der Konsole aus.
     */
    @Override
    public void printDetails() {
        System.out.println("Gebühr für " + this.name() + " beträgt" + String.format(Locale.US, " %.2f€", getGebuehr()));
    }
}
