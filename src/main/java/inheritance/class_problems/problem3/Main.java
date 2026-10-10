package inheritance.class_problems.problem3;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        WorkshopTicket w = new WorkshopTicket(1200);
        w.pay(1200);
        w.applyLateFee(100);
        System.out.println(w.getBalanceDue()); // 200.0

        double[] history = w.getLateFeeHistory();
        System.out.println(Arrays.toString(history)); // [200.0]
        history[0] = 999;
        System.out.println(Arrays.toString(w.getLateFeeHistory())); // [200.0]
    }
}
