package inheritance.assigment_problems.problem3;

import java.util.Arrays;

public class RaceEntry {
    private static final int MAX_LATE_FEES = 10;

    private final String bibNumber;
    private final double entryFee;
    private double amountPaid;
    private double lateFees;
    private final double[] lateFeeHistory = new double[MAX_LATE_FEES];
    private int lateFeeCount;

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
        return entryFee + lateFees - amountPaid;
    }

    protected void applyLateFee(double amount) {
        if (lateFeeCount >= MAX_LATE_FEES) {
            throw new IllegalStateException("Late fee history is full");
        }
        lateFees += amount;
        lateFeeHistory[lateFeeCount++] = amount;
    }

    double[] getLateFeeHistory() {
        return Arrays.copyOf(lateFeeHistory, lateFeeCount);
    }
}
