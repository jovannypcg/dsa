package mx.jovannypcg.base.p70_rottingoranges;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * You are given an {@code m x n} grid where each cell can have one of three values:
 * {@code 0} representing an empty cell, {@code 1} representing a fresh orange, or
 * {@code 2} representing a rotten orange.
 *
 * <p>Every minute, any fresh orange that is 4-directionally adjacent to a rotten orange
 * becomes rotten.
 *
 * <p>Return the minimum number of minutes that must elapse until no cell has a fresh
 * orange. If this is impossible, return {@code -1}.
 *
 * @see <a href="https://leetcode.com/problems/rotting-oranges">Problem Source</a>
 */
public class Solution {

    private static final int FRESH = 1;
    private static final int ROTTEN = 2;
    private static final int[][] directions = {
        { 0, 1 },
        { 0, -1 },
        { 1, 0 },
        { -1, 0 },
    };

    public int orangesRotting(int[][] grid) {
        Deque<int[]> queue = new ArrayDeque<>();
        int freshRemaining = 0;

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (isRotten(grid, row, col)) queue.addLast(new int[] { row, col });
                if (isFresh(grid, row, col)) freshRemaining++;
            }
        }

        int minutes = 0;

        while (!queue.isEmpty() && freshRemaining > 0) {
            minutes++;
            int levelSize = queue.size();

            for (; levelSize > 0; levelSize--) {
                int[] current = queue.removeFirst();
                int row = current[0],
                    col = current[1];

                for (int[] direction : directions) {
                    int newRow = row + direction[0];
                    int newCol = col + direction[1];

                    if (!isFresh(grid, newRow, newCol)) continue;

                    rot(grid, newRow, newCol);
                    freshRemaining--;
                    queue.addLast(new int[] { newRow, newCol });
                }
            }
        }

        return freshRemaining == 0 ? minutes : -1;
    }

    private void rot(int[][] grid, int row, int col) {
        grid[row][col] = ROTTEN;
    }

    private boolean isFresh(int[][] grid, int row, int col) {
        return !outOfBounds(grid, row, col) && grid[row][col] == FRESH;
    }

    private boolean isRotten(int[][] grid, int row, int col) {
        return !outOfBounds(grid, row, col) && grid[row][col] == ROTTEN;
    }

    private boolean outOfBounds(int[][] grid, int row, int col) {
        return (
            row < 0 || row >= grid.length || col < 0 || col >= grid[0].length
        );
    }
}
