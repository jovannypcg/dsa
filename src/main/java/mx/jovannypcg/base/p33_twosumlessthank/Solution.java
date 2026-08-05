package mx.jovannypcg.base.p33_twosumlessthank;

import java.util.Arrays;

/**
 * Given an array of integers {@code nums} and an integer {@code k}, return the maximum sum
 * of two distinct elements from {@code nums} such that the sum is strictly less than {@code k}.
 *
 * <p>If no such pair exists, return {@code -1}.
 *
 * @see <a href="https://leetcode.com/problems/two-sum-less-than-k/">Two Sum Less Than K - LeetCode</a>
 */
public class Solution {
    public int twoSumLessThanK(int[] nums, int k) {
        int n = nums.length,
            left = 0,
            right = n - 1,
            maxSum = -1;

        Arrays.sort(nums);

        while (left < right) {
            int sum = nums[left] + nums[right];

            if (sum >= k) {
                right--;
            } else {
                maxSum = Math.max(maxSum, sum);
                left++;
            }
        }

        return maxSum;
    }
}
