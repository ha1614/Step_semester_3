package fundamentals.assigment_problems;

import java.util.Locale;

public final class Week1AssignmentProblems {
    private Week1AssignmentProblems() {
    }

    public static void checkDuplicateSeats(int[] seatNumbers) {
        if (seatNumbers == null) throw new IllegalArgumentException("Seat numbers cannot be null");
        boolean found = false;
        for (int i = 0; i < seatNumbers.length; i++) {
            boolean seenEarlier = false;
            for (int k = 0; k < i; k++) if (seatNumbers[k] == seatNumbers[i]) seenEarlier = true;
            if (seenEarlier) continue;
            for (int j = i + 1; j < seatNumbers.length; j++) {
                if (seatNumbers[i] == seatNumbers[j]) {
                    System.out.println("Duplicate Seat Number Found: " + seatNumbers[i]);
                    found = true;
                    break;
                }
            }
        }
        if (!found) System.out.println("No Duplicate Seats Found");
    }

    public static void checkTypingAccuracy(String original, String typed) {
        if (original == null || typed == null || original.length() != typed.length()) {
            throw new IllegalArgumentException("Both passages must be non-null and have equal length");
        }
        if (original.isEmpty()) throw new IllegalArgumentException("Passage cannot be empty");
        int matched = 0;
        int firstMismatch = -1;
        for (int i = 0; i < original.length(); i++) {
            if (original.charAt(i) == typed.charAt(i)) matched++;
            else if (firstMismatch == -1) firstMismatch = i;
        }
        double accuracy = 100.0 * matched / original.length();
        if (firstMismatch == -1) {
            System.out.printf(Locale.ROOT, "Matched: %d/%d | Accuracy: %.2f%% | No Mismatches%n",
                matched, original.length(), accuracy);
        } else {
            System.out.printf(Locale.ROOT,
                "Matched: %d/%d | Accuracy: %.2f%% | First Mismatch at position %d ('%c' vs '%c')%n",
                matched, original.length(), accuracy, firstMismatch + 1,
                original.charAt(firstMismatch), typed.charAt(firstMismatch));
        }
    }

    public static void findLongestStreak(String signalLog) {
        if (signalLog == null) throw new IllegalArgumentException("Signal log cannot be null");
        if (signalLog.isEmpty()) {
            System.out.println("No signal readings");
            return;
        }
        char bestColor = signalLog.charAt(0);
        int bestLength = 1;
        char currentColor = bestColor;
        int currentLength = 1;
        for (int i = 1; i < signalLog.length(); i++) {
            char color = signalLog.charAt(i);
            if (color == currentColor) currentLength++;
            else {
                currentColor = color;
                currentLength = 1;
            }
            if (currentLength > bestLength) {
                bestLength = currentLength;
                bestColor = currentColor;
            }
        }
        System.out.println("Longest Streak: '" + bestColor + "' repeated " + bestLength + " times");
    }

    public static void analyzeInventory(int[] sectionA, int[] sectionB) {
        if (sectionA == null || sectionB == null || sectionA.length != sectionB.length || sectionA.length == 0) {
            throw new IllegalArgumentException("Sections must be non-empty arrays of equal length");
        }
        long totalA = 0;
        long totalB = 0;
        int highest = Integer.MIN_VALUE;
        char highestSection = 'A';
        int highestIndex = 0;
        for (int i = 0; i < sectionA.length; i++) {
            totalA += sectionA[i];
            totalB += sectionB[i];
            if (sectionA[i] > highest) {
                highest = sectionA[i];
                highestSection = 'A';
                highestIndex = i;
            }
            if (sectionB[i] > highest) {
                highest = sectionB[i];
                highestSection = 'B';
                highestIndex = i;
            }
        }
        System.out.println("Section A Total: " + totalA + " | Section B Total: " + totalB
            + " | Status: " + (totalA == totalB ? "Balanced" : "Not Balanced")
            + " | Highest Quantity: " + highest + " (Section " + highestSection
            + ", Item " + (highestIndex + 1) + ")");
    }

    public static void classifyWordLengths(String review) {
        if (review == null) throw new IllegalArgumentException("Review cannot be null");
        int shortCount = 0;
        int mediumCount = 0;
        int longCount = 0;
        String trimmed = review.trim();
        if (!trimmed.isEmpty()) {
            for (String word : trimmed.split("\\s+")) {
                int length = word.length();
                if (length <= 4) shortCount++;
                else if (length <= 8) mediumCount++;
                else longCount++;
            }
        }
        System.out.println("Short: " + shortCount + " | Medium: " + mediumCount + " | Long: " + longCount);
    }

    public static void main(String[] args) {
        checkDuplicateSeats(new int[] {101, 102, 103, 102, 105});
        checkTypingAccuracy("hello world", "hello worlt");
        findLongestStreak("RRGGGYRR");
        analyzeInventory(new int[] {20, 15, 30}, new int[] {25, 10, 30});
        classifyWordLengths("This movie was absolutely fantastic and thrilling");
    }
}
