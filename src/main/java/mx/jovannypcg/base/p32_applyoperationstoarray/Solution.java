package mx.jovannypcg.base.p32_applyoperationstoarray;

/**
 * You are given a 0-indexed array {@code nums} of size {@code n} consisting of
 * non-negative integers.
 *
 * <p>You need to apply {@code n - 1} operations to this array where, in the {@code i}th
 * operation (0-indexed), you will apply the following on the {@code i}th element of
 * {@code nums}:
 *
 * <p>If {@code nums[i] == nums[i + 1]}, then multiply {@code nums[i]} by 2 and set
 * {@code nums[i + 1]} to 0. Otherwise, you skip this operation.
 *
 * <p>After performing all the operations, shift all the 0's to the end of the array.
 *
 * <p>The operations are applied sequentially, not all at once.
 *
 * @see <a href="https://leetcode.com/problems/apply-operations-to-an-array/">Apply Operations to an Array - LeetCode</a>
 */
public class Solution {

    public int[] applyOperations(int[] nums) {
        if (nums == null || nums.length == 0) return nums;

        int n = nums.length,
            zeroIdx = 0,
            nonZeroIdx = 0;

        for (int i = 0; i < n - 1; i++) {
            if (nums[i] != nums[i + 1]) continue;

            nums[i] *= 2;
            nums[i + 1] = 0;
        }

        // Shift all the 0's to the end

        int insertPos = 0;

        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) continue;

            int tmp = nums[insertPos];
            nums[insertPos] = nums[i];
            nums[i] = tmp;

            insertPos++;
        }

        return nums;
    }
}
