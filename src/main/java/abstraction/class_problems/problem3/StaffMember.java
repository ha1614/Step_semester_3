package abstraction.class_problems.problem3;

public abstract class StaffMember {
    private double baseSalary;
    private final double bonusRate;

    public StaffMember(double baseSalary) {
        this(baseSalary, 0.10);
    }

    public StaffMember(double baseSalary, double bonusRate) {
        this.baseSalary = baseSalary;
        this.bonusRate = bonusRate;
    }

    public abstract double calculateBonus();

    public double getSalary() {
        return baseSalary;
    }

    public void setSalary(double baseSalary) {
        if (baseSalary < 0) {
            System.out.println("rejected, salary unchanged");
            return;
        }
        this.baseSalary = baseSalary;
    }

    public double getBonusRate() {
        return bonusRate;
    }
}
