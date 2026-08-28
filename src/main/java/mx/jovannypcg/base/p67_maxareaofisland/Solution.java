package mx.jovannypcg.base.p67_maxareaofisland;

/**
 * You are given an {@code m x n} binary matrix {@code grid}. An island is a group of 1's
 * (representing land) connected 4-directionally (horizontal or vertical). You may assume
 * all four edges of the grid are surrounded by water.
 *
 * <p>The area of an island is the number of cells with a value 1 in the island.
 *
 * <p>Return the maximum area of an island in {@code grid}. If there is no island, return 0.
 *
 * @see <a href="https://leetcode.com/problems/max-area-of-island/description/">Max Area of Island - LeetCode</a>
 */
public class Solution {
    private static final int[][] directions = new int[][]{
                 {-1, 0},
        {0, -1},          {0, 1},
                 {1, 0},
    };

    public int maxAreaOfIsland(int[][] grid) {
        int m = grid.length,
            n = grid[0].length,
            maxArea = 0;

        for (int row = 0; row < m; row++) {
            for (int col = 0; col < n; col++) {
                if (!isLand(grid, row, col)) continue;

                int area = getArea(grid, row, col);
                maxArea = Math.max(maxArea, area);
            }
        }

        return maxArea;
    }

    private int getArea(int[][] grid, int row, int col) {
        if (outOfBounds(grid, row, col)) return 0;
        if (!isLand(grid, row, col)) return 0;

        int area = 1;
        sink(grid, row, col);

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            area += getArea(grid, newRow, newCol);
        }

        return area;
    }

    private void sink(int[][] grid, int row, int col) {
        grid[row][col] = 0;
    }

    private boolean isLand(int[][] grid, int row, int col) {
        return !outOfBounds(grid, row, col) && grid[row][col] != 0;
    }

    private boolean outOfBounds(int[][] grid, int row, int col) {
        return row < 0 || row >= grid.length || col < 0 || col >= grid[0].length;
    }
}
