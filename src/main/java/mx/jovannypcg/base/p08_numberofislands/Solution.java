package mx.jovannypcg.base.p08_numberofislands;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Given an {@code m x n} 2D binary grid {@code grid} which represents a map of {@code '1'}s
 * (land) and {@code '0'}s (water), return the number of islands.
 *
 * <p>An island is surrounded by water and is formed by connecting adjacent lands horizontally
 * or vertically. You may assume all four edges of the grid are all surrounded by water.
 *
 * @see <a href="https://leetcode.com/problems/number-of-islands/">Number of Islands - LeetCode</a>
 */
public class Solution {
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int islandsCount = 0;

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] != '0') {
                    islandsCount++;
                    sinkIsland(grid, row, col);
                }
            }
        }

        return islandsCount;
    }

    void sinkIsland(char[][] grid, int row, int col) {
        int[][] directions = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};
        Deque<int[]> queue = new ArrayDeque<>();

        grid[row][col] = '0';
        queue.add(new int[]{row, col});

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();

            for (int[] dir : directions) {
                int r = cell[0] + dir[0];
                int c = cell[1] + dir[1];

                if (r >= 0 && r < grid.length && c >= 0 && c < grid[0].length && grid[r][c] == '1') {
                    grid[r][c] = '0';
                    queue.add(new int[]{r, c});
                }
            }
        }
    }
}
