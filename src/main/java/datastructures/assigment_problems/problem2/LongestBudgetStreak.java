package datastructures.assigment_problems.problem2;

/*
 * Brute force: every run -> O(n^2) (O(n^3) if each sum is recomputed).
 * Variable sliding window: O(n) time, O(1) space - each index enters and leaves the window once.
 * Non-negative costs are essential: extending the window can only increase the sum and shrinking can
 * only decrease it, so moving 'left' forward when over budget never skips a valid answer.
 */
public class LongestBudgetStreak {

    static String longestStreak(int[] costs, long budget) {
        int bestLen = 0;
        int bestStart = -1;
        long sum = 0;
        int left = 0;
        for (int right = 0; right < costs.length; right++) {
            sum += costs[right];
            while (sum > budget && left <= right) {
                sum -= costs[left++];
            }
            int len = right - left + 1;
            if (len > bestLen) { // strict '>' keeps the earliest start on ties
                bestLen = len;
                bestStart = left;
            }
        }
        return "(" + bestLen + ", " + bestStart + ")";
    }

    public static void main(String[] args) {
        System.out.println(longestStreak(new int[]{4, 2, 1, 7, 3, 1, 2, 1, 5}, 8)); // (4, 4)
        System.out.println(longestStreak(new int[]{9, 10}, 8));                     // (0, -1)
    }
}
