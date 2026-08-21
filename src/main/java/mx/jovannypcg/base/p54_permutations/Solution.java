package mx.jovannypcg.base.p54_permutations;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Given an array {@code nums} of distinct integers, return all the possible permutations.
 * You can return the answer in any order.
 *
 * @see <a href="https://leetcode.com/problems/permutations/">Permutations - LeetCode</a>
 */
public class Solution {

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> permutations = new ArrayList<>();
        Set<Integer> permutation = new LinkedHashSet<>();

        backtracking(nums, permutations, permutation);

        return permutations;
    }

    /*
    nums = [1, 2, 3]
    permutations = []

    bt(num, permutation)
    bt(1, [])
    bt(2, [])
    bt(3, [])
    */
    void backtracking(
        int[] nums,
        List<List<Integer>> permutations,
        Set<Integer> permutation
    ) {
        if (permutation.size() == nums.length) {
            permutations.add(new ArrayList<>(permutation));
            return;
        }

        for (int num : nums) {
            if (!permutation.contains(num)) {
                permutation.add(num);
                backtracking(nums, permutations, permutation);
                permutation.remove(num);
            }
        }
    }
}
