# Two Sum Less Than K

**Date added:** 2026-08-04

## Problem Description

Given an array of integers `nums` and an integer `k`, return the maximum sum of two distinct elements from `nums` such that the sum is strictly less than `k`. If no such pair exists, return `-1`.

**Source:** https://leetcode.com/problems/two-sum-less-than-k/

## Examples

**Example 1**
```
Input: nums = [34, 23, 1, 24, 75, 33, 54, 8], k = 60
Output: 58
Explanation: We can use 34 and 24 to sum 58 which is less than 60.
```

**Example 2**
```
Input: nums = [10, 20, 30], k = 15
Output: -1
Explanation: In this case it is not possible to get a pair sum less than 15.
```

**Example 3**
```
Input: nums = [1, 2, 3, 4], k = 6
Output: 5
Explanation: 2 + 3 = 5, which is the largest sum strictly less than 6.
```

**Example 4**
```
Input: nums = [5, 5], k = 11
Output: 10
Explanation: The two 5s sum to 10, which is less than 11. Duplicate values are allowed as long as they occupy distinct positions.
```

**Example 5**
```
Input: nums = [1, 1, 1], k = 2
Output: -1
Explanation: Every pair sums to 2, which is not strictly less than k.
```

## Constraints

- `1 <= nums.length <= 100`
- `1 <= nums[i] <= 1000`
- `1 <= k <= 2000`

## Hints

1. A brute-force approach checks every pair — what is its time complexity?
2. Sorting the array first can help you avoid checking every pair.
3. After sorting, think about what happens when you look at the smallest and largest remaining elements together.
4. If the sum of the two ends is too large, which pointer should move? If it's small enough, could a better pair still exist?
5. Use two pointers starting at both ends of the sorted array, moving inward while tracking the best valid sum you've seen.
