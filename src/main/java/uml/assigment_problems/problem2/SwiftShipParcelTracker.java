package uml.assigment_problems.problem2;

import java.util.ArrayList;
import java.util.List;

/*
 * Customer 1 ---- 0..* Parcel ; Parcel 1 ---- 1 ShippingType ; Parcel 1 ---- 0..* NotificationChannel
 * ShippingType <<interface>> <|.. StandardShipping, ExpressShipping, FragileShipping
 *   FragileShipping reuses StandardShipping through COMPOSITION (it wraps a StandardShipping and adds
 *   the handling fee) - so a change to the standard rate automatically flows into Fragile.
 * NotificationChannel <<interface>> <|.. SmsChannel, EmailChannel
 * Status transitions live inside Parcel only; outside code can only call advanceTo()/cancel().
 */
interface ShippingType {
    String name();

    double charge(double weightKg);
}

class StandardShipping implements ShippingType {
    public String name() {
        return "Standard";
    }

    public double charge(double weightKg) {
        return 40 + 10 * weightKg;
    }
}

class ExpressShipping implements ShippingType {
    public String name() {
        return "Express";
    }

    public double charge(double weightKg) {
        return 80 + 15 * weightKg;
    }
}

class FragileShipping implements ShippingType {
    private final ShippingType base = new StandardShipping();

    public String name() {
        return "Fragile";
    }

    public double charge(double weightKg) {
        return base.charge(weightKg) + 50;
    }
}

interface NotificationChannel {
    void notify(String message);
}

class SmsChannel implements NotificationChannel {
    public void notify(String message) {
        System.out.println("[SMS] " + message);
    }
}

class EmailChannel implements NotificationChannel {
    public void notify(String message) {
        System.out.println("[Email] " + message);
    }
}

enum ParcelStatus {
    BOOKED, PICKED_UP, IN_TRANSIT, OUT_FOR_DELIVERY, DELIVERED, CANCELLED
}

class Parcel {
    private final String id;
    private final double weightKg;
    private final ShippingType type;
    private final List<NotificationChannel> channels = new ArrayList<>();
    private ParcelStatus status;

    Parcel(String id, double weightKg, ShippingType type) {
        this.id = id;
        this.weightKg = weightKg;
        this.type = type;
    }

    String getId() {
        return id;
    }

    double getCharge() {
        return type.charge(weightKg);
    }

    ShippingType getType() {
        return type;
    }

    double getWeightKg() {
        return weightKg;
    }

    ParcelStatus getStatus() {
        return status;
    }

    void subscribe(NotificationChannel channel) {
        channels.add(channel);
    }

    void markBooked() {
        changeStatus(ParcelStatus.BOOKED);
    }

    boolean advanceTo(ParcelStatus next) {
        boolean valid = status != ParcelStatus.CANCELLED && status != ParcelStatus.DELIVERED
                && next != ParcelStatus.CANCELLED && next.ordinal() == status.ordinal() + 1;
        if (!valid) {
            System.out.println("Invalid transition: " + status + " → " + next + " is not allowed.");
            return false;
        }
        changeStatus(next);
        return true;
    }

    boolean cancel() {
        if (status != ParcelStatus.BOOKED) {
            System.out.println("Cancellation failed: " + id + " can be cancelled only while BOOKED.");
            return false;
        }
        changeStatus(ParcelStatus.CANCELLED);
        return true;
    }

    private void changeStatus(ParcelStatus next) {
        status = next;
        for (NotificationChannel c : channels) {
            c.notify(id + " is now " + status + ".");
        }
    }
}

class Customer {
    private final String name;
    private final List<Parcel> parcels = new ArrayList<>();

    Customer(String name) {
        this.name = name;
    }

    void addParcel(Parcel p) {
        parcels.add(p);
    }
}

class ParcelService {

    Parcel book(Customer customer, String id, double weightKg, ShippingType type, NotificationChannel... channels) {
        Parcel p = new Parcel(id, weightKg, type);
        for (NotificationChannel c : channels) {
            p.subscribe(c);
        }
        customer.addParcel(p);
        System.out.printf("Parcel %s booked (%s, %s kg). Charge: ₹%.2f.%n",
                id, type.name(), formatWeight(weightKg), p.getCharge());
        p.markBooked();
        return p;
    }

    private static String formatWeight(double w) {
        return w == Math.floor(w) ? String.valueOf((long) w) : String.valueOf(w);
    }
}

public class SwiftShipParcelTracker {
    public static void main(String[] args) {
        ParcelService service = new ParcelService();
        Customer customer = new Customer("Asha");

        Parcel p101 = service.book(customer, "P101", 2, new ExpressShipping(), new SmsChannel(), new EmailChannel());
        p101.advanceTo(ParcelStatus.PICKED_UP);
        p101.cancel();
        p101.advanceTo(ParcelStatus.IN_TRANSIT);
        p101.advanceTo(ParcelStatus.DELIVERED);
    }
}
