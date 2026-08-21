# Permutations

**Date added:** 2026-08-19

## Problem Description

Given an array `nums` of distinct integers, return all the possible permutations. You can return the answer in any order.

**Source:** https://leetcode.com/problems/permutations

## Examples

**Example 1**
```
Input: nums = [1,2,3]
Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
Explanation: There are 3! = 6 ways to arrange three distinct elements, and all of them are listed.
```

**Example 2**
```
Input: nums = [0,1]
Output: [[0,1],[1,0]]
Explanation: There are 2! = 2 ways to arrange two distinct elements.
```

**Example 3**
```
Input: nums = [1]
Output: [[1]]
Explanation: A single element has only one possible arrangement.
```

## Constraints

- `1 <= nums.length <= 6`
- `-10 <= nums[i] <= 10`
- All the integers of `nums` are unique.

## Hints

1. Think about building each permutation one element at a time, choosing which unused number goes next.
2. This is a natural fit for backtracking: pick a number, recurse on the rest, then undo the pick and try another.
3. You'll need a way to track which numbers have already been used in the current partial permutation.
4. When the partial permutation has the same length as `nums`, it's complete — add a copy of it to the result.
5. Remember to add a *copy* of the current permutation list, not a reference to the mutable list you're building.
