package abstraction.class_problems.problem1;

public class CheckoutHandler {

    static void printConfirmation(PaymentMethod payment, double amount) {
        System.out.println(payment.processPayment(amount));
    }

    static void testUpcasting(CreditCardPayment cc) {
        PaymentMethod ref = cc; // upcasting: a CreditCardPayment reference stored as its parent type
        printConfirmation(ref, 250.0);
    }

    public static void main(String[] args) {
        // new PaymentMethod(); // does not compile: PaymentMethod is abstract
        CreditCardPayment cc = new CreditCardPayment("4471");
        System.out.println(cc.processPayment(250.0));

        CashPayment cash = new CashPayment();
        System.out.println(cash.processPayment(40.0));

        System.out.println(cc.processPayment(250.0, "Birthday gift"));

        testUpcasting(cc);
    }
}
