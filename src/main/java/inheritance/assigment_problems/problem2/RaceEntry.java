package inheritance.assigment_problems.problem2;

public class RaceEntry {
    private final String bibNumber;
    private final double entryFee;
    private double amountPaid;

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

    double getBalanceDue() {
        return entryFee - amountPaid;
    }

    public String announce() {
        return "Race Entry | Bib: " + bibNumber + " | Balance: " + getBalanceDue();
    }
}
