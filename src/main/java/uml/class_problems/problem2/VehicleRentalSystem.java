package uml.class_problems.problem2;

import java.util.ArrayList;
import java.util.List;

/*
 * Vehicle <<abstract>> <|-- StandardCar, LuxuryCar, SUV   (each owns its pricing rule)
 * Customer 1 ---- 0..* Rental ;  Vehicle 1 ---- 0..1 active Rental
 * RentalService 1 ---- 0..* Vehicle, 0..* Rental
 * New categories only add a new Vehicle subclass; RentalService never changes.
 */
abstract class Vehicle {
    private final String name;
    private boolean available = true;

    Vehicle(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    boolean isAvailable() {
        return available;
    }

    void setAvailable(boolean available) {
        this.available = available;
    }

    abstract double calculateCharge(int days);
}

class StandardCar extends Vehicle {
    StandardCar(String name) {
        super(name);
    }

    @Override
    double calculateCharge(int days) {
        return 50.0 * days;
    }
}

class LuxuryCar extends Vehicle {
    LuxuryCar(String name) {
        super(name);
    }

    @Override
    double calculateCharge(int days) {
        return 100.0 * days;
    }
}

class SUV extends Vehicle {
    SUV(String name) {
        super(name);
    }

    @Override
    double calculateCharge(int days) {
        // base 75/day plus a flat 20 cleaning fee
        return 75.0 * days + 20.0;
    }
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

class Rental {
    private final Customer customer;
    private final Vehicle vehicle;
    private final int days;
    private final double charge;
    private boolean active = true;

    Rental(Customer customer, Vehicle vehicle, int days) {
        this.customer = customer;
        this.vehicle = vehicle;
        this.days = days;
        this.charge = vehicle.calculateCharge(days);
    }

    Vehicle getVehicle() {
        return vehicle;
    }

    double getCharge() {
        return charge;
    }

    boolean isActive() {
        return active;
    }

    void close() {
        active = false;
    }
}

class RentalService {
    private final List<Rental> rentals = new ArrayList<>();

    Rental rent(Customer customer, Vehicle vehicle, int days) {
        if (days <= 0) {
            System.out.println("Rental failed: days must be positive.");
            return null;
        }
        if (!vehicle.isAvailable()) {
            System.out.println("Rental failed: " + vehicle.getName() + " already has an active rental.");
            return null;
        }
        Rental rental = new Rental(customer, vehicle, days);
        vehicle.setAvailable(false);
        rentals.add(rental);
        System.out.printf("%s rented for %d days. Total charge: $%.2f%n", vehicle.getName(), days, rental.getCharge());
        return rental;
    }

    void returnVehicle(Vehicle vehicle) {
        for (Rental r : rentals) {
            if (r.isActive() && r.getVehicle() == vehicle) {
                r.close();
                vehicle.setAvailable(true);
                System.out.println(vehicle.getName() + " returned. Now available.");
                return;
            }
        }
        System.out.println("No active rental found for " + vehicle.getName() + ".");
    }
}

public class VehicleRentalSystem {
    public static void main(String[] args) {
        RentalService service = new RentalService();
        Customer customer = new Customer("Ravi");
        Vehicle luxury = new LuxuryCar("Luxury Car A");
        Vehicle standard = new StandardCar("Standard Car B");

        service.rent(customer, luxury, 3);
        service.rent(customer, standard, 5);
        service.rent(customer, luxury, 2);   // double-booking prevented
        service.returnVehicle(luxury);
    }
}
