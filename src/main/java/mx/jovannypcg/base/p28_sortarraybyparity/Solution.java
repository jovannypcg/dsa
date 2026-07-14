package mx.jovannypcg.base.p28_sortarraybyparity;

/**
 * Given an integer array {@code nums}, move all the even integers at the beginning of the
 * array followed by all the odd integers.
 *
 * <p>Return any array that satisfies this condition.
 *
 * @see <a href="https://leetcode.com/problems/sort-array-by-parity">Sort Array by Parity - LeetCode</a>
 */
public class Solution {

    public int[] sortArrayByParity(int[] nums) {
        int n = nums.length;
        int left = 0;
        int right = n - 1;

        while (left < right) {
            if (nums[left] % 2 == 0) {
                left++;
            } else if (nums[right] % 2 != 0) {
                right--;
            } else {
                int tmp = nums[left];
                nums[left] = nums[right];
                nums[right] = tmp;

                left++;
                right--;
            }
        }

        return nums;
    }
}
