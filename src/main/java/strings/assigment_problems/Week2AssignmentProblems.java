package strings.assigment_problems;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Locale;

public final class Week2AssignmentProblems {
    private static final Set<String> STOP_WORDS = new HashSet<>(
        Arrays.asList("the", "was", "and", "a", "is", "of", "in"));

    private Week2AssignmentProblems() {
    }

    public static void checkPinLength(String pin) {
        if (pin == null || pin.length() != 4) {
            System.out.println("Invalid PIN — must be exactly 4 digits.");
        } else {
            System.out.println("PIN length OK.");
        }
    }

    public static String reverseEachWord(String sentence) {
        if (sentence == null) throw new IllegalArgumentException("Sentence cannot be null");
        String[] words = sentence.split(" ", -1);
        StringBuilder result = new StringBuilder();
        for (int wordIndex = 0; wordIndex < words.length; wordIndex++) {
            if (wordIndex > 0) result.append(' ');
            StringBuilder reversed = new StringBuilder();
            for (int i = words[wordIndex].length() - 1; i >= 0; i--) reversed.append(words[wordIndex].charAt(i));
            result.append(reversed);
        }
        return result.toString();
    }

    public static void parseInventoryRecord(String csvLine) {
        if (csvLine == null) {
            System.out.println("Invalid Record");
            return;
        }
        String[] fields = csvLine.split(",", -1);
        if (fields.length != 3) {
            System.out.println("Invalid Record");
            return;
        }
        System.out.println("Product: " + fields[0].trim() + " | SKU: " + fields[1].trim()
            + " | Qty: " + fields[2].trim());
    }

    public static String normalizeCode(String raw) {
        if (raw == null) throw new IllegalArgumentException("Code cannot be null");
        String trimmed = raw.trim();
        if (trimmed.length() < 3) return trimmed.toUpperCase(Locale.ROOT);
        return trimmed.substring(0, 3).toUpperCase(Locale.ROOT) + trimmed.substring(3);
    }

    public static String validateAndFormat(String code) {
        if (code == null || code.length() != 13) return "Invalid: wrong length";
        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(code.charAt(i))) return "Invalid: publisher code must be 3 letters";
        }
        for (int i = 3; i < code.length(); i++) {
            if (!Character.isDigit(code.charAt(i))) return "Invalid: year and catalog must be digits";
        }
        return new StringBuilder().append('[').append(code, 0, 3).append("] YEAR: ")
            .append(code, 3, 7).append(" | CATALOG: ").append(code, 7, 13).toString();
    }

    public static void printFilteredWordFrequency(String feedback) {
        if (feedback == null) throw new IllegalArgumentException("Feedback cannot be null");
        String cleaned = feedback.toLowerCase().replace(".", "").replace(",", "")
            .replace("!", "").replace("?", "");
        Map<String, Integer> frequencies = new HashMap<>();
        for (String word : cleaned.trim().split("\\s+")) {
            if (!word.isEmpty() && !STOP_WORDS.contains(word)) {
                frequencies.put(word, frequencies.getOrDefault(word, 0) + 1);
            }
        }
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(frequencies.entrySet());
        entries.sort(Comparator.<Map.Entry<String, Integer>>comparingInt(Map.Entry::getValue)
            .reversed().thenComparing(Map.Entry::getKey));
        for (Map.Entry<String, Integer> entry : entries) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        checkPinLength("4820");
        System.out.println(reverseEachWord("hello club"));
        parseInventoryRecord("Wireless Mouse,WM-2201,150");
        System.out.println(validateAndFormat(normalizeCode(" pen2026004251 ")));
        printFilteredWordFrequency("The mentor was great, the session was great and clear.");
    }
}
