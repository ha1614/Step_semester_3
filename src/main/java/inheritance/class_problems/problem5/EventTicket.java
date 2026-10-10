package inheritance.class_problems.problem5;

public class EventTicket {
    private static int ticketCounter = 1000;
    private static int ticketsIssued = 0;

    public final String ticketId;
    private final double basePrice;
    private double amountPaid;

    public EventTicket(double basePrice) {
        ticketCounter++;
        ticketsIssued++;
        this.ticketId = "TCK-" + ticketCounter;
        this.basePrice = basePrice;
    }

    void pay(double amount) {
        amountPaid += amount;
    }

    void pay(double amount, String mode) {
        System.out.println("Paying via " + mode);
        pay(amount);
    }

    double getBalanceDue() {
        return basePrice - amountPaid;
    }

    static boolean isValidPromoCode(String code) {
        if (code == null || code.length() != 5) {
            return false;
        }
        if (code.charAt(0) != 'F') {
            return false;
        }
        for (int i = 1; i <= 3; i++) {
            if (!Character.isDigit(code.charAt(i))) {
                return false;
            }
        }
        return Character.isUpperCase(code.charAt(4));
    }

    static int getTicketsIssued() {
        return ticketsIssued;
    }

    static String processNightlySettlement(EventTicket[] tickets) {
        int processed = 0;
        int nullSkipped = 0;
        int group = 0;
        int individual = 0;
        for (EventTicket t : tickets) {
            if (t == null) {
                nullSkipped++;
                continue;
            }
            processed++;
            if (t instanceof GroupTicket) {
                group++;
            } else {
                individual++;
            }
        }
        return processed + " processed | " + nullSkipped + " null skipped | " + group + " group | " + individual + " individual";
    }
}
