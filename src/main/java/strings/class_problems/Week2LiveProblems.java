package strings.class_problems;

import java.util.Locale;

public final class Week2LiveProblems {
    private Week2LiveProblems() {
    }

    public static void countVowelsAndConsonants(String text) {
        if (text == null) throw new IllegalArgumentException("Text cannot be null");
        int vowels = 0;
        int consonants = 0;
        for (int i = 0; i < text.length(); i++) {
            char letter = Character.toLowerCase(text.charAt(i));
            if (letter == 'a' || letter == 'e' || letter == 'i' || letter == 'o' || letter == 'u') vowels++;
            else if (letter >= 'a' && letter <= 'z') consonants++;
        }
        System.out.println("Vowels: " + vowels + " | Consonants: " + consonants);
    }

    public static void parseStudentRecord(String csvLine) {
        if (csvLine == null) {
            System.out.println("Invalid Record");
            return;
        }
        String[] fields = csvLine.split(",", -1);
        if (fields.length != 3) {
            System.out.println("Invalid Record");
            return;
        }
        System.out.println("Name: " + fields[0].trim() + " | Roll No: " + fields[1].trim()
            + " | Dept: " + fields[2].trim());
    }

    public static String validateFileExtension(String filename) {
        if (filename == null) return "Rejected - invalid file type";
        int dot = filename.lastIndexOf('.');
        if (dot <= 0 || dot == filename.length() - 1) return "Rejected - invalid file type";
        String extension = filename.substring(dot + 1);
        if (extension.equalsIgnoreCase("pdf") || extension.equalsIgnoreCase("docx")
            || extension.equalsIgnoreCase("zip")) return "Accepted";
        return "Rejected - invalid file type";
    }

    public static String maskPhoneNumber(String phone) {
        if (phone == null || phone.length() != 10) return "Invalid phone number";
        for (int i = 0; i < phone.length(); i++) {
            if (phone.charAt(i) < '0' || phone.charAt(i) > '9') return "Invalid phone number";
        }
        StringBuilder masked = new StringBuilder("XXXXXX");
        masked.append(phone.substring(6));
        masked.insert(6, '-');
        return masked.toString();
    }

    public static String normalizeReference(String raw) {
        if (raw == null) throw new IllegalArgumentException("Reference cannot be null");
        String trimmed = raw.trim();
        if (trimmed.length() < 3) return trimmed.toUpperCase(Locale.ROOT);
        return trimmed.substring(0, 3).toUpperCase(Locale.ROOT) + trimmed.substring(3);
    }

    public static String validateAndFormat(String reference) {
        if (reference == null || reference.length() != 14) return "Invalid: wrong length";
        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(reference.charAt(i))) return "Invalid: bank code must be 3 letters";
        }
        for (int i = 3; i < reference.length(); i++) {
            if (!Character.isDigit(reference.charAt(i))) return "Invalid: date and sequence must be digits";
        }
        StringBuilder display = new StringBuilder();
        display.append('[').append(reference, 0, 3).append("] DATE: ")
            .append(reference, 3, 5).append('/')
            .append(reference, 5, 7).append('/')
            .append(reference, 7, 9).append(" | SEQ: ")
            .append(reference, 9, 14);
        return display.toString();
    }

    public static void main(String[] args) {
        countVowelsAndConsonants("Java Programming");
        parseStudentRecord("Ananya Verma,RA2211003010123,CSE");
        System.out.println(validateFileExtension("Assignment1.PDF"));
        System.out.println(maskPhoneNumber("9876543210"));
        String code = normalizeReference("  hdf03022600042  ");
        System.out.println(validateAndFormat(code));
    }
}
