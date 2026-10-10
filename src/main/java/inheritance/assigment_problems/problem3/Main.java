package inheritance.assigment_problems.problem3;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        RunnerEntry r = new RunnerEntry("BIB2001", 80, "Open 10K");
        r.pay(30);
        r.applyLateFee(20);
        System.out.println(r.getBalanceDue()); // 90.0

        double[] history = r.getLateFeeHistory();
        System.out.println(Arrays.toString(history)); // [40.0]
        history[0] = 999;
        System.out.println(Arrays.toString(r.getLateFeeHistory())); // [40.0]
    }
}
