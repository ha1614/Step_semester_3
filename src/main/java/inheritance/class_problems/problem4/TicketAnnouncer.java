package inheritance.class_problems.problem4;

public class TicketAnnouncer {

    static String batchPrint(EventTicket[] tickets) {
        StringBuilder sb = new StringBuilder();
        for (EventTicket t : tickets) {
            sb.append(t.printTicket());
            if (t instanceof WorkshopTicket) {
                WorkshopTicket w = (WorkshopTicket) t;
                sb.append(" [Track via downcast: ").append(w.getTrack()).append("]");
            }
            sb.append(" | ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(batchPrint(new EventTicket[]{new EventTicket(500), new WorkshopTicket(1200, "AI/ML")}));

        EventTicket plain = new EventTicket(500);
        try {
            WorkshopTicket bad = (WorkshopTicket) plain;
            System.out.println(bad.getTrack());
        } catch (ClassCastException e) {
            System.out.println("ClassCastException at runtime");
        }
    }
}
