package constructors.class_problems;

public final class BoardingPenaltyCalculator {
    private final double minimumPenaltyPercent;

    public BoardingPenaltyCalculator(double minimumPenaltyPercent) {
        if (!Double.isFinite(minimumPenaltyPercent) || minimumPenaltyPercent < 0) {
            throw new IllegalArgumentException("Minimum penalty percent must be non-negative");
        }
        this.minimumPenaltyPercent = minimumPenaltyPercent;
    }

    public final double calculatePenalty(double ticketFare, int minutesLate) {
        if (!Double.isFinite(ticketFare) || ticketFare < 0 || minutesLate < 0) {
            throw new IllegalArgumentException("Fare and delay cannot be negative or non-finite");
        }
        if (minutesLate == 0) return 0.0;

        int firstTierMinutes = Math.min(minutesLate, 5);
        int secondTierMinutes = Math.min(Math.max(minutesLate - 5, 0), 10);
        int thirdTierMinutes = Math.max(minutesLate - 15, 0);
        double tierPercent = firstTierMinutes * 0.5 + secondTierMinutes * 1.0 + thirdTierMinutes * 2.0;
        double tieredPenalty = ticketFare * tierPercent / 100.0;
        double floorPenalty = ticketFare * minimumPenaltyPercent / 100.0;
        return Math.max(tieredPenalty, floorPenalty);
    }

    public static void main(String[] args) {
        BoardingPenaltyCalculator calculator = new BoardingPenaltyCalculator(1.0);
        System.out.println("Rs " + calculator.calculatePenalty(1000, 0));
        System.out.println("Rs " + calculator.calculatePenalty(1000, 1));
        System.out.println("Rs " + calculator.calculatePenalty(1000, 16));
    }
}
