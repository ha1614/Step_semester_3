package inheritance.class_problems.problem4;

public class EventTicket {
    private final double basePrice;
    private double amountPaid;

    public EventTicket(double basePrice) {
        this.basePrice = basePrice;
    }

    void pay(double amount) {
        amountPaid += amount;
    }

    double getBalanceDue() {
        return basePrice - amountPaid;
    }

    public String printTicket() {
        return "Standard | Balance: " + getBalanceDue();
    }
}
