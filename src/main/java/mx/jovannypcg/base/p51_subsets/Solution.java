package mx.jovannypcg.base.p51_subsets;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Given an integer array {@code nums} of unique elements, return all possible subsets
 * (the power set).
 *
 * <p>The solution set must not contain duplicate subsets. The subsets may be returned
 * in any order.
 *
 * @see <a href="https://leetcode.com/problems/subsets/">Subsets - LeetCode</a>
 */
public class Solution {

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> out = new ArrayList<>();
        Deque<Integer> subset = new ArrayDeque<>();

        dfs(nums, out, subset, 0);

        return out;
    }

    /*
    nums = [1, 2, 3]

    out = [
        [1, 2, 3],
        [1, 2],
        [1, 3],
        [1],
        [2, 3],
        [2],
        [3],
        []
    ]

    dfs([1, 2, 3], [], [], 0)
        dfs([1, 2, 3], [], [1], 1)
            dfs([1, 2, 3], [], [1, 2], 2)
                dfs([1, 2, 3], [], [1, 2, 3], 3) => [1, 2, 3]
                dfs([1, 2, 3], [], [1, 2], 3) => [1, 2]
            dfs([1, 2, 3], [], [1], 2)
                dfs([1, 2, 3], [], [1, 3], 3) => [1, 3]
                dfs([1, 2, 3], [], [1], 3) => [1]
        dfs([1, 2, 3], [], [], 1)
            dfs([1, 2, 3], [], [2], 2)
                dfs([1, 2, 3], [], [2, 3], 3) => [2, 3]
                dfs([1, 2, 3], [], [2], 3) => [2]
            dfs([1, 2, 3], [], [], 2)
                dfs([1, 2, 3], [], [3], 3) => [3]
                dfs([1, 2, 3], [], [], 3) => []
    */
    private void dfs(
        int[] nums,
        List<List<Integer>> out,
        Deque<Integer> subset,
        int idx
    ) {
        if (idx >= nums.length) {
            out.add(new ArrayList<>(subset));
            return;
        }

        subset.addLast(nums[idx]);
        dfs(nums, out, subset, idx + 1);

        subset.removeLast();
        dfs(nums, out, subset, idx + 1);
    }
}
