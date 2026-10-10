package inheritance.assigment_problems.problem1;

public class RaceEntry {
    private final String bibNumber;
    private final double entryFee;
    private double amountPaid;

    public RaceEntry(String bibNumber, double entryFee) {
        if (bibNumber == null || bibNumber.trim().isEmpty() || bibNumber.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid bibNumber: " + bibNumber);
        }
        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
    }

    public String getBibNumber() {
        return bibNumber;
    }

    public double getEntryFee() {
        return entryFee;
    }

    void pay(double amount) {
        amountPaid += amount;
    }

    double getBalanceDue() {
        return entryFee - amountPaid;
    }

    static String registerBatch(String[] bibNumbers, double entryFee) {
        int registered = 0;
        int rejected = 0;
        for (String bib : bibNumbers) {
            try {
                new RaceEntry(bib, entryFee);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }
        return "Registered: " + registered + " | Rejected: " + rejected;
    }
}
