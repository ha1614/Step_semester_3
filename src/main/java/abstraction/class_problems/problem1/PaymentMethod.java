package abstraction.class_problems.problem1;

public abstract class PaymentMethod {
    private static int txnCounter = 1000;

    private final String transactionId;

    protected PaymentMethod() {
        txnCounter++;
        this.transactionId = "TXN-" + txnCounter;
    }

    public abstract String processPayment(double amount);

    // Overload: same name, different parameter list (compile-time polymorphism)
    String processPayment(double amount, String note) {
        return processPayment(amount) + " (" + note + ")";
    }

    public String getTransactionId() {
        return transactionId;
    }
}
