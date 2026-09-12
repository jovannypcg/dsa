package mx.jovannypcg.base.p80_spiralmatrixii;

/**
 * Given a positive integer {@code n}, generate an {@code n x n} matrix filled with elements
 * from 1 to n<sup>2</sup> in spiral order.
 *
 * @see <a href="https://leetcode.com/problems/spiral-matrix-ii/description/">Spiral Matrix II - LeetCode</a>
 */
public class Solution {
    public int[][] generateMatrix(int n) {
        if (n <= 0) throw new IllegalArgumentException("n must be greater than 0");

        int[][] matrix = new int[n][n];

        int item = 1,
            left = 0, right = n - 1,
            top = 0, bottom = n - 1;

        while (left <= right && top <= bottom) {
            for (int i = left; i <= right; i++) matrix[top][i] = item++;
            for (int i = top + 1; i <= bottom; i++) matrix[i][right] = item++;
            for (int i = right - 1; i >= left; i--) matrix[bottom][i] = item++;
            for (int i = bottom - 1; i > top; i--) matrix[i][left] = item++;

            left++;
            right--;
            top++;
            bottom--;
        }

        return matrix;
    }
}
