package abstraction.class_problems.problem5;

public class LibraryCheckout {

    static void processCheckouts(LibraryItem[] items) {
        for (LibraryItem item : items) {
            System.out.println(item.getItemId() + " " + item.getTitle() + ": " + item.getLoanPeriodDays() + " days");
        }
    }

    static String reserveIfSupported(Object o) {
        if (o instanceof Reservable) {
            return ((Reservable) o).reserve();
        }
        return "Reservation not supported";
    }

    public static void main(String[] args) {
        Textbook t = new Textbook("Java Fundamentals");
        System.out.println(t.getLoanPeriodDays());
        System.out.println(t.renew());
        System.out.println(t.reserve());

        Magazine m = new Magazine("Tech Monthly");
        System.out.println(reserveIfSupported(m));

        DigitalPass d = new DigitalPass("E-Journal Access");
        System.out.println(reserveIfSupported(d));

        LibraryItem ref = t; // upcasting: Textbook stored as its parent type
        System.out.println(reserveIfSupported(ref));

        processCheckouts(new LibraryItem[]{t, m});
    }
}
