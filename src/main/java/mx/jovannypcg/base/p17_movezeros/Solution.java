package mx.jovannypcg.base.p17_movezeros;

/**
 * Given an integer array {@code nums}, move all {@code 0}'s to the end of it while maintaining
 * the relative order of the non-zero elements.
 *
 * <p>The operation must be done in-place without making a copy of the array.
 *
 * @see <a href="https://neetcode.io/problems/move-zeroes">Move Zeros - NeetCode</a>
 */
public class Solution {

    public void moveZeroes(int[] nums) {
        if (nums == null) return;

        int n = nums.length;
        int slow = 0;

        for (int fast = 0; fast < n; fast++) {
            if (nums[fast] != 0) {
                nums[slow++] = nums[fast];
            }
        }

        while (slow < n) nums[slow++] = 0;
    }
}
