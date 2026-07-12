package mx.jovannypcg.base.p25_squaresofsortedarray;

/**
 * Given an integer array {@code nums} sorted in non-decreasing order, return an array of the
 * squares of each number sorted in non-decreasing order.
 *
 * @see <a href="https://leetcode.com/problems/squares-of-a-sorted-array/">Squares of a Sorted Array - LeetCode</a>
 */
public class Solution {

    public int[] sortedSquares(int[] nums) {
        int n = nums.length;
        int left = 0;
        int right = n - 1;
        int[] out = new int[n];
        int outIdx = n - 1; // Fills `out` from the end

        while (left <= right) {
            if (Math.abs(nums[left]) > Math.abs(nums[right])) {
                out[outIdx] = nums[left] * nums[left];

                left++;
            } else {
                out[outIdx] = nums[right] * nums[right];

                right--;
            }

            outIdx--;
        }

        return out;
    }
}
