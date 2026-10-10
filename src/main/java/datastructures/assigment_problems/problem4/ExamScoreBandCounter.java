package datastructures.assigment_problems.problem4;

/*
 * Linear scan: O(n) time, O(1) space.
 * Two boundary binary searches: O(log n) time, O(1) space.
 * Duplicates: a plain binary search may stop on ANY copy of low/high. To count every copy we must find
 * the FIRST index with score >= low (lower bound) and the FIRST index with score > high (upper bound),
 * continuing the search past a match instead of returning on it.
 */
public class ExamScoreBandCounter {

    // first index i such that scores[i] >= value (or scores.length)
    private static int lowerBound(int[] scores, int value) {
        int lo = 0;
        int hi = scores.length;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (scores[mid] < value) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }

    // first index i such that scores[i] > value (or scores.length)
    private static int upperBound(int[] scores, int value) {
        int lo = 0;
        int hi = scores.length;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (scores[mid] <= value) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }

    static int countInBand(int[] scores, int low, int high) {
        return upperBound(scores, high) - lowerBound(scores, low);
    }

    public static void main(String[] args) {
        int[] scores = {35, 42, 42, 50, 58, 58, 58, 63, 71, 88};
        System.out.println(countInBand(scores, 42, 58));  // 6
        System.out.println(countInBand(scores, 90, 100)); // 0
    }
}
