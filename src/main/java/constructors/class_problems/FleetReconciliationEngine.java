package constructors.class_problems;

public final class FleetReconciliationEngine {
    private int processed;
    private int nullSkipped;
    private int sleeperCount;
    private int regularCount;
    private double totalPenalties;

    public void processAccount(BusTicketAccount account, double amount, int minutesLate) {
        if (account == null) {
            nullSkipped++;
            return;
        }
        account.setTicketFare(amount);
        double penalty;
        if (account instanceof BusTicketAccount.SleeperCoachAccount) {
            penalty = ((BusTicketAccount.SleeperCoachAccount) account).settlementPenalty(minutesLate);
            sleeperCount++;
        } else {
            penalty = account.calculatePenalty(minutesLate);
            regularCount++;
        }
        processed++;
        totalPenalties += penalty;
        System.out.println(account.getBookingId() + " penalty: " + BusTicketAccount.getCurrencyLabel() + " " + penalty);
    }

    public static void processBatch(BusTicketAccount[] accounts, double[] amounts, int[] minutesLateArray) {
        if (accounts == null || amounts == null || minutesLateArray == null
            || accounts.length != amounts.length || accounts.length != minutesLateArray.length) {
            throw new IllegalArgumentException("Parallel batch arrays must be non-null and have matching lengths");
        }
        for (int i = 0; i < accounts.length; i++) {
            if (!Double.isFinite(amounts[i]) || amounts[i] < 0 || minutesLateArray[i] < 0) {
                throw new IllegalArgumentException("Batch fares and delays must be non-negative");
            }
        }
        FleetReconciliationEngine engine = new FleetReconciliationEngine();
        for (int i = 0; i < accounts.length; i++) {
            engine.processAccount(accounts[i], amounts[i], minutesLateArray[i]);
        }
        System.out.println(engine.processed + " processed | " + engine.nullSkipped
            + " null skipped | " + engine.sleeperCount + " sleeper | " + engine.regularCount
            + " regular | grand total penalties = " + engine.totalPenalties);
    }

    public static void main(String[] args) {
        BusTicketAccount[] accounts = {
            new BusTicketAccount.SleeperCoachAccount("BK001", 2000), null,
            new BusTicketAccount("BK002", 1200)
        };
        processBatch(accounts, new double[] {1200, 900, 700}, new int[] {10, 5, 0});
    }
}
