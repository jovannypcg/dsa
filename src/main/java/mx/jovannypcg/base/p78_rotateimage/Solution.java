package mx.jovannypcg.base.p78_rotateimage;

/**
 * You are given an {@code n x n} 2D matrix representing an image, rotate the image by 90
 * degrees (clockwise).
 *
 * <p>You have to rotate the image in-place, which means you have to modify the input 2D
 * matrix directly. Do not allocate another 2D matrix and do the rotation.
 *
 * @see <a href="https://leetcode.com/problems/rotate-image/">Rotate Image - LeetCode</a>
 */
public class Solution {
    public void rotate(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return;

        transpose(matrix);
        reverseRows(matrix);
    }

    void transpose(int[][] matrix) {
        int n = matrix.length;

        for (int row = 0; row < n; row++) {
            for (int col = row + 1; col < n; col++) {
                swap(matrix, col, row);
            }
        }
    }

    void reverseRows(int[][] matrix) {
        int n = matrix.length;

        for (int row = 0; row < n; row++) {
            for (int left = 0, right = n - 1; left < right; left++, right--) {
                int tmp = matrix[row][left];
                matrix[row][left] = matrix[row][right];
                matrix[row][right] = tmp;
            }
        }
    }

    void swap(int[][] matrix, int row, int col) {
        int tmp = matrix[row][col];
        matrix[row][col] = matrix[col][row];
        matrix[col][row] = tmp;
    }
}
