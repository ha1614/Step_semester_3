package datastructures.class_problems.problem5;

/*
 * Naive: recompute each window -> O(n * k) time.
 * Sliding window: add the entering element, subtract the leaving one -> O(n) time, O(1) space.
 */
public class MaxSumSubarray {

    static int maxSumSubarray(int[] sales, int k) {
        if (k < 1 || k > sales.length) {
            throw new IllegalArgumentException("k must be between 1 and sales.length");
        }
        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += sales[i];
        }
        int max = windowSum;
        for (int i = k; i < sales.length; i++) {
            windowSum += sales[i] - sales[i - k];
            max = Math.max(max, windowSum);
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(maxSumSubarray(new int[]{2, 1, 5, 1, 3, 2}, 3)); // 9
    }
}
