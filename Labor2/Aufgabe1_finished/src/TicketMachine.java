/**
 * Diese Klasse simuliert eine einfache Ticketmaschine.
 * @author Max Gebert, 21513
 */
public class TicketMachine {

    /**
     * Startet die Ticketmaschine.
     * Führt alle verfügbaren Ausgaben und Beispielaktionen durch.
     * @param args Kommandozeilenargumente (werden in dieser Anwendung nicht verwendet)
     */
    public static void main(String[] args){

        TicketUtils.printAllTickets();
        TicketUtils.findCheapestTicket();

        TicketType type = TicketType.SINGLE;
        type.printDetails();

        Zone zone = Zone.INNER_CITY;
        zone.printDetails();

    }
}
