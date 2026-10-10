package inheritance.class_problems.problem2;

public class TicketFamily {

    static String classifyGeneration(EventTicket ticket) {
        if (ticket instanceof PremiumWorkshopTicket) {
            return "Multilevel descendant (3 generations deep)";
        } else if (ticket instanceof WorkshopTicket) {
            return "Single-inheritance child (2 generations deep)";
        } else if (ticket instanceof HackathonTicket) {
            return "Hierarchical sibling (independent branch)";
        }
        return "Base ticket (1 generation)";
    }

    static double getTotalBalanceDue(EventTicket[] tickets) {
        double total = 0;
        for (EventTicket t : tickets) {
            total += t.getBalanceDue();
        }
        return total;
    }

    public static void main(String[] args) {
        EventTicket standardTicket = new EventTicket("STU1", 500);
        WorkshopTicket workshopTicket = new WorkshopTicket("STU2", 1200, "AI/ML");
        PremiumWorkshopTicket premiumTicket = new PremiumWorkshopTicket("STU3", 2000, "Cloud Native", 300);
        HackathonTicket hackathonTicket = new HackathonTicket("STU4", 800, "Byte Force");

        System.out.println(standardTicket.printTicket());
        System.out.println(workshopTicket.printTicket());
        System.out.println(premiumTicket.printTicket());
        System.out.println(hackathonTicket.printTicket());

        System.out.println(classifyGeneration(premiumTicket));
        System.out.println(classifyGeneration(hackathonTicket));

        System.out.println(getTotalBalanceDue(new EventTicket[]{standardTicket, workshopTicket, premiumTicket, hackathonTicket})); // 4500.0
    }
}
