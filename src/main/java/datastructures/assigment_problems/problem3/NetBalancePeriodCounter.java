package datastructures.assigment_problems.problem3;

import java.util.HashMap;
import java.util.Map;

/*
 * Brute force: sum every subarray -> O(n^2) time (O(n^3) if recomputed from scratch).
 * Prefix sum + hash map of how often each running total has appeared: O(n) time, O(n) space.
 * A period ending at j sums to k exactly when an earlier running total equals (S - k).
 */
public class NetBalancePeriodCounter {

    static long countPeriods(int[] transactions, long k) {
        Map<Long, Integer> seen = new HashMap<>();
        seen.put(0L, 1);
        long running = 0;
        long count = 0;
        for (int t : transactions) {
            running += t;
            count += seen.getOrDefault(running - k, 0);
            seen.merge(running, 1, Integer::sum);
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(countPeriods(new int[]{3, 4, -7, 1, 3, 3, 1, -4}, 7)); // 4
        System.out.println(countPeriods(new int[]{1, 2, 3}, 10));                 // 0
    }
}
