package mx.jovannypcg.base.p69_islandsandtreasures;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * You are given an {@code m x n} 2D grid initialized with these three possible values:
 *
 * <ul>
 *   <li>{@code -1} - A water cell that cannot be traversed.
 *   <li>{@code 0} - A treasure chest.
 *   <li>{@code INF} - A land cell that can be traversed. We use the integer
 *       {@code 2^31 - 1 = 2147483647} to represent {@code INF}.
 * </ul>
 *
 * <p>Fill each land cell with the distance to its nearest treasure chest. If a land cell
 * cannot reach a treasure chest then its value should remain {@code INF}.
 *
 * <p>Assume the grid can only be traversed up, down, left, or right. Modify the grid in-place.
 *
 * @see <a href="https://neetcode.io/problems/islands-and-treasure">Islands and Treasures - NeetCode</a>
 */
public class Solution {

    private static final int LAND = Integer.MAX_VALUE;
    private static final int TREASURE = 0;
    private static final int[][] directions = new int[][] {
        { 1, 0 },
        { -1, 0 },
        { 0, 1 },
        { 0, -1 },
    };

    public void islandsAndTreasure(int[][] grid) {
        if (grid == null || grid.length == 0) return;

        var treasureLocations = getTreasureLocations(grid);
        var queue = new ArrayDeque<>(treasureLocations);
        int distance = 0;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            for (; levelSize > 0; levelSize--) {
                int[] current = queue.removeFirst();
                int row = current[0];
                int col = current[1];

                if (!isVisitable(grid, row, col)) continue;

                grid[row][col] = distance;

                for (int[] direction : directions) {
                    int newRow = row + direction[0];
                    int newCol = col + direction[1];

                    if (isVisitable(grid, newRow, newCol)) {
                        queue.addLast(new int[] { newRow, newCol });
                    }
                }
            }

            distance++;
        }
    }

    private boolean isVisitable(int[][] grid, int row, int col) {
        return isLand(grid, row, col);
    }

    private List<int[]> getTreasureLocations(int[][] grid) {
        List<int[]> treasureLocations = new ArrayList<>();

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == TREASURE) {
                    grid[row][col] = LAND;
                    treasureLocations.add(new int[] { row, col });
                }
            }
        }

        return treasureLocations;
    }

    private boolean isLand(int[][] grid, int row, int col) {
        return !outOfBounds(grid, row, col) && grid[row][col] == LAND;
    }

    private boolean outOfBounds(int[][] grid, int row, int col) {
        return (
            row < 0 || row >= grid.length || col < 0 || col >= grid[0].length
        );
    }
}
