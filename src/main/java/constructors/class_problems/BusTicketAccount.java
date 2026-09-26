package constructors.class_problems;

public class BusTicketAccount {
    private static final String CURRENCY_LABEL;
    static {
        CURRENCY_LABEL = "Rs";
    }

    private final String bookingId;
    private double ticketFare;

    public BusTicketAccount(String bookingId, double ticketFare) {
        if (bookingId == null || bookingId.trim().isEmpty() || !Double.isFinite(ticketFare) || ticketFare < 0) {
            throw new IllegalArgumentException("Booking ID and non-negative fare are required");
        }
        this.bookingId = bookingId;
        this.ticketFare = ticketFare;
    }

    public BusTicketAccount(String bookingId) {
        this(bookingId, 0.0);
    }

    public final double calculatePenalty(int minutesLate) {
        return new BoardingPenaltyCalculator(1.0).calculatePenalty(ticketFare, minutesLate);
    }

    void setTicketFare(double amount) {
        if (!Double.isFinite(amount) || amount < 0) throw new IllegalArgumentException("Fare must be non-negative");
        ticketFare = amount;
    }

    double getTicketFare() {
        return ticketFare;
    }

    String getBookingId() {
        return bookingId;
    }

    static String getCurrencyLabel() {
        return CURRENCY_LABEL;
    }

    public static final class SleeperCoachAccount extends BusTicketAccount {
        public SleeperCoachAccount(String bookingId, double ticketFare) {
            super(bookingId, ticketFare);
        }

        public SleeperCoachAccount(String bookingId) {
            super(bookingId);
        }

        double settlementPenalty(int minutesLate) {
            return calculatePenalty(minutesLate) + getTicketFare() * 0.02;
        }
    }
}
