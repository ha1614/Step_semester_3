package inheritance.assigment_problems.problem5;

public class Main {
    public static void main(String[] args) {
        System.out.println(RaceEntry.isValidDiscountCode("M123A")); // true
        System.out.println(RaceEntry.isValidDiscountCode("M12A"));  // false
        System.out.println(RaceEntry.isValidDiscountCode("X123A")); // false

        RunnerEntry r = new RunnerEntry("BIB2001", 80, "Open 10K");
        EliteRunnerEntry eliteEntry = new EliteRunnerEntry("BIB3001", 150, "Elite Full Marathon", 500);
        RelayTeamEntry relayEntry = new RelayTeamEntry("BIB4001", 300, 4);
        new RaceEntry("BIB5001", 50);
        try {
            new RaceEntry("B1", 50); // rejected, must not increment counter
        } catch (IllegalArgumentException ignored) {
        }

        r.pay(10, "UPI"); // Paying via UPI
        System.out.println(RaceEntry.settleNight(new RaceEntry[]{eliteEntry, null, relayEntry}));
        // 2 processed | 1 null skipped | 1 relay | 1 individual
        System.out.println(RaceEntry.getBibCounter()); // 4
        System.out.println(r.getEntryCode());          // ENTRY-1001
    }
}
