package accessmodifiers.class_problems.problem1;

public final class PatientRecord {
    private final String patientId;
    protected final String wardCode;
    private final double vitalsScore;
    public final String facilityName;

    public PatientRecord(String patientId, String wardCode, double vitalsScore, String facilityName) {
        if (patientId == null || patientId.trim().length() < 4) {
            throw new IllegalArgumentException("patientId must contain at least four non-space characters");
        }
        if (wardCode == null || wardCode.trim().isEmpty() || !Double.isFinite(vitalsScore)
            || facilityName == null || facilityName.trim().isEmpty()) {
            throw new IllegalArgumentException("Ward, finite vitals, and facility are required");
        }
        this.patientId = patientId.trim();
        this.wardCode = wardCode.trim();
        this.vitalsScore = vitalsScore;
        this.facilityName = facilityName.trim();
    }

    public String getPatientId() {
        return patientId;
    }

    public double getVitalsScore() {
        return vitalsScore;
    }
}
