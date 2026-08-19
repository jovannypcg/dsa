# Combination Sum II

**Date added:** 2026-08-19

## Problem Description

Given a collection of candidate numbers `candidates` and a target number `target`, find all unique combinations in `candidates` where the candidate numbers sum to `target`.

Each number in `candidates` may only be used once in the combination.

**Note:** The solution set must not contain duplicate combinations.

**Source:** https://leetcode.com/problems/combination-sum-ii

## Examples

**Example 1**
```
Input: candidates = [10,1,2,7,6,1,5], target = 8
Output: [[1,1,6],[1,2,5],[1,7],[2,6]]
Explanation: Each element of candidates may be used at most once. [1,1,6] uses both 1s (indices 1 and 5). No duplicate combinations appear even though candidates contains repeated values.
```

**Example 2**
```
Input: candidates = [2,5,2,1,2], target = 5
Output: [[1,2,2],[5]]
Explanation: Three 2s exist in candidates, but only two of them combine with the 1 to reach 5. The third 2 alone, or any other grouping, does not reach the target without repeating a combination already found.
```

**Example 3**
```
Input: candidates = [1,1], target = 2
Output: [[1,1]]
Explanation: Both 1s are distinct elements (different indices), so using both once each is valid and yields exactly one combination.
```

**Example 4**
```
Input: candidates = [5], target = 3
Output: []
Explanation: No combination of the single candidate reaches the target, so the result is an empty list.
```

**Example 5**
```
Input: candidates = [3,1,3,5,1,1], target = 8
Output: [[1,1,1,5],[1,1,3,3],[3,5]]
Explanation: candidates has three 1s and two 3s. Different counts of the repeated values combine with 5 or with each other to reach 8, each counted only once in the output.
```

## Constraints

- `1 <= candidates.length <= 100`
- `1 <= candidates[i] <= 50`
- `1 <= target <= 30`

## Hints

1. This is similar to Combination Sum, but here each element can be used at most once, and candidates may contain duplicate values.
2. Sorting the array first makes both duplicate values and the "stop early" pruning easier to reason about.
3. When you skip a candidate at a given recursion depth, you must also skip any later candidate that's equal to it *at that same depth* — otherwise you'll produce duplicate combinations.
4. A candidate can still be reused across different combinations (e.g., two separate 1s at different array indices); the restriction is on using the *same array position* twice within one combination.
5. Since candidates are sorted, once the running sum plus the current candidate exceeds the target, you can break out of the loop entirely instead of continuing to the next candidate.
