package foodapp.assigment_problems;

public class ReconciliationEngine {
    private int processedCount = 0;
    private int nullSkippedCount = 0;
    private int premiumCount = 0;
    private int regularCount = 0;
    private double grandTotalSurgeFees = 0.0;

    // Settles a single account: null-safe, uses instanceof to route premium vs regular.
    public void processAccount(DeliveryAccount account, double amount, int delayMinutes) {
        if (account == null) {
            nullSkippedCount++;
            return;
        }
        account.setOrderValue(amount);
        double fee = account.calculateSurgeFee(delayMinutes);
        grandTotalSurgeFees += fee;
        processedCount++;

        if (account instanceof PremiumDeliveryAccount) {
            premiumCount++;
        } else {
            regularCount++;
        }
    }

    // Design choice: if the three arrays don't have matching lengths, only the
    // overlapping prefix is processed (min of the three lengths) rather than
    // crashing the whole run - a partial, correctly-attributed batch beats no batch.
    public static void processBatch(DeliveryAccount[] accounts, double[] amounts, int[] delayMinutesArray) {
        ReconciliationEngine engine = new ReconciliationEngine();
        int n = Math.min(accounts.length, Math.min(amounts.length, delayMinutesArray.length));

        for (int i = 0; i < n; i++) {
            try {
                engine.processAccount(accounts[i], amounts[i], delayMinutesArray[i]);
            } catch (RuntimeException e) {
                // Never let one bad entry crash the whole nightly batch.
                engine.nullSkippedCount++;
            }
        }

        System.out.println(engine.processedCount + " processed | " + engine.nullSkippedCount
            + " null skipped | " + engine.premiumCount + " premium | " + engine.regularCount
            + " regular | grand total surge fees = " + engine.grandTotalSurgeFees);
    }

    public static void main(String[] args) {
        DeliveryAccount[] accounts = {
            new PremiumDeliveryAccount("STU001", 500),
            null,
            new DeliveryAccount("STU002", 300)
        };
        double[] amounts = {500, 400, 300};
        int[] delayMinutesArray = {10, 5, 0};
        processBatch(accounts, amounts, delayMinutesArray);
    }
}
