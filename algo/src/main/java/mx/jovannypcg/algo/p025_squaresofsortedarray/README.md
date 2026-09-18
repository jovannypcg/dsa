# Squares of a Sorted Array

**Date added:** 2026-07-11

## Problem Description

Given an integer array `nums` sorted in non-decreasing order, return an array of the squares of each number sorted in non-decreasing order.

**Source:** https://leetcode.com/problems/squares-of-a-sorted-array/

## Examples

**Example 1**
```
Input: nums = [-4,-1,0,3,10]
Output: [0,1,9,16,100]
Explanation: After squaring, the array becomes [16,1,0,9,100]. After sorting, it becomes [0,1,9,16,100].
```

**Example 2**
```
Input: nums = [-7,-3,2,3,11]
Output: [4,9,9,49,121]
Explanation: After squaring, the array becomes [49,9,4,9,121]. After sorting, it becomes [4,9,9,49,121].
```

**Example 3**
```
Input: nums = [-5,-3,-2,-1]
Output: [1,4,9,25]
Explanation: All elements are negative, so squaring reverses their relative order before sorting.
```

**Example 4**
```
Input: nums = [0]
Output: [0]
Explanation: A single element squares to itself.
```

**Example 5**
```
Input: nums = [1,2,3,4]
Output: [1,4,9,16]
Explanation: All elements are non-negative, so squaring preserves their relative order.
```

## Constraints

- `1 <= nums.length <= 10^4`
- `-10^4 <= nums[i] <= 10^4`
- `nums` is sorted in non-decreasing order.

## Hints

1. A brute-force approach would square every element and then sort the result — what is its time complexity?
2. The array is already sorted, but negative numbers "flip" order once squared. Where in the array can the largest squared values occur?
3. The largest squared values can only come from the two ends of the array (the most negative or the most positive numbers).
4. Consider placing two pointers at the start and end of `nums`, comparing the absolute values at each pointer.
5. Fill the result array from the back (largest to smallest), moving whichever pointer has the larger absolute value inward.
