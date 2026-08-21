package mx.jovannypcg.base.p55_subsets2;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * Given an integer array {@code nums} that may contain duplicates, return all possible
 * subsets (the power set).
 *
 * <p>The solution set must not contain duplicate subsets. The subsets may be returned in
 * any order.
 *
 * @see <a href="https://leetcode.com/problems/subsets-ii">Subsets II - LeetCode</a>
 */
public class Solution {

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> subsets = new ArrayList<>();
        Deque<Integer> subset = new ArrayDeque<>();

        Arrays.sort(nums);

        backtracking(nums, subsets, subset, 0);

        return subsets;
    }

    void backtracking(
        int[] nums,
        List<List<Integer>> subsets,
        Deque<Integer> subset,
        int idx
    ) {
        if (idx >= nums.length) {
            subsets.add(new ArrayList<>(subset));
            return;
        }

        int num = nums[idx];

        // Decision: add current num to subset
        subset.addLast(num);
        backtracking(nums, subsets, subset, idx + 1);

        // Decision: remove current num
        // Skip duplicates
        subset.removeLast();

        while (idx < nums.length && nums[idx] == num) idx++;

        backtracking(nums, subsets, subset, idx);
    }
}
