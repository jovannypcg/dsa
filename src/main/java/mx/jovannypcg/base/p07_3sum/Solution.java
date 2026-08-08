package mx.jovannypcg.base.p07_3sum;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

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
        if (nums == null || nums.length < 3) return List.of();

        int n = nums.length;
        List<List<Integer>> triplets = new ArrayList<>();

        Arrays.sort(nums);

        for (int i = 0; i < n; i++) {
            int left = i + 1,
                right = n - 1;

            while (left < right) {

                int sum = nums[i] + nums[left] + nums[right];

                if (sum == 0) {
                    triplets.add(List.of(nums[i], nums[left], nums[right]));
                    left++;
                    right--;

                    while (left < right && nums[left] == nums[left - 1]) left++;
                    while (left < right && nums[right] == nums[right + 1]) right--;
                } else if (sum > 0) {
                    right--;
                } else {
                    left++;
                }
            }

            while (i < n - 1 && nums[i] == nums[i + 1]) i++;
        }

        return triplets;
    }
}
