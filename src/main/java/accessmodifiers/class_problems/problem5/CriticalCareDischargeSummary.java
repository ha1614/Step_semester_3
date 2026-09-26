package accessmodifiers.class_problems.problem5;

public final class CriticalCareDischargeSummary implements DischargeRecord {
    private final DischargeSummary summary;
    private final int icuDays;

    public CriticalCareDischargeSummary(String patientId, String[] medicationCodes, int icuDays) {
        if (icuDays < 0) throw new IllegalArgumentException("ICU days cannot be negative");
        this.summary = new DischargeSummary(patientId, medicationCodes);
        this.icuDays = icuDays;
    }

    @Override
    public String getPatientId() {
        return summary.getPatientId();
    }

    @Override
    public String[] getMedicationCodes() {
        return summary.getMedicationCodes();
    }

    public int getIcuDays() {
        return icuDays;
    }
}
