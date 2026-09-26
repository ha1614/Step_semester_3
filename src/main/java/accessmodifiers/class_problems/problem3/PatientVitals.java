package accessmodifiers.class_problems.problem3;

public final class PatientVitals {
    private static final int MAX_READINGS = 500;
    private final double[] readings = new double[MAX_READINGS];
    private int readingCount;

    public PatientVitals(double[] initialReadings) {
        if (initialReadings != null) {
            for (double reading : initialReadings) recordReading(reading);
        }
    }

    public void recordReading(double reading) {
        if (!Double.isFinite(reading) || reading <= 0 || reading > 45.0 || readingCount == MAX_READINGS) return;
        readings[readingCount++] = reading;
    }

    public double getAverage() {
        if (readingCount == 0) return 0.0;
        double total = 0.0;
        for (int i = 0; i < readingCount; i++) total += readings[i];
        return total / readingCount;
    }

    public double[] getAllReadings() {
        double[] copy = new double[readingCount];
        System.arraycopy(readings, 0, copy, 0, readingCount);
        return copy;
    }

    public static void main(String[] args) {
        PatientVitals vitals = new PatientVitals(new double[] {36.5, -2, 37.1});
        double[] copy = vitals.getAllReadings();
        copy[0] = 999;
        System.out.println(java.util.Arrays.toString(vitals.getAllReadings()));
        System.out.println("Average: " + vitals.getAverage());
    }
}
