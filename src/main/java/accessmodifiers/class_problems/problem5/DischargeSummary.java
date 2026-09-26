package accessmodifiers.class_problems.problem5;

public final class DischargeSummary implements DischargeRecord {
    private final String patientId;
    private final String[] medicationCodes;

    public DischargeSummary(String patientId, String[] medicationCodes) {
        if (patientId == null || patientId.trim().isEmpty() || medicationCodes == null
            || medicationCodes.length > 20) {
            throw new IllegalArgumentException("Patient ID and at most 20 medication codes are required");
        }
        this.patientId = patientId.trim();
        this.medicationCodes = medicationCodes.clone();
        for (String code : this.medicationCodes) {
            if (!isValidMedicationCode(code)) {
                throw new IllegalArgumentException("Each medication code must match MED- followed by one uppercase letter");
            }
        }
    }

    private static boolean isValidMedicationCode(String code) {
        return code != null && code.length() == 5 && code.startsWith("MED-")
            && code.charAt(4) >= 'A' && code.charAt(4) <= 'Z';
    }

    @Override
    public String getPatientId() {
        return patientId;
    }

    @Override
    public String[] getMedicationCodes() {
        return medicationCodes.clone();
    }

    public DischargeSummary withCorrectedMedication(int index, String newCode) {
        if (index < 0 || index >= medicationCodes.length) throw new IndexOutOfBoundsException("Medication index");
        String[] corrected = medicationCodes.clone();
        corrected[index] = newCode;
        return new DischargeSummary(patientId, corrected);
    }
}
