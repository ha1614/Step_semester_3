package inheritance.class_problems.problem3;

import java.util.Arrays;

public class EventTicket {
    private static final int MAX_LATE_FEES = 10;

    private final double basePrice;
    private double amountPaid;
    private double lateFees;
    private final double[] lateFeeHistory = new double[MAX_LATE_FEES];
    private int lateFeeCount;

    public EventTicket(double basePrice) {
        this.basePrice = basePrice;
    }

    void pay(double amount) {
        amountPaid += amount;
    }

    double getBalanceDue() {
        return basePrice + lateFees - amountPaid;
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
