package abstraction.assigment_problems.problem3;

public abstract class ServiceableVehicle {
    private double mileage;

    public ServiceableVehicle() {
        this.mileage = 0;
    }

    public abstract String performMaintenance();

    double getMileage() {
        return mileage;
    }

    void addMileage(double km) {
        if (km < 0) {
            System.out.println("Rejected: distance cannot be negative");
            return;
        }
        mileage += km;
    }
}
