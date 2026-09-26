package constructors.class_problems;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public final class FareSplitter {
    private final String tripId;
    private final long totalCents;
    private final int passengerCount;

    public FareSplitter(String tripId, double totalFare, int passengerCount) {
        if (tripId == null || tripId.trim().isEmpty() || !Double.isFinite(totalFare)
            || totalFare < 0 || passengerCount <= 0) {
            throw new IllegalArgumentException("Trip, non-negative fare, and positive passenger count are required");
        }
        this.tripId = tripId;
        this.totalCents = BigDecimal.valueOf(totalFare).setScale(2, RoundingMode.HALF_UP)
            .movePointRight(2).longValueExact();
        this.passengerCount = passengerCount;
    }

    public FareSplitter(String tripId, double totalFare) {
        this(tripId, totalFare, 2);
    }

    public FareSplitter(String tripId) {
        this(tripId, 0.0, 2);
    }

    public double[] fareBreakdown() {
        long each = totalCents / passengerCount;
        long remainder = totalCents % passengerCount;
        double[] shares = new double[passengerCount];
        for (int i = 0; i < passengerCount; i++) {
            long share = each + (i == passengerCount - 1 ? remainder : 0);
            shares[i] = BigDecimal.valueOf(share, 2).doubleValue();
        }
        return shares;
    }

    public boolean isConfirmationOverdue(int confirmed, int expected) {
        if (confirmed < 0 || expected < 0) throw new IllegalArgumentException("Counts cannot be negative");
        return confirmed < expected;
    }

    public String getTripId() {
        return tripId;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new FareSplitter("TRIP001", 100000, 3).fareBreakdown()));
        System.out.println(Arrays.toString(new FareSplitter("TRIP003").fareBreakdown()));
    }
}
