# Remove Duplicates from Sorted Array

**Date added:** 2026-06-30

## Problem Description

Given an integer array `nums` sorted in non-decreasing order, remove duplicates in-place so
that each element appears only once. Return `k`, the number of unique elements, such that
the first `k` elements of `nums` contain all the unique elements in their original order.
Elements beyond the first `k` positions do not matter.

**Source:** https://neetcode.io/problems/remove-duplicates-from-sorted-array

## Examples

**Example 1**
```
Input: nums = [1,1,2,3,4]
Output: 4
Explanation: Four unique elements — the first 4 positions of nums become [1,2,3,4].
```

**Example 2**
```
Input: nums = [2,10,10,30,30,30]
Output: 3
Explanation: Three unique elements — the first 3 positions of nums become [2,10,30].
```

**Example 3**
```
Input: nums = [1,1,1,1]
Output: 1
Explanation: All elements are duplicates of 1; only one unique element remains.
```

**Example 4**
```
Input: nums = [1]
Output: 1
Explanation: A single-element array is already duplicate-free.
```

**Example 5**
```
Input: nums = [-3,-1,-1,0,0,5]
Output: 4
Explanation: Four unique elements — the first 4 positions of nums become [-3,-1,0,5].
```

## Constraints

- `1 <= nums.length <= 30,000`
- `-100 <= nums[i] <= 100`
- `nums` is sorted in non-decreasing order.

## Hints

1. Because the array is sorted, all duplicates of a value are grouped together — you never need to look far.
2. Think about maintaining a "write pointer" that tracks where the next unique element should be placed.
3. Walk through the array with a "read pointer"; when you encounter a value different from the last unique value you wrote, act on it.
4. The write pointer starts at index 1 — why does index 0 never need to change?
5. When the read pointer finds a new unique value, copy it to the write pointer position and advance the write pointer.
