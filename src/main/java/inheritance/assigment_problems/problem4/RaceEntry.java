package inheritance.assigment_problems.problem4;

public class RaceEntry {
    private final String bibNumber;
    private final double entryFee;
    private double amountPaid;
    private double lateFees;

    public RaceEntry(String bibNumber, double entryFee) {
        if (bibNumber == null || bibNumber.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid bibNumber: " + bibNumber);
        }
        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
    }

    public String getBibNumber() {
        return bibNumber;
    }

    void pay(double amount) {
        amountPaid += amount;
    }

    protected void applyLateFee(double amount) {
        lateFees += amount;
    }

    double getBalanceDue() {
        return entryFee + lateFees - amountPaid;
    }

    public String announce() {
        return "Race Entry | Bib: " + bibNumber + " | Balance: " + getBalanceDue();
    }
}
