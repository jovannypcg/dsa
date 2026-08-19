package mx.jovannypcg.base.p53_combinationsum2;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * Given a collection of candidate numbers {@code candidates} and a target number {@code target},
 * find all unique combinations in {@code candidates} where the candidate numbers sum to
 * {@code target}.
 *
 * <p>Each number in {@code candidates} may only be used once in the combination. The solution
 * set must not contain duplicate combinations.
 *
 * @see <a href="https://leetcode.com/problems/combination-sum-ii">Combination Sum II - LeetCode</a>
 */
public class Solution {

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> combinations = new ArrayList<>();
        Deque<Integer> combination = new ArrayDeque<>();

        Arrays.sort(candidates);

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
        if (sum == target) {
            combinations.add(new ArrayList<>(combination));
            return;
        }

        if (sum > target || idx >= candidates.length) return;

        int candidate = candidates[idx];
        combination.addLast(candidate);
        sum += candidate;

        backtracking(
            candidates,
            target,
            combinations,
            combination,
            sum,
            idx + 1
        );

        sum -= candidate;
        combination.removeLast();

        while (idx < candidates.length && candidates[idx] == candidate) idx++;

        backtracking(candidates, target, combinations, combination, sum, idx);
    }
}
