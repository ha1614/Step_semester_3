package accessmodifiers.class_problems.problem5;

public final class NightlyProcessor {
    private static final String PROCESSOR_LABEL;
    static {
        PROCESSOR_LABEL = "Nightly discharge processor";
    }

    private NightlyProcessor() {
    }

    public static String processNightlyBatch(DischargeRecord[] summaries) {
        if (summaries == null) throw new IllegalArgumentException("Batch cannot be null");
        int processed = 0;
        int nullSkipped = 0;
        int criticalCare = 0;
        int routine = 0;
        for (DischargeRecord summary : summaries) {
            if (summary == null) {
                nullSkipped++;
                continue;
            }
            processed++;
            if (summary instanceof CriticalCareDischargeSummary) criticalCare++;
            else routine++;
        }
        return processed + " processed | " + nullSkipped + " null skipped | " + criticalCare
            + " critical-care | " + routine + " routine";
    }

    // DischargeSummary is required to be final, so a critical-care subtype cannot extend it.
    // The common DischargeRecord interface allows both immutable implementations in one batch.
    public static String processNightlyBatch(DischargeSummary[] summaries) {
        if (summaries == null) throw new IllegalArgumentException("Batch cannot be null");
        DischargeRecord[] records = new DischargeRecord[summaries.length];
        System.arraycopy(summaries, 0, records, 0, summaries.length);
        return processNightlyBatch(records);
    }

    public static void main(String[] args) {
        DischargeRecord[] batch = {
            new CriticalCareDischargeSummary("MT001", new String[] {"MED-X"}, 4),
            null,
            new DischargeSummary("MT002", new String[] {"MED-Y"})
        };
        System.out.println(PROCESSOR_LABEL + ": " + processNightlyBatch(batch));
    }
}
