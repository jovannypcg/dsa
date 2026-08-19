# Combination Sum

**Date added:** 2026-08-19

## Problem Description

Given an array of distinct integers `candidates` and a target integer `target`, return a list of all unique combinations of `candidates` where the chosen numbers sum to `target`. You may return the combinations in any order.

The same number may be chosen from `candidates` an unlimited number of times. Two combinations are unique if the frequency of at least one of the chosen numbers is different.

The test cases are generated such that the number of unique combinations that sum up to `target` is less than 150 combinations for the given input.

**Source:** https://leetcode.com/problems/combination-sum

## Examples

**Example 1**
```
Input: candidates = [2,3,6,7], target = 7
Output: [[2,2,3],[7]]
Explanation: 2 and 3 are candidates, and 2 + 2 + 3 = 7. Note that 2 can be used multiple times. 7 is a candidate, and 7 = 7. These are the only two combinations.
```

**Example 2**
```
Input: candidates = [2,3,5], target = 8
Output: [[2,2,2,2],[2,3,3],[3,5]]
Explanation: These are the three unique combinations of candidates that sum to 8, each using one or more numbers with repetition allowed.
```

**Example 3**
```
Input: candidates = [2], target = 1
Output: []
Explanation: No combination of 2s can ever sum to 1, since 2 alone already exceeds the target.
```

## Constraints

- `1 <= candidates.length <= 30`
- `2 <= candidates[i] <= 40`
- All elements of `candidates` are distinct.
- `1 <= target <= 40`

## Hints

1. Think about this as choosing numbers one decision at a time, where at each step you can either use the current candidate again or move on to the next one.
2. A brute-force search over all possible combinations would explore too much — how can you prune branches early?
3. If the running sum ever exceeds the target, that branch cannot lead to a valid combination.
4. Recursion with backtracking works well here: try including a candidate, recurse, then remove it and try without it.
5. To avoid revisiting the same candidate before an earlier one (which would produce duplicate combinations in a different order), only allow the recursion to move forward through the candidates array, never backward.
