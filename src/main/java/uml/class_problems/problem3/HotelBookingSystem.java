package uml.class_problems.problem3;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/*
 * Room 1 ---- 0..* Reservation ; Customer 1 ---- 0..* Reservation
 * Room 1 ---- 1 PricingStrategy (StandardPricing, DeluxePricing, SuitePricing)
 * BookingManager 1 ---- 1..* Room, 0..* Reservation
 * Overlap rule: [s1, e1) overlaps [s2, e2) when s1 < e2 && s2 < e1 (check-out day is free).
 * Cancellation allowed only up to CANCELLATION_DAYS before check-in.
 */
interface PricingStrategy {
    double pricePerNight();
}

class StandardPricing implements PricingStrategy {
    public double pricePerNight() {
        return 150.0;
    }
}

class DeluxePricing implements PricingStrategy {
    public double pricePerNight() {
        return 200.0;
    }
}

class SuitePricing implements PricingStrategy {
    public double pricePerNight() {
        return 350.0;
    }
}

enum ReservationStatus {
    ACTIVE, CANCELLED
}

class Customer {
    private final String name;

    Customer(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }
}

class Room {
    private final String name;
    private final PricingStrategy pricing;
    private final List<Reservation> reservations = new ArrayList<>();

    Room(String name, PricingStrategy pricing) {
        this.name = name;
        this.pricing = pricing;
    }

    String getName() {
        return name;
    }

    boolean isAvailable(LocalDate from, LocalDate to) {
        for (Reservation r : reservations) {
            if (r.getStatus() == ReservationStatus.ACTIVE
                    && from.isBefore(r.getTo()) && r.getFrom().isBefore(to)) {
                return false;
            }
        }
        return true;
    }

    double priceFor(LocalDate from, LocalDate to) {
        return ChronoUnit.DAYS.between(from, to) * pricing.pricePerNight();
    }

    void addReservation(Reservation r) {
        reservations.add(r);
    }
}

class Reservation {
    private final Room room;
    private final Customer customer;
    private final LocalDate from;
    private final LocalDate to;
    private final double amount;
    private ReservationStatus status = ReservationStatus.ACTIVE;

    Reservation(Room room, Customer customer, LocalDate from, LocalDate to) {
        this.room = room;
        this.customer = customer;
        this.from = from;
        this.to = to;
        this.amount = room.priceFor(from, to);
    }

    Room getRoom() {
        return room;
    }

    LocalDate getFrom() {
        return from;
    }

    LocalDate getTo() {
        return to;
    }

    double getAmount() {
        return amount;
    }

    ReservationStatus getStatus() {
        return status;
    }

    void cancel() {
        status = ReservationStatus.CANCELLED;
    }
}

class BookingManager {
    static final int CANCELLATION_DAYS = 2;

    Reservation book(Customer customer, Room room, LocalDate from, LocalDate to) {
        if (!from.isBefore(to)) {
            System.out.println("Booking failed: invalid date range.");
            return null;
        }
        if (!room.isAvailable(from, to)) {
            System.out.println("Booking failed: " + room.getName() + " is not available for " + from + " to " + to + ".");
            return null;
        }
        Reservation r = new Reservation(room, customer, from, to);
        room.addReservation(r);
        System.out.printf("%s booked from %s to %s. Total price: $%.2f%n", room.getName(), from, to, r.getAmount());
        return r;
    }

    boolean cancel(Reservation r, LocalDate today) {
        if (r.getStatus() == ReservationStatus.CANCELLED) {
            System.out.println("Reservation already cancelled.");
            return false;
        }
        LocalDate deadline = r.getFrom().minusDays(CANCELLATION_DAYS);
        if (today.isAfter(deadline)) {
            System.out.println("Cancellation failed: deadline " + deadline + " has passed.");
            return false;
        }
        r.cancel();
        System.out.println("Reservation for " + r.getRoom().getName() + " cancelled successfully.");
        return true;
    }
}

public class HotelBookingSystem {
    public static void main(String[] args) {
        BookingManager manager = new BookingManager();
        Customer c = new Customer("Meera");
        Room deluxe = new Room("Deluxe Room 101", new DeluxePricing());
        Room standard = new Room("Standard Room 205", new StandardPricing());

        Reservation r1 = manager.book(c, deluxe, LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 5));
        manager.book(c, standard, LocalDate.of(2024, 12, 3), LocalDate.of(2024, 12, 7));
        manager.book(c, deluxe, LocalDate.of(2024, 12, 3), LocalDate.of(2024, 12, 7));
        manager.cancel(r1, LocalDate.of(2024, 11, 25));
    }
}
