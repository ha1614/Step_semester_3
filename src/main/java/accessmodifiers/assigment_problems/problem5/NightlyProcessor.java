package accessmodifiers.assigment_problems.problem5;

public class NightlyProcessor {

    // One-time shared state set up via a static block.
    private static final String RUN_LABEL;

    static {
        RUN_LABEL = "nightly-circulation";
    }

    // NOTE: because LoanReceipt is final, it and ReferenceOnlyLoanReceipt cannot share
    // a LoanReceipt[] array (see ReferenceOnlyLoanReceipt.java). This method accepts
    // Object[] instead so both variants - and null entries - can be reconciled together,
    // each settled via instanceof, with the batch never crashing on a null.
    public static String processNightlyCirculation(Object[] receipts) {
        int processed = 0;
        int nullSkipped = 0;
        int referenceOnly = 0;
        int regular = 0;

        for (Object entry : receipts) {
            if (entry == null) {
                nullSkipped++;
                continue;
            }
            if (entry instanceof ReferenceOnlyLoanReceipt) {
                referenceOnly++;
                processed++;
            } else if (entry instanceof LoanReceipt) {
                regular++;
                processed++;
            }
        }

        return processed + " processed | " + nullSkipped + " null skipped | "
            + referenceOnly + " reference-only | " + regular + " regular";
    }

    public static void main(String[] args) {
        Object[] receipts = {
            new ReferenceOnlyLoanReceipt("LIB-001", new String[]{"BK-200"}, "Reading Room 3"),
            null,
            new LoanReceipt("LIB-002", new String[]{"BK-201"})
        };
        System.out.println(processNightlyCirculation(receipts));
        // 2 processed | 1 null skipped | 1 reference-only | 1 regular
    }
}
