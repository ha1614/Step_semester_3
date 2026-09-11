package foodapp.assigment_problems;

// The class is locked (final) since its pricing rule shouldn't be subclassed/overridden.
public final class SurgeFeeCalculator {
    // The configured minimum is locked at construction time - it's the field the rule depends on.
    private final double minimumSurgePercent;

    public SurgeFeeCalculator(double minimumSurgePercent) {
        this.minimumSurgePercent = minimumSurgePercent;
    }

    // The rule itself is locked against being overridden - final method.
    public final double calculateSurgeFee(double orderValue, int delayMinutes) {
        if (orderValue < 0) {
            throw new IllegalArgumentException("orderValue cannot be negative");
        }
        if (delayMinutes < 0) {
            throw new IllegalArgumentException("delayMinutes cannot be negative");
        }
        if (delayMinutes == 0) {
            return 0.0; // on-time orders never trigger the floor
        }

        double tieredPercent = 0.0;
        int remaining = delayMinutes;

        int tier1Minutes = Math.min(remaining, 5);          // minutes 1-5 @ 0.5%
        tieredPercent += tier1Minutes * 0.5;
        remaining -= tier1Minutes;

        if (remaining > 0) {
            int tier2Minutes = Math.min(remaining, 10);      // minutes 6-15 (10 minutes) @ 1%
            tieredPercent += tier2Minutes * 1.0;
            remaining -= tier2Minutes;
        }

        if (remaining > 0) {
            tieredPercent += remaining * 2.0;                 // minute 16+ @ 2%
        }

        double tieredFee = orderValue * tieredPercent / 100.0;
        double floorFee = orderValue * minimumSurgePercent / 100.0;

        // The configured rate is a floor, only once genuinely delayed.
        return Math.max(tieredFee, floorFee);
    }

    public static void main(String[] args) {
        SurgeFeeCalculator calc = new SurgeFeeCalculator(1.0);
        System.out.println("Rs " + calc.calculateSurgeFee(500, 0));  // Rs 0.0
        System.out.println("Rs " + calc.calculateSurgeFee(500, 1));  // Rs 5.0
        System.out.println("Rs " + calc.calculateSurgeFee(500, 16)); // Rs 72.5
    }
}
