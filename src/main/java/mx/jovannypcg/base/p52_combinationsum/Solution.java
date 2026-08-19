package mx.jovannypcg.base.p52_combinationsum;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Given an array of distinct integers {@code candidates} and a target integer {@code target},
 * return a list of all unique combinations of {@code candidates} where the chosen numbers sum
 * to {@code target}.
 *
 * <p>The same number may be chosen from {@code candidates} an unlimited number of times. Two
 * combinations are unique if the frequency of at least one of the chosen numbers is different.
 *
 * @see <a href="https://leetcode.com/problems/combination-sum/">Combination Sum - LeetCode</a>
 */
public class Solution {

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> combinations = new ArrayList<>();
        Deque<Integer> combination = new ArrayDeque<>();

        backtracking(candidates, target, combinations, combination, 0, 0);

        return combinations;
    }

    void backtracking(
        int[] candidates,
        int target,
        List<List<Integer>> combinations,
        Deque<Integer> combination,
        int sum,
        int idx
    ) {
        if (idx >= candidates.length) return;

        if (sum == target) {
            // A combination was found, record it.
            //
            // No need to try other candidates: they are unique, so
            // summing up another one would result in a different number.
            combinations.add(new ArrayList<>(combination));
        } else if (sum > target) {
            return;
        } else {
            combination.addLast(candidates[idx]);
            sum += candidates[idx];

            // Keep trying with the same candidate (idx untouched)
            backtracking(candidates, target, combinations, combination, sum, idx);

            // Pop the last item if the same candidate doesn't work out,
            // and try with the next candidate (`idx + 1`).
            sum -= combination.removeLast();
            backtracking(candidates, target, combinations, combination, sum, idx + 1);
        }
    }
}
