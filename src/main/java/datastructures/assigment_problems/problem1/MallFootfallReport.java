package datastructures.assigment_problems.problem1;

import java.util.ArrayList;
import java.util.List;

/*
 * Naive: re-add each range -> O(n * q) for q queries.
 * Optimized: prefix sums, prefix[i] = visitors[0..i-1]; sum(start..end) = prefix[end+1] - prefix[start].
 * Preprocessing O(n) time + O(n) space, then O(1) per query -> O(n + q) overall.
 * Worth it whenever there are many queries on the same data (q large), as on a busy day.
 */
public class MallFootfallReport {

    static List<Long> footfallReport(int[] visitors, int[][] queries) {
        long[] prefix = new long[visitors.length + 1];
        for (int i = 0; i < visitors.length; i++) {
            prefix[i + 1] = prefix[i] + visitors[i];
        }
        List<Long> result = new ArrayList<>();
        for (int[] q : queries) {
            result.add(prefix[q[1] + 1] - prefix[q[0]]);
        }
        return result;
    }

    public static void main(String[] args) {
        int[] visitors = {12, 7, 3, 9, 15, 4, 8};
        int[][] queries = {{0, 2}, {2, 5}, {4, 6}, {3, 3}};
        System.out.println(footfallReport(visitors, queries)); // [22, 31, 27, 9]
    }
}
