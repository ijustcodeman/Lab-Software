import java.util.ArrayList;
import java.util.Locale;

/**
 * Dienstklasse mit Hilfsmethoden zur Preisberechnung und Ausgabe von Ticketinformationen.
 * @author Max Gebert, 21513
 */
public class TicketUtils {

    /**
     * Berechnet den Preis eines Tickets auf Basis des übergebenen Tickettyps und der Zone.
     * @param type Der Tickettyp
     * @param zone Die Tarifzone
     * @return Der berechnete Ticketpreis
     */
    public static double calculatePrice(TicketType type, Zone zone){
        return zone.getGebuehr() * type.getTicketfaktor();
    }

    /**
     * Gibt alle möglichen Kombinationen aus Tickettypen und Zonen zusammen mit den berechneten Preisen auf der Konsole aus.
     */
    public static void printAllTickets(){
        System.out.println("Verfügbare Tickets: ");
        for (TicketType type: TicketType.values()){
            for (Zone zone: Zone.values()){
                System.out.println(type + " in " + zone + ": " + String.format(Locale.US, "%.2f", TicketUtils.calculatePrice(type, zone)) + "€");
            }
        }
    }

    /**
     * Ermittelt die günstigste Ticket-Zonen-Kombination basierend auf dem berechneten Preis
     * und gibt diese Kombination auf der Konsole aus.
     */
    public static void findCheapestTicket(){
        ArrayList<TicketType> ticketart = new ArrayList<>();
        ticketart.add(TicketType.values()[0]);

        ArrayList<Zone> zonenart = new ArrayList<>();
        zonenart.add(Zone.values()[0]);

        ArrayList<Double> lowestPrice = new ArrayList<>();
        lowestPrice.add(calculatePrice(TicketType.values()[0], Zone.values()[0]));

        for (TicketType type: TicketType.values()){
            for (Zone zone: Zone.values()){
                if (lowestPrice.getFirst() > calculatePrice(type, zone)){
                    ticketart.removeFirst();
                    zonenart.removeFirst();
                    lowestPrice.removeFirst();

                    ticketart.add(type);
                    zonenart.add(zone);
                    lowestPrice.add(calculatePrice(type, zone));
                }
            }
        }
        System.out.println("Günstigstes Ticket: " + ticketart.getFirst() + " in " + zonenart.getFirst() + " (" + String.format(Locale.US, "%.2f",lowestPrice.getFirst()) + "€)");
    }
}
