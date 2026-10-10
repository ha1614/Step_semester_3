package inheritance.assigment_problems.problem4;

public class AnnouncerBoard {

    static String announceAll(RaceEntry[] entries) {
        StringBuilder sb = new StringBuilder();
        for (RaceEntry e : entries) {
            sb.append(e.announce());
            if (e instanceof RelayTeamEntry) {
                RelayTeamEntry relay = (RelayTeamEntry) e;
                sb.append(" [Team size via downcast: ").append(relay.getTeamSize()).append("]");
            }
            sb.append(" | ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        RunnerEntry runnerEntry = new RunnerEntry("BIB2001", 80, "Open 10K");
        runnerEntry.pay(30);
        runnerEntry.applyLateFee(20); // balance 90.0
        RelayTeamEntry relayEntry = new RelayTeamEntry("BIB4001", 300, 4);

        RaceEntry[] fleet = {runnerEntry, relayEntry};
        System.out.println(announceAll(fleet));

        RaceEntry plain = new RaceEntry("BIB5001", 50);
        try {
            RelayTeamEntry bad = (RelayTeamEntry) plain;
            System.out.println(bad.getTeamSize());
        } catch (ClassCastException ex) {
            System.out.println("ClassCastException at runtime");
        }
    }
}
