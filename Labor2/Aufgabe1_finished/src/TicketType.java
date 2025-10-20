/**
 * Repräsentiert die verschiedenen Arten von Tickets, die im Fahrkarten-Automaten verfügbar sind.
 * Jeder Tickettyp besitzt eine Beschreibung, einen Preisfaktor und eine erläuternde Beschreibung.
 * Implementiert das Printable-Interface zur strukturierten Ausgabe der Ticketdetails.
 * @author Max Gebert, 21513
 */
public enum TicketType implements Printable {

    /**
     * Einzelfahrkarte – für eine einfache fahrt.
     */
    SINGLE("Einzelfahrkarte", 1.0, "Für einsame Menschen"),

    /**
     * Tagesfahrkarte – gültig für den ganzen Tag.
     */
    DAYPASS("Tageskarte", 2.5, "Für alle die zur Arbeit wollen"),

    /**
     * Wochenkarte – gültig für die ganze Woche.
     */
    WEEKLY("Wochenkarte", 10.0, "Für alle die Urlaub machen wollen");

    private final String ticketart;
    private final double ticketfaktor;
    private final String ticketbeschreibung;

    /**
     * Privater Konstruktor für die Enum-Konstanten.
     * @param _ticketart Bezeichnung des Tickets
     * @param _ticketfaktor Multiplikator für die Preisberechnung
     * @param _ticketbeschreibung Beschreibung des Tickets
     */
    TicketType(String _ticketart, double _ticketfaktor, String _ticketbeschreibung){
        this.ticketart = _ticketart;
        this.ticketfaktor = _ticketfaktor;
        this.ticketbeschreibung = _ticketbeschreibung;
    }

    /**
     * Gibt die Bezeichnung des Tickets zurück.
     * @return Bezeichnung des Tickets
     */
    public String getTicketart(){
        return this.ticketart;
    }

    /**
     * Gibt den Multiplikator für die Preisberechnung zurück.
     * @return Multiplikator für die Preisberechnung
     */
    public double getTicketfaktor(){
        return this.ticketfaktor;
    }

    /**
     * Gibt die Beschreibung des Tickets zurück.
     * @return Beschreibung des Tickets
     */
    public String getTicketbeschreibung(){
        return this.ticketbeschreibung;
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
     * Gibt die vollständigen Details des Tickets auf der Konsole aus.
     */
    @Override
    public void printDetails() {
        System.out.println(this.name() + " Details:");
        System.out.println("Ticketart: " + getTicketart());
        System.out.println("Preisfaktor: " + getTicketfaktor());
        System.out.println("Beschreibung: " + getTicketbeschreibung());
        System.out.println();
    }
}
