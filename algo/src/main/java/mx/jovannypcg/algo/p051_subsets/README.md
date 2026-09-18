# Subsets

**Date added:** 2026-08-18

## Problem Description

Given an integer array `nums` of unique elements, return all possible subsets (the power set). The solution set must not contain duplicate subsets. Return the solution in any order.

**Source:** https://leetcode.com/problems/subsets/

## Examples

**Example 1**
```
Input: nums = [1,2,3]
Output: [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
Explanation: Every combination of the elements, including the empty set and the full set, forms a distinct subset.
```

**Example 2**
```
Input: nums = [0]
Output: [[],[0]]
Explanation: A single-element array has exactly two subsets: the empty set and the set containing the element itself.
```

**Example 3**
```
Input: nums = [1,2]
Output: [[],[1],[2],[1,2]]
Explanation: With two elements there are 2^2 = 4 subsets.
```

**Example 4**
```
Input: nums = [-2,4]
Output: [[],[-2],[4],[-2,4]]
Explanation: Negative values are handled the same way as positive ones; subsets are formed purely by inclusion or exclusion of each element.
```

**Example 5**
```
Input: nums = [5,-5,10]
Output: [[],[5],[-5],[5,-5],[10],[5,10],[-5,10],[5,-5,10]]
Explanation: With three elements there are 2^3 = 8 subsets, regardless of the sign or magnitude of the values.
```

## Constraints

- `1 <= nums.length <= 10`
- `-10 <= nums[i] <= 10`
- All the numbers of `nums` are unique.

## Hints

1. Think about how many subsets exist for an array of length `n`. What power of 2 does that suggest about how each element is treated?
2. For each element, there are exactly two choices: include it in the current subset, or don't. How could that choice be modeled recursively?
3. Consider building subsets incrementally with backtracking: pick an element, recurse, then "un-pick" it before trying the next option.
4. Alternatively, think about iterating over all numbers from `0` to `2^n - 1` and using each bit of the number to decide whether the corresponding array element belongs in that subset.
5. Whichever approach you choose, make sure you add a *copy* of your current subset to the result list at the right moment — mutating a list you already added will corrupt the output.
