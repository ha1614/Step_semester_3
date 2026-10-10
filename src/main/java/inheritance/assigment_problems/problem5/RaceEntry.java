package inheritance.assigment_problems.problem5;

public class RaceEntry {
    private static int bibCounter = 0;

    private final String entryCode;
    private final String bibNumber;
    private final double entryFee;
    private double amountPaid;

    public RaceEntry(String bibNumber, double entryFee) {
        if (bibNumber == null || bibNumber.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid bibNumber: " + bibNumber);
        }
        bibCounter++;
        this.entryCode = "ENTRY-" + (1000 + bibCounter);
        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
    }

    public String getEntryCode() {
        return entryCode;
    }

    public String getBibNumber() {
        return bibNumber;
    }

    void pay(double amount) {
        amountPaid += amount;
    }

    void pay(double amount, String mode) {
        System.out.println("Paying via " + mode);
        pay(amount);
    }

    double getBalanceDue() {
        return entryFee - amountPaid;
    }

    static boolean isValidDiscountCode(String code) {
        if (code == null || code.length() != 5) {
            return false;
        }
        if (code.charAt(0) != 'M') {
            return false;
        }
        for (int i = 1; i <= 3; i++) {
            if (!Character.isDigit(code.charAt(i))) {
                return false;
            }
        }
        return Character.isUpperCase(code.charAt(4));
    }

    static int getBibCounter() {
        return bibCounter;
    }

    static String settleNight(RaceEntry[] entries) {
        int processed = 0;
        int nullSkipped = 0;
        int relay = 0;
        int individual = 0;
        for (RaceEntry e : entries) {
            if (e == null) {
                nullSkipped++;
                continue;
            }
            processed++;
            if (e instanceof RelayTeamEntry) {
                relay++;
            } else {
                individual++;
            }
        }
        return processed + " processed | " + nullSkipped + " null skipped | " + relay + " relay | " + individual + " individual";
    }
}
