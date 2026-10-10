package inheritance.class_problems.problem4;

public class WorkshopTicket extends EventTicket {
    private final String track;

    public WorkshopTicket(double basePrice, String track) {
        super(basePrice);
        this.track = track;
    }

    public String getTrack() {
        return track;
    }

    @Override
    public String printTicket() {
        return "Workshop | Track: " + track + " | Balance: " + getBalanceDue();
    }
}
