# Subsets II

**Date added:** 2026-08-21

## Problem Description

Given an integer array `nums` that may contain duplicates, return all possible subsets (the power set).

The solution set must not contain duplicate subsets. Return the solution in any order.

**Source:** https://leetcode.com/problems/subsets-ii

## Examples

**Example 1**
```
Input: nums = [1,2,2]
Output: [[],[1],[1,2],[1,2,2],[2],[2,2]]
Explanation: There are 8 subsets of a 3-element array in general, but [1,2] and [2,1,2]-style duplicates collapse together, and the two possible "pick one 2" subsets both become [2]. Only 6 distinct subsets remain.
```

**Example 2**
```
Input: nums = [0]
Output: [[],[0]]
Explanation: A single-element array with no duplicates has exactly two subsets: the empty set and the set containing that element.
```

**Example 3**
```
Input: nums = [4,4,4]
Output: [[],[4],[4,4],[4,4,4]]
Explanation: All three elements are identical, so the only distinguishing factor between subsets is how many 4s they contain: zero, one, two, or three.
```

**Example 4**
```
Input: nums = [1,2,3]
Output: [[],[1],[1,2],[1,2,3],[1,3],[2],[2,3],[3]]
Explanation: No duplicates exist in the input, so this reduces to the plain power set: all 8 possible subsets are distinct.
```

**Example 5**
```
Input: nums = [-1,0,-1]
Output: [[],[-1],[-1,-1],[-1,-1,0],[-1,0],[0]]
Explanation: Negative values behave the same as positive ones. Sorting first groups the two -1s together so duplicate subsets like [-1,0] formed from either -1 are only counted once.
```

## Constraints

- `1 <= nums.length <= 10`
- `-10 <= nums[i] <= 10`

## Hints

1. Start from the plain Subsets (no duplicates) approach — every subset corresponds to a choice of "include or exclude" for each element.
2. Sorting `nums` first groups equal values next to each other, which makes duplicates easy to detect during the search.
3. Think of the search as trying, at each position, how many copies of the *next distinct value* to include (0, 1, 2, ...) rather than a simple include/exclude branch.
4. If you instead do a straightforward include/exclude backtrack, you need a rule that skips a candidate at a given recursion depth if it's equal to the candidate right before it *and* the previous one wasn't included at this depth.
5. A `Set` of serialized subsets can also filter duplicates after generating everything, but it does more work than necessary — pruning during the search avoids generating the duplicates in the first place.
