package inheritance.class_problems.problem2;

public class EventTicket {
    private final String attendeeId;
    private final double basePrice;
    private double amountPaid;

    public EventTicket(String attendeeId, double basePrice) {
        if (attendeeId == null || attendeeId.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid attendeeId: " + attendeeId);
        }
        this.attendeeId = attendeeId;
        this.basePrice = basePrice;
    }

    public String getAttendeeId() {
        return attendeeId;
    }

    void pay(double amount) {
        amountPaid += amount;
    }

    double getBalanceDue() {
        return basePrice - amountPaid;
    }

    public String printTicket() {
        return "Standard Event Ticket | Balance Due: " + getBalanceDue();
    }
}
