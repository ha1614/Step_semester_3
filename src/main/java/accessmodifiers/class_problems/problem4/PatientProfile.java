package accessmodifiers.class_problems.problem4;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PatientProfile {
    private String patientId;
    private String name;
    private boolean discharged;
    private String lockerPinDigest;

    public PatientProfile() {
        this((String) null);
    }

    public PatientProfile(String name) {
        this(null, name);
    }

    public PatientProfile(String patientId, String name) {
        this.patientId = clean(patientId);
        this.name = clean(name);
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String id) {
        if (patientId == null) patientId = clean(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = clean(name);
    }

    public boolean isDischarged() {
        return discharged;
    }

    public void setDischarged(boolean discharged) {
        this.discharged = discharged;
    }

    public void setLockerPin(String pin) {
        if (pin == null || pin.length() < 4 || pin.length() > 6) {
            throw new IllegalArgumentException("Locker PIN must contain 4 to 6 digits");
        }
        for (int i = 0; i < pin.length(); i++) {
            if (pin.charAt(i) < '0' || pin.charAt(i) > '9') {
                throw new IllegalArgumentException("Locker PIN must contain only digits");
            }
        }
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256").digest(pin.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder(digest.length * 2);
            for (byte value : digest) hex.append(String.format("%02x", value & 0xff));
            lockerPinDigest = hex.toString();
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 is unavailable", exception);
        }
    }

    private static String clean(String value) {
        if (value == null || value.trim().isEmpty()) return null;
        return value.trim();
    }

    public static void main(String[] args) {
        PatientProfile profile = new PatientProfile("Arjun Iyer");
        profile.setPatientId("MT2026-0142");
        profile.setPatientId("HACKED-0000");
        profile.setLockerPin("4820");
        System.out.println(profile.getPatientId());
    }
}
