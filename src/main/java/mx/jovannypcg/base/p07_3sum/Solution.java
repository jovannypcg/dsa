package mx.jovannypcg.base.p07_3sum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Given an integer array {@code nums}, return all the triplets
 * {@code [nums[i], nums[j], nums[k]]} such that {@code i != j}, {@code i != k},
 * and {@code j != k}, and {@code nums[i] + nums[j] + nums[k] == 0}.
 *
 * <p>The solution set must not contain duplicate triplets.
 *
 * @see <a href="https://leetcode.com/problems/3sum/">3Sum - LeetCode</a>
 */
public class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];

                if (sum == 0) {
                    result.add(List.of(nums[i], nums[left], nums[right]));
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    while (left < right && nums[right] == nums[right - 1]) right--;
                    left++;
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }

        return result;
    }
}
