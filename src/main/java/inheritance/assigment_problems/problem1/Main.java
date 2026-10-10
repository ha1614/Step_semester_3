package inheritance.assigment_problems.problem1;

public class Main {
    public static void main(String[] args) {
        try {
            new RaceEntry("B1", 50);
            System.out.println("construction accepted");
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }

        RunnerEntry r = new RunnerEntry("BIB2001", 80, "Open 10K");
        r.pay(30);
        System.out.println(r.getBalanceDue()); // 50.0

        System.out.println(RaceEntry.registerBatch(new String[]{"BIB1", "B1", "BIB2"}, 80));
        // Registered: 2 | Rejected: 1
    }
}
