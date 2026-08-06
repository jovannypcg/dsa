package mx.jovannypcg.base.p34_productofarrayexceptself;

/**
 * Given an integer array {@code nums}, return an array {@code answer} such that
 * {@code answer[i]} is equal to the product of all the elements of {@code nums} except
 * {@code nums[i]}.
 *
 * <p>The product of any prefix or suffix of {@code nums} is guaranteed to fit in a 32-bit
 * integer.
 *
 * <p>The algorithm must run in O(n) time and must not use the division operation.
 *
 * @see <a href="https://leetcode.com/problems/product-of-array-except-self">Product of Array Except Self - LeetCode</a>
 */
public class Solution {
    public int[] productExceptSelf(int[] nums) {
        int acc = 1;
        int n = nums.length;
        int[] answer = new int[n];
        answer[0] = 1;

        for (int i = 1; i < n; i++) {
            answer[i] = answer[i - 1] * nums[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            answer[i] *= acc;
            acc *= nums[i];
        }

        return answer;
    }

    public int[] productExceptSelfExtraSpace(int[] nums) {
        int[] prefix = getPrefix(nums);
        int[] suffix = getSuffix(nums);
        int[] output = new int[nums.length];

        for (int i = 0; i < nums.length; i++) {
            output[i] = prefix[i] * suffix[i];
        }

        return output;
    }

    int[] getPrefix(int[] nums) {
        int n = nums.length;
        int[] output = new int[n];
        output[0] = 1;

        for (int i = 1; i < n; i++) {
            // Note to self: `output[i - 1]` is the accumulator in this approach
            output[i] = output[i - 1] * nums[i - 1];
        }

        return output;
    }

    int[] getSuffix(int[] nums) {
        int n = nums.length;
        int[] output = new int[n];
        output[n - 1] = 1;

        for (int i = n - 2; i >= 0; i--) {
            // Note to self: `output[i + 1]` is the accumulator in this approach
            output[i] = output[i + 1] * nums[i + 1];
        }

        return output;
    }
}
