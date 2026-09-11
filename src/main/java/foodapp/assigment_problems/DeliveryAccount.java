package foodapp.assigment_problems;

public class DeliveryAccount {
    // One-time, class-level state set up via a static block.
    private static int totalAccountsCreated;

    static {
        totalAccountsCreated = 0;
    }

    protected String studentId;
    protected double orderValue;

    public DeliveryAccount(String studentId, double orderValue) {
        this.studentId = studentId;
        this.orderValue = orderValue;
        totalAccountsCreated++;
    }

    // Provisional constructor - chains to the full one.
    public DeliveryAccount(String studentId) {
        this(studentId, 0.0);
    }

    // Reuses Problem 4's SurgeFeeCalculator, with a 1% minimum floor. Kept final as required.
    public final double calculateSurgeFee(int delayMinutes) {
        SurgeFeeCalculator calculator = new SurgeFeeCalculator(1.0);
        return calculator.calculateSurgeFee(orderValue, delayMinutes);
    }

    public String getStudentId() {
        return studentId;
    }

    public void setOrderValue(double orderValue) {
        this.orderValue = orderValue;
    }

    public static int getTotalAccountsCreated() {
        return totalAccountsCreated;
    }
}
