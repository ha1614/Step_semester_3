package fundamentals.class_problems;

import java.util.Locale;
import java.util.Random;

public final class Week1LiveProblems {
    private Week1LiveProblems() {
    }

    public static String playRound(String playerMove, String computerMove) {
        String player = normalizeMove(playerMove);
        String computer = normalizeMove(computerMove);
        if (player.equals(computer)) {
            return "Draw";
        }
        boolean playerWins = (player.equals("Rock") && computer.equals("Scissors"))
            || (player.equals("Paper") && computer.equals("Rock"))
            || (player.equals("Scissors") && computer.equals("Paper"));
        return playerWins ? "Player Wins" : "Computer Wins";
    }

    private static String normalizeMove(String move) {
        if (move == null) {
            throw new IllegalArgumentException("Move cannot be null");
        }
        String normalized = move.trim().toLowerCase(Locale.ROOT);
        if (normalized.equals("rock")) return "Rock";
        if (normalized.equals("paper")) return "Paper";
        if (normalized.equals("scissors")) return "Scissors";
        throw new IllegalArgumentException("Move must be Rock, Paper, or Scissors");
    }

    public static void playGame(String[] playerMoves, Random random) {
        if (playerMoves == null || random == null) {
            throw new IllegalArgumentException("Moves and random generator are required");
        }
        String[] moves = {"Rock", "Paper", "Scissors"};
        int wins = 0;
        int losses = 0;
        int draws = 0;
        System.out.println("Round | Player Move | Computer Move | Result");
        for (int i = 0; i < playerMoves.length; i++) {
            String computer = moves[random.nextInt(moves.length)];
            String result = playRound(playerMoves[i], computer);
            if (result.equals("Player Wins")) wins++;
            else if (result.equals("Computer Wins")) losses++;
            else draws++;
            System.out.printf("%d | %s | %s | %s%n", i + 1, normalizeMove(playerMoves[i]), computer, result);
        }
        double winPercent = playerMoves.length == 0 ? 0.0 : 100.0 * wins / playerMoves.length;
        System.out.printf(Locale.ROOT, "Wins: %d | Losses: %d | Draws: %d | Win %% = %.1f%%%n",
            wins, losses, draws, winPercent);
    }

    public static boolean isPalindromeIterative(String text) {
        requireText(text);
        int left = 0;
        int right = text.length() - 1;
        while (left < right) {
            if (text.charAt(left++) != text.charAt(right--)) return false;
        }
        return true;
    }

    public static boolean isPalindromeRecursive(String text) {
        requireText(text);
        return isPalindromeRecursive(text, 0, text.length() - 1);
    }

    private static boolean isPalindromeRecursive(String text, int left, int right) {
        if (left >= right) return true;
        return text.charAt(left) == text.charAt(right)
            && isPalindromeRecursive(text, left + 1, right - 1);
    }

    public static boolean isPalindromeArrayReversal(String text) {
        requireText(text);
        char[] chars = text.toCharArray();
        for (int left = 0, right = chars.length - 1; left < right; left++, right--) {
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
        }
        return text.equals(new String(chars));
    }

    public static String getBmiStatus(double bmi) {
        if (!Double.isFinite(bmi) || bmi < 0) throw new IllegalArgumentException("BMI must be non-negative");
        if (bmi < 18.5) return "Underweight";
        if (bmi < 25.0) return "Normal";
        if (bmi < 30.0) return "Overweight";
        return "Obese";
    }

    public static void printWellnessReport(double[] heights, double[] weights) {
        if (heights == null || weights == null || heights.length != weights.length) {
            throw new IllegalArgumentException("Height and weight arrays must have matching lengths");
        }
        System.out.println("Person | Height (m) | Weight (kg) | BMI | Status");
        for (int i = 0; i < heights.length; i++) {
            if (!Double.isFinite(heights[i]) || heights[i] <= 0 || !Double.isFinite(weights[i]) || weights[i] < 0) {
                throw new IllegalArgumentException("Each height must be positive and each weight non-negative");
            }
            double bmi = weights[i] / (heights[i] * heights[i]);
            System.out.printf(Locale.ROOT, "Person %d | %.2f | %.2f | %.2f | %s%n",
                i + 1, heights[i], weights[i], bmi, getBmiStatus(bmi));
        }
    }

    public static char findFirstNonRepeatingChar(String text) {
        requireText(text);
        int[] frequency = new int[Character.MAX_VALUE + 1];
        for (int i = 0; i < text.length(); i++) frequency[text.charAt(i)]++;
        for (int i = 0; i < text.length(); i++) {
            if (frequency[text.charAt(i)] == 1) return text.charAt(i);
        }
        return '\0';
    }

    public static String reverseCustomerName(String customerName) {
        requireText(customerName);
        StringBuilder reversed = new StringBuilder(customerName.length());
        for (int i = customerName.length() - 1; i >= 0; i--) reversed.append(customerName.charAt(i));
        return reversed.toString();
    }

    private static void requireText(String text) {
        if (text == null) throw new IllegalArgumentException("Text cannot be null");
    }

    public static void main(String[] args) {
        System.out.println(playRound("Rock", "Scissors"));
        System.out.println("Palindrome: " + isPalindromeIterative("madam") + " / "
            + isPalindromeRecursive("madam") + " / " + isPalindromeArrayReversal("madam"));
        printWellnessReport(new double[] {1.75, 1.60}, new double[] {70, 90});
        System.out.println("First unique: " + findFirstNonRepeatingChar("swiss"));
        System.out.println("Sunil -> " + reverseCustomerName("Sunil"));
    }
}
