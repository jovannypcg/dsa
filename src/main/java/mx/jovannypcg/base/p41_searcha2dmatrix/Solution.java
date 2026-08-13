package mx.jovannypcg.base.p41_searcha2dmatrix;

/**
 * You are given an {@code m x n} integer matrix {@code matrix} with the following two properties:
 *
 * <ul>
 *     <li>Each row is sorted in non-decreasing order.</li>
 *     <li>The first integer of each row is greater than the last integer of the previous row.</li>
 * </ul>
 *
 * <p>Given an integer {@code target}, return {@code true} if {@code target} is in {@code matrix}
 * or {@code false} otherwise.
 *
 * <p>The solution must run in {@code O(log(m * n))} time complexity.
 *
 * @see <a href="https://leetcode.com/problems/search-a-2d-matrix/">Search a 2D Matrix - LeetCode</a>
 */
public class Solution {

    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) return false;

        int m = matrix.length,
            n = matrix[0].length,
            left = 0,
            right = (m * n) - 1;

        while (left <= right) {
            /*
            Note to self:

            Treat the 2D matrix as if it were a single flattened, sorted array,
            then run ordinary binary search over it. The only extra step is
            converting the linear `mid` index back into (row, col):

                row = mid / n   (n = number of columns)
                col = mid % n

            Why `/` for row? Every row holds exactly `n` elements, so the rows
            occupy linear index ranges [0, n), [n, 2n), [2n, 3n), etc. Dividing
            by `n` tells you how many full rows of size `n` fit before `mid`,
            which is exactly the row `mid` falls into.

            Why `%` for col? Once you know which row you're in, the remainder
            of that division is simply how far past the start of that row
            `mid` landed, i.e. the column offset.

            Example: a 3 x 4 matrix (n = 4) has linear indices 0..11.
            Index 11 (the last element):
                row = 11 / 4 = 2
                col = 11 % 4 = 3
            Indices 0-3 map to row 0, 4-7 to row 1, 8-11 to row 2 — dividing by
            `n` picks the row "block", and the remainder picks the offset
            within it.
            */
            int mid = (left + right) / 2,
                row = mid / n,
                col = mid % n;

            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return false;
    }
}
