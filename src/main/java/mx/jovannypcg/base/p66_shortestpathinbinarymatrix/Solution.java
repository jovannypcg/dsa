package mx.jovannypcg.base.p66_shortestpathinbinarymatrix;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Given an {@code n x n} binary matrix {@code grid}, return the length of the shortest clear
 * path in the matrix. If there is no clear path, return {@code -1}.
 *
 * <p>A clear path in a binary matrix is a path from the top-left cell (i.e., {@code (0, 0)}) to
 * the bottom-right cell (i.e., {@code (n - 1, n - 1)}) such that:
 * <ul>
 *   <li>All the visited cells of the path are {@code 0}.</li>
 *   <li>All the adjacent cells of the path are 8-directionally connected (i.e., they are
 *   different and they share an edge or a corner).</li>
 * </ul>
 *
 * <p>The length of a clear path is the number of visited cells of this path.
 *
 * @see <a href="https://leetcode.com/problems/shortest-path-in-binary-matrix/">Shortest Path in Binary Matrix - LeetCode</a>
 */
public class Solution {

    private static final int[][] directions = new int[][] {
        { -1, -1 }, { -1, 0 }, { -1, 1 },
        { 0, -1 },             { 0, 1 },
        { 1, -1 },  { 1, 0 },  { 1, 1 },
    };

    public int shortestPathBinaryMatrix(int[][] grid) {
        int n = grid.length;
        int target = n - 1;

        if (grid[0][0] != 0) return -1;

        Deque<int[]> queue = new ArrayDeque<>();

        queue.addLast(new int[]{0, 0});
        grid[0][0] = 1;
        int length = 1;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            while (levelSize > 0) {
                int[] cell = queue.removeFirst();

                int row = cell[0];
                int col = cell[1];

                if (row == target && col == target) return length;

                for (int[] direction : directions) {
                    int newRow = row + direction[0];
                    int newCol = col + direction[1];

                    if (outOfBounds(grid, newRow, newCol)) continue;
                    if (grid[newRow][newCol] != 0) continue;

                    queue.addLast(new int[]{newRow, newCol});

                    grid[newRow][newCol] = 1;
                }

                levelSize--;
            }

            length++;
        }

        return -1;
    }

    private boolean outOfBounds(int[][] grid, int row, int col) {
        int n = grid.length;

        return row < 0 || row >= n || col < 0 || col >= n;
    }
}
