package mx.jovannypcg.base.p79_spiralmatrix;

import java.util.ArrayList;
import java.util.List;

/**
 * Given an {@code m x n} matrix, return all elements of the matrix in spiral order.
 *
 * @see <a href="https://leetcode.com/problems/spiral-matrix/description/">Spiral Matrix - LeetCode</a>
 */
public class Solution {

    public List<Integer> spiralOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
            return List.of();

        List<Integer> traversal = new ArrayList<>();

        int m = matrix.length,
            n = matrix[0].length,
            left = 0,
            right = n - 1,
            top = 0,
            bottom = m - 1;

        while (top <= bottom && left <= right) {
            for (int i = left; i <= right; i++)
                traversal.add(matrix[top][i]);

            for (int i = top + 1; i <= bottom; i++)
                traversal.add(matrix[i][right]);

            if (top < bottom) {
                for (int i = right - 1; i >= left; i--)
                    traversal.add(matrix[bottom][i]);
            }

            if (left < right) {
                for (int i = bottom - 1; i > top; i--)
                    traversal.add(matrix[i][left]);
            }

            left++;
            right--;
            top++;
            bottom--;
        }

        return traversal;
    }
}
