# 3Sum

**Date added:** 2026-06-12

## Problem Description

Given an integer array `nums`, return all the triplets `[nums[i], nums[j], nums[k]]` such
that `i != j`, `i != k`, and `j != k`, and `nums[i] + nums[j] + nums[k] == 0`.

The solution set must not contain duplicate triplets.

**Source:** https://leetcode.com/problems/3sum/

## Examples

**Example 1**
```
Input: nums = [-1,0,1,2,-1,-4]
Output: [[-1,-1,2],[-1,0,1]]
Explanation: The distinct triplets that sum to 0 are [-1,-1,2] and [-1,0,1].
             The order of the output and the order within each triplet does not matter.
```

**Example 2**
```
Input: nums = [0,1,1]
Output: []
Explanation: The only possible triplet (0+1+1=2) does not sum to 0.
```

**Example 3**
```
Input: nums = [0,0,0]
Output: [[0,0,0]]
Explanation: The only triplet sums to 0.
```

**Example 4**
```
Input: nums = [-2,0,0,2,2]
Output: [[-2,0,2]]
Explanation: [-2,0,2] is the only unique triplet that sums to 0; duplicates are skipped.
```

**Example 5**
```
Input: nums = [-4,-1,-1,0,1,2]
Output: [[-1,-1,2],[-1,0,1]]
Explanation: Two distinct triplets sum to 0.
```

## Constraints

- `3 <= nums.length <= 3000`
- `-10^5 <= nums[i] <= 10^5`

## Hints

1. A brute-force approach checks every possible triple — what is its time complexity, and can you do better?
2. Sorting the array first opens up efficient techniques that exploit order.
3. After fixing one element, can you reduce the remaining problem to finding two numbers that sum to a specific target?
4. With the array sorted, two pointers starting at opposite ends of the remaining subarray can find all valid pairs in linear time.
5. To avoid duplicate triplets, think about when to skip over elements that are the same as the one you just processed — both for the fixed element and for the two pointers.
