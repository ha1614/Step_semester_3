package inheritance.class_problems.problem5;

public class Main {
    public static void main(String[] args) {
        EventTicket t1 = new EventTicket(500);
        System.out.println(t1.ticketId);                     // TCK-1001
        System.out.println(EventTicket.getTicketsIssued());  // 1

        System.out.println(EventTicket.isValidPromoCode("F123A")); // true
        System.out.println(EventTicket.isValidPromoCode("F12A"));  // false
        System.out.println(EventTicket.isValidPromoCode("X123A")); // false

        t1.pay(200);
        t1.pay(200, "UPI");
        System.out.println(t1.getBalanceDue()); // 100.0

        System.out.println(EventTicket.processNightlySettlement(
                new EventTicket[]{new GroupTicket(2000, 5), null, new EventTicket(500)}));
        // 2 processed | 1 null skipped | 1 group | 1 individual
    }
}
