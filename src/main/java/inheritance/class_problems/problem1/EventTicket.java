package inheritance.class_problems.problem1;

public class EventTicket {
    private final String attendeeId;
    private final double basePrice;
    private double amountPaid;

    public EventTicket(String attendeeId, double basePrice) {
        if (attendeeId == null || attendeeId.trim().isEmpty() || attendeeId.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid attendeeId: " + attendeeId);
        }
        this.attendeeId = attendeeId;
        this.basePrice = basePrice;
    }

    public String getAttendeeId() {
        return attendeeId;
    }

    public double getBasePrice() {
        return basePrice;
    }

    void pay(double amount) {
        amountPaid += amount;
    }

    double getBalanceDue() {
        return basePrice - amountPaid;
    }

    static String registerBatch(String[] attendeeIds, double basePrice) {
        int registered = 0;
        int rejected = 0;
        for (String id : attendeeIds) {
            try {
                new EventTicket(id, basePrice);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }
        return "Registered: " + registered + " | Rejected: " + rejected;
    }
}
