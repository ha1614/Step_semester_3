package inheritance.class_problems.problem2;

public class HackathonTicket extends EventTicket {
    private final String teamName;

    public HackathonTicket(String attendeeId, double basePrice, String teamName) {
        super(attendeeId, basePrice);
        this.teamName = teamName;
    }

    public String getTeamName() {
        return teamName;
    }

    @Override
    public String printTicket() {
        return "Hackathon Ticket | Team: " + teamName + " | Balance Due: " + getBalanceDue();
    }
}
