package datastructures.class_problems.problem2;

/*
 * Single row-major traversal of the grid.
 * Time: O(m * n)  Extra space: O(1)
 * Strict '>' keeps the FIRST maximum encountered on ties.
 */
public class WarehouseBinGridScan {

    static String warehouseSummary(int[][] grid) {
        long total = 0;
        int maxValue = Integer.MIN_VALUE;
        int maxRow = -1;
        int maxCol = -1;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                total += grid[r][c];
                if (grid[r][c] > maxValue) {
                    maxValue = grid[r][c];
                    maxRow = r;
                    maxCol = c;
                }
            }
        }
        return "total = " + total + ", maxCoordinate = (" + maxRow + ", " + maxCol + ")";
    }

    public static void main(String[] args) {
        int[][] grid = {
                {4, 9, 2},
                {7, 1, 6},
                {3, 12, 5}
        };
        System.out.println(warehouseSummary(grid)); // total = 49, maxCoordinate = (2, 1)
    }
}
