# Sort Array by Parity

**Date added:** 2026-07-13

## Problem Description

Given an integer array `nums`, move all the even integers at the beginning of the array followed by all the odd integers. Return any array that satisfies this condition — the relative order within the evens or within the odds does not matter.

**Source:** https://leetcode.com/problems/sort-array-by-parity

## Examples

**Example 1**
```
Input: nums = [3,1,2,4]
Output: [2,4,3,1]
Explanation: The outputs [4,2,3,1], [2,4,1,3], and [4,2,1,3] would also be accepted.
```

**Example 2**
```
Input: nums = [0]
Output: [0]
Explanation: A single element already satisfies the condition.
```

**Example 3**
```
Input: nums = [2,4,6]
Output: [2,4,6]
Explanation: All elements are already even, so no reordering is needed.
```

**Example 4**
```
Input: nums = [1,3,5]
Output: [1,3,5]
Explanation: All elements are odd, so any order (including the original) satisfies the condition since there are no evens to place first.
```

**Example 5**
```
Input: nums = [1,2]
Output: [2,1]
Explanation: The even value 2 must come before the odd value 1.
```

## Constraints

- `1 <= nums.length <= 5000`
- `0 <= nums[i] <= 5000`

## Hints

1. You don't need to fully sort the array — you only need to separate two categories.
2. Think about how partitioning works in quicksort, where elements are grouped by a condition rather than fully ordered.
3. Two pointers, one starting from the left and one from the right, can help you swap out-of-place elements in a single pass.
4. Advance the left pointer while it points at even numbers, and the right pointer while it points at odd numbers.
5. When the left pointer lands on an odd number and the right pointer lands on an even number, swap them and continue.
