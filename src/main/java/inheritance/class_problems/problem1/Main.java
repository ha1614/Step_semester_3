package inheritance.class_problems.problem1;

public class Main {
    public static void main(String[] args) {
        try {
            new EventTicket("ST1", 500);
            System.out.println("construction accepted");
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }

        WorkshopTicket w = new WorkshopTicket("STU2", 1200, "AI/ML");
        w.pay(500);
        System.out.println(w.getBalanceDue()); // 700.0

        System.out.println(EventTicket.registerBatch(new String[]{"STU1", "ST1", "STU2", " ", "STU3"}, 500));
        // Registered: 3 | Rejected: 2
    }
}
