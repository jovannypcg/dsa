package mx.jovannypcg.base.p06_twosum2;

/**
 * Given a 1-indexed array of integers {@code numbers} sorted in non-decreasing order,
 * find two numbers such that they add up to a specific {@code target}. Return the
 * 1-based indices as an array {@code [index1, index2]}.
 *
 * <p>The solution must use only constant extra space.
 *
 * @see <a href="https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/">Two Sum II - LeetCode</a>
 */
public class Solution {
    public int[] twoSum(int[] numbers, int target) {
        int n = numbers.length;
        int left = 0;
        int right = n - 1;

        while (left < right) {
            int sum = numbers[left] + numbers[right];

            if (sum == target) {
                return new int[]{left + 1, right + 1};
            }

            if (sum > target) {
                right--;
            }

            if (sum < target) {
                left++;
            }
        }

        return null;
    }
}
